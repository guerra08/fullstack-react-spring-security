import { createContext, useState } from 'react';
import api from '../api/api';
import { authUser } from '../services/AuthService';

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
    const [error, data] = await authUser(payload);
    if (error) {
      doShowAlert({ severity: "error", contents: error, timeout: 3000 });
    }
    else {
      const { token, user } = data;
      api.defaults.headers.Authorization = `${token.type} ${token.token}`;
      setUser(user);
    }
  };

  const doSignOut = () => {
    api.defaults.headers.Authorization = null;
    setUser(null);
  }

  const doShowAlert = ({ severity, contents, timeout }) => {
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
        setLoading,
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
