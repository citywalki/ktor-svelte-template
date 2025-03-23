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
  MessageCircleQuestion,
  Settings2,
  Trash2,
} from "lucide-react";
import * as React from "react";

const Navigation = observer(() => {
  return (
    <Sidebar className="border-r-0">
      <SidebarHeader>
        {/*<TeamSwitcher teams={data.teams} />*/}
        <NavMain />
      </SidebarHeader>
      <SidebarContent>
        <NavSecondary className="mt-auto" />
      </SidebarContent>
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
