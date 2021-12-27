import { Alert, Box, Button, Container, CssBaseline, Link, TextField, Typography } from '@mui/material';
import { useContext, useState } from 'react';
import { AppContext } from '../context/AppProvider';
import { forgotPassword } from '../services/AuthService';

function ForgotPasswordPage(){

  const { doShowAlert, showAlert, alertSeverity, alertContents} = useContext(AppContext);
  const [ passwordToken, setPasswordToken ] = useState(null);

  async function handleSubmit(event){
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    const payload = {
      email: data.get('email')
    };
    const [error, result] = await forgotPassword(payload);
    if(error) doShowAlert({ severity: "error", contents: error, timeout: 3000 })
    else setPasswordToken(result);
  }

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
          Forgot my password
        </Typography>
        {passwordToken ? 
          <>
            <Link href={`/reset-password?token=${passwordToken}`}>Click here to reset your password</Link>
          </>
          : <></>}
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
          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 2 }}
          >
            Submit
          </Button>
        </Box>
        <Link href="/">Back to Login</Link>
      </Box>
    </Container>
  )

}

export default ForgotPasswordPage;