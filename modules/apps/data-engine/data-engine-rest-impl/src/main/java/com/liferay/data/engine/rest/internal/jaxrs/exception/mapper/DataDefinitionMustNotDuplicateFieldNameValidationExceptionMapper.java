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

package com.liferay.data.engine.rest.internal.jaxrs.exception.mapper;

import com.liferay.data.engine.rest.resource.exception.DataDefinitionValidationException;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Data.Engine.REST)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Data.Engine.REST.DataDefinitionMustNotDuplicateFieldNameValidationExceptionMapper"
	},
	service = ExceptionMapper.class
)
public class DataDefinitionMustNotDuplicateFieldNameValidationExceptionMapper
	extends BaseExceptionMapper
		<DataDefinitionValidationException.MustNotDuplicateFieldName> {

	@Override
	protected Problem getProblem(
		DataDefinitionValidationException.MustNotDuplicateFieldName
			mustNotDuplicateFieldName) {

		return new Problem(
			StringUtil.merge(
				mustNotDuplicateFieldName.getDuplicatedFieldNames(),
				StringPool.COMMA),
			Response.Status.BAD_REQUEST, mustNotDuplicateFieldName.getMessage(),
			DataDefinitionValidationException.MustNotDuplicateFieldName.class.
				getName());
	}

}