import { createContext, useState } from 'react';
import api from '../api/api';

const AppContext = createContext();

function AppProvider({ children }) {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(false);
  /**
   * GLOBAL ALERTS (MUI ALERTS) 
   */
  const [showAlert, setShowAlert] = useState(false);
  const [alertSeverity, setAlertSeverity] = useState(null);
  const [alertContents, setAlertContents] = useState(null);

  const doLogin = async (payload) => {
    setLoading(true);
    try{
      const response = await api.post("/auth", payload);
      if(response.status !== 200){
        doShowAlert({ severity: "error", contents: "Unable to Login", timeout: 3000 });
      }
      else {
        const { token, user } = response.data;
        api.defaults.headers.Authorization = `${token.type} ${token.token}`;
        setUser(user);
      }
    } catch (err) {
      doShowAlert({ severity: "error", contents: "Unable to Login", timeout: 3000 });
    }
  };

  const doSignOut = () => {
    api.defaults.headers.Authorization = null;
    setUser(null);
  }

  const doShowAlert = ({severity, contents, timeout}) => {
    setAlertContents(contents);
    setAlertSeverity(severity);
    setShowAlert(timeout);

    setTimeout(() => {
      setShowAlert(false);
      setAlertContents(null);
      setAlertSeverity(null);
    }, timeout);
  }

  return (
    <AppContext.Provider
      value={{
        doLogin,
        doSignOut,
        doShowAlert,
        user,
        loading,
        showAlert,
        alertContents,
        alertSeverity
      }}
    >
      {children}
    </AppContext.Provider>
  );
}

export { AppContext, AppProvider };
