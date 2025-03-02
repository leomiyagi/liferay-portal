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

package com.liferay.wiki.web.internal.util;

import com.liferay.diff.DiffVersion;
import com.liferay.diff.DiffVersionsInfo;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.wiki.engine.WikiEngine;
import com.liferay.wiki.engine.WikiEngineRenderer;
import com.liferay.wiki.exception.WikiFormatException;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.WikiPageDisplay;
import com.liferay.wiki.service.WikiPageLocalService;
import com.liferay.wiki.util.comparator.PageVersionComparator;

import java.io.IOException;
import java.io.Writer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class WikiUtil {

	public static String getAttachmentURLPrefix(
		String mainPath, long plid, long nodeId, String title) {

		return StringBundler.concat(
			mainPath, "/wiki/get_page_attachment?p_l_id=", plid, "&nodeId=",
			nodeId, "&title=", URLCodec.encodeURL(title), "&fileName=");
	}

	public static DiffVersionsInfo getDiffVersionsInfo(
		long nodeId, String title, double sourceVersion, double targetVersion,
		HttpServletRequest httpServletRequest) {

		double previousVersion = 0;
		double nextVersion = 0;

		WikiPageLocalService wikiPageLocalService =
			_wikiPageLocalServiceSnapshot.get();

		List<WikiPage> pages = wikiPageLocalService.getPages(
			nodeId, title, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new PageVersionComparator(true));

		for (WikiPage page : pages) {
			if ((page.getVersion() < sourceVersion) &&
				(page.getVersion() > previousVersion)) {

				previousVersion = page.getVersion();
			}

			if ((page.getVersion() > targetVersion) &&
				((page.getVersion() < nextVersion) || (nextVersion == 0))) {

				nextVersion = page.getVersion();
			}
		}

		List<DiffVersion> diffVersions = new ArrayList<>();

		for (WikiPage page : pages) {
			String extraInfo = StringPool.BLANK;

			if (page.isMinorEdit()) {
				extraInfo = LanguageUtil.get(httpServletRequest, "minor-edit");
			}

			DiffVersion diffVersion = new DiffVersion(
				page.getUserId(), page.getVersion(), page.getModifiedDate(),
				page.getSummary(), extraInfo);

			diffVersions.add(diffVersion);
		}

		return new DiffVersionsInfo(diffVersions, nextVersion, previousVersion);
	}

	public static String getFormatLabel(
		WikiEngineRenderer wikiEngineRenderer, String format, Locale locale) {

		WikiEngine wikiEngine = wikiEngineRenderer.fetchWikiEngine(format);

		if (wikiEngine != null) {
			return wikiEngine.getFormatLabel(locale);
		}

		return StringPool.BLANK;
	}

	public static String getFormattedContent(
			WikiEngineRenderer wikiEngineRenderer, RenderRequest renderRequest,
			RenderResponse renderResponse, WikiPage page,
			PortletURL viewPageURL, PortletURL editPageURL, String title,
			boolean preview)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		double version = ParamUtil.getDouble(renderRequest, "version");

		PortletURL curViewPageURL = PortletURLUtil.clone(
			viewPageURL, renderResponse);
		PortletURL curEditPageURL = PortletURLUtil.clone(
			editPageURL, renderResponse);

		String attachmentURLPrefix = StringBundler.concat(
			themeDisplay.getPathMain(), "/wiki/get_page_attachment?p_l_id=",
			themeDisplay.getPlid(), "&nodeId=", page.getNodeId(), "&title=",
			URLCodec.encodeURL(page.getTitle()), "&fileName=");

		if (!preview && (version == 0)) {
			WikiPageLocalService wikiPageLocalService =
				_wikiPageLocalServiceSnapshot.get();

			WikiPageDisplay pageDisplay = wikiPageLocalService.getDisplay(
				page.getNodeId(), title, curViewPageURL, () -> curEditPageURL,
				attachmentURLPrefix);

			if (pageDisplay != null) {
				return pageDisplay.getFormattedContent();
			}
		}

		return wikiEngineRenderer.convert(
			page, curViewPageURL, curEditPageURL, attachmentURLPrefix);
	}

	public static List<WikiNode> getNodes(
			List<WikiNode> nodes, String[] hiddenNodes,
			PermissionChecker permissionChecker)
		throws PortalException {

		nodes = ListUtil.copy(nodes);

		Arrays.sort(hiddenNodes);

		Iterator<WikiNode> iterator = nodes.iterator();

		while (iterator.hasNext()) {
			WikiNode node = iterator.next();

			ModelResourcePermission<WikiNode> wikiNodeModelResourcePermission =
				_wikiNodeModelResourcePermissionSnapshot.get();

			if (!(Arrays.binarySearch(hiddenNodes, node.getName()) < 0) ||
				!wikiNodeModelResourcePermission.contains(
					permissionChecker, node, ActionKeys.VIEW)) {

				iterator.remove();
			}
		}

		return nodes;
	}

	public static List<WikiNode> orderNodes(
		List<WikiNode> nodes, String[] visibleNodeNames) {

		if (ArrayUtil.isEmpty(visibleNodeNames)) {
			return nodes;
		}

		nodes = ListUtil.copy(nodes);

		List<WikiNode> orderedNodes = new ArrayList<>(nodes.size());

		for (String visibleNodeName : visibleNodeNames) {
			for (WikiNode node : nodes) {
				String name = node.getName();

				if (name.equals(visibleNodeName)) {
					orderedNodes.add(node);

					nodes.remove(node);

					break;
				}
			}
		}

		orderedNodes.addAll(nodes);

		return orderedNodes;
	}

	public static void renderEditPageHTML(
			WikiEngineRenderer wikiEngineRenderer, String format,
			PageContext pageContext, WikiNode node, WikiPage page)
		throws IOException, ServletException, WikiFormatException {

		WikiEngine wikiEngine = wikiEngineRenderer.fetchWikiEngine(format);

		if (wikiEngine == null) {
			throw new WikiFormatException();
		}

		HttpServletResponse httpServletResponse =
			(HttpServletResponse)pageContext.getResponse();

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		PipingServletResponse pipingServletResponse = new PipingServletResponse(
			httpServletResponse, unsyncStringWriter);

		wikiEngine.renderEditPage(
			pageContext.getRequest(), pipingServletResponse, node, page);

		Writer writer = pageContext.getOut();

		StringBundler sb = unsyncStringWriter.getStringBundler();

		writer.write(sb.toString());
	}

	private static final Snapshot<ModelResourcePermission<WikiNode>>
		_wikiNodeModelResourcePermissionSnapshot = new Snapshot<>(
			WikiUtil.class, Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.wiki.model.WikiNode)");
	private static final Snapshot<WikiPageLocalService>
		_wikiPageLocalServiceSnapshot = new Snapshot<>(
			WikiUtil.class, WikiPageLocalService.class);

}