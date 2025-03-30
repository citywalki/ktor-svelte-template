import { observer } from "mobx-react-lite";
import {
  Sidebar,
  SidebarContent,
  SidebarGroup,
  SidebarGroupContent,
  SidebarGroupLabel,
  SidebarHeader,
  SidebarMenu,
  SidebarMenuBadge,
  SidebarMenuButton,
  SidebarMenuItem,
} from "@/components/ui/sidebar";
import { Separator } from "@/components/ui/separator";
import {
  BadgeCheck,
  ChevronsUpDown,
  Home,
  Inbox,
  LogIn,
  LogOut,
  MessageCircleQuestion,
  Settings2,
  Trash2,
} from "lucide-react";
import * as React from "react";
import useCurrentUser from "@/hooks/useCurrentUser.ts";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "../ui/dropdown-menu";
import { Avatar, AvatarFallback, AvatarImage } from "../ui/avatar";
import { useIsMobile } from "@/hooks";
import userStore from "../../store/mobx/user.ts";
import { gql } from "@/gql";
import { useQuery } from "@tanstack/react-query";
import { graphqlClient } from "@/api/apiClient.ts";

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
        <UserWorkspacesGroup />
        <UserFollowGroup />
      </SidebarContent>
      <NavSecondary className="mt-auto" />
    </Sidebar>
  );
};

const UserWorkspacesGroup = () => {
  const currentUserSpaces = gql(`
    query CurrentUserSpaces {
      currentUser {
        userSpaces {
          id
          name
        }
      }
    }
  `);

  const { data } = useQuery({
    queryKey: ["currentUserSpaces"],
    queryFn: async () => graphqlClient.request(currentUserSpaces),
  });

  return (
    <SidebarGroup>
      <SidebarGroupLabel>空间</SidebarGroupLabel>
      <SidebarGroupContent>
        <SidebarMenu>
          {data?.currentUser?.userSpaces?.map((item) => (
            <SidebarMenuItem key={item?.id}>
              <SidebarMenuButton asChild>
                <a>{item?.name}</a>
              </SidebarMenuButton>
            </SidebarMenuItem>
          ))}
        </SidebarMenu>
      </SidebarGroupContent>
    </SidebarGroup>
  );
};

const UserFollowGroup = () => {
  const items = [
    {
      title: "User Workspaces",
      url: "/users/workspaces",
    },
    {
      title: "User Workspaces",
      url: "/users/workspaces",
    },
  ];
  return (
    <SidebarGroup>
      <SidebarGroupLabel>关注</SidebarGroupLabel>
      <SidebarGroupContent>
        <SidebarMenu>
          {items.map((item) => (
            <SidebarMenuItem key={item.title}>
              <SidebarMenuButton asChild>
                <a href={item.url}>{item.title}</a>
              </SidebarMenuButton>
            </SidebarMenuItem>
          ))}
        </SidebarMenu>
      </SidebarGroupContent>
    </SidebarGroup>
  );
};

const UnLoginSidebar = observer(() => {
  return (
    <Sidebar className="border-r-0">
      <SidebarHeader>
        <SidebarMenu>
          <SidebarMenuItem>
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
      <NavUser />
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

const NavUser = observer(() => {
  const user = useCurrentUser();
  const isMobile = useIsMobile();

  const handleLogout = () => {
    userStore.signOut();
    window.location.href = "/";
  };
  return (
    <SidebarMenu>
      <SidebarMenuItem>
        <DropdownMenu>
          <DropdownMenuTrigger asChild>
            <SidebarMenuButton
              size="lg"
              className="data-[state=open]:bg-sidebar-accent data-[state=open]:text-sidebar-accent-foreground"
            >
              <Avatar className="h-8 w-8 rounded-lg">
                <AvatarImage alt={user.username} />
                <AvatarFallback className="rounded-lg">
                  {user.username}
                </AvatarFallback>
              </Avatar>
              <div className="grid flex-1 text-left text-sm leading-tight">
                <span className="truncate font-semibold">{user.username}</span>
                <span className="truncate text-xs">{user.email}</span>
              </div>
              <ChevronsUpDown className="ml-auto size-4" />
            </SidebarMenuButton>
          </DropdownMenuTrigger>
          <DropdownMenuContent
            className="w-[--radix-dropdown-menu-trigger-width] min-w-56 rounded-lg"
            side={isMobile ? "bottom" : "right"}
            align="end"
            sideOffset={4}
          >
            <DropdownMenuItem>
              <BadgeCheck />
              Account
            </DropdownMenuItem>
            <DropdownMenuSeparator />
            <DropdownMenuItem onSelect={handleLogout}>
              <LogOut />
              Log out
            </DropdownMenuItem>
          </DropdownMenuContent>
        </DropdownMenu>
      </SidebarMenuItem>
    </SidebarMenu>
  );
});

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
