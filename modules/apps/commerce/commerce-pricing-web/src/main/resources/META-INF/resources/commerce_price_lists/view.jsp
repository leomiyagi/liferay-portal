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
CommercePriceListDisplayContext commercePriceListDisplayContext = (CommercePriceListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<div class="pt-4">
	<aui:form action="<%= currentURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="deletePriceLists" type="hidden" />

		<frontend-data-set:headless-display
			apiURL="<%= commercePriceListDisplayContext.getPriceListsApiUrl(portletName) %>"
			creationMenu="<%= commercePriceListDisplayContext.getPriceListCreationMenu(portletName) %>"
			fdsActionDropdownItems="<%= commercePriceListDisplayContext.getPriceListFDSActionDropdownItems() %>"
			formName="fm"
			id="<%= CommercePricingFDSNames.PRICE_LISTS %>"
			itemsPerPage="<%= 10 %>"
			style="stacked"
		/>
	</aui:form>
</div>