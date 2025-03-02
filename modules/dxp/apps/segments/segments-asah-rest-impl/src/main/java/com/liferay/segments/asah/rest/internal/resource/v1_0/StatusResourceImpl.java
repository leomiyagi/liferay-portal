/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.segments.asah.rest.internal.resource.v1_0;

import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.segments.asah.rest.dto.v1_0.Experiment;
import com.liferay.segments.asah.rest.dto.v1_0.Status;
import com.liferay.segments.asah.rest.resource.v1_0.StatusResource;
import com.liferay.segments.constants.SegmentsExperimentConstants;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.SegmentsExperimentService;

import javax.ws.rs.ClientErrorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/status.properties",
	scope = ServiceScope.PROTOTYPE, service = StatusResource.class
)
public class StatusResourceImpl extends BaseStatusResourceImpl {

	@Override
	public Experiment postExperimentStatus(
			Long segmentsExperimentKey, Status status)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.popServiceContext();

		if (serviceContext == null) {
			serviceContext = new ServiceContext();
		}

		serviceContext.setAttribute("updateAsah", Boolean.FALSE);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		SegmentsExperimentConstants.Status segmentsExperimentConstantsStatus =
			SegmentsExperimentConstants.Status.parse(status.getStatus());

		if (segmentsExperimentConstantsStatus == null) {
			throw new ClientErrorException("Experiment status is invalid", 422);
		}

		return _toExperiment(
			_segmentsExperimentService.updateSegmentsExperimentStatus(
				String.valueOf(segmentsExperimentKey),
				status.getWinnerVariantId(),
				segmentsExperimentConstantsStatus.getValue()));
	}

	private Experiment _toExperiment(SegmentsExperiment segmentsExperiment) {
		SegmentsExperimentConstants.Status segmentsExperimentConstantsStatus =
			SegmentsExperimentConstants.Status.valueOf(
				segmentsExperiment.getStatus());

		return new Experiment() {
			{
				dateCreated = segmentsExperiment.getCreateDate();
				dateModified = segmentsExperiment.getModifiedDate();
				description = segmentsExperiment.getDescription();
				id = segmentsExperiment.getSegmentsExperimentKey();
				name = segmentsExperiment.getName();
				siteId = segmentsExperiment.getGroupId();
				status = segmentsExperimentConstantsStatus.toString();
				winnerVariantId =
					segmentsExperiment.getWinnerSegmentsExperienceId();
			}
		};
	}

	@Reference
	private SegmentsExperimentService _segmentsExperimentService;

}