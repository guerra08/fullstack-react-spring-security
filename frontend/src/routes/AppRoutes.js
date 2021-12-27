import { useContext } from "react";
import { Route, Routes, BrowserRouter as Router } from "react-router-dom"
import { AppContext } from "../context/AppProvider";
import ForgotPasswordPage from "../pages/ForgotPasswordPage";
import HomePage from "../pages/HomePage"
import LoginPage from "../pages/LoginPage"
import ResetPasswordPage from "../pages/ResetPasswordPage";
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
            <Route path="/forgot-password" exact element={<ForgotPasswordPage/>} /> 
            <Route path="/reset-password" element={<ResetPasswordPage/>} /> 
          </>
        :
          <Route path="/" exact element={<HomePage />} />
        }
      </Routes>
    </Router>
  )

}

export default AppRoutes;