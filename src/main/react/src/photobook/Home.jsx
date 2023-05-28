import React, { Component, Fragment } from 'react';

import appService from '../common/app-service';

import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
import ListItemAvatar from '@mui/material/ListItemAvatar';
import Avatar from '@mui/material/Avatar';
import ImageIcon from '@mui/icons-material/Image';
import { Button } from 'react-bootstrap';

import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

const imageStyle = {
	   width: '100%', 
	   maxWidth: '600px'
}

class Home extends Component {

	constructor(props) {
		super(props);
		this.handlePhotobook = this.handlePhotobook.bind(this);
		this.state = {
			photobookList: null,
			photobookId: null,
			photobookImages: null
		}
	}

	componentDidMount() {
		this.handleHome();
	}
	
	handleHome() {
		this.setState( { photobookId:null, photobookImages:null } );
		var reactState = this;
		appService.doAjaxJson('GET', '/photobook/view/list', null).then(response => {
			if (response.success) {
				reactState.setState({
					photobookList: response.result
				})
			} else {
				reactState.setState({
					supportedExtensions: null
				})
			}
		})
	};

	handlePhotobook( photobookId ) {
		var reactState = this;
		appService.doAjaxJson('GET', '/photobook/view/images/'+photobookId, null).then(response => {
			if (response.success) {
				reactState.setState({
					photobookId: photobookId,
					photobookImages: response.result
				})
			} else {
				reactState.setState({
					supportedExtensions: null
				})
			}
		})
	};

	render() {
		let printList = 'loading...';
		if ( this.state.photobookImages != null ) {
			this.setState( { photobookList:null } )
			let count = 0;
			let renderList = this.state.photobookImages.content.data.map( (current) =>  
				 <Row key={count++} className="align-items-center viewport-height">
				 	 <Col><img style={imageStyle} src={'/photobook-demo/api/photobook/view/download/'+this.state.photobookId+'_'+current.imageId+'.jpg'}/></Col>
				     <Col><div align="left" dangerouslySetInnerHTML={{ __html: current.info.caption }} /></Col>
				 </Row>
			)	        
			printList = <Fragment>
								<Button variant="primary" onClick={ () => this.handleHome() }>Indietro</Button> 
								<div></div> <Container fluid>{renderList} </Container>
						</Fragment>
		} else if ( this.state.photobookList != null ) {
			let count = 0;
			let renderList = this.state.photobookList.content.data.map( (current) =>  
				 <ListItem key={count++}>
				 	 <ListItemAvatar>
			          <Avatar>
			            <ImageIcon />
			          </Avatar>
			        </ListItemAvatar>
			        <Button variant="primary" onClick={ () => this.handlePhotobook(current.photobookId) }>
			        	<ListItemText primary="Foto" secondary={current.info.photobookTitle} />
			        </Button> 
				 </ListItem>
			)
			printList =  <List sx={{ width: '100%', maxWidth: 800 }}>{renderList}</List>
		}
		return <Fragment>
			<h1>Springboot Demo : Album fotografico</h1>
			{printList}
		</Fragment>
	}

}

export default Home;