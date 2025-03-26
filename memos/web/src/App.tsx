import { Outlet } from "react-router";
import { observer } from "mobx-react-lite";
import { userStore, workspaceStore } from "./store";
import { useEffect } from "react";
import useNavigateTo from "./hooks/useNavigateTo";

const App = observer(() => {
  const navigateTo = useNavigateTo();
  const userSetting = userStore.state.userSetting;
  const workspaceProfile = workspaceStore.state.profile;

  useEffect(() => {
    if (!workspaceProfile.owner) {
      navigateTo("/auth/signup");
    }
  }, [workspaceProfile.owner]);

  useEffect(() => {
    if (!userSetting) {
      return;
    }

    workspaceStore.state.setPartial({
      locale: userSetting.locale || workspaceStore.state.locale,
      appearance: userSetting.appearance || workspaceStore.state.appearance,
    });
  }, [userSetting?.locale, userSetting?.appearance]);

  return <Outlet />;
});

export default App;
