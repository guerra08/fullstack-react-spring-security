import { Container, IconButton, Typography, CircularProgress } from "@mui/material";
import { Box } from "@mui/system";
import LogoutIcon from '@mui/icons-material/Logout';
import { useContext, useEffect, useState } from "react";
import { AppContext } from "../context/AppProvider";
import api from '../api/api';

function HomePage() {

  const { doSignOut, user, loading, setLoading } = useContext(AppContext);
  const [ beer, setBeer ] = useState(null);

  useEffect(() => {
    async function getRandomBeer() {
      const response = await api.get("/beer");
      setLoading(false);
      setBeer(response.data);
    }
    setLoading(true);
    getRandomBeer()
  }, [])

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
        {loading ? <CircularProgress /> : <></>}
        {beer ? <pre>{JSON.stringify(beer, null, 2)}</pre> : <></>}
      </Box>
    </Container>
  )
}

export default HomePage;