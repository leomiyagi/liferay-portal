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

package com.liferay.message.boards.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBCategoryServiceUtil;
import com.liferay.message.boards.service.MBMessageLocalServiceUtil;
import com.liferay.message.boards.service.MBThreadLocalServiceUtil;
import com.liferay.message.boards.service.MBThreadServiceUtil;
import com.liferay.message.boards.test.util.MBTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.test.util.BaseSearchTestCase;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
@Sync
public class MBMessageSearchTest extends BaseSearchTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Override
	@Test
	public void testLocalizedSearch() throws Exception {
	}

	@Override
	@Test
	public void testSearchByDDMStructureField() throws Exception {
	}

	@Override
	@Test
	public void testSearchByKeywordsInsideParentBaseModel() throws Exception {
	}

	@Override
	@Test
	public void testSearchComments() throws Exception {
	}

	@Override
	@Test
	public void testSearchCommentsByKeywords() throws Exception {
	}

	@Override
	@Test
	public void testSearchExpireAllVersions() throws Exception {
	}

	@Override
	@Test
	public void testSearchExpireLatestVersion() throws Exception {
	}

	@Override
	@Test
	public void testSearchStatus() throws Exception {
	}

	@Override
	@Test
	public void testSearchVersions() throws Exception {
	}

	@Override
	@Test
	public void testSearchWithinDDMStructure() throws Exception {
	}

	@Override
	protected void addAttachment(ClassedModel classedModel) throws Exception {
		MBMessage message = (MBMessage)classedModel;

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"org.apache.xmlbeans.impl.common.SAXHelper",
				LoggerTestUtil.WARN)) {

			MBMessageLocalServiceUtil.updateMessage(
				TestPropsValues.getUserId(), message.getMessageId(),
				getSearchKeywords(), getSearchKeywords(),
				MBTestUtil.getInputStreamOVPs(
					"OSX_Test.docx", getClass(), getSearchKeywords()),
				0, false,
				ServiceContextTestUtil.getServiceContext(
					message.getGroupId(), TestPropsValues.getUserId()));
		}
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, boolean approved, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		MBCategory category = (MBCategory)parentBaseModel;

		return MBTestUtil.addMessageWithWorkflow(
			category.getGroupId(), category.getCategoryId(),
			RandomTestUtil.randomString(), keywords, approved, serviceContext);
	}

	@Override
	protected void deleteBaseModel(long primaryKey) throws Exception {
		MBMessage message = MBMessageLocalServiceUtil.getMessage(primaryKey);

		if (!message.isRoot()) {
			MBMessageLocalServiceUtil.deleteMessage(primaryKey);
		}
		else {
			MBThreadLocalServiceUtil.deleteThread(message.getThreadId());
		}
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return MBMessage.class;
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, ServiceContext serviceContext)
		throws Exception {

		return MBCategoryServiceUtil.addCategory(
			TestPropsValues.getUserId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			RandomTestUtil.randomString(), StringPool.BLANK, serviceContext);
	}

	@Override
	protected String getParentBaseModelClassName() {
		return MBCategory.class.getName();
	}

	@Override
	protected String getSearchKeywords() {
		return "Title";
	}

	@Override
	protected void moveBaseModelToTrash(long primaryKey) throws Exception {
		MBMessage message = MBMessageLocalServiceUtil.getMessage(primaryKey);

		MBThreadServiceUtil.moveThreadToTrash(message.getThreadId());
	}

	@Override
	protected void moveParentBaseModelToTrash(long primaryKey)
		throws Exception {

		MBCategoryServiceUtil.moveCategoryToTrash(primaryKey);
	}

	@Override
	protected Hits searchGroupEntries(long groupId, long creatorUserId)
		throws Exception {

		return MBThreadServiceUtil.search(
			groupId, creatorUserId, WorkflowConstants.STATUS_APPROVED,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@Override
	protected BaseModel<?> updateBaseModel(
			BaseModel<?> baseModel, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		MBMessage message = (MBMessage)baseModel;

		ServiceContext updateServiceContext =
			ServiceContextTestUtil.getServiceContext(
				message.getGroupId(), TestPropsValues.getUserId());

		updateServiceContext.setWorkflowAction(
			WorkflowConstants.ACTION_PUBLISH);

		return MBMessageLocalServiceUtil.updateMessage(
			TestPropsValues.getUserId(), message.getMessageId(), keywords,
			updateServiceContext);
	}

}