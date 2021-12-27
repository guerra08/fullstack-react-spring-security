import { Alert, Box, Button, Container, CssBaseline, TextField, Typography } from '@mui/material';
import { useContext } from 'react';
import api from '../api/api';
import { AppContext } from '../context/AppProvider';

function SignUpPage() {

  const { alertContents, alertSeverity, showAlert, doShowAlert } = useContext(AppContext);

  /**
   * Handles the login form submission
   * @param {*} event 
   */
  const handleSubmit = async (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    const payload = {
      name: data.get('name'),
      email: data.get('email'),
      password: data.get('password'),
    };
    try {
      const response = await api.post("/register", payload);
      if (response.status === 201) {
        doShowAlert({ severity: "success", contents: "User created!", timeout: 3000 });
      }
      else {
        doShowAlert({ severity: "error", contents: "Error", timeout: 3000 });
      }
    } catch (err) {
      doShowAlert({ severity: "error", contents: "Error", timeout: 3000 });
    }
  };

  return (
    <Container component="main" maxWidth="xs">
      <CssBaseline />
      {showAlert ? <Alert severity={alertSeverity}>{alertContents}</Alert> : <></>}
      <Box
        sx={{
          marginTop: 8,
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
        }}
      >
        <Typography component="h1" variant="h5">
          Sign Up
        </Typography>
        <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
          <TextField
            margin="normal"
            required
            fullWidth
            id="name"
            label="Name"
            name="name"
            autoComplete="name"
            autoFocus
          />
          <TextField
            margin="normal"
            required
            fullWidth
            id="email"
            label="Email Address"
            name="email"
            autoComplete="email"
            autoFocus
          />
          <TextField
            margin="normal"
            required
            fullWidth
            name="password"
            label="Password"
            type="password"
            id="password"
            autoComplete="current-password"
          />
          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 2 }}
          >
            Sign Up
          </Button>
        </Box>
      </Box>
    </Container>
  );
}

export default SignUpPage;