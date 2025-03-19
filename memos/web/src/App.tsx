import { Outlet } from "react-router";
import { observer } from "mobx-react-lite";
import { workspaceStore } from "./store";
import { useEffect } from "react";
import useNavigateTo from "./hooks/useNavigateTo";

const App = observer(() => {
  const navigateTo = useNavigateTo();

  const workspaceProfile = workspaceStore.state.profile;

  useEffect(() => {
    if (!workspaceProfile.owner) {
      navigateTo("/auth/signup");
    }
  }, [workspaceProfile.owner]);

  return <Outlet />;
});

export default App;
