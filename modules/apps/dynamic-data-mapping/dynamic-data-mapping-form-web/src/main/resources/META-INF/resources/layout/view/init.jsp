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

<%@ taglib uri="http://liferay.com/tld/layout" prefix="liferay-layout" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.portal.kernel.layoutconfiguration.util.RuntimePageUtil" %><%@
page import="com.liferay.portal.kernel.model.LayoutTemplateConstants" %><%@
page import="com.liferay.portal.kernel.service.LayoutTemplateLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %>

<liferay-theme:defineObjects />

<liferay-util:html-top>
	<meta content="initial-scale=1.0, width=device-width" name="viewport" />
</liferay-util:html-top>