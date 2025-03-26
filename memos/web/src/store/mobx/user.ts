import { User, userService, UserSetting } from "@/api/services/user_service";
import { makeAutoObservable } from "mobx";
import workspaceStore from "./workspace";
import authService from "@/api/services/auth_service.ts";

class LocalState {
  currentUser?: string;
  userMapByName: Record<string, User> = {};
  userSetting?: UserSetting;

  constructor() {
    makeAutoObservable(this);
  }

  setPartial(partial: Partial<LocalState>) {
    Object.assign(this, partial);
  }
}

const userStore = (() => {
  const state = new LocalState();

  return {
    state: state,
  };
})();

export const initialUserStore = async () => {
  try {
    const currentUser = await authService.getAuthStatus();
    const userSetting = await userService.getUserSetting();
    userStore.state.setPartial({
      currentUser: currentUser.name,
      userSetting: userSetting,
      userMapByName: {
        [currentUser.name!]: currentUser,
      },
    });
    workspaceStore.state.setPartial({
      locale: userSetting.locale,
      appearance: userSetting.appearance,
    });
  } catch {
    // Do nothing.
  }
};

export default userStore;
