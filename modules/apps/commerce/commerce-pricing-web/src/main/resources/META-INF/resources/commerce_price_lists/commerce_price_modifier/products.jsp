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

CommercePriceModifier commercePriceModifier = commercePriceListDisplayContext.getCommercePriceModifier();
long commercePriceModifierId = commercePriceListDisplayContext.getCommercePriceModifierId();
%>

<c:if test="<%= commercePriceListDisplayContext.hasPermission(commercePriceListDisplayContext.getCommercePriceListId(), ActionKeys.UPDATE) %>">
	<div class="row">
		<div class="col-12">
			<div id="item-finder-root"></div>

			<aui:script require="commerce-frontend-js/components/item_finder/entry as itemFinder, commerce-frontend-js/utilities/slugify as slugify, commerce-frontend-js/utilities/eventsDefinitions as events, commerce-frontend-js/ServiceProvider/index as ServiceProvider">
				var CommercePriceModifierProductsResource = ServiceProvider.default.AdminPricingAPI(
					'v2'
				);

				var id = <%= commercePriceModifierId %>;
				var priceModifierExternalReferenceCode =
					'<%= HtmlUtil.escapeJS(commercePriceModifier.getExternalReferenceCode()) %>';

				function selectItem(product) {
					var productData = {
						productExternalReferenceCode: product.externalReferenceCode,
						productId: product.id,
						priceModifierExternalReferenceCode: priceModifierExternalReferenceCode,
						priceModifierId: id,
					};

					return CommercePriceModifierProductsResource.addPriceModifierProduct(
						id,
						productData
					)
						.then(() => {
							Liferay.fire(events.UPDATE_DATASET_DISPLAY, {
								id:
									'<%= CommercePricingFDSNames.PRICE_MODIFIER_PRODUCT_DEFINITIONS %>',
							});
						})
						.catch((error) => {
							return Promise.reject(error);
						});
				}

				function getSelectedItems() {
					return Promise.resolve([]);
				}

				itemFinder.default('itemFinder', 'item-finder-root', {
					apiUrl:
						'/o/headless-commerce-admin-catalog/v1.0/products?filter=catalogId eq <%= commercePriceListDisplayContext.getCommerceCatalogId() %>',
					getSelectedItems: getSelectedItems,
					inputPlaceholder: '<%= LanguageUtil.get(request, "find-a-product") %>',
					itemSelectedMessage: '<%= LanguageUtil.get(request, "product-selected") %>',
					linkedDatasetsId: [
						'<%= CommercePricingFDSNames.PRICE_MODIFIER_PRODUCT_DEFINITIONS %>',
					],
					itemCreation: false,
					itemsKey: 'id',
					onItemSelected: selectItem,
					pageSize: 10,
					panelHeaderLabel: '<%= LanguageUtil.get(request, "add-products") %>',
					portletId: '<%= portletDisplay.getRootPortletId() %>',
					schema: [
						{
							fieldName: ['name', 'LANG'],
						},
						{
							fieldName: 'productId',
						},
					],
					spritemap: '<%= themeDisplay.getPathThemeSpritemap() %>',
					titleLabel: '<%= LanguageUtil.get(request, "add-existing-product") %>',
				});
			</aui:script>
		</div>

		<div class="col-12">
			<commerce-ui:panel
				bodyClasses="p-0"
				title='<%= LanguageUtil.get(request, "products") %>'
			>
				<frontend-data-set:headless-display
					apiURL="<%= commercePriceListDisplayContext.getPriceModifierCPDefinitionApiUrl() %>"
					fdsActionDropdownItems="<%= commercePriceListDisplayContext.getPriceModifierCPDefinitionFDSActionDropdownItems() %>"
					formName="fm"
					id="<%= CommercePricingFDSNames.PRICE_MODIFIER_PRODUCT_DEFINITIONS %>"
					itemsPerPage="<%= 10 %>"
				/>
			</commerce-ui:panel>
		</div>
	</div>
</c:if>