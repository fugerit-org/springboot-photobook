import React, { Component, Fragment } from 'react';

import {
	BrowserRouter as Router,
	Routes,
	Route,
	Link
} from "react-router-dom";

import Home from './photobook/Home';
import Version from './photobook/Version';
import { Button, Grid } from "@mui/material";

const homepage = '/photobook-demo/home';

class Photobook extends Component {

	constructor(props) {
		super(props);
	}

	render() {
		
		return (
			<Router>
				<div className="App">

					<Version/>

					<Grid container spacing={4} columns={{ xs: 16 }}>
					  <Grid item xs={4}><Link to={homepage}><Button color="primary">Home</Button></Link></Grid>
					</Grid>
					<Routes>
						<Route path="*" element={<Home />} />
					</Routes>

				</div>
			</Router>
		);
	}

}

export default Photobook;