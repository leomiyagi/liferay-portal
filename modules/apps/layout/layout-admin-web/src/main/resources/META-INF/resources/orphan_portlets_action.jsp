<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Portlet portlet = (Portlet)row.getObject();
%>

<portlet:actionURL name="/layout_admin/delete_orphan_portlets" var="deleteOrphanPortletsURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="portletId" value="<%= portlet.getPortletId() %>" />
	<portlet:param name="selPlid" value="<%= String.valueOf(layoutsAdminDisplayContext.getSelPlid()) %>" />
</portlet:actionURL>

<clay:link
	aria-label='<%= LanguageUtil.get(request, "delete") %>'
	cssClass="lfr-portal-tooltip"
	href="<%= deleteOrphanPortletsURL %>"
	icon="trash"
	monospaced="<%= true %>"
	title='<%= LanguageUtil.get(request, "delete") %>'
/>