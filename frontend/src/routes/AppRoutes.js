import { useContext } from "react";
import { Route, Routes, BrowserRouter as Router, Navigate } from "react-router-dom"
import { AppContext } from "../context/AppProvider";
import HomePage from "../pages/HomePage"
import LoginPage from "../pages/LoginPage"

function AppRoutes() {

  const { userAuth } = useContext(AppContext);

  function PrivateRoute({ children }) {
    const auth = userAuth;
    return auth ? children : <Navigate to="/login" />;
  }

  return (
    <Router>
      <Routes>
        <Route path="/login" exact element={<LoginPage/>}></Route>
        <Route path="/" exact element={
          <PrivateRoute>
            <HomePage />
          </PrivateRoute>
        }/>
      </Routes>
    </Router>
  )

}

export default AppRoutes;