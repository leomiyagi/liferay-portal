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

<%@ include file="/layout/init.jsp" %>

<%
String url = StringPool.BLANK;

if (selLayout != null) {
	UnicodeProperties typeSettingsUnicodeProperties = selLayout.getTypeSettingsProperties();

	url = typeSettingsUnicodeProperties.getProperty("embeddedLayoutURL", StringPool.BLANK);
}
%>

<aui:input cssClass="lfr-input-text-container" helpMessage="embedded-layout-url-help-message" id="urlEmbedded" label="url" name="TypeSettingsProperties--embeddedLayoutURL--" type="text" value="<%= url %>">
	<aui:validator errorMessage="please-enter-a-valid-url" name="required" />
</aui:input>