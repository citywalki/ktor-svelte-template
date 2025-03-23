import {
  WorkspaceGeneralSetting,
  WorkspaceMemoRelatedSetting,
  WorkspaceProfile,
  WorkspaceSetting,
  WorkspaceSettingKey,
  workspaceSettingService,
} from "@/api/services/workspace_setting_service";
import { isValidateLocale } from "@/utils/i18n";
import { makeAutoObservable } from "mobx";
import { uniqBy } from "lodash-es";

class LocalState {
  locale: string = "zh-Hans";
  appearance: string = "system";
  profile: WorkspaceProfile = {
    owner: "",
    version: "",
    mode: "",
    instanceUrl: "",
  };
  settings: WorkspaceSetting[] = [];

  get memoRelatedSetting(): WorkspaceMemoRelatedSetting {
    return (
      this.settings.find(
        (setting) => setting.name === WorkspaceSettingKey.MEMO_RELATED,
      )?.memoRelatedSetting || {
        disallowPublicVisibility: true,
        displayWithUpdateTime: true,
        contentLengthLimit: 120,
        enableAutoCompact: true,
        enableDoubleClickEdit: true,
        enableLinkPreview: true,
        enableComment: true,
        enableLocation: true,
        defaultVisibility: "",
        reactions: [],
        disableMarkdownShortcuts: false,
      }
    );
  }

  get generalSetting(): WorkspaceGeneralSetting {
    return (
      this.settings.find(
        (setting) => setting.name === WorkspaceSettingKey.GENERAL,
      )?.generalSetting || {
        disallowChangeNickname: false,
        disallowUserRegistration: false,
        disallowChangeUsername: true,
        weekStartDayOffset: 1,
        disallowPasswordAuth: false,
      }
    );
  }

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

  const fetchWorkspaceSetting = async (settingKey: WorkspaceSettingKey) => {
    const setting =
      await workspaceSettingService.getWorkspaceSetting(settingKey);
    state.setPartial({
      settings: uniqBy([setting, ...state.settings], "name"),
    });
  };

  return {
    state,
    fetchWorkspaceSetting,
  };
})();

export const initialWorkspaceStore = async () => {
  const workspaceProfile = await workspaceSettingService.getWorkspaceProfile();
  // Prepare workspace settings.
  for (const key of [
    WorkspaceSettingKey.GENERAL,
    WorkspaceSettingKey.MEMO_RELATED,
  ]) {
    await workspaceStore.fetchWorkspaceSetting(key);
  }

  const workspaceGeneralSetting = workspaceStore.state.generalSetting;
  workspaceStore.state.setPartial({
    locale: workspaceGeneralSetting.customProfile?.locale,
    appearance: workspaceGeneralSetting.customProfile?.appearance,
    profile: workspaceProfile,
  });
};

export default workspaceStore;
