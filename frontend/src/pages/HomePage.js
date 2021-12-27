import { Container, IconButton, Typography } from "@mui/material";
import { Box } from "@mui/system";
import LogoutIcon from '@mui/icons-material/Logout';
import { useContext } from "react";
import { AppContext } from "../context/AppProvider";

function HomePage() {

  const { doSignOut, user } = useContext(AppContext);

  function handleClickLogOut(){
    doSignOut();
  }

  return (
    <Container component="main">
      <IconButton color="primary" onClick={handleClickLogOut}>
        <LogoutIcon />
      </IconButton>
      <Box
        sx={{
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
        }}
      >
        <Typography variant="h2">Home</Typography>
        <Typography>Welcome, {user.name}!</Typography>
      </Box>
    </Container>
  )
}

export default HomePage;