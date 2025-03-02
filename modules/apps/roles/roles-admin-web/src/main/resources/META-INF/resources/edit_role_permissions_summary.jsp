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
EditRolePermissionsSummaryDisplayContext editRolePermissionsSummaryDisplayContext = new EditRolePermissionsSummaryDisplayContext(request, response, liferayPortletRequest, liferayPortletResponse, roleDisplayContext, application);

SearchContainer<PermissionDisplay> searchContainer = editRolePermissionsSummaryDisplayContext.getSearchContainer();
%>

<aui:form action="#" method="POST" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<clay:sheet>
		<clay:sheet-header>
			<h3 class="sheet-title"><liferay-ui:message key="summary" /></h3>
		</clay:sheet-header>

		<clay:sheet-section>
			<liferay-ui:search-iterator
				markupView="deprecated"
				paginate="<%= false %>"
				searchContainer="<%= searchContainer %>"
				searchResultCssClass="show-quick-actions-on-hover table table-autofit"
			/>
		</clay:sheet-section>

		<c:if test="<%= searchContainer.getTotal() > 0 %>">
			<clay:sheet-footer>
				<clay:content-row>
					<clay:content-col
						cssClass="taglib-search-iterator-page-iterator-bottom"
						expand="<%= true %>"
					>
						<liferay-ui:search-paginator
							markupView="lexicon"
							searchContainer="<%= searchContainer %>"
						/>
					</clay:content-col>
				</clay:content-row>
			</clay:sheet-footer>
		</c:if>
	</clay:sheet>
</aui:form>