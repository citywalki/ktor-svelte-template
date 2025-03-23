import { observer } from "mobx-react-lite";
import { useEffect, useState } from "react";
import Loading from "@/pages/Loading.tsx";
import useCurrentUser from "@/hooks/useCurrentUser";
import { workspaceStore } from "@/store";
import { Routes } from "@/router";
import { SidebarProvider } from "@/components/ui/sidebar";
import Navigation from "@/components/navigation";

const RootLayout = observer(() => {
  const [initialized, setInitialized] = useState(false);
  const currentUser = useCurrentUser();

  useEffect(() => {
    if (!currentUser) {
      // If disallowPublicVisibility is enabled, redirect to the login page if the user is not logged in.
      if (workspaceStore.state.memoRelatedSetting.disallowPublicVisibility) {
        window.location.href = Routes.AUTH;
        return;
      } else if (
        (
          [
            Routes.ROOT,
            Routes.RESOURCES,
            Routes.INBOX,
            Routes.ARCHIVED,
            Routes.SETTING,
          ] as string[]
        ).includes(location.pathname)
      ) {
        window.location.href = Routes.EXPLORE;
        return;
      }
    }
    setInitialized(true);
  }, []);

  return !initialized ? (
    <Loading />
  ) : (
    <div className="w-full min-h-full">
      <SidebarProvider>
        <Navigation></Navigation>
      </SidebarProvider>
    </div>
  );
});

export default RootLayout;
