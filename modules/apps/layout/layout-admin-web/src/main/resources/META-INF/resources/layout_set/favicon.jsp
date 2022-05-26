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
LayoutSet selLayoutSet = layoutsAdminDisplayContext.getSelLayoutSet();
%>

<div class="form-group">
	<p>
		<b><liferay-ui:message key="favicon-name" />:</b> <span id="<portlet:namespace />faviconFileEntryTitle"><%= layoutsAdminDisplayContext.getFaviconTitle() %></span>
	</p>

	<aui:input name="faviconFileEntryId" type="hidden" value="<%= selLayoutSet.getFaviconFileEntryId() %>" />

	<aui:button name="selectFaviconButton" value="change-favicon" />

	<aui:button name="clearFaviconButton" value="clear" />

	<aui:script sandbox="<%= true %>">
		const selectLayoutButton = document.getElementById(
			'<portlet:namespace />selectFaviconButton'
		);

		selectLayoutButton.addEventListener('click', (event) => {
			event.preventDefault();

			Liferay.Util.openSelectionModal({
				onSelect: function (selectedItem) {
					const faviconFileEntryId = document.getElementById(
						'<portlet:namespace />faviconFileEntryId'
					);
					const faviconFileEntryTitle = document.getElementById(
						'<portlet:namespace />faviconFileEntryTitle'
					);

					if (
						selectedItem &&
						selectedItem.value &&
						faviconFileEntryId &&
						faviconFileEntryTitle
					) {
						const itemValue = JSON.parse(selectedItem.value);

						faviconFileEntryId.value = itemValue.fileEntryId;
						faviconFileEntryTitle.innerHTML = itemValue.title;
					}
				},
				selectEventName:
					'<%= layoutsAdminDisplayContext.getSelectFaviconEventName() %>',
				title: '<liferay-ui:message key="change-favicon" />',
				url: '<%= layoutsAdminDisplayContext.getFileEntryItemSelectorURL() %>',
			});
		});

		const clearFaviconButton = document.getElementById(
			'<portlet:namespace />clearFaviconButton'
		);
		const faviconFileEntryId = document.getElementById(
			'<portlet:namespace />faviconFileEntryId'
		);
		const faviconFileEntryTitle = document.getElementById(
			'<portlet:namespace />faviconFileEntryTitle'
		);

		if (clearFaviconButton && faviconFileEntryId && faviconFileEntryTitle) {
			clearFaviconButton.addEventListener('click', (event) => {
				faviconFileEntryId.value = '0';
				faviconFileEntryTitle.innerHTML =
					'<liferay-ui:message key="favicon-from-theme" />';
			});
		}
	</aui:script>
</div>