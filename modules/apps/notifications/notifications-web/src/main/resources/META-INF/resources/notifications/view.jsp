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
String navigation = ParamUtil.getString(request, "navigation", "all");

boolean actionRequired = ParamUtil.getBoolean(request, "actionRequired");

if (actionRequired) {
	navigation = "unread";
}

SearchContainer<UserNotificationEvent> notificationsSearchContainer = new SearchContainer(renderRequest, currentURLObj, null, actionRequired ? "you-do-not-have-any-requests" : "you-do-not-have-any-notifications");

String searchContainerId = "userNotificationEvents";

if (actionRequired) {
	searchContainerId = "actionableUserNotificationEvents";
}

int delta = ParamUtil.getInteger(request, "delta");

if (delta == 0) {
	delta = 10;
}

notificationsSearchContainer.setDelta(delta);

notificationsSearchContainer.setId(searchContainerId);

NotificationsManagementToolbarDisplayContext notificationsManagementToolbarDisplayContext = new NotificationsManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, currentURLObj);

NotificationsUtil.populateResults(themeDisplay.getUserId(), actionRequired, navigation, notificationsManagementToolbarDisplayContext.getOrderByType(), notificationsSearchContainer);
%>

<clay:container-fluid>
	<button class="btn btn-link px-0" onclick="history.back()">
		<span class="inline-item inline-item-before">
			<clay:icon
				symbol="order-arrow-left"
			/>
		</span>
		Back
	</button>

	<h3>All Notifications</h3>
</clay:container-fluid>

<portlet:actionURL name="deleteNotifications" var="deleteNotificationsURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<portlet:actionURL name="markNotificationsAsRead" var="markNotificationsAsReadURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<portlet:actionURL name="markNotificationsAsUnread" var="markNotificationsAsUnreadURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<div class="custom-notifications-toolbar">
	<clay:management-toolbar
		actionDropdownItems="<%= notificationsManagementToolbarDisplayContext.getActionDropdownItems() %>"
		additionalProps='<%=
			HashMapBuilder.<String, Object>put(
				"deleteNotificationsURL", deleteNotificationsURL.toString()
			).put(
				"markNotificationsAsReadURL", markNotificationsAsReadURL.toString()
			).put(
				"markNotificationsAsUnreadURL", markNotificationsAsUnreadURL.toString()
			).build()
		%>'
		clearResultsURL="<%= notificationsManagementToolbarDisplayContext.getClearResultsURL() %>"
		disabled="<%= NotificationsUtil.getAllNotificationsCount(themeDisplay.getUserId(), actionRequired) == 0 %>"
		filterDropdownItems="<%= notificationsManagementToolbarDisplayContext.getFilterDropdownItems() %>"
		filterLabelItems="<%= notificationsManagementToolbarDisplayContext.getFilterLabelItems() %>"
		itemsTotal="<%= notificationsSearchContainer.getTotal() %>"
		propsTransformer="notifications/js/NotificationsManagementToolbarPropsTransformer"
		searchContainerId="<%= searchContainerId %>"
		selectable="<%= actionRequired ? false : true %>"
		showCreationMenu="<%= false %>"
		showInfoButton="<%= false %>"
		showSearch="<%= false %>"
		sortingOrder="<%= notificationsManagementToolbarDisplayContext.getOrderByType() %>"
		sortingURL="<%= String.valueOf(notificationsManagementToolbarDisplayContext.getSortingURL()) %>"
	/>
</div>

<clay:container-fluid>
	<aui:form action="<%= currentURL %>" method="get" name="fm">
		<div class="user-notifications">
			<liferay-ui:search-container
				rowChecker="<%= actionRequired ? null : new UserNotificationEventRowChecker(renderResponse) %>"
				searchContainer="<%= notificationsSearchContainer %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.portal.kernel.model.UserNotificationEvent"
					keyProperty="userNotificationEventId"
					modelVar="userNotificationEvent"
				>

					<%
					UserNotificationFeedEntry userNotificationFeedEntry = UserNotificationManagerUtil.interpret(StringPool.BLANK, userNotificationEvent, ServiceContextFactory.getInstance(request));

					row.setData(
						HashMapBuilder.<String, Object>put(
							"actions", StringUtil.merge(notificationsManagementToolbarDisplayContext.getAvailableActions(userNotificationEvent, userNotificationFeedEntry))
						).put(
							"userNotificationFeedEntry", userNotificationFeedEntry
						).build());
					%>

					<%@ include file="/notifications/user_notification_entry.jspf" %>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					displayStyle="descriptive"
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</div>
	</aui:form>
</clay:container-fluid>

<script>
	if (typeof <portlet:namespace />openAndRedirect !== 'function') {
		const <portlet:namespace />openAndRedirect = (open, redirect) => {
			window.open(open);
			window.location.replace(redirect);
		}
	}
</script>

<aui:script use="aui-base">
	var sortDirection = new URL(window.location).searchParams.get('<portlet:namespace />orderByType')
	var selectAllBox = document.querySelector('.custom-notifications-toolbar .custom-checkbox input');

	selectAllBox.addEventListener('change', initSortButton);
	selectAllBox.addEventListener('change', initFilterButton);
	selectAllBox.addEventListener('change', e => {
		if (!e.target.checked) {
			initFilterDropdown();
		}
	});

	initSortButton();
	initFilterButton();
	initFilterDropdown();
	initFormCheckboxes();

	function initFilterButton() {
		var filterButton = document.querySelector('.custom-notifications-toolbar .navbar-nav .dropdown-toggle');
		var svg = document.querySelector('.lexicon-icon[role=presentation]').cloneNode(true);
		svg.firstChild.href.baseVal = `${Liferay.ThemeDisplay.getPathContext()}/o/hisc-theme/images/clay/icons.svg#filter`;
		filterButton.appendChild(svg);
	}

	function initSortButton() {
		var sortButton = document.querySelector("a[title='Reverse Sort Direction']");
		if (sortButton) {
			var text = sortDirection === 'asc' ? 'Oldest to Newest' : 'Newest to Oldest' ;
			var pNode = document.createElement('p');
			var svg = sortButton.firstChild;

			sortButton.removeChild(sortButton.firstChild);
			pNode.textContent = text;
			pNode.classList.add('mb-0', 'mr-2')
			sortButton.appendChild(pNode);
			sortButton.appendChild(svg);
		}
	}

	function initFilterDropdown() {
		var filterButton = document.querySelector('.custom-notifications-toolbar .management-bar-light .navbar-nav .nav-item .dropdown button');
		var filterDropdownId = filterButton.attributes['aria-controls'].value;
		var filterType = new URL(window.location).searchParams.get('<portlet:namespace />navigation')
		waitForElement('#' + filterDropdownId).then((elem) => {
			const dropdownList = elem.firstChild;
			dropdownList.removeChild(dropdownList.firstChild);
			dropdownList.removeChild(dropdownList.lastChild);
			dropdownList.removeChild(dropdownList.lastChild);

			const innerDropdownList = elem.firstChild.firstChild.firstChild;
			innerDropdownList.removeChild(innerDropdownList.firstChild);

			const filterText = document.createElement('p');
			filterText.classList.add('pl-3', 'pt-2');
			filterText.textContent = 'Filter by';
			elem.appendChild(filterText);
			elem.appendChild(elem.firstChild);

			const dropdownItems = elem.querySelectorAll('.dropdown-item');

			for (const item of dropdownItems) {
				const checkbox = selectAllBox.parentElement.parentElement.cloneNode(true);
				const labelText = document.createElement('p');
				labelText.textContent = item.firstChild.textContent;
				labelText.classList.add('pl-4', 'pt-1', 'm-0');
				checkbox.firstChild.after(labelText);

				if (item.textContent.toLowerCase() === filterType) {
					checkbox.querySelector('input[type=checkbox]').checked = true;
					checkbox.querySelector('input[type=checkbox]').classList.add('mr-2');
				}

				item.appendChild(checkbox);
				item.removeChild(item.firstChild);
			}
		});
	}

	function initFormCheckboxes() {
		waitForElement('#<portlet:namespace />fm').then((elem) => {
			const formCheckboxes = elem.querySelectorAll('input[type=checkbox]');
			for (const checkbox of formCheckboxes) {
				checkbox.addEventListener('change', initSortButton);
				checkbox.addEventListener('change', initFilterButton);
				checkbox.addEventListener('change', e => {
					if (!e.target.checked) {
						initFilterDropdown();
					}
				});
			}
		});
	}

	function waitForElement(selector) {
		return new Promise(resolve => {
			if (document.querySelector(selector)) {
				return resolve(document.querySelector(selector));
			}

			const observer = new MutationObserver(mutations => {
				if (document.querySelector(selector)) {
					resolve(document.querySelector(selector));
					observer.disconnect();
				}
			});

			observer.observe(document.body, {
				childList: true,
				subtree: true
			});
		});
	}
</aui:script>

<aui:script use="aui-base">
	var form = A.one('#<portlet:namespace />fm');

	form.delegate(
		'click',
		(event) => {
			event.preventDefault();

			var currentTarget = event.currentTarget;

			Liferay.Util.fetch(currentTarget.attr('href'), {
				method: 'POST',
			})
				.then((response) => {
					return response.json();
				})
				.then((response) => {
					if (response.success) {
						var notificationContainer = currentTarget.ancestor(
							'li.list-group-item'
						);

						if (notificationContainer) {
							var markAsReadURL = notificationContainer
								.one('a')
								.attr('href');

							form.attr('method', 'post');

							submitForm(form, markAsReadURL);

							notificationContainer.remove();
						}
					}
					else {
						Liferay.Util.openToast({
							message:
								'<liferay-ui:message key="an-unexpected-error-occurred" />',
							toastProps: {
								autoClose: 5000,
							},
							type: 'warning',
						});
					}
				});
		},
		'.user-notification-action'
	);
</aui:script>