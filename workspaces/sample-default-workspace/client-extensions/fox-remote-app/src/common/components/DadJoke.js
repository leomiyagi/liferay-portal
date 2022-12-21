import React from 'react';

class DadJoke extends React.Component {
	constructor(props) {
		super(props);

		this.oAuth2Client = props.oAuth2Client;
		this.state = "";
	}

	componentDidMount() {
		this._request = this.oAuth2Client.fetch(
			'/dad-joke'
		).then(response => response.text()
		).then(text => {
			this._request = null;
			this.setState(text);
		});
	}

	componentWillUnmount() {
		if (this._request) {
			this._request.cancel();
		}
	}

	render() {
		if (this.state === null) {
			return <div>Loading...</div>
		}
		else {
			return <div>{this.state}</div>
		}
	}
}

export default DadJoke;
