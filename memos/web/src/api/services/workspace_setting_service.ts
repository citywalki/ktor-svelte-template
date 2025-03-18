import apiClient from "@/api/apiClient.ts";

export interface WorkspaceSetting {
  name: string;
  generalSetting?: WorkspaceGeneralSetting | undefined;
  storageSetting?: WorkspaceStorageSetting | undefined;
  memoRelatedSetting?: WorkspaceMemoRelatedSetting | undefined;
}
export enum WorkspaceSettingKey {
  WORKSPACE_SETTING_KEY_UNSPECIFIED = "WORKSPACE_SETTING_KEY_UNSPECIFIED",
  /** BASIC - BASIC is the key for basic settings. */
  BASIC = "BASIC",
  /** GENERAL - GENERAL is the key for general settings. */
  GENERAL = "GENERAL",
  /** STORAGE - STORAGE is the key for storage settings. */
  STORAGE = "STORAGE",
  /** MEMO_RELATED - MEMO_RELATED is the key for memo related settings. */
  MEMO_RELATED = "MEMO_RELATED",
  UNRECOGNIZED = "UNRECOGNIZED",
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

export interface WorkspaceCustomProfile {
  title: string;
  description: string;
  logoUrl: string;
  locale: string;
  appearance: string;
}

export interface WorkspaceStorageSetting {
  /** storage_type is the storage type. */
  storageType: WorkspaceStorageSetting_StorageType;
  /**
   * The template of file path.
   * e.g. assets/{timestamp}_{filename}
   */
  filepathTemplate: string;
  /** The max upload size in megabytes. */
  uploadSizeLimitMb: number;
}
export enum WorkspaceStorageSetting_StorageType {
  STORAGE_TYPE_UNSPECIFIED = "STORAGE_TYPE_UNSPECIFIED",
  /** DATABASE - DATABASE is the database storage type. */
  DATABASE = "DATABASE",
  /** LOCAL - LOCAL is the local storage type. */
  LOCAL = "LOCAL",
  /** S3 - S3 is the S3 storage type. */
  S3 = "S3",
  UNRECOGNIZED = "UNRECOGNIZED",
}

export interface WorkspaceMemoRelatedSetting {
  /** disallow_public_visibility disallows set memo as public visibility. */
  disallowPublicVisibility: boolean;
  /** display_with_update_time orders and displays memo with update time. */
  displayWithUpdateTime: boolean;
  /** content_length_limit is the limit of content length. Unit is byte. */
  contentLengthLimit: number;
  /** enable_auto_compact enables auto compact for large content. */
  enableAutoCompact: boolean;
  /** enable_double_click_edit enables editing on double click. */
  enableDoubleClickEdit: boolean;
  /** enable_link_preview enables links preview. */
  enableLinkPreview: boolean;
  /** enable_comment enables comment. */
  enableComment: boolean;
  /** enable_location enables setting location for memo. */
  enableLocation: boolean;
  /** default_visibility set the global memos default visibility. */
  defaultVisibility: string;
  /** reactions is the list of reactions. */
  reactions: string[];
  /** disable_markdown_shortcuts disallow the registration of markdown shortcuts. */
  disableMarkdownShortcuts: boolean;
}

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
export enum WorkspaceSettingApi {}
const workspaceSettingService = (() => {
  function getWorkspaceProfile() {
    return apiClient.get<WorkspaceProfile>({
      url: "/workspace/profile",
    });
  }
  function getWorkspaceSetting(settingKey: WorkspaceSettingKey) {
    return apiClient.get<WorkspaceSetting>({
      url: `/workspace/${settingKey}`,
    });
  }
  return {
    getWorkspaceProfile,
    getWorkspaceSetting,
  };
})();

export { workspaceSettingService };
