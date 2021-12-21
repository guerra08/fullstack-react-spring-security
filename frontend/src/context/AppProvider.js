import { createContext, useState } from 'react';

const AppContext = createContext();

function AppProvider({ children }) {
  const [userAuth, setUserAuth] = useState(null);
  const [loading, setLoading] = useState(false);

  const doLogin = () => {
    // IMPLEMENT LOGIN LOGIC
    setLoading(true);
    console.log("Doing login...");
    setUserAuth(true);
  };

  const doSignOut = () => {
    setUserAuth(null);
  }

  return (
    <AppContext.Provider
      value={{
        doLogin,
        doSignOut,
        userAuth,
        loading
      }}
    >
      {children}
    </AppContext.Provider>
  );
}

export { AppContext, AppProvider };
