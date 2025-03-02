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

package com.liferay.headless.commerce.admin.site.setting.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.commerce.admin.site.setting.client.dto.v1_0.MeasurementUnit;
import com.liferay.headless.commerce.admin.site.setting.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.site.setting.client.pagination.Page;
import com.liferay.headless.commerce.admin.site.setting.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.site.setting.client.resource.v1_0.MeasurementUnitResource;
import com.liferay.headless.commerce.admin.site.setting.client.serdes.v1_0.MeasurementUnitSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.Method;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.lang.time.DateUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
public abstract class BaseMeasurementUnitResourceTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");
	}

	@Before
	public void setUp() throws Exception {
		irrelevantGroup = GroupTestUtil.addGroup();
		testGroup = GroupTestUtil.addGroup();

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());

		_measurementUnitResource.setContextCompany(testCompany);

		MeasurementUnitResource.Builder builder =
			MeasurementUnitResource.builder();

		measurementUnitResource = builder.authentication(
			"test@liferay.com", "test"
		).locale(
			LocaleUtil.getDefault()
		).build();
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testClientSerDesToDTO() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				enable(SerializationFeature.INDENT_OUTPUT);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		MeasurementUnit measurementUnit1 = randomMeasurementUnit();

		String json = objectMapper.writeValueAsString(measurementUnit1);

		MeasurementUnit measurementUnit2 = MeasurementUnitSerDes.toDTO(json);

		Assert.assertTrue(equals(measurementUnit1, measurementUnit2));
	}

	@Test
	public void testClientSerDesToJSON() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		MeasurementUnit measurementUnit = randomMeasurementUnit();

		String json1 = objectMapper.writeValueAsString(measurementUnit);
		String json2 = MeasurementUnitSerDes.toJSON(measurementUnit);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		MeasurementUnit measurementUnit = randomMeasurementUnit();

		measurementUnit.setExternalReferenceCode(regex);
		measurementUnit.setKey(regex);
		measurementUnit.setType(regex);

		String json = MeasurementUnitSerDes.toJSON(measurementUnit);

		Assert.assertFalse(json.contains(regex));

		measurementUnit = MeasurementUnitSerDes.toDTO(json);

		Assert.assertEquals(regex, measurementUnit.getExternalReferenceCode());
		Assert.assertEquals(regex, measurementUnit.getKey());
		Assert.assertEquals(regex, measurementUnit.getType());
	}

	@Test
	public void testGetMeasurementUnitsPage() throws Exception {
		Page<MeasurementUnit> page =
			measurementUnitResource.getMeasurementUnitsPage(
				null, Pagination.of(1, 10), null);

		long totalCount = page.getTotalCount();

		MeasurementUnit measurementUnit1 =
			testGetMeasurementUnitsPage_addMeasurementUnit(
				randomMeasurementUnit());

		MeasurementUnit measurementUnit2 =
			testGetMeasurementUnitsPage_addMeasurementUnit(
				randomMeasurementUnit());

		page = measurementUnitResource.getMeasurementUnitsPage(
			null, Pagination.of(1, 10), null);

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(
			measurementUnit1, (List<MeasurementUnit>)page.getItems());
		assertContains(
			measurementUnit2, (List<MeasurementUnit>)page.getItems());
		assertValid(page, testGetMeasurementUnitsPage_getExpectedActions());

		measurementUnitResource.deleteMeasurementUnit(measurementUnit1.getId());

		measurementUnitResource.deleteMeasurementUnit(measurementUnit2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetMeasurementUnitsPage_getExpectedActions()
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetMeasurementUnitsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		MeasurementUnit measurementUnit1 = randomMeasurementUnit();

		measurementUnit1 = testGetMeasurementUnitsPage_addMeasurementUnit(
			measurementUnit1);

		for (EntityField entityField : entityFields) {
			Page<MeasurementUnit> page =
				measurementUnitResource.getMeasurementUnitsPage(
					getFilterString(entityField, "between", measurementUnit1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(measurementUnit1),
				(List<MeasurementUnit>)page.getItems());
		}
	}

	@Test
	public void testGetMeasurementUnitsPageWithFilterDoubleEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DOUBLE);

		if (entityFields.isEmpty()) {
			return;
		}

		MeasurementUnit measurementUnit1 =
			testGetMeasurementUnitsPage_addMeasurementUnit(
				randomMeasurementUnit());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		MeasurementUnit measurementUnit2 =
			testGetMeasurementUnitsPage_addMeasurementUnit(
				randomMeasurementUnit());

		for (EntityField entityField : entityFields) {
			Page<MeasurementUnit> page =
				measurementUnitResource.getMeasurementUnitsPage(
					getFilterString(entityField, "eq", measurementUnit1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(measurementUnit1),
				(List<MeasurementUnit>)page.getItems());
		}
	}

	@Test
	public void testGetMeasurementUnitsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		MeasurementUnit measurementUnit1 =
			testGetMeasurementUnitsPage_addMeasurementUnit(
				randomMeasurementUnit());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		MeasurementUnit measurementUnit2 =
			testGetMeasurementUnitsPage_addMeasurementUnit(
				randomMeasurementUnit());

		for (EntityField entityField : entityFields) {
			Page<MeasurementUnit> page =
				measurementUnitResource.getMeasurementUnitsPage(
					getFilterString(entityField, "eq", measurementUnit1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(measurementUnit1),
				(List<MeasurementUnit>)page.getItems());
		}
	}

	@Test
	public void testGetMeasurementUnitsPageWithPagination() throws Exception {
		Page<MeasurementUnit> totalPage =
			measurementUnitResource.getMeasurementUnitsPage(null, null, null);

		int totalCount = GetterUtil.getInteger(totalPage.getTotalCount());

		MeasurementUnit measurementUnit1 =
			testGetMeasurementUnitsPage_addMeasurementUnit(
				randomMeasurementUnit());

		MeasurementUnit measurementUnit2 =
			testGetMeasurementUnitsPage_addMeasurementUnit(
				randomMeasurementUnit());

		MeasurementUnit measurementUnit3 =
			testGetMeasurementUnitsPage_addMeasurementUnit(
				randomMeasurementUnit());

		Page<MeasurementUnit> page1 =
			measurementUnitResource.getMeasurementUnitsPage(
				null, Pagination.of(1, totalCount + 2), null);

		List<MeasurementUnit> measurementUnits1 =
			(List<MeasurementUnit>)page1.getItems();

		Assert.assertEquals(
			measurementUnits1.toString(), totalCount + 2,
			measurementUnits1.size());

		Page<MeasurementUnit> page2 =
			measurementUnitResource.getMeasurementUnitsPage(
				null, Pagination.of(2, totalCount + 2), null);

		Assert.assertEquals(totalCount + 3, page2.getTotalCount());

		List<MeasurementUnit> measurementUnits2 =
			(List<MeasurementUnit>)page2.getItems();

		Assert.assertEquals(
			measurementUnits2.toString(), 1, measurementUnits2.size());

		Page<MeasurementUnit> page3 =
			measurementUnitResource.getMeasurementUnitsPage(
				null, Pagination.of(1, totalCount + 3), null);

		assertContains(
			measurementUnit1, (List<MeasurementUnit>)page3.getItems());
		assertContains(
			measurementUnit2, (List<MeasurementUnit>)page3.getItems());
		assertContains(
			measurementUnit3, (List<MeasurementUnit>)page3.getItems());
	}

	@Test
	public void testGetMeasurementUnitsPageWithSortDateTime() throws Exception {
		testGetMeasurementUnitsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, measurementUnit1, measurementUnit2) -> {
				BeanTestUtil.setProperty(
					measurementUnit1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetMeasurementUnitsPageWithSortDouble() throws Exception {
		testGetMeasurementUnitsPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, measurementUnit1, measurementUnit2) -> {
				BeanTestUtil.setProperty(
					measurementUnit1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(
					measurementUnit2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetMeasurementUnitsPageWithSortInteger() throws Exception {
		testGetMeasurementUnitsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, measurementUnit1, measurementUnit2) -> {
				BeanTestUtil.setProperty(
					measurementUnit1, entityField.getName(), 0);
				BeanTestUtil.setProperty(
					measurementUnit2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetMeasurementUnitsPageWithSortString() throws Exception {
		testGetMeasurementUnitsPageWithSort(
			EntityField.Type.STRING,
			(entityField, measurementUnit1, measurementUnit2) -> {
				Class<?> clazz = measurementUnit1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						measurementUnit1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						measurementUnit2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						measurementUnit1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						measurementUnit2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						measurementUnit1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						measurementUnit2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetMeasurementUnitsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, MeasurementUnit, MeasurementUnit, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		MeasurementUnit measurementUnit1 = randomMeasurementUnit();
		MeasurementUnit measurementUnit2 = randomMeasurementUnit();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, measurementUnit1, measurementUnit2);
		}

		measurementUnit1 = testGetMeasurementUnitsPage_addMeasurementUnit(
			measurementUnit1);

		measurementUnit2 = testGetMeasurementUnitsPage_addMeasurementUnit(
			measurementUnit2);

		for (EntityField entityField : entityFields) {
			Page<MeasurementUnit> ascPage =
				measurementUnitResource.getMeasurementUnitsPage(
					null, Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(measurementUnit1, measurementUnit2),
				(List<MeasurementUnit>)ascPage.getItems());

			Page<MeasurementUnit> descPage =
				measurementUnitResource.getMeasurementUnitsPage(
					null, Pagination.of(1, 2), entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(measurementUnit2, measurementUnit1),
				(List<MeasurementUnit>)descPage.getItems());
		}
	}

	protected MeasurementUnit testGetMeasurementUnitsPage_addMeasurementUnit(
			MeasurementUnit measurementUnit)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetMeasurementUnitsPage() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"measurementUnits",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 10);
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject measurementUnitsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/measurementUnits");

		long totalCount = measurementUnitsJSONObject.getLong("totalCount");

		MeasurementUnit measurementUnit1 =
			testGraphQLGetMeasurementUnitsPage_addMeasurementUnit();
		MeasurementUnit measurementUnit2 =
			testGraphQLGetMeasurementUnitsPage_addMeasurementUnit();

		measurementUnitsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/measurementUnits");

		Assert.assertEquals(
			totalCount + 2, measurementUnitsJSONObject.getLong("totalCount"));

		assertContains(
			measurementUnit1,
			Arrays.asList(
				MeasurementUnitSerDes.toDTOs(
					measurementUnitsJSONObject.getString("items"))));
		assertContains(
			measurementUnit2,
			Arrays.asList(
				MeasurementUnitSerDes.toDTOs(
					measurementUnitsJSONObject.getString("items"))));
	}

	protected MeasurementUnit
			testGraphQLGetMeasurementUnitsPage_addMeasurementUnit()
		throws Exception {

		return testGraphQLMeasurementUnit_addMeasurementUnit();
	}

	@Test
	public void testPostMeasurementUnit() throws Exception {
		MeasurementUnit randomMeasurementUnit = randomMeasurementUnit();

		MeasurementUnit postMeasurementUnit =
			testPostMeasurementUnit_addMeasurementUnit(randomMeasurementUnit);

		assertEquals(randomMeasurementUnit, postMeasurementUnit);
		assertValid(postMeasurementUnit);
	}

	protected MeasurementUnit testPostMeasurementUnit_addMeasurementUnit(
			MeasurementUnit measurementUnit)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteMeasurementUnitByExternalReferenceCode()
		throws Exception {

		@SuppressWarnings("PMD.UnusedLocalVariable")
		MeasurementUnit measurementUnit =
			testDeleteMeasurementUnitByExternalReferenceCode_addMeasurementUnit();

		assertHttpResponseStatusCode(
			204,
			measurementUnitResource.
				deleteMeasurementUnitByExternalReferenceCodeHttpResponse(
					measurementUnit.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			measurementUnitResource.
				getMeasurementUnitByExternalReferenceCodeHttpResponse(
					measurementUnit.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			measurementUnitResource.
				getMeasurementUnitByExternalReferenceCodeHttpResponse(
					measurementUnit.getExternalReferenceCode()));
	}

	protected MeasurementUnit
			testDeleteMeasurementUnitByExternalReferenceCode_addMeasurementUnit()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetMeasurementUnitByExternalReferenceCode()
		throws Exception {

		MeasurementUnit postMeasurementUnit =
			testGetMeasurementUnitByExternalReferenceCode_addMeasurementUnit();

		MeasurementUnit getMeasurementUnit =
			measurementUnitResource.getMeasurementUnitByExternalReferenceCode(
				postMeasurementUnit.getExternalReferenceCode());

		assertEquals(postMeasurementUnit, getMeasurementUnit);
		assertValid(getMeasurementUnit);
	}

	protected MeasurementUnit
			testGetMeasurementUnitByExternalReferenceCode_addMeasurementUnit()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetMeasurementUnitByExternalReferenceCode()
		throws Exception {

		MeasurementUnit measurementUnit =
			testGraphQLGetMeasurementUnitByExternalReferenceCode_addMeasurementUnit();

		Assert.assertTrue(
			equals(
				measurementUnit,
				MeasurementUnitSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"measurementUnitByExternalReferenceCode",
								new HashMap<String, Object>() {
									{
										put(
											"externalReferenceCode",
											"\"" +
												measurementUnit.
													getExternalReferenceCode() +
														"\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data",
						"Object/measurementUnitByExternalReferenceCode"))));
	}

	@Test
	public void testGraphQLGetMeasurementUnitByExternalReferenceCodeNotFound()
		throws Exception {

		String irrelevantExternalReferenceCode =
			"\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"measurementUnitByExternalReferenceCode",
						new HashMap<String, Object>() {
							{
								put(
									"externalReferenceCode",
									irrelevantExternalReferenceCode);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected MeasurementUnit
			testGraphQLGetMeasurementUnitByExternalReferenceCode_addMeasurementUnit()
		throws Exception {

		return testGraphQLMeasurementUnit_addMeasurementUnit();
	}

	@Test
	public void testPatchMeasurementUnitByExternalReferenceCode()
		throws Exception {

		Assert.assertTrue(false);
	}

	@Test
	public void testDeleteMeasurementUnitByKey() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		MeasurementUnit measurementUnit =
			testDeleteMeasurementUnitByKey_addMeasurementUnit();

		assertHttpResponseStatusCode(
			204,
			measurementUnitResource.deleteMeasurementUnitByKeyHttpResponse(
				measurementUnit.getKey()));

		assertHttpResponseStatusCode(
			404,
			measurementUnitResource.getMeasurementUnitByKeyHttpResponse(
				measurementUnit.getKey()));

		assertHttpResponseStatusCode(
			404,
			measurementUnitResource.getMeasurementUnitByKeyHttpResponse(
				measurementUnit.getKey()));
	}

	protected MeasurementUnit
			testDeleteMeasurementUnitByKey_addMeasurementUnit()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetMeasurementUnitByKey() throws Exception {
		MeasurementUnit postMeasurementUnit =
			testGetMeasurementUnitByKey_addMeasurementUnit();

		MeasurementUnit getMeasurementUnit =
			measurementUnitResource.getMeasurementUnitByKey(
				postMeasurementUnit.getKey());

		assertEquals(postMeasurementUnit, getMeasurementUnit);
		assertValid(getMeasurementUnit);
	}

	protected MeasurementUnit testGetMeasurementUnitByKey_addMeasurementUnit()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetMeasurementUnitByKey() throws Exception {
		MeasurementUnit measurementUnit =
			testGraphQLGetMeasurementUnitByKey_addMeasurementUnit();

		Assert.assertTrue(
			equals(
				measurementUnit,
				MeasurementUnitSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"measurementUnitByKey",
								new HashMap<String, Object>() {
									{
										put(
											"key",
											"\"" + measurementUnit.getKey() +
												"\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/measurementUnitByKey"))));
	}

	@Test
	public void testGraphQLGetMeasurementUnitByKeyNotFound() throws Exception {
		String irrelevantKey = "\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"measurementUnitByKey",
						new HashMap<String, Object>() {
							{
								put("key", irrelevantKey);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected MeasurementUnit
			testGraphQLGetMeasurementUnitByKey_addMeasurementUnit()
		throws Exception {

		return testGraphQLMeasurementUnit_addMeasurementUnit();
	}

	@Test
	public void testPatchMeasurementUnitByKey() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGetMeasurementUnitsByType() throws Exception {
		String measurementUnitType =
			testGetMeasurementUnitsByType_getMeasurementUnitType();
		String irrelevantMeasurementUnitType =
			testGetMeasurementUnitsByType_getIrrelevantMeasurementUnitType();

		Page<MeasurementUnit> page =
			measurementUnitResource.getMeasurementUnitsByType(
				measurementUnitType, Pagination.of(1, 10), null);

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantMeasurementUnitType != null) {
			MeasurementUnit irrelevantMeasurementUnit =
				testGetMeasurementUnitsByType_addMeasurementUnit(
					irrelevantMeasurementUnitType,
					randomIrrelevantMeasurementUnit());

			page = measurementUnitResource.getMeasurementUnitsByType(
				irrelevantMeasurementUnitType, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantMeasurementUnit),
				(List<MeasurementUnit>)page.getItems());
			assertValid(
				page,
				testGetMeasurementUnitsByType_getExpectedActions(
					irrelevantMeasurementUnitType));
		}

		MeasurementUnit measurementUnit1 =
			testGetMeasurementUnitsByType_addMeasurementUnit(
				measurementUnitType, randomMeasurementUnit());

		MeasurementUnit measurementUnit2 =
			testGetMeasurementUnitsByType_addMeasurementUnit(
				measurementUnitType, randomMeasurementUnit());

		page = measurementUnitResource.getMeasurementUnitsByType(
			measurementUnitType, Pagination.of(1, 10), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(measurementUnit1, measurementUnit2),
			(List<MeasurementUnit>)page.getItems());
		assertValid(
			page,
			testGetMeasurementUnitsByType_getExpectedActions(
				measurementUnitType));

		measurementUnitResource.deleteMeasurementUnit(measurementUnit1.getId());

		measurementUnitResource.deleteMeasurementUnit(measurementUnit2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetMeasurementUnitsByType_getExpectedActions(
				String measurementUnitType)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetMeasurementUnitsByTypeWithPagination() throws Exception {
		String measurementUnitType =
			testGetMeasurementUnitsByType_getMeasurementUnitType();

		MeasurementUnit measurementUnit1 =
			testGetMeasurementUnitsByType_addMeasurementUnit(
				measurementUnitType, randomMeasurementUnit());

		MeasurementUnit measurementUnit2 =
			testGetMeasurementUnitsByType_addMeasurementUnit(
				measurementUnitType, randomMeasurementUnit());

		MeasurementUnit measurementUnit3 =
			testGetMeasurementUnitsByType_addMeasurementUnit(
				measurementUnitType, randomMeasurementUnit());

		Page<MeasurementUnit> page1 =
			measurementUnitResource.getMeasurementUnitsByType(
				measurementUnitType, Pagination.of(1, 2), null);

		List<MeasurementUnit> measurementUnits1 =
			(List<MeasurementUnit>)page1.getItems();

		Assert.assertEquals(
			measurementUnits1.toString(), 2, measurementUnits1.size());

		Page<MeasurementUnit> page2 =
			measurementUnitResource.getMeasurementUnitsByType(
				measurementUnitType, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<MeasurementUnit> measurementUnits2 =
			(List<MeasurementUnit>)page2.getItems();

		Assert.assertEquals(
			measurementUnits2.toString(), 1, measurementUnits2.size());

		Page<MeasurementUnit> page3 =
			measurementUnitResource.getMeasurementUnitsByType(
				measurementUnitType, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(measurementUnit1, measurementUnit2, measurementUnit3),
			(List<MeasurementUnit>)page3.getItems());
	}

	@Test
	public void testGetMeasurementUnitsByTypeWithSortDateTime()
		throws Exception {

		testGetMeasurementUnitsByTypeWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, measurementUnit1, measurementUnit2) -> {
				BeanTestUtil.setProperty(
					measurementUnit1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetMeasurementUnitsByTypeWithSortDouble() throws Exception {
		testGetMeasurementUnitsByTypeWithSort(
			EntityField.Type.DOUBLE,
			(entityField, measurementUnit1, measurementUnit2) -> {
				BeanTestUtil.setProperty(
					measurementUnit1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(
					measurementUnit2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetMeasurementUnitsByTypeWithSortInteger()
		throws Exception {

		testGetMeasurementUnitsByTypeWithSort(
			EntityField.Type.INTEGER,
			(entityField, measurementUnit1, measurementUnit2) -> {
				BeanTestUtil.setProperty(
					measurementUnit1, entityField.getName(), 0);
				BeanTestUtil.setProperty(
					measurementUnit2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetMeasurementUnitsByTypeWithSortString() throws Exception {
		testGetMeasurementUnitsByTypeWithSort(
			EntityField.Type.STRING,
			(entityField, measurementUnit1, measurementUnit2) -> {
				Class<?> clazz = measurementUnit1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						measurementUnit1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						measurementUnit2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						measurementUnit1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						measurementUnit2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						measurementUnit1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						measurementUnit2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetMeasurementUnitsByTypeWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, MeasurementUnit, MeasurementUnit, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		String measurementUnitType =
			testGetMeasurementUnitsByType_getMeasurementUnitType();

		MeasurementUnit measurementUnit1 = randomMeasurementUnit();
		MeasurementUnit measurementUnit2 = randomMeasurementUnit();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, measurementUnit1, measurementUnit2);
		}

		measurementUnit1 = testGetMeasurementUnitsByType_addMeasurementUnit(
			measurementUnitType, measurementUnit1);

		measurementUnit2 = testGetMeasurementUnitsByType_addMeasurementUnit(
			measurementUnitType, measurementUnit2);

		for (EntityField entityField : entityFields) {
			Page<MeasurementUnit> ascPage =
				measurementUnitResource.getMeasurementUnitsByType(
					measurementUnitType, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(measurementUnit1, measurementUnit2),
				(List<MeasurementUnit>)ascPage.getItems());

			Page<MeasurementUnit> descPage =
				measurementUnitResource.getMeasurementUnitsByType(
					measurementUnitType, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(measurementUnit2, measurementUnit1),
				(List<MeasurementUnit>)descPage.getItems());
		}
	}

	protected MeasurementUnit testGetMeasurementUnitsByType_addMeasurementUnit(
			String measurementUnitType, MeasurementUnit measurementUnit)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String testGetMeasurementUnitsByType_getMeasurementUnitType()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetMeasurementUnitsByType_getIrrelevantMeasurementUnitType()
		throws Exception {

		return null;
	}

	@Test
	public void testDeleteMeasurementUnit() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		MeasurementUnit measurementUnit =
			testDeleteMeasurementUnit_addMeasurementUnit();

		assertHttpResponseStatusCode(
			204,
			measurementUnitResource.deleteMeasurementUnitHttpResponse(
				measurementUnit.getId()));

		assertHttpResponseStatusCode(
			404,
			measurementUnitResource.getMeasurementUnitHttpResponse(
				measurementUnit.getId()));

		assertHttpResponseStatusCode(
			404,
			measurementUnitResource.getMeasurementUnitHttpResponse(
				measurementUnit.getId()));
	}

	protected MeasurementUnit testDeleteMeasurementUnit_addMeasurementUnit()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteMeasurementUnit() throws Exception {
		MeasurementUnit measurementUnit =
			testGraphQLDeleteMeasurementUnit_addMeasurementUnit();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteMeasurementUnit",
						new HashMap<String, Object>() {
							{
								put("id", measurementUnit.getId());
							}
						})),
				"JSONObject/data", "Object/deleteMeasurementUnit"));
		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"measurementUnit",
					new HashMap<String, Object>() {
						{
							put("id", measurementUnit.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	protected MeasurementUnit
			testGraphQLDeleteMeasurementUnit_addMeasurementUnit()
		throws Exception {

		return testGraphQLMeasurementUnit_addMeasurementUnit();
	}

	@Test
	public void testGetMeasurementUnit() throws Exception {
		MeasurementUnit postMeasurementUnit =
			testGetMeasurementUnit_addMeasurementUnit();

		MeasurementUnit getMeasurementUnit =
			measurementUnitResource.getMeasurementUnit(
				postMeasurementUnit.getId());

		assertEquals(postMeasurementUnit, getMeasurementUnit);
		assertValid(getMeasurementUnit);
	}

	protected MeasurementUnit testGetMeasurementUnit_addMeasurementUnit()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetMeasurementUnit() throws Exception {
		MeasurementUnit measurementUnit =
			testGraphQLGetMeasurementUnit_addMeasurementUnit();

		Assert.assertTrue(
			equals(
				measurementUnit,
				MeasurementUnitSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"measurementUnit",
								new HashMap<String, Object>() {
									{
										put("id", measurementUnit.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/measurementUnit"))));
	}

	@Test
	public void testGraphQLGetMeasurementUnitNotFound() throws Exception {
		Long irrelevantId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"measurementUnit",
						new HashMap<String, Object>() {
							{
								put("id", irrelevantId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected MeasurementUnit testGraphQLGetMeasurementUnit_addMeasurementUnit()
		throws Exception {

		return testGraphQLMeasurementUnit_addMeasurementUnit();
	}

	@Test
	public void testPatchMeasurementUnit() throws Exception {
		Assert.assertTrue(false);
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected MeasurementUnit testGraphQLMeasurementUnit_addMeasurementUnit()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(
		MeasurementUnit measurementUnit,
		List<MeasurementUnit> measurementUnits) {

		boolean contains = false;

		for (MeasurementUnit item : measurementUnits) {
			if (equals(measurementUnit, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			measurementUnits + " does not contain " + measurementUnit,
			contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		MeasurementUnit measurementUnit1, MeasurementUnit measurementUnit2) {

		Assert.assertTrue(
			measurementUnit1 + " does not equal " + measurementUnit2,
			equals(measurementUnit1, measurementUnit2));
	}

	protected void assertEquals(
		List<MeasurementUnit> measurementUnits1,
		List<MeasurementUnit> measurementUnits2) {

		Assert.assertEquals(measurementUnits1.size(), measurementUnits2.size());

		for (int i = 0; i < measurementUnits1.size(); i++) {
			MeasurementUnit measurementUnit1 = measurementUnits1.get(i);
			MeasurementUnit measurementUnit2 = measurementUnits2.get(i);

			assertEquals(measurementUnit1, measurementUnit2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<MeasurementUnit> measurementUnits1,
		List<MeasurementUnit> measurementUnits2) {

		Assert.assertEquals(measurementUnits1.size(), measurementUnits2.size());

		for (MeasurementUnit measurementUnit1 : measurementUnits1) {
			boolean contains = false;

			for (MeasurementUnit measurementUnit2 : measurementUnits2) {
				if (equals(measurementUnit1, measurementUnit2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				measurementUnits2 + " does not contain " + measurementUnit1,
				contains);
		}
	}

	protected void assertValid(MeasurementUnit measurementUnit)
		throws Exception {

		boolean valid = true;

		if (measurementUnit.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("companyId", additionalAssertFieldName)) {
				if (measurementUnit.getCompanyId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (measurementUnit.getExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("key", additionalAssertFieldName)) {
				if (measurementUnit.getKey() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (measurementUnit.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("primary", additionalAssertFieldName)) {
				if (measurementUnit.getPrimary() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("priority", additionalAssertFieldName)) {
				if (measurementUnit.getPriority() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("rate", additionalAssertFieldName)) {
				if (measurementUnit.getRate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("type", additionalAssertFieldName)) {
				if (measurementUnit.getType() == null) {
					valid = false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(Page<MeasurementUnit> page) {
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<MeasurementUnit> page,
		Map<String, Map<String, String>> expectedActions) {

		boolean valid = false;

		java.util.Collection<MeasurementUnit> measurementUnits =
			page.getItems();

		int size = measurementUnits.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);

		Map<String, Map<String, String>> actions = page.getActions();

		for (String key : expectedActions.keySet()) {
			Map action = actions.get(key);

			Assert.assertNotNull(key + " does not contain an action", action);

			Map expectedAction = expectedActions.get(key);

			Assert.assertEquals(
				expectedAction.get("method"), action.get("method"));
			Assert.assertEquals(expectedAction.get("href"), action.get("href"));
		}
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (java.lang.reflect.Field field :
				getDeclaredFields(
					com.liferay.headless.commerce.admin.site.setting.dto.v1_0.
						MeasurementUnit.class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			graphQLFields.addAll(getGraphQLFields(field));
		}

		return graphQLFields;
	}

	protected List<GraphQLField> getGraphQLFields(
			java.lang.reflect.Field... fields)
		throws Exception {

		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (java.lang.reflect.Field field : fields) {
			com.liferay.portal.vulcan.graphql.annotation.GraphQLField
				vulcanGraphQLField = field.getAnnotation(
					com.liferay.portal.vulcan.graphql.annotation.GraphQLField.
						class);

			if (vulcanGraphQLField != null) {
				Class<?> clazz = field.getType();

				if (clazz.isArray()) {
					clazz = clazz.getComponentType();
				}

				List<GraphQLField> childrenGraphQLFields = getGraphQLFields(
					getDeclaredFields(clazz));

				graphQLFields.add(
					new GraphQLField(field.getName(), childrenGraphQLFields));
			}
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(
		MeasurementUnit measurementUnit1, MeasurementUnit measurementUnit2) {

		if (measurementUnit1 == measurementUnit2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("companyId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						measurementUnit1.getCompanyId(),
						measurementUnit2.getCompanyId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						measurementUnit1.getExternalReferenceCode(),
						measurementUnit2.getExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						measurementUnit1.getId(), measurementUnit2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("key", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						measurementUnit1.getKey(), measurementUnit2.getKey())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!equals(
						(Map)measurementUnit1.getName(),
						(Map)measurementUnit2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("primary", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						measurementUnit1.getPrimary(),
						measurementUnit2.getPrimary())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("priority", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						measurementUnit1.getPriority(),
						measurementUnit2.getPriority())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("rate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						measurementUnit1.getRate(),
						measurementUnit2.getRate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("type", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						measurementUnit1.getType(),
						measurementUnit2.getType())) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		return true;
	}

	protected boolean equals(
		Map<String, Object> map1, Map<String, Object> map2) {

		if (Objects.equals(map1.keySet(), map2.keySet())) {
			for (Map.Entry<String, Object> entry : map1.entrySet()) {
				if (entry.getValue() instanceof Map) {
					if (!equals(
							(Map)entry.getValue(),
							(Map)map2.get(entry.getKey()))) {

						return false;
					}
				}
				else if (!Objects.deepEquals(
							entry.getValue(), map2.get(entry.getKey()))) {

					return false;
				}
			}

			return true;
		}

		return false;
	}

	protected java.lang.reflect.Field[] getDeclaredFields(Class clazz)
		throws Exception {

		return TransformUtil.transform(
			ReflectionUtil.getDeclaredFields(clazz),
			field -> {
				if (field.isSynthetic()) {
					return null;
				}

				return field;
			},
			java.lang.reflect.Field.class);
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_measurementUnitResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_measurementUnitResource;

		EntityModel entityModel = entityModelResource.getEntityModel(
			new MultivaluedHashMap());

		if (entityModel == null) {
			return Collections.emptyList();
		}

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		return TransformUtil.transform(
			getEntityFields(),
			entityField -> {
				if (!Objects.equals(entityField.getType(), type) ||
					ArrayUtil.contains(
						getIgnoredEntityFieldNames(), entityField.getName())) {

					return null;
				}

				return entityField;
			});
	}

	protected String getFilterString(
		EntityField entityField, String operator,
		MeasurementUnit measurementUnit) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("companyId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("externalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(measurementUnit.getExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("key")) {
			sb.append("'");
			sb.append(String.valueOf(measurementUnit.getKey()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("name")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("primary")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("priority")) {
			sb.append(String.valueOf(measurementUnit.getPriority()));

			return sb.toString();
		}

		if (entityFieldName.equals("rate")) {
			sb.append(String.valueOf(measurementUnit.getRate()));

			return sb.toString();
		}

		if (entityFieldName.equals("type")) {
			sb.append("'");
			sb.append(String.valueOf(measurementUnit.getType()));
			sb.append("'");

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected String invoke(String query) throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			JSONUtil.put(
				"query", query
			).toString(),
			"application/json");
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);
		httpInvoker.path("http://localhost:8080/o/graphql");
		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return httpResponse.getContent();
	}

	protected JSONObject invokeGraphQLMutation(GraphQLField graphQLField)
		throws Exception {

		GraphQLField mutationGraphQLField = new GraphQLField(
			"mutation", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(mutationGraphQLField.toString()));
	}

	protected JSONObject invokeGraphQLQuery(GraphQLField graphQLField)
		throws Exception {

		GraphQLField queryGraphQLField = new GraphQLField(
			"query", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(queryGraphQLField.toString()));
	}

	protected MeasurementUnit randomMeasurementUnit() throws Exception {
		return new MeasurementUnit() {
			{
				companyId = RandomTestUtil.randomLong();
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				key = StringUtil.toLowerCase(RandomTestUtil.randomString());
				primary = RandomTestUtil.randomBoolean();
				priority = RandomTestUtil.randomDouble();
				rate = RandomTestUtil.randomDouble();
				type = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	protected MeasurementUnit randomIrrelevantMeasurementUnit()
		throws Exception {

		MeasurementUnit randomIrrelevantMeasurementUnit =
			randomMeasurementUnit();

		return randomIrrelevantMeasurementUnit;
	}

	protected MeasurementUnit randomPatchMeasurementUnit() throws Exception {
		return randomMeasurementUnit();
	}

	protected MeasurementUnitResource measurementUnitResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	protected static class BeanTestUtil {

		public static void copyProperties(Object source, Object target)
			throws Exception {

			Class<?> sourceClass = _getSuperClass(source.getClass());

			Class<?> targetClass = target.getClass();

			for (java.lang.reflect.Field field :
					sourceClass.getDeclaredFields()) {

				if (field.isSynthetic()) {
					continue;
				}

				Method getMethod = _getMethod(
					sourceClass, field.getName(), "get");

				Method setMethod = _getMethod(
					targetClass, field.getName(), "set",
					getMethod.getReturnType());

				setMethod.invoke(target, getMethod.invoke(source));
			}
		}

		public static boolean hasProperty(Object bean, String name) {
			Method setMethod = _getMethod(
				bean.getClass(), "set" + StringUtil.upperCaseFirstLetter(name));

			if (setMethod != null) {
				return true;
			}

			return false;
		}

		public static void setProperty(Object bean, String name, Object value)
			throws Exception {

			Class<?> clazz = bean.getClass();

			Method setMethod = _getMethod(
				clazz, "set" + StringUtil.upperCaseFirstLetter(name));

			if (setMethod == null) {
				throw new NoSuchMethodException();
			}

			Class<?>[] parameterTypes = setMethod.getParameterTypes();

			setMethod.invoke(bean, _translateValue(parameterTypes[0], value));
		}

		private static Method _getMethod(Class<?> clazz, String name) {
			for (Method method : clazz.getMethods()) {
				if (name.equals(method.getName()) &&
					(method.getParameterCount() == 1) &&
					_parameterTypes.contains(method.getParameterTypes()[0])) {

					return method;
				}
			}

			return null;
		}

		private static Method _getMethod(
				Class<?> clazz, String fieldName, String prefix,
				Class<?>... parameterTypes)
			throws Exception {

			return clazz.getMethod(
				prefix + StringUtil.upperCaseFirstLetter(fieldName),
				parameterTypes);
		}

		private static Class<?> _getSuperClass(Class<?> clazz) {
			Class<?> superClass = clazz.getSuperclass();

			if ((superClass == null) || (superClass == Object.class)) {
				return clazz;
			}

			return superClass;
		}

		private static Object _translateValue(
			Class<?> parameterType, Object value) {

			if ((value instanceof Integer) &&
				parameterType.equals(Long.class)) {

				Integer intValue = (Integer)value;

				return intValue.longValue();
			}

			return value;
		}

		private static final Set<Class<?>> _parameterTypes = new HashSet<>(
			Arrays.asList(
				Boolean.class, Date.class, Double.class, Integer.class,
				Long.class, Map.class, String.class));

	}

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(String key, List<GraphQLField> graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = Arrays.asList(graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			List<GraphQLField> graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = graphQLFields;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(_key);

			if (!_parameterMap.isEmpty()) {
				sb.append("(");

				for (Map.Entry<String, Object> entry :
						_parameterMap.entrySet()) {

					sb.append(entry.getKey());
					sb.append(": ");
					sb.append(entry.getValue());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append(")");
			}

			if (!_graphQLFields.isEmpty()) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append("}");
			}

			return sb.toString();
		}

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final com.liferay.portal.kernel.log.Log _log =
		LogFactoryUtil.getLog(BaseMeasurementUnitResourceTestCase.class);

	private static DateFormat _dateFormat;

	@Inject
	private com.liferay.headless.commerce.admin.site.setting.resource.v1_0.
		MeasurementUnitResource _measurementUnitResource;

}