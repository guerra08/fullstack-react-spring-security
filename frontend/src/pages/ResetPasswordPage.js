import { useContext, useEffect } from "react";
import { Alert, Box, Button, Container, CssBaseline, Link, TextField, Typography } from '@mui/material';
import { useNavigate, useSearchParams } from "react-router-dom";
import { resetPassword, verifyToken } from "../services/AuthService";
import { AppContext } from "../context/AppProvider";

function ResetPasswordPage(){
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();
  const token = searchParams.get("token");
  const { doShowAlert, showAlert, alertContents, alertSeverity } = useContext(AppContext);

  useEffect(() => {
    verifyToken(token).then(result => {
      const [error] = result;
      if(error) navigate("/");
    })
  }, []);

  async function handleSubmit(event){
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    const payload = {
      updatedPassword: data.get('updatedPassword'),
      updatedPasswordConfirmation: data.get('updatedPasswordConfirmation'),
      token
    };
    const [error] = await resetPassword(payload);
    if(error)
      doShowAlert({ severity: "error", contents: error, timeout: 3000 });
    else
      doShowAlert({ severity: "success", contents: "Password updated!", timeout: 3000 })
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
          Sign Up
        </Typography>
        <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
          <TextField
            margin="normal"
            required
            fullWidth
            name="updatedPassword"
            label="New password"
            type="password"
            id="updatedPassword"
            autoComplete="new-password"
          />
          <TextField
            margin="normal"
            required
            fullWidth
            name="updatedPasswordConfirmation"
            label="New password confirmation"
            type="password"
            id="updatedPasswordConfirmation"
            autoComplete="new-password-confirmation"
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

export default ResetPasswordPage;