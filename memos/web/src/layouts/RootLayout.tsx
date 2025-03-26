import { observer } from "mobx-react-lite";
import { useEffect, useState } from "react";
import Loading from "@/pages/Loading.tsx";
import useCurrentUser from "@/hooks/useCurrentUser";
import { workspaceStore } from "@/store";
import { Routes } from "@/router";
import { SidebarContent, SidebarProvider } from "@/components/ui/sidebar";
import Navigation from "@/components/navigation";
import { Outlet } from "react-router";

const RootLayout = observer(() => {
  const [initialized, setInitialized] = useState(false);
  const currentUser = useCurrentUser();

  useEffect(() => {
    if (!currentUser) {
      // 如果启用了不允许公开的可视性，请重定向到登录页面，如果用户未登录。
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
  }, [currentUser]);

  return !initialized ? (
    <Loading />
  ) : (
    <div className="w-full min-h-full">
      <SidebarProvider>
        <Navigation></Navigation>
        <SidebarContent>
          <Outlet />
        </SidebarContent>
      </SidebarProvider>
    </div>
  );
});

export default RootLayout;
