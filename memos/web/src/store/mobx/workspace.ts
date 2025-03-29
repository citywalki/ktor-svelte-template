import { WorkspaceCustomProfile } from "@/api/services/workspace_setting_service";
import { isValidateLocale } from "@/utils/i18n";
import { makeAutoObservable } from "mobx";
import { gql } from "@/gql";
import { graphqlClient } from "@/api/apiClient";

export interface WorkspaceProfile {
  /**
   * The name of instance owner.
   * Format: "users/{id}"
   */
  owner: string;
  /** version is the current version of instance */
  version: string;
  /** mode is the instance mode (e.g. "prod", "dev" or "demo"). */
  mode: string;
  /** instance_url is the URL of the instance. */
  instanceUrl: string;
}

export interface WorkspaceGeneralSetting {
  /** disallow_user_registration disallows user registration. */
  disallowUserRegistration: boolean;
  /** disallow_password_auth disallows password authentication. */
  disallowPasswordAuth: boolean;
  /** custom_profile is the custom profile. */
  customProfile?: WorkspaceCustomProfile | undefined;
  /**
   * week_start_day_offset is the week start day offset from Sunday.
   * 0: Sunday, 1: Monday, 2: Tuesday, 3: Wednesday, 4: Thursday, 5: Friday, 6: Saturday
   * Default is Sunday.
   */
  weekStartDayOffset: number;
  /** disallow_change_username disallows changing username. */
  disallowChangeUsername: boolean;
  /** disallow_change_nickname disallows changing nickname. */
  disallowChangeNickname: boolean;
}

export interface WorkspaceMemoRelatedSetting {
  /** disallow_public_visibility disallows set memo as public visibility. */
  disallowPublicVisibility?: boolean | null;
  /** display_with_update_time orders and displays memo with update time. */
  displayWithUpdateTime?: boolean | null;
  /** content_length_limit is the limit of content length. Unit is byte. */
  contentLengthLimit?: number | null;
  /** enable_auto_compact enables auto compact for large content. */
  enableAutoCompact?: boolean | null;
  /** enable_double_click_edit enables editing on double click. */
  enableDoubleClickEdit?: boolean | null;
  /** enable_link_preview enables links preview. */
  enableLinkPreview?: boolean | null;
  /** enable_comment enables comment. */
  enableComment?: boolean | null;
  /** enable_location enables setting location for memo. */
  enableLocation?: boolean | null;
  /** default_visibility set the global memos default visibility. */
  defaultVisibility?: string | null;
  /** reactions is the list of reactions. */
  reactions?: Array<string | null> | null;
  /** disable_markdown_shortcuts disallow the registration of markdown shortcuts. */
  disableMarkdownShortcuts?: boolean | null;
}

class LocalState {
  locale: string = "zh-Hans";
  appearance: string = "system";
  profile: WorkspaceProfile = {
    owner: "",
    version: "",
    mode: "",
    instanceUrl: "",
  };
  generalSetting: WorkspaceGeneralSetting = {
    disallowUserRegistration: false,
    disallowPasswordAuth: false,
    weekStartDayOffset: 0,
    disallowChangeUsername: false,
    disallowChangeNickname: false,
  };
  memoRelatedSetting: WorkspaceMemoRelatedSetting = {};

  constructor() {
    makeAutoObservable(this);
  }

  setPartial(partial: Partial<LocalState>) {
    const finalState = {
      ...this,
      ...partial,
    };
    if (!isValidateLocale(finalState.locale)) {
      finalState.locale = "en";
    }
    if (!["system", "light", "dark"].includes(finalState.appearance)) {
      finalState.appearance = "system";
    }
    Object.assign(this, finalState);
  }
}

const workspaceStore = (() => {
  const state = new LocalState();

  return {
    state,
  };
})();

export const initialWorkspaceStoreFromGraphql = async () => {
  const INIT_GLOBAL_SETTING = gql(`
  query INIT_GLOBAL_SETTING {
    profile {
      owner
      version
      mode
    }
    globalSettings {
      generalSetting {
        disallowUserRegistration
        disallowPasswordAuth
        additionalScript
        additionalStyle
        customProfile {
          title
          description
          locale
          logoUrl
          appearance
        }
        weekStartDayOffset
        disallowChangeNickname
        disallowChangeUsername
      }
      memoRelatedSetting {
        disallowPublicVisibility
        displayWithUpdateTime
        contentLengthLimit
        enableAutoCompact
        enableDoubleClickEdit
        enableLinkPreview
        enableComment
        enableLocation
        defaultVisibility
        reactions
        disableMarkdownShortcuts
      }
    }
  }
  `);

  const result = await graphqlClient.query(INIT_GLOBAL_SETTING, {}).toPromise();
  if (result.data) {
    const { profile, globalSettings } = result.data;
    const generalSetting = globalSettings.generalSetting;
    const memoRelatedSetting = globalSettings.memoRelatedSetting;
    workspaceStore.state.setPartial({
      profile: {
        owner: profile?.owner,
        version: profile?.version,
        mode: profile?.mode,
        instanceUrl: "",
      },
      generalSetting: {
        disallowUserRegistration: generalSetting.disallowUserRegistration,
        disallowPasswordAuth: generalSetting.disallowPasswordAuth,
        disallowChangeNickname: generalSetting.disallowChangeNickname,
        disallowChangeUsername: generalSetting.disallowChangeUsername,
        customProfile: {
          title: generalSetting.customProfile.title,
          description: generalSetting.customProfile.description,
          logoUrl: generalSetting.customProfile.logoUrl,
          locale: generalSetting.customProfile.locale,
          appearance: generalSetting.customProfile.appearance,
        },
        weekStartDayOffset: generalSetting.weekStartDayOffset,
      },
      memoRelatedSetting: {
        disallowPublicVisibility: memoRelatedSetting.disallowPublicVisibility,
        displayWithUpdateTime: memoRelatedSetting.displayWithUpdateTime,
        contentLengthLimit: memoRelatedSetting.contentLengthLimit,
        enableAutoCompact: memoRelatedSetting.enableAutoCompact,
        enableDoubleClickEdit: memoRelatedSetting.enableDoubleClickEdit,
        enableLinkPreview: memoRelatedSetting.enableLinkPreview,
        enableComment: memoRelatedSetting.enableComment,
        enableLocation: memoRelatedSetting.enableLocation,
        defaultVisibility: memoRelatedSetting.defaultVisibility,
        reactions: memoRelatedSetting.reactions,
        disableMarkdownShortcuts: memoRelatedSetting.disableMarkdownShortcuts,
      },
    });
  }
};

// export const initialWorkspaceStore = async () => {
//   const workspaceProfile = await workspaceSettingService.getWorkspaceProfile();
//   // Prepare workspace settings.
//   for (const key of [
//     WorkspaceSettingKey.GENERAL,
//     WorkspaceSettingKey.MEMO_RELATED,
//   ]) {
//     await workspaceStore.fetchWorkspaceSetting(key);
//   }
//
//   const workspaceGeneralSetting = workspaceStore.state.generalSetting;
//   workspaceStore.state.setPartial({
//     locale: workspaceGeneralSetting.customProfile?.locale,
//     appearance: workspaceGeneralSetting.customProfile?.appearance,
//     profile: workspaceProfile,
//   });
// };

export default workspaceStore;
