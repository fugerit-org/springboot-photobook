import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Helmet } from 'react-helmet';
import Photobook from './Photobook';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';

const TITLE = 'Photobook Demo';

const darkTheme = createTheme({
  palette: {
    mode: 'dark',
  },
});

function App() {

	return (
		<ThemeProvider theme={darkTheme}>
      		<CssBaseline />
			<Helmet>
				<title>{TITLE}</title>
			</Helmet>
			<Photobook/>
		</ThemeProvider>
	);
}

export default App;
