/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.IdsQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexName;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexNameBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(service = DuplicateQueryStringsDetector.class)
public class DuplicateQueryStringsDetectorImpl
	implements DuplicateQueryStringsDetector {

	@Override
	public Criteria.Builder builder() {
		return new CriteriaImpl.BuilderImpl();
	}

	@Override
	public List<String> detect(Criteria criteria) {
		Collection<String> queryStrings = criteria.getQueryStrings();

		if (queryStrings.isEmpty()) {
			return Collections.emptyList();
		}

		SearchSearchResponse searchSearchResponse = searchEngineAdapter.execute(
			new SearchSearchRequest() {
				{
					RankingIndexName rankingIndexName =
						criteria.getRankingIndexName();

					setIndexNames(rankingIndexName.getIndexName());

					setQuery(_getCriteriaQuery(criteria));
					setScoreEnabled(false);
				}
			});

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		List<String> duplicateQueryStrings = new ArrayList<>();

		for (SearchHit searchHit : searchHits.getSearchHits()) {
			duplicateQueryStrings.addAll(
				_getDuplicateQueryStrings(searchHit, queryStrings));
		}

		return duplicateQueryStrings;
	}

	@Reference
	protected Queries queries;

	@Reference
	protected RankingIndexNameBuilder rankingIndexNameBuilder;

	@Reference
	protected SearchEngineAdapter searchEngineAdapter;

	protected static class CriteriaImpl implements Criteria {

		@Override
		public String getIndex() {
			return _index;
		}

		@Override
		public Collection<String> getQueryStrings() {
			return _queryStrings;
		}

		@Override
		public RankingIndexName getRankingIndexName() {
			return _rankingIndexName;
		}

		@Override
		public String getUnlessRankingDocumentId() {
			return _unlessRankingDocumentId;
		}

		protected CriteriaImpl(CriteriaImpl criteriaImpl) {
			if (criteriaImpl == null) {
				return;
			}

			_index = criteriaImpl._index;
			_queryStrings = new HashSet<>(criteriaImpl._queryStrings);
			_rankingIndexName = criteriaImpl._rankingIndexName;
			_unlessRankingDocumentId = criteriaImpl._unlessRankingDocumentId;
		}

		protected static class BuilderImpl implements Criteria.Builder {

			@Override
			public Criteria build() {
				return new CriteriaImpl(_criteriaImpl);
			}

			@Override
			public BuilderImpl index(String index) {
				_criteriaImpl._index = index;

				return this;
			}

			@Override
			public BuilderImpl queryStrings(Collection<String> queryStrings) {
				if (queryStrings == null) {
					_criteriaImpl._queryStrings = Collections.emptySet();
				}
				else {
					_criteriaImpl._queryStrings = new HashSet<>(queryStrings);
				}

				return this;
			}

			@Override
			public Builder rankingIndexName(RankingIndexName rankingIndexName) {
				_criteriaImpl._rankingIndexName = rankingIndexName;

				return this;
			}

			@Override
			public BuilderImpl unlessRankingDocumentId(
				String unlessRankingDocumentId) {

				_criteriaImpl._unlessRankingDocumentId =
					unlessRankingDocumentId;

				return this;
			}

			private final CriteriaImpl _criteriaImpl = new CriteriaImpl(null);

		}

		private String _index;
		private Collection<String> _queryStrings = new HashSet<>();
		private RankingIndexName _rankingIndexName;
		private String _unlessRankingDocumentId;

	}

	private void _addQueryClauses(Consumer<Query> consumer, Query... queries) {
		List<Query> queriesList = Arrays.asList(queries);

		List<Query> isNotNullQueriesList = ListUtil.filter(
			queriesList, Objects::nonNull);

		isNotNullQueriesList.forEach(consumer);
	}

	private BooleanQuery _getCriteriaQuery(Criteria criteria) {
		BooleanQuery booleanQuery = queries.booleanQuery();

		_addQueryClauses(
			booleanQuery::addFilterQueryClauses,
			_getQueryStringsQuery(criteria), _getIndexQuery(criteria));
		_addQueryClauses(
			booleanQuery::addMustNotQueryClauses,
			queries.term(RankingFields.INACTIVE, true),
			_getUnlessRankingIdQuery(criteria));

		return booleanQuery;
	}

	private Collection<String> _getDuplicateQueryStrings(
		SearchHit searchHit, Collection<String> queryStrings) {

		Document document = searchHit.getDocument();

		Collection<String> documentQueryStrings = document.getStrings(
			RankingFields.QUERY_STRINGS);

		documentQueryStrings.retainAll(queryStrings);

		return documentQueryStrings;
	}

	private Query _getIndexQuery(Criteria criteria) {
		if (Validator.isBlank(criteria.getIndex())) {
			return null;
		}

		return queries.term(RankingFields.INDEX, criteria.getIndex());
	}

	private TermsQuery _getQueryStringsQuery(Criteria criteria) {
		TermsQuery termsQuery = queries.terms(
			RankingFields.QUERY_STRINGS_KEYWORD);

		Collection<String> queryStrings = criteria.getQueryStrings();

		termsQuery.addValues(queryStrings.toArray());

		return termsQuery;
	}

	private IdsQuery _getUnlessRankingIdQuery(Criteria criteria) {
		if (Validator.isBlank(criteria.getUnlessRankingDocumentId())) {
			return null;
		}

		IdsQuery idsQuery = queries.ids();

		idsQuery.addIds(criteria.getUnlessRankingDocumentId());

		return idsQuery;
	}

}