import { UserSetting } from "@/api/services/user_service";
import { makeAutoObservable } from "mobx";
import { gql } from "@/gql";
import { RowStatus, UserRole } from "@/gql/graphql";
import { graphqlClient } from "@/api/apiClient.ts";

export interface User {
  /** The system generated uid of the user. */
  id?: string;
  role: UserRole;
  username: string;
  email: string;
  nickname: string;
  avatarUrl: string;
  rowStatus: RowStatus;
  createdAt?: Date | undefined;
  updatedAt?: Date | undefined;
}

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

export const initialUserStoreFromGraphql = async () => {
  try {
    const INIT_USER_STORE = gql(`
        query INIT_USER_STORE {
          currentUser {
            id
            username
            role
            email
            nickname
            avatarUrl
            status
            createdAt
            updatedAt
            userSetting {
              id
              locale
              memoVisibility
              appearance
            }
          }
        }
    `);

    const result = await graphqlClient.query(INIT_USER_STORE, {}).toPromise();
    if (result) {
      const ownerUser = result.data?.currentUser;
      if (ownerUser) {
        userStore.state.setPartial({
          currentUser: ownerUser.id,
          userSetting: {
            id: ownerUser.userSetting.id,
            locale: ownerUser.userSetting.locale,
            appearance: ownerUser.userSetting.appearance!,
            memoVisibility: ownerUser.userSetting.memoVisibility,
          },
          userMapByName: {
            [ownerUser.id!]: {
              id: ownerUser.id,
              role: ownerUser.role,
              username: ownerUser.username,
              email: ownerUser.email!,
              nickname: ownerUser.nickname,
              avatarUrl: ownerUser.avatarUrl!,
              rowStatus: ownerUser.status,
              createdAt: ownerUser.createdAt,
              updatedAt: ownerUser.updatedAt,
            },
          },
        });
      }
    }
  } catch (error) {
    console.error(error);
    // Do nothing.
  }
};

export default userStore;
