import { createContext, useState } from 'react';

const AppContext = createContext();

function AppProvider({ children }) {
  const [userAuth, setUserAuth] = useState(null);
  const [loading, setLoading] = useState(false);
  
  /**
   * GLOBAL ALERTS (MUI ALERTS) 
   */
  const [showAlert, setShowAlert] = useState(false);
  const [alertSeverity, setAlertSeverity] = useState(null);
  const [alertContents, setAlertContents] = useState(null);

  const doLogin = () => {
    // IMPLEMENT LOGIN LOGIC
    setLoading(true);
    console.log("Doing login...");
    setUserAuth(true);
  };

  const doSignOut = () => {
    setUserAuth(null);
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
        userAuth,
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
