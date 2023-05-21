import React, { Component, Fragment } from 'react';
import appService from '../common/app-service';
import { useLocation } from 'react-router-dom';


class PhotobookImages extends Component {

	

	constructor(props) {
		super(props);
		this.state = {
			photobookImages: null
		}
		console.log( JSON.stringify( this.props ) );
	}



	render() {
		let printImages = 'loading...';
		if ( this.state.photobookImages != null ) {
			printImages = "OK";
		}
		return <Fragment>
			<p>{printImages}</p>
		</Fragment>
	}

}

export default PhotobookImages;