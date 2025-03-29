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

export interface WorkspaceCustomProfile {
  title?: string | null;
  description?: string | null;
  logoUrl?: string | null;
  locale?: string | null;
  appearance?: string | null;
}

// export interface WorkspaceStorageSetting {
//   /** storage_type is the storage type. */
//   storageType: WorkspaceStorageSetting_StorageType;
//   /**
//    * The template of file path.
//    * e.g. assets/{timestamp}_{filename}
//    */
//   filepathTemplate: string;
//   /** The max upload size in megabytes. */
//   uploadSizeLimitMb: number;
// }
// export enum WorkspaceStorageSetting_StorageType {
//   STORAGE_TYPE_UNSPECIFIED = "STORAGE_TYPE_UNSPECIFIED",
//   /** DATABASE - DATABASE is the database storage type. */
//   DATABASE = "DATABASE",
//   /** LOCAL - LOCAL is the local storage type. */
//   LOCAL = "LOCAL",
//   /** S3 - S3 is the S3 storage type. */
//   S3 = "S3",
//   UNRECOGNIZED = "UNRECOGNIZED",
// }

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
  };
})();

export { workspaceSettingService };
