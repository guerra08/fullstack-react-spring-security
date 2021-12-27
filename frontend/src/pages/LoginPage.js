import { Alert, Box, Button, Checkbox, Container, CssBaseline, FormControlLabel, Link, TextField, Typography } from '@mui/material';
import { useContext } from 'react';
import { AppContext } from '../context/AppProvider';

function LoginPage() {

  const { doLogin, showAlert, alertSeverity, alertContents} = useContext(AppContext);

  /**
   * Handles the login form submission
   * @param {*} event 
   */
  const handleSubmit = async (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    const payload = {
      email: data.get('email'),
      password: data.get('password'),
    };
    await doLogin(payload);
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
          Sign in
        </Typography>
        <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
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
          <FormControlLabel
            control={<Checkbox value="remember" color="primary" />}
            label="Remember me"
          />
          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 2 }}
          >
            Sign In
          </Button>
        </Box>
        <Link href="/signup">Create an account</Link>
      </Box>
    </Container>
  );
}

export default LoginPage;