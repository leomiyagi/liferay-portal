/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {
	getActivationDownloadKey,
	getAggregatedActivationDownloadKey,
	getExportedLicenseKeys,
	getMultipleActivationDownloadKey,
} from '../../../../../common/services/liferay/rest/raysource/LicenseKeys';
import downloadFromBlob from '../../../../../common/utils/downloadFromBlob';
import {EXTENSION_FILE_TYPES, STATUS_CODE} from '../../../utils/constants';

export async function downloadActivationLicenseKey(
	licenseKey,
	provisioningServerAPI,
	sessionId,
	activationKeyName,
	activationKeyVersion,
	projectName
) {
	const license = await getActivationDownloadKey(
		licenseKey,
		provisioningServerAPI,
		sessionId
	);

	if (license.status === STATUS_CODE.success) {
		const contentType = license.headers.get('content-type');
		const extensionFile = EXTENSION_FILE_TYPES[contentType] || '.txt';
		const licenseBlob = await license.blob();

		const projectFileName = projectName.replaceAll(' ', '').toLowerCase();
		const productNameFormated = activationKeyName
			.replaceAll(' ', '')
			.toLowerCase();

		return downloadFromBlob(
			licenseBlob,
			`activation-key-${productNameFormated}-${activationKeyVersion
				.replaceAll(' ', '-')
				.toLowerCase()}-${projectFileName}${extensionFile}`
		);
	}
}

export async function downloadAggregatedActivationKey(
	selectedKeysIDs,
	provisioningServerAPI,
	sessionId,
	selectedKeysObjects,
	projectName
) {
	const license = await getAggregatedActivationDownloadKey(
		selectedKeysIDs,
		provisioningServerAPI,
		sessionId
	);

	const DIFFERENT_AGGREGATED_NAMES = 'multiple-products';
	const DIFFERENT_AGGREGATED_VERSIONS = 'multiple-versions';

	const aggregatedNamesAndVersions = selectedKeysObjects.reduce(
		(selectedKeysAccumulator, selectedKeysObject) => {
			if (
				selectedKeysObject.productName !==
				selectedKeysAccumulator.productName
			) {
				selectedKeysAccumulator.productName = DIFFERENT_AGGREGATED_NAMES;
			}
			if (
				selectedKeysObject.productVersion !==
				selectedKeysAccumulator.productVersion
			) {
				selectedKeysAccumulator.productVersion = DIFFERENT_AGGREGATED_VERSIONS;
			}

			return selectedKeysAccumulator;
		},
		{
			productName: selectedKeysObjects[0]?.productName,
			productVersion: selectedKeysObjects[0]?.productVersion,
		}
	);

	const projectFileName = projectName.replaceAll(' ', '').toLowerCase();

	const productFileName = aggregatedNamesAndVersions.productName
		.replaceAll(' ', '')
		.toLowerCase();

	const versionFileName = aggregatedNamesAndVersions.productVersion;

	if (license.status === STATUS_CODE.success) {
		const contentType = license.headers.get('content-type');
		const extensionFile = EXTENSION_FILE_TYPES[contentType] || '.txt';
		const licenseBlob = await license.blob();

		return downloadFromBlob(
			licenseBlob,
			`activation-key-${productFileName}-${versionFileName}-${projectFileName}${extensionFile}`
		);
	}
}

export async function downloadMultipleActivationKey(
	selectedKeysIDs,
	provisioningServerAPI,
	sessionId,
	projectName
) {
	const license = await getMultipleActivationDownloadKey(
		selectedKeysIDs,
		provisioningServerAPI,
		sessionId
	);

	const projectFileName = projectName.replaceAll(' ', '').toLowerCase();

	if (license.status === STATUS_CODE.success) {
		const contentType = license.headers.get('content-type');
		const extensionFile = EXTENSION_FILE_TYPES[contentType] || '.zip';
		const licenseBlob = await license.blob();

		return downloadFromBlob(
			licenseBlob,
			`activation-key-${projectFileName}${extensionFile}`
		);
	}
}

export async function downloadAllKeysDetails(
	accountKey,
	provisioningServerAPI,
	sessionId,
	productName
) {
	const license = await getExportedLicenseKeys(
		accountKey,
		provisioningServerAPI,
		sessionId,
		productName
	);

	if (license.status === STATUS_CODE.success) {
		const contentType = license.headers.get('content-type');
		const extensionFile = EXTENSION_FILE_TYPES[contentType] || '.txt';
		const licenseBlob = await license.blob();

		return downloadFromBlob(licenseBlob, `activation-keys${extensionFile}`);
	}
}
