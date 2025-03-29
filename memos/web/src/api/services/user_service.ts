import apiClient from "../apiClient";

export interface UserSetting {
  id?: string;
  locale: Locale;
  appearance?: string;
  memoVisibility: string;
}

const userService = {
  getUserSetting() {
    return apiClient.get<UserSetting>({
      url: `/users/setting`,
    });
  },
};

export { userService };
