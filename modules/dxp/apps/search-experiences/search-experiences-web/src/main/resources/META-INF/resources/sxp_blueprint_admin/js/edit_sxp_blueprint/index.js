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

import React, {useEffect, useState} from 'react';

import useClipboardJS from '../hooks/useClipboardJS';
import ErrorBoundary from '../shared/ErrorBoundary';
import ThemeContext from '../shared/ThemeContext';
import {COPY_BUTTON_CSS_CLASS} from '../utils/constants';
import fetchData from '../utils/fetch/fetch_data';
import renameKeys from '../utils/language/rename_keys';
import transformLocale from '../utils/language/transform_locale';
import {openInitialSuccessToast} from '../utils/toasts';
import EditSXPBlueprintForm from './EditSXPBlueprintForm';

export default function ({
	contextPath,
	defaultLocale,
	featureFlagLps153813,
	isCompanyAdmin,
	learnMessages,
	locale,
	namespace,
	redirectURL,
	sxpBlueprintId,
}) {
	const [resource, setResource] = useState(null);

	useClipboardJS('.' + COPY_BUTTON_CSS_CLASS);

	useEffect(() => {
		openInitialSuccessToast();

		fetchData(
			`/o/search-experiences-rest/v1.0/sxp-blueprints/${sxpBlueprintId}`,
			{headers: {'X-Liferay-Accept-All-Languages': true}}
		)
			.then((responseContent) => setResource(responseContent))
			.catch(() => setResource({}));
	}, []); //eslint-disable-line

	if (!resource) {
		return null;
	}

	return (
		<ThemeContext.Provider
			value={{
				availableLanguages: Liferay.Language.available,
				contextPath,
				defaultLocale,
				featureFlagLps153813,
				isCompanyAdmin,
				learnMessages,
				locale,
				namespace,
				redirectURL,
			}}
		>
			<div className="edit-sxp-blueprint-root">
				<ErrorBoundary>
					<EditSXPBlueprintForm
						entityJSON={resource.entityJSON}
						initialConfiguration={resource.configuration}
						initialDescription={resource.description}
						initialDescriptionI18n={renameKeys(
							resource.description_i18n,
							transformLocale
						)}
						initialSXPElementInstances={resource.elementInstances}
						initialTitle={resource.title}
						initialTitleI18n={renameKeys(
							resource.title_i18n,
							transformLocale
						)}
						sxpBlueprintId={sxpBlueprintId}
					/>
				</ErrorBoundary>
			</div>
		</ThemeContext.Provider>
	);
}
