/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.elasticsearch7.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.suggest.PhraseSuggester;
import com.liferay.portal.kernel.search.suggest.QuerySuggester;
import com.liferay.portal.kernel.search.suggest.Suggester;
import com.liferay.portal.kernel.search.suggest.SuggesterResult;
import com.liferay.portal.kernel.search.suggest.SuggesterResults;
import com.liferay.portal.kernel.search.suggest.TermSuggester;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SuggestSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SuggestSearchResponse;
import com.liferay.portal.search.engine.adapter.search.SuggestSearchResult;
import com.liferay.portal.search.index.IndexNameBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.StopWatch;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	property = "search.engine.impl=Elasticsearch",
	service = QuerySuggester.class
)
public class ElasticsearchQuerySuggester implements QuerySuggester {

	@Override
	public String spellCheckKeywords(SearchContext searchContext) {
		Suggester suggester = _createSpellCheckSuggester(searchContext, 1);

		SuggestSearchResponse suggestSearchResponse =
			_executeSuggestSearchRequest(suggester, searchContext);

		if (suggestSearchResponse == null) {
			return StringPool.BLANK;
		}

		SuggestSearchResult suggestSearchResult =
			suggestSearchResponse.getSuggesterResult(_SPELL_CHECK_REQUEST_NAME);

		if (suggestSearchResult == null) {
			return StringPool.BLANK;
		}

		List<String> words = _getHighestRankedSuggestions(suggestSearchResult);

		return StringUtil.merge(words, StringPool.SPACE);
	}

	@Override
	public Map<String, List<String>> spellCheckKeywords(
		SearchContext searchContext, int max) {

		Suggester suggester = _createSpellCheckSuggester(searchContext, max);

		SuggestSearchResponse suggestSearchResponse =
			_executeSuggestSearchRequest(suggester, searchContext);

		if (suggestSearchResponse == null) {
			return Collections.emptyMap();
		}

		SuggestSearchResult suggestSearchResult =
			suggestSearchResponse.getSuggesterResult(_SPELL_CHECK_REQUEST_NAME);

		if (suggestSearchResult == null) {
			return Collections.emptyMap();
		}

		Map<String, List<String>> results = new LinkedHashMap<>();

		List<SuggestSearchResult.Entry> suggestSearchResultEntries =
			suggestSearchResult.getEntries();

		suggestSearchResultEntries.forEach(
			suggestSearchResultEntry -> {
				List<SuggestSearchResult.Entry.Option>
					suggestSearchResultEntryOptions =
						suggestSearchResultEntry.getOptions();

				String keyword = suggestSearchResultEntry.getText();

				List<String> wordsList = results.computeIfAbsent(
					keyword, keywords -> new ArrayList<>());

				suggestSearchResultEntryOptions.forEach(
					suggestSearchResultEntryOption -> {
						String word = suggestSearchResultEntryOption.getText();

						if (!wordsList.contains(word)) {
							wordsList.add(word);
						}
					});
			});

		return results;
	}

	@Override
	public SuggesterResults suggest(
		SearchContext searchContext, Suggester suggester) {

		SuggestSearchResponse suggestSearchResponse =
			_executeSuggestSearchRequest(suggester, searchContext);

		if (suggestSearchResponse == null) {
			return new SuggesterResults();
		}

		return translate(suggestSearchResponse);
	}

	@Override
	public String[] suggestKeywordQueries(
		SearchContext searchContext, int max) {

		Suggester suggester = _createQuerySuggester(searchContext, max);

		SuggestSearchResponse suggestSearchResponse =
			_executeSuggestSearchRequest(suggester, searchContext);

		if (suggestSearchResponse == null) {
			return StringPool.EMPTY_ARRAY;
		}

		SuggestSearchResult suggestSearchResult =
			suggestSearchResponse.getSuggesterResult(_KEY_WORD_REQUEST_NAME);

		if (suggestSearchResult == null) {
			return StringPool.EMPTY_ARRAY;
		}

		List<String> keywordQueries = _getHighestRankedSuggestions(
			suggestSearchResult);

		return keywordQueries.toArray(new String[0]);
	}

	protected void setLocalization(Localization localization) {
		_localization = localization;
	}

	protected SuggesterResults translate(
		SuggestSearchResponse suggestSearchResponse) {

		SuggesterResults suggesterResults = new SuggesterResults();

		Collection<SuggestSearchResult> suggestSearchResults =
			suggestSearchResponse.getSuggestSearchResults();

		suggestSearchResults.forEach(
			suggestSearchResult -> {
				SuggesterResult suggesterResult = new SuggesterResult(
					suggestSearchResult.getName());

				suggesterResults.addSuggesterResult(suggesterResult);

				List<SuggestSearchResult.Entry> suggestSearchResultEntries =
					suggestSearchResult.getEntries();

				suggestSearchResultEntries.forEach(
					suggestSearchResultEntry -> {
						SuggesterResult.Entry suggesterResultEntry =
							new SuggesterResult.Entry(
								suggestSearchResultEntry.getText());

						suggesterResult.addEntry(suggesterResultEntry);

						List<SuggestSearchResult.Entry.Option>
							suggestSearchResultEntryOptions =
								suggestSearchResultEntry.getOptions();

						suggestSearchResultEntryOptions.forEach(
							suggestSearchResultEntryOption -> {
								SuggesterResult.Entry.Option
									suggesterResultEntryOption =
										new SuggesterResult.Entry.Option(
											suggestSearchResultEntryOption.
												getText(),
											suggestSearchResultEntryOption.
												getScore());

								suggesterResultEntry.addOption(
									suggesterResultEntryOption);

								suggesterResultEntryOption.setFrequency(
									suggestSearchResultEntryOption.
										getFrequency());
								suggesterResultEntryOption.setHighlightedText(
									suggestSearchResultEntryOption.
										getHighlightedText());
							});
					});
			});

		return suggesterResults;
	}

	private PhraseSuggester _createQuerySuggester(
		SearchContext searchContext, int max) {

		String field = _localization.getLocalizedName(
			Field.KEYWORD_SEARCH, searchContext.getLanguageId());

		PhraseSuggester phraseSuggester = new PhraseSuggester(
			_KEY_WORD_REQUEST_NAME, field, searchContext.getKeywords());

		phraseSuggester.setSize(max);

		return phraseSuggester;
	}

	private Suggester _createSpellCheckSuggester(
		SearchContext searchContext, int max) {

		String field = _localization.getLocalizedName(
			Field.SPELL_CHECK_WORD, searchContext.getLanguageId());

		TermSuggester termSuggester = new TermSuggester(
			_SPELL_CHECK_REQUEST_NAME, field, searchContext.getKeywords());

		termSuggester.setSize(max);

		return termSuggester;
	}

	private SuggestSearchResponse _executeSuggestSearchRequest(
		Suggester suggester, SearchContext searchContext) {

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		try {
			SuggestSearchRequest suggestSearchRequest =
				new SuggestSearchRequest(
					_indexNameBuilder.getIndexName(
						searchContext.getCompanyId()));

			suggestSearchRequest.addSuggester(suggester);

			return _searchEngineAdapter.execute(suggestSearchRequest);
		}
		catch (RuntimeException runtimeException) {
			String message = runtimeException.getMessage();

			if (!message.contains("no mapping found for field")) {
				Throwable throwable = runtimeException.getCause();

				if (throwable != null) {
					message = throwable.getMessage();
				}
			}

			if (message.contains("no mapping found for field")) {
				if (_log.isWarnEnabled()) {
					_log.warn("No dictionary indexed", runtimeException);
				}

				return null;
			}

			throw runtimeException;
		}
		finally {
			if (_log.isInfoEnabled()) {
				stopWatch.stop();

				_log.info(
					"Spell checked keywords in " + stopWatch.getTime() + "ms");
			}
		}
	}

	private List<String> _getHighestRankedSuggestions(
		SuggestSearchResult suggestSearchResult) {

		List<String> suggestions = new ArrayList<>();

		boolean hasDifferences = false;

		for (SuggestSearchResult.Entry suggestSearchResultEntry :
				suggestSearchResult.getEntries()) {

			List<SuggestSearchResult.Entry.Option>
				suggestSearchResultEntryOptions =
					suggestSearchResultEntry.getOptions();

			if (!suggestSearchResultEntryOptions.isEmpty()) {
				hasDifferences = true;

				for (SuggestSearchResult.Entry.Option
						suggestSearchResultEntryOption :
							suggestSearchResultEntryOptions) {

					suggestions.add(suggestSearchResultEntryOption.getText());
				}
			}
			else {
				suggestions.add(suggestSearchResultEntry.getText());
			}
		}

		if (hasDifferences) {
			return suggestions;
		}

		return new ArrayList<>();
	}

	private static final String _KEY_WORD_REQUEST_NAME = "keywordQueryRequest";

	private static final String _SPELL_CHECK_REQUEST_NAME = "spellCheckRequest";

	private static final Log _log = LogFactoryUtil.getLog(
		ElasticsearchQuerySuggester.class);

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	@Reference
	private Localization _localization;

	@Reference(target = "(search.engine.impl=Elasticsearch)")
	private SearchEngineAdapter _searchEngineAdapter;

}