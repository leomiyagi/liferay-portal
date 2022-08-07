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

import ClayForm from '@clayui/form';
import classNames from 'classnames';

import PRMFormFieldProps from '../../interfaces/prmFormFieldProps';

interface IProps {
	children?: React.ReactNode;
	error?: string;
	touched: boolean;
}

const WrapperInput = ({
	children,
	error,
	label,
	required,
	touched,
}: PRMFormFieldProps & IProps) => (
	<div
		className={classNames('mb-4', {
			'has-error': touched && error,
			'has-success': touched && !error,
		})}
	>
		{label && (
			<label>
				{label}

				{required && <span className="text-danger">*</span>}
			</label>
		)}

		{children}

		{error && touched && (
			<ClayForm.FeedbackItem>{error}</ClayForm.FeedbackItem>
		)}
	</div>
);

export default WrapperInput;
