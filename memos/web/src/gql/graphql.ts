/* eslint-disable */
import { TypedDocumentNode as DocumentNode } from '@graphql-typed-document-node/core';
export type Maybe<T> = T | null;
export type InputMaybe<T> = Maybe<T>;
export type Exact<T extends { [key: string]: unknown }> = { [K in keyof T]: T[K] };
export type MakeOptional<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]?: Maybe<T[SubKey]> };
export type MakeMaybe<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]: Maybe<T[SubKey]> };
export type MakeEmpty<T extends { [key: string]: unknown }, K extends keyof T> = { [_ in K]?: never };
export type Incremental<T> = T | { [P in keyof T]?: P extends ' $fragmentName' | '__typename' ? T[P] : never };
/** All built-in and custom scalars, mapped to their actual values */
export type Scalars = {
  ID: { input: string; output: string; }
  String: { input: string; output: string; }
  Boolean: { input: boolean; output: boolean; }
  Int: { input: number; output: number; }
  Float: { input: number; output: number; }
  Date: { input: any; output: any; }
  DateTime: { input: any; output: any; }
  LocalDateTime: { input: any; output: any; }
  Long: { input: any; output: any; }
};

export type FindUser = {
  id?: InputMaybe<Scalars['ID']['input']>;
  role?: InputMaybe<UserRole>;
  username?: InputMaybe<Scalars['String']['input']>;
};

export type GlobalCustomProfile = {
  __typename?: 'GlobalCustomProfile';
  appearance?: Maybe<Scalars['String']['output']>;
  description?: Maybe<Scalars['String']['output']>;
  locale?: Maybe<Scalars['String']['output']>;
  logoUrl?: Maybe<Scalars['String']['output']>;
  title?: Maybe<Scalars['String']['output']>;
};

export type GlobalGeneralSetting = {
  __typename?: 'GlobalGeneralSetting';
  additionalScript: Scalars['String']['output'];
  additionalStyle: Scalars['String']['output'];
  customProfile: GlobalCustomProfile;
  disallowChangeNickname: Scalars['Boolean']['output'];
  disallowChangeUsername: Scalars['Boolean']['output'];
  disallowPasswordAuth: Scalars['Boolean']['output'];
  disallowUserRegistration: Scalars['Boolean']['output'];
  weekStartDayOffset: Scalars['Int']['output'];
};

export type GlobalMemoRelatedSetting = {
  __typename?: 'GlobalMemoRelatedSetting';
  contentLengthLimit?: Maybe<Scalars['Int']['output']>;
  defaultVisibility?: Maybe<Scalars['String']['output']>;
  disableMarkdownShortcuts?: Maybe<Scalars['Boolean']['output']>;
  disallowPublicVisibility?: Maybe<Scalars['Boolean']['output']>;
  displayWithUpdateTime?: Maybe<Scalars['Boolean']['output']>;
  enableAutoCompact?: Maybe<Scalars['Boolean']['output']>;
  enableComment?: Maybe<Scalars['Boolean']['output']>;
  enableDoubleClickEdit?: Maybe<Scalars['Boolean']['output']>;
  enableLinkPreview?: Maybe<Scalars['Boolean']['output']>;
  enableLocation?: Maybe<Scalars['Boolean']['output']>;
  reactions?: Maybe<Array<Maybe<Scalars['String']['output']>>>;
};

export type GlobalProfile = {
  __typename?: 'GlobalProfile';
  instanceUrl: Scalars['String']['output'];
  mode: Scalars['String']['output'];
  owner?: Maybe<Scalars['String']['output']>;
  version: Scalars['String']['output'];
};

export type GlobalSettings = {
  __typename?: 'GlobalSettings';
  generalSetting: GlobalGeneralSetting;
  memoRelatedSetting: GlobalMemoRelatedSetting;
  storageSetting: GlobalStorageSetting;
};

export type GlobalStorageSetting = {
  __typename?: 'GlobalStorageSetting';
  id?: Maybe<Scalars['ID']['output']>;
};

export type Inbox = {
  __typename?: 'Inbox';
  createdTs: Scalars['Date']['output'];
  id: Scalars['ID']['output'];
  message: InboxMessage;
  receiverId: Scalars['Long']['output'];
  senderId: Scalars['Long']['output'];
  status: InboxStatus;
};

export type InboxMessage = {
  __typename?: 'InboxMessage';
  activityId?: Maybe<Scalars['Long']['output']>;
  type?: Maybe<InboxMessageType>;
};

export enum InboxMessageType {
  MemoComment = 'MEMO_COMMENT',
  TypeUnspecified = 'TYPE_UNSPECIFIED',
  VersionUpdate = 'VERSION_UPDATE'
}

export enum InboxStatus {
  Archived = 'ARCHIVED',
  Unread = 'UNREAD'
}

export enum MemosVisibility {
  Private = 'PRIVATE',
  Protected = 'PROTECTED',
  Public = 'PUBLIC',
  VisibilityUnspecified = 'VISIBILITY_UNSPECIFIED'
}

export type Query = {
  __typename?: 'Query';
  currentUser?: Maybe<User>;
  globalSettings: GlobalSettings;
  inboxes?: Maybe<Array<Maybe<Inbox>>>;
  profile: GlobalProfile;
  user?: Maybe<User>;
  userSetting?: Maybe<UserSetting>;
  users?: Maybe<Array<Maybe<User>>>;
};


export type QueryUserArgs = {
  find?: InputMaybe<FindUser>;
};


export type QueryUsersArgs = {
  find?: InputMaybe<FindUser>;
};

export enum RowStatus {
  Archived = 'ARCHIVED',
  Normal = 'NORMAL'
}

export type User = {
  __typename?: 'User';
  avatarUrl?: Maybe<Scalars['String']['output']>;
  createdAt: Scalars['LocalDateTime']['output'];
  email?: Maybe<Scalars['String']['output']>;
  id: Scalars['ID']['output'];
  nickname?: Maybe<Scalars['String']['output']>;
  role: UserRole;
  status: RowStatus;
  updatedAt: Scalars['LocalDateTime']['output'];
  userSetting?: Maybe<UserSetting>;
  userSpaces?: Maybe<Array<Maybe<UserSpace>>>;
  username: Scalars['String']['output'];
  version: Scalars['Int']['output'];
};

export enum UserRole {
  Admin = 'ADMIN',
  Host = 'HOST',
  RoleUnspecified = 'ROLE_UNSPECIFIED',
  User = 'USER'
}

export type UserSetting = {
  __typename?: 'UserSetting';
  appearance?: Maybe<Scalars['String']['output']>;
  id: Scalars['ID']['output'];
  locale: Scalars['String']['output'];
  memoVisibility: MemosVisibility;
};

export type UserSpace = {
  __typename?: 'UserSpace';
  id?: Maybe<Scalars['ID']['output']>;
  name?: Maybe<Scalars['String']['output']>;
  userId?: Maybe<Scalars['ID']['output']>;
};

export type CurrentUserSpacesQueryVariables = Exact<{ [key: string]: never; }>;


export type CurrentUserSpacesQuery = { __typename?: 'Query', currentUser?: { __typename?: 'User', userSpaces?: Array<{ __typename?: 'UserSpace', id?: string | null, name?: string | null } | null> | null } | null };

export type Init_User_StoreQueryVariables = Exact<{ [key: string]: never; }>;


export type Init_User_StoreQuery = { __typename?: 'Query', currentUser?: { __typename?: 'User', id: string, username: string, role: UserRole, email?: string | null, nickname?: string | null, avatarUrl?: string | null, status: RowStatus, createdAt: any, updatedAt: any, userSetting?: { __typename?: 'UserSetting', id: string, locale: string, memoVisibility: MemosVisibility, appearance?: string | null } | null } | null };

export type Init_Global_SettingQueryVariables = Exact<{ [key: string]: never; }>;


export type Init_Global_SettingQuery = { __typename?: 'Query', profile: { __typename?: 'GlobalProfile', owner?: string | null, version: string, mode: string }, globalSettings: { __typename?: 'GlobalSettings', generalSetting: { __typename?: 'GlobalGeneralSetting', disallowUserRegistration: boolean, disallowPasswordAuth: boolean, additionalScript: string, additionalStyle: string, weekStartDayOffset: number, disallowChangeNickname: boolean, disallowChangeUsername: boolean, customProfile: { __typename?: 'GlobalCustomProfile', title?: string | null, description?: string | null, locale?: string | null, logoUrl?: string | null, appearance?: string | null } }, memoRelatedSetting: { __typename?: 'GlobalMemoRelatedSetting', disallowPublicVisibility?: boolean | null, displayWithUpdateTime?: boolean | null, contentLengthLimit?: number | null, enableAutoCompact?: boolean | null, enableDoubleClickEdit?: boolean | null, enableLinkPreview?: boolean | null, enableComment?: boolean | null, enableLocation?: boolean | null, defaultVisibility?: string | null, reactions?: Array<string | null> | null, disableMarkdownShortcuts?: boolean | null } } };


export const CurrentUserSpacesDocument = {"kind":"Document","definitions":[{"kind":"OperationDefinition","operation":"query","name":{"kind":"Name","value":"CurrentUserSpaces"},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"currentUser"},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"userSpaces"},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"id"}},{"kind":"Field","name":{"kind":"Name","value":"name"}}]}}]}}]}}]} as unknown as DocumentNode<CurrentUserSpacesQuery, CurrentUserSpacesQueryVariables>;
export const Init_User_StoreDocument = {"kind":"Document","definitions":[{"kind":"OperationDefinition","operation":"query","name":{"kind":"Name","value":"INIT_USER_STORE"},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"currentUser"},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"id"}},{"kind":"Field","name":{"kind":"Name","value":"username"}},{"kind":"Field","name":{"kind":"Name","value":"role"}},{"kind":"Field","name":{"kind":"Name","value":"email"}},{"kind":"Field","name":{"kind":"Name","value":"nickname"}},{"kind":"Field","name":{"kind":"Name","value":"avatarUrl"}},{"kind":"Field","name":{"kind":"Name","value":"status"}},{"kind":"Field","name":{"kind":"Name","value":"createdAt"}},{"kind":"Field","name":{"kind":"Name","value":"updatedAt"}},{"kind":"Field","name":{"kind":"Name","value":"userSetting"},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"id"}},{"kind":"Field","name":{"kind":"Name","value":"locale"}},{"kind":"Field","name":{"kind":"Name","value":"memoVisibility"}},{"kind":"Field","name":{"kind":"Name","value":"appearance"}}]}}]}}]}}]} as unknown as DocumentNode<Init_User_StoreQuery, Init_User_StoreQueryVariables>;
export const Init_Global_SettingDocument = {"kind":"Document","definitions":[{"kind":"OperationDefinition","operation":"query","name":{"kind":"Name","value":"INIT_GLOBAL_SETTING"},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"profile"},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"owner"}},{"kind":"Field","name":{"kind":"Name","value":"version"}},{"kind":"Field","name":{"kind":"Name","value":"mode"}}]}},{"kind":"Field","name":{"kind":"Name","value":"globalSettings"},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"generalSetting"},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"disallowUserRegistration"}},{"kind":"Field","name":{"kind":"Name","value":"disallowPasswordAuth"}},{"kind":"Field","name":{"kind":"Name","value":"additionalScript"}},{"kind":"Field","name":{"kind":"Name","value":"additionalStyle"}},{"kind":"Field","name":{"kind":"Name","value":"customProfile"},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"title"}},{"kind":"Field","name":{"kind":"Name","value":"description"}},{"kind":"Field","name":{"kind":"Name","value":"locale"}},{"kind":"Field","name":{"kind":"Name","value":"logoUrl"}},{"kind":"Field","name":{"kind":"Name","value":"appearance"}}]}},{"kind":"Field","name":{"kind":"Name","value":"weekStartDayOffset"}},{"kind":"Field","name":{"kind":"Name","value":"disallowChangeNickname"}},{"kind":"Field","name":{"kind":"Name","value":"disallowChangeUsername"}}]}},{"kind":"Field","name":{"kind":"Name","value":"memoRelatedSetting"},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"disallowPublicVisibility"}},{"kind":"Field","name":{"kind":"Name","value":"displayWithUpdateTime"}},{"kind":"Field","name":{"kind":"Name","value":"contentLengthLimit"}},{"kind":"Field","name":{"kind":"Name","value":"enableAutoCompact"}},{"kind":"Field","name":{"kind":"Name","value":"enableDoubleClickEdit"}},{"kind":"Field","name":{"kind":"Name","value":"enableLinkPreview"}},{"kind":"Field","name":{"kind":"Name","value":"enableComment"}},{"kind":"Field","name":{"kind":"Name","value":"enableLocation"}},{"kind":"Field","name":{"kind":"Name","value":"defaultVisibility"}},{"kind":"Field","name":{"kind":"Name","value":"reactions"}},{"kind":"Field","name":{"kind":"Name","value":"disableMarkdownShortcuts"}}]}}]}}]}}]} as unknown as DocumentNode<Init_Global_SettingQuery, Init_Global_SettingQueryVariables>;
/** All built-in and custom scalars, mapped to their actual values */
export type Scalars = {
  ID: { input: string; output: string; }
  String: { input: string; output: string; }
  Boolean: { input: boolean; output: boolean; }
  Int: { input: number; output: number; }
  Float: { input: number; output: number; }
  Date: { input: any; output: any; }
  DateTime: { input: any; output: any; }
  LocalDateTime: { input: any; output: any; }
  Long: { input: any; output: any; }
};

export type FindUser = {
  id?: InputMaybe<Scalars['ID']['input']>;
  role?: InputMaybe<UserRole>;
  username?: InputMaybe<Scalars['String']['input']>;
};

export type GlobalCustomProfile = {
  __typename?: 'GlobalCustomProfile';
  appearance?: Maybe<Scalars['String']['output']>;
  description?: Maybe<Scalars['String']['output']>;
  locale?: Maybe<Scalars['String']['output']>;
  logoUrl?: Maybe<Scalars['String']['output']>;
  title?: Maybe<Scalars['String']['output']>;
};

export type GlobalGeneralSetting = {
  __typename?: 'GlobalGeneralSetting';
  additionalScript: Scalars['String']['output'];
  additionalStyle: Scalars['String']['output'];
  customProfile: GlobalCustomProfile;
  disallowChangeNickname: Scalars['Boolean']['output'];
  disallowChangeUsername: Scalars['Boolean']['output'];
  disallowPasswordAuth: Scalars['Boolean']['output'];
  disallowUserRegistration: Scalars['Boolean']['output'];
  weekStartDayOffset: Scalars['Int']['output'];
};

export type GlobalMemoRelatedSetting = {
  __typename?: 'GlobalMemoRelatedSetting';
  contentLengthLimit?: Maybe<Scalars['Int']['output']>;
  defaultVisibility?: Maybe<Scalars['String']['output']>;
  disableMarkdownShortcuts?: Maybe<Scalars['Boolean']['output']>;
  disallowPublicVisibility?: Maybe<Scalars['Boolean']['output']>;
  displayWithUpdateTime?: Maybe<Scalars['Boolean']['output']>;
  enableAutoCompact?: Maybe<Scalars['Boolean']['output']>;
  enableComment?: Maybe<Scalars['Boolean']['output']>;
  enableDoubleClickEdit?: Maybe<Scalars['Boolean']['output']>;
  enableLinkPreview?: Maybe<Scalars['Boolean']['output']>;
  enableLocation?: Maybe<Scalars['Boolean']['output']>;
  reactions?: Maybe<Array<Maybe<Scalars['String']['output']>>>;
};

export type GlobalProfile = {
  __typename?: 'GlobalProfile';
  instanceUrl: Scalars['String']['output'];
  mode: Scalars['String']['output'];
  owner?: Maybe<Scalars['String']['output']>;
  version: Scalars['String']['output'];
};

export type GlobalSettings = {
  __typename?: 'GlobalSettings';
  generalSetting: GlobalGeneralSetting;
  memoRelatedSetting: GlobalMemoRelatedSetting;
  storageSetting: GlobalStorageSetting;
};

export type GlobalStorageSetting = {
  __typename?: 'GlobalStorageSetting';
  id?: Maybe<Scalars['ID']['output']>;
};

export type Inbox = {
  __typename?: 'Inbox';
  createdTs: Scalars['Date']['output'];
  id: Scalars['ID']['output'];
  message: InboxMessage;
  receiverId: Scalars['Long']['output'];
  senderId: Scalars['Long']['output'];
  status: InboxStatus;
};

export type InboxMessage = {
  __typename?: 'InboxMessage';
  activityId?: Maybe<Scalars['Long']['output']>;
  type?: Maybe<InboxMessageType>;
};

export enum InboxMessageType {
  MemoComment = 'MEMO_COMMENT',
  TypeUnspecified = 'TYPE_UNSPECIFIED',
  VersionUpdate = 'VERSION_UPDATE'
}

export enum InboxStatus {
  Archived = 'ARCHIVED',
  Unread = 'UNREAD'
}

export enum MemosVisibility {
  Private = 'PRIVATE',
  Protected = 'PROTECTED',
  Public = 'PUBLIC',
  VisibilityUnspecified = 'VISIBILITY_UNSPECIFIED'
}

export type Query = {
  __typename?: 'Query';
  currentUser?: Maybe<User>;
  globalSettings: GlobalSettings;
  inboxes?: Maybe<Array<Maybe<Inbox>>>;
  profile: GlobalProfile;
  user?: Maybe<User>;
  userSetting?: Maybe<UserSetting>;
  users?: Maybe<Array<Maybe<User>>>;
};


export type QueryUserArgs = {
  find?: InputMaybe<FindUser>;
};


export type QueryUsersArgs = {
  find?: InputMaybe<FindUser>;
};

export enum RowStatus {
  Archived = 'ARCHIVED',
  Normal = 'NORMAL'
}

export type User = {
  __typename?: 'User';
  avatarUrl?: Maybe<Scalars['String']['output']>;
  createdAt: Scalars['LocalDateTime']['output'];
  email?: Maybe<Scalars['String']['output']>;
  id: Scalars['ID']['output'];
  nickname?: Maybe<Scalars['String']['output']>;
  role: UserRole;
  status: RowStatus;
  updatedAt: Scalars['LocalDateTime']['output'];
  userSetting?: Maybe<UserSetting>;
  userSpaces?: Maybe<Array<Maybe<UserSpace>>>;
  username: Scalars['String']['output'];
  version: Scalars['Int']['output'];
};

export enum UserRole {
  Admin = 'ADMIN',
  Host = 'HOST',
  RoleUnspecified = 'ROLE_UNSPECIFIED',
  User = 'USER'
}

export type UserSetting = {
  __typename?: 'UserSetting';
  appearance?: Maybe<Scalars['String']['output']>;
  id: Scalars['ID']['output'];
  locale: Scalars['String']['output'];
  memoVisibility: MemosVisibility;
};

export type UserSpace = {
  __typename?: 'UserSpace';
  id?: Maybe<Scalars['ID']['output']>;
  name?: Maybe<Scalars['String']['output']>;
  userId?: Maybe<Scalars['ID']['output']>;
};
