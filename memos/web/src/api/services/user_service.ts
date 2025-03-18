import { RowStatus } from "@/api/services/common.ts";
import apiClient from "../apiClient";

export type ListUsersRequest = object;

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

export enum UserApi {
  ListUsers = "/api/v1/user/listUsers",
  SearchUsers = "/api/v1/user/searchUsers",
  GetUser = "/api/v1/user/getUser",
  GetUserAvatarBinary = "/api/v1/user/getUserAvatarBinary",
  CreateUser = "/api/v1/user/createUser",
  UpdateUser = "/api/v1/user/updateUser",
  DeleteUser = "/api/v1/user/deleteUser",
  GetUserSetting = "/api/v1/user/getUserSetting",
  UpdateUserSetting = "/api/v1/user/updateUserSetting",
  ListUserAccessTokens = "/api/v1/user/listUserAccessTokens",
  CreateUserAccessToken = "/api/v1/user/createUserAccessToken",
  DeleteUserAccessToken = "/api/v1/user/deleteUserAccessToken",
}

const userService = {
  listUsers(request: ListUsersRequest) {
    return apiClient.get<ListUsersResponse>({
      url: UserApi.ListUsers,
      params: request,
    });
  },
};

export { userService };
