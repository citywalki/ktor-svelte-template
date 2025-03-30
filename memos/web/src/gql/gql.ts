/* eslint-disable */
import * as types from './graphql';
import { TypedDocumentNode as DocumentNode } from '@graphql-typed-document-node/core';

/**
 * Map of all GraphQL operations in the project.
 *
 * This map has several performance disadvantages:
 * 1. It is not tree-shakeable, so it will include all operations in the project.
 * 2. It is not minifiable, so the string of a GraphQL query will be multiple times inside the bundle.
 * 3. It does not support dead code elimination, so it will add unused operations.
 *
 * Therefore it is highly recommended to use the babel or swc plugin for production.
 * Learn more about it here: https://the-guild.dev/graphql/codegen/plugins/presets/preset-client#reducing-bundle-size
 */
type Documents = {
    "\n    query CurrentUserSpaces {\n      currentUser {\n        userSpaces {\n          id\n          name\n        }\n      }\n    }\n  ": typeof types.CurrentUserSpacesDocument,
    "\n        query INIT_USER_STORE {\n          currentUser {\n            id\n            username\n            role\n            email\n            nickname\n            avatarUrl\n            status\n            createdAt\n            updatedAt\n            userSetting {\n              id\n              locale\n              memoVisibility\n              appearance\n            }\n          }\n        }\n    ": typeof types.Init_User_StoreDocument,
    "\n  query INIT_GLOBAL_SETTING {\n    profile {\n      owner\n      version\n      mode\n    }\n    globalSettings {\n      generalSetting {\n        disallowUserRegistration\n        disallowPasswordAuth\n        additionalScript\n        additionalStyle\n        customProfile {\n          title\n          description\n          locale\n          logoUrl\n          appearance\n        }\n        weekStartDayOffset\n        disallowChangeNickname\n        disallowChangeUsername\n      }\n      memoRelatedSetting {\n        disallowPublicVisibility\n        displayWithUpdateTime\n        contentLengthLimit\n        enableAutoCompact\n        enableDoubleClickEdit\n        enableLinkPreview\n        enableComment\n        enableLocation\n        defaultVisibility\n        reactions\n        disableMarkdownShortcuts\n      }\n    }\n  }\n  ": typeof types.Init_Global_SettingDocument,
};
const documents: Documents = {
    "\n    query CurrentUserSpaces {\n      currentUser {\n        userSpaces {\n          id\n          name\n        }\n      }\n    }\n  ": types.CurrentUserSpacesDocument,
    "\n        query INIT_USER_STORE {\n          currentUser {\n            id\n            username\n            role\n            email\n            nickname\n            avatarUrl\n            status\n            createdAt\n            updatedAt\n            userSetting {\n              id\n              locale\n              memoVisibility\n              appearance\n            }\n          }\n        }\n    ": types.Init_User_StoreDocument,
    "\n  query INIT_GLOBAL_SETTING {\n    profile {\n      owner\n      version\n      mode\n    }\n    globalSettings {\n      generalSetting {\n        disallowUserRegistration\n        disallowPasswordAuth\n        additionalScript\n        additionalStyle\n        customProfile {\n          title\n          description\n          locale\n          logoUrl\n          appearance\n        }\n        weekStartDayOffset\n        disallowChangeNickname\n        disallowChangeUsername\n      }\n      memoRelatedSetting {\n        disallowPublicVisibility\n        displayWithUpdateTime\n        contentLengthLimit\n        enableAutoCompact\n        enableDoubleClickEdit\n        enableLinkPreview\n        enableComment\n        enableLocation\n        defaultVisibility\n        reactions\n        disableMarkdownShortcuts\n      }\n    }\n  }\n  ": types.Init_Global_SettingDocument,
};

/**
 * The gql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 *
 *
 * @example
 * ```ts
 * const query = gql(`query GetUser($id: ID!) { user(id: $id) { name } }`);
 * ```
 *
 * The query argument is unknown!
 * Please regenerate the types.
 */
export function gql(source: string): unknown;

/**
 * The gql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function gql(source: "\n    query CurrentUserSpaces {\n      currentUser {\n        userSpaces {\n          id\n          name\n        }\n      }\n    }\n  "): (typeof documents)["\n    query CurrentUserSpaces {\n      currentUser {\n        userSpaces {\n          id\n          name\n        }\n      }\n    }\n  "];
/**
 * The gql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function gql(source: "\n        query INIT_USER_STORE {\n          currentUser {\n            id\n            username\n            role\n            email\n            nickname\n            avatarUrl\n            status\n            createdAt\n            updatedAt\n            userSetting {\n              id\n              locale\n              memoVisibility\n              appearance\n            }\n          }\n        }\n    "): (typeof documents)["\n        query INIT_USER_STORE {\n          currentUser {\n            id\n            username\n            role\n            email\n            nickname\n            avatarUrl\n            status\n            createdAt\n            updatedAt\n            userSetting {\n              id\n              locale\n              memoVisibility\n              appearance\n            }\n          }\n        }\n    "];
/**
 * The gql function is used to parse GraphQL queries into a document that can be used by GraphQL clients.
 */
export function gql(source: "\n  query INIT_GLOBAL_SETTING {\n    profile {\n      owner\n      version\n      mode\n    }\n    globalSettings {\n      generalSetting {\n        disallowUserRegistration\n        disallowPasswordAuth\n        additionalScript\n        additionalStyle\n        customProfile {\n          title\n          description\n          locale\n          logoUrl\n          appearance\n        }\n        weekStartDayOffset\n        disallowChangeNickname\n        disallowChangeUsername\n      }\n      memoRelatedSetting {\n        disallowPublicVisibility\n        displayWithUpdateTime\n        contentLengthLimit\n        enableAutoCompact\n        enableDoubleClickEdit\n        enableLinkPreview\n        enableComment\n        enableLocation\n        defaultVisibility\n        reactions\n        disableMarkdownShortcuts\n      }\n    }\n  }\n  "): (typeof documents)["\n  query INIT_GLOBAL_SETTING {\n    profile {\n      owner\n      version\n      mode\n    }\n    globalSettings {\n      generalSetting {\n        disallowUserRegistration\n        disallowPasswordAuth\n        additionalScript\n        additionalStyle\n        customProfile {\n          title\n          description\n          locale\n          logoUrl\n          appearance\n        }\n        weekStartDayOffset\n        disallowChangeNickname\n        disallowChangeUsername\n      }\n      memoRelatedSetting {\n        disallowPublicVisibility\n        displayWithUpdateTime\n        contentLengthLimit\n        enableAutoCompact\n        enableDoubleClickEdit\n        enableLinkPreview\n        enableComment\n        enableLocation\n        defaultVisibility\n        reactions\n        disableMarkdownShortcuts\n      }\n    }\n  }\n  "];

export function gql(source: string) {
  return (documents as any)[source] ?? {};
}

export type DocumentType<TDocumentNode extends DocumentNode<any, any>> = TDocumentNode extends DocumentNode<  infer TType,  any>  ? TType  : never;