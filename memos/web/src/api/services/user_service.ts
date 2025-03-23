import { RowStatus } from "@/api/services/common.ts";
import apiClient from "../apiClient";

export interface ListUsersResponse {
  users: User[];
}

export enum User_Role {
  ROLE_UNSPECIFIED = "ROLE_UNSPECIFIED",
  HOST = "HOST",
  ADMIN = "ADMIN",
  USER = "USER",
  UNRECOGNIZED = "UNRECOGNIZED",
}

export interface User {
  /**
   * The name of the user.
   * Format: users/{id}
   */
  name: string;
  /** The system generated uid of the user. */
  id: number;
  role: User_Role;
  username: string;
  email: string;
  nickname: string;
  avatarUrl: string;
  description: string;
  password: string;
  rowStatus: RowStatus;
  createTime?: Date | undefined;
  updateTime?: Date | undefined;
}

export interface UserSetting {
  name: string;
  locale: Locale;
  appearance: string;
  memoVisibility: string;
}

const userService = {
  listUsers() {
    return apiClient.get<ListUsersResponse>({
      url: "/users",
    });
  },

  getUserSetting() {
    return apiClient.get<UserSetting>({
      url: `/users/setting`,
    });
  },
};

export { userService };
