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

package com.liferay.portal.osgi.web.portlet.container.error.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.osgi.web.portlet.container.test.BasePortletContainerTestCase;
import com.liferay.portal.osgi.web.portlet.container.test.util.PortletContainerTestUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import javax.portlet.PortletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class UncontrolledExceptionErrorTest
	extends BasePortletContainerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = _userLocalService.getDefaultUser(group.getCompanyId());
	}

	@Test
	public void testUncontrolledExceptionError() throws Exception {
		UncontrolledExceptionErrorPortlet uncontrolledExceptionErrorPortlet =
			_setUpUncontrolledExceptionErrorPortlet();

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"portal_web.docroot.html.portal.render_portlet_jsp",
				LoggerTestUtil.ERROR)) {

			PortletContainerTestUtil.Response response =
				PortletContainerTestUtil.request(
					PortletURLBuilder.create(
						_portletURLFactory.create(
							PortletContainerTestUtil.getHttpServletRequest(
								group, layout),
							UncontrolledExceptionErrorPortlet.PORTLET_NAME,
							layout.getPlid(), PortletRequest.RENDER_PHASE)
					).buildString());

			Assert.assertTrue(
				uncontrolledExceptionErrorPortlet.isCalledRender());

			_assertLogEntryMessage(logCapture);

			_assertUncontrolledExceptionErrorHTML(
				_language.format(
					_user.getLocale(), "is-temporarily-unavailable",
					uncontrolledExceptionErrorPortlet.getTitle()),
				response.getBody());
		}
	}

	private void _assertLogEntryMessage(LogCapture logCapture) {
		List<LogEntry> logEntries = logCapture.getLogEntries();

		Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

		LogEntry logEntry = logEntries.get(0);

		Assert.assertEquals("null", logEntry.getMessage());
	}

	private void _assertUncontrolledExceptionErrorHTML(
		String expectedError, String responseBodyHTML) {

		Assert.assertTrue(
			"Page body should contain error message : " + expectedError,
			responseBodyHTML.contains(expectedError));
	}

	private UncontrolledExceptionErrorPortlet
			_setUpUncontrolledExceptionErrorPortlet()
		throws Exception {

		UncontrolledExceptionErrorPortlet uncontrolledExceptionErrorPortlet =
			new UncontrolledExceptionErrorPortlet();

		setUpPortlet(
			uncontrolledExceptionErrorPortlet,
			HashMapDictionaryBuilder.<String, Object>put(
				"com.liferay.portlet.use-default-template",
				Boolean.TRUE.toString()
			).put(
				"javax.portlet.display-name",
				"Uncontrolled Exception Error Portlet"
			).put(
				"javax.portlet.init-param.view-template", "/error_test/view.jsp"
			).put(
				"javax.portlet.name",
				UncontrolledExceptionErrorPortlet.PORTLET_NAME
			).put(
				"javax.portlet.resource-bundle", "content.Language"
			).build(),
			UncontrolledExceptionErrorPortlet.PORTLET_NAME);

		return uncontrolledExceptionErrorPortlet;
	}

	@Inject
	private Language _language;

	@Inject
	private PortletURLFactory _portletURLFactory;

	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}