import { Container, Typography } from "@mui/material";
import { Box } from "@mui/system";

function HomePage() {
  return (
    <Container component="main">
      <Box
        sx={{
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
        }}
      >
        <Typography variant="h2">Home</Typography>
        <Typography>Welcome, Fulano!</Typography>
      </Box>
    </Container>
  )
}

export default HomePage;