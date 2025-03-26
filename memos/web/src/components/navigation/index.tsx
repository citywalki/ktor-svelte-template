import { observer } from "mobx-react-lite";
import {
  Sidebar,
  SidebarContent,
  SidebarGroup,
  SidebarGroupContent,
  SidebarHeader,
  SidebarMenu,
  SidebarMenuBadge,
  SidebarMenuButton,
  SidebarMenuItem,
} from "../ui/sidebar";
import {
  Home,
  Inbox,
  LogIn,
  MessageCircleQuestion,
  Settings2,
  Trash2,
} from "lucide-react";
import * as React from "react";
import useCurrentUser from "@/hooks/useCurrentUser.ts";

const Navigation = observer(() => {
  const currentUser = useCurrentUser();

  return currentUser ? <LoggedSidebar /> : <UnLoginSidebar />;
});

const LoggedSidebar = () => {
  return (
    <Sidebar className="border-r-0">
      <SidebarHeader>
        <NavMain />
      </SidebarHeader>
      <SidebarContent>
        <NavSecondary className="mt-auto" />
      </SidebarContent>
    </Sidebar>
  );
};

const UnLoginSidebar = observer(() => {
  return (
    <Sidebar className="border-r-0">
      <SidebarHeader>
        <SidebarMenu>
          <SidebarMenuItem key={"home"}>
            <SidebarMenuButton asChild>
              <a href={"/auth"}>
                <LogIn />
                <span>Login</span>
              </a>
            </SidebarMenuButton>
          </SidebarMenuItem>
        </SidebarMenu>
      </SidebarHeader>
    </Sidebar>
  );
});

const NavMain = () => {
  return (
    <SidebarMenu>
      <SidebarMenuItem key={"home"}>
        <SidebarMenuButton asChild>
          <a href={"/"}>
            <Home />
            <span>Home</span>
          </a>
        </SidebarMenuButton>
      </SidebarMenuItem>
      <SidebarMenuItem key={"Inbox"}>
        <SidebarMenuButton asChild>
          <a href={"/inbox"}>
            <Inbox />
            <span>Inbox</span>
          </a>
        </SidebarMenuButton>
        <SidebarMenuBadge>20</SidebarMenuBadge>
      </SidebarMenuItem>
    </SidebarMenu>
  );
};

const NavSecondary = ({
  ...props
}: {} & React.ComponentPropsWithoutRef<typeof SidebarGroup>) => {
  return (
    <SidebarGroup {...props}>
      <SidebarGroupContent>
        <SidebarMenu>
          <SidebarMenuItem>
            <SidebarMenuButton>
              <Settings2 />
              <span>Settings</span>
            </SidebarMenuButton>
          </SidebarMenuItem>
          <SidebarMenuItem>
            <SidebarMenuButton>
              <Trash2 />
              <span>Trash</span>
            </SidebarMenuButton>
          </SidebarMenuItem>
          <SidebarMenuItem>
            <SidebarMenuButton>
              <MessageCircleQuestion />
              <span>Help</span>
            </SidebarMenuButton>
          </SidebarMenuItem>
        </SidebarMenu>
      </SidebarGroupContent>
    </SidebarGroup>
  );
};

export default Navigation;
