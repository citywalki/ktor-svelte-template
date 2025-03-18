import { User } from "@/api/services/user_service";
import { makeAutoObservable } from "mobx";

class LocalState {
  currentUser?: string;
  userMapByName: Record<string, User> = {};

  constructor() {
    makeAutoObservable(this);
  }

  setPartial(partial: Partial<LocalState>) {
    Object.assign(this, partial);
  }
}

const userStore = () => {
  const state = new LocalState();

  return {
    state: state,
  };
};

export default userStore;
