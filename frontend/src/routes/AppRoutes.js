import { useContext } from "react";
import { Route, Routes, BrowserRouter as Router } from "react-router-dom"
import { AppContext } from "../context/AppProvider";
import HomePage from "../pages/HomePage"
import LoginPage from "../pages/LoginPage"
import SignUpPage from "../pages/SignUpPage";

function AppRoutes() {

  const { user } = useContext(AppContext);

  return (
    <Router>
      <Routes>
        {!user 
        ? 
          <>
            <Route path="/" exact element={<LoginPage/>} />
            <Route path="/signup" exact element={<SignUpPage/>} /> 
          </>
        :
          <Route path="/" exact element={<HomePage />} />
        }
      </Routes>
    </Router>
  )

}

export default AppRoutes;