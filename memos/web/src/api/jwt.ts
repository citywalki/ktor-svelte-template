import { IAuthTokens, Token } from "@/types/api.ts";
import axios from "axios";
import {
  getAccessToken,
  getRefreshToken,
  setAccessToken,
  setAuthTokens,
} from "./tokensUtils";
import { jwtDecode, JwtPayload } from "jwt-decode";

const STORAGE_KEY = "jwt-token";
const BASE_URL = import.meta.env.VITE_APP_BASE_API;
const expireFudge = 10;

const requestRefresh = async (
  refreshToken: string,
): Promise<IAuthTokens | string> => {
  const response = await axios.post(`${BASE_URL}/auth/refresh_token`, {
    refreshToken: refreshToken,
  });

  return response.data.accessToken;
};

/**
 * Refreshes the access token using the provided function
 * Note: NOT to be called externally.  Only accessible through an interceptor
 *
 * @param {requestRefresh} requestRefresh - Function that is used to get a new access token
 * @returns {string} - Fresh access token
 */
const refreshToken = async (): Promise<Token> => {
  const refreshToken = await getRefreshToken();
  if (!refreshToken) throw new Error("No refresh token available");

  try {
    // Refresh and store access token using the supplied refresh function
    const newTokens = await requestRefresh(refreshToken);
    if (typeof newTokens === "object" && newTokens?.accessToken) {
      setAuthTokens(newTokens);
      return newTokens.accessToken;
    } else if (typeof newTokens === "string") {
      setAccessToken(newTokens);
      return newTokens;
    }

    throw new Error(
      "requestRefresh must either return a string or an object with an accessToken",
    );
  } catch (error) {
    // Failed to refresh token
    if (axios.isAxiosError(error)) {
      const status = error.response?.status;
      if (status === 401 || status === 422) {
        // The refresh token is invalid so remove the stored tokens
        await localStorage.remove(STORAGE_KEY);
        throw new Error(
          `Got ${status} on token refresh; clearing both auth tokens`,
        );
      }
    }

    // A different error, probably network error
    if (error instanceof Error) {
      throw new Error(`Failed to refresh auth token: ${error.message}`);
    } else {
      throw new Error("Failed to refresh auth token and failed to parse error");
    }
  }
};

const getTimestampFromToken = (token: Token): number | undefined => {
  const decoded = jwtDecode<JwtPayload>(token);

  return decoded.exp;
};

const getExpiresIn = (token: Token): number => {
  const expiration = getTimestampFromToken(token);

  if (!expiration) return -1;

  return expiration - Date.now() / 1000;
};

const isTokenExpired = (token: Token): boolean => {
  if (!token) return true;
  const expiresIn = getExpiresIn(token);
  return !expiresIn || expiresIn <= expireFudge;
};

const refreshTokenIfNeeded = async (): Promise<Token | undefined> => {
  // use access token (if we have it)
  let accessToken = await getAccessToken();

  // check if access token is expired
  if (!accessToken || isTokenExpired(accessToken)) {
    // do refresh
    accessToken = await refreshToken();
  }

  return accessToken;
};
let currentlyRequestingPromise: Promise<Token | undefined> | undefined =
  undefined;

const getCurrentAccessToken = async (
  headerPrefix = "Bearer ",
): Promise<string | null> => {
  // Waiting for a fix in axios types
  // We need refresh token to do any authenticated requests
  if (!(await getRefreshToken())) return null;

  let accessToken = undefined;

  // Try to await a current request
  if (currentlyRequestingPromise)
    accessToken = await currentlyRequestingPromise;

  if (!accessToken) {
    try {
      // Sets the promise so everyone else will wait - then get the value
      currentlyRequestingPromise = refreshTokenIfNeeded();
      accessToken = await currentlyRequestingPromise;

      // Reset the promise
      currentlyRequestingPromise = undefined;
    } catch (error: unknown) {
      // Reset the promise
      currentlyRequestingPromise = undefined;

      if (error instanceof Error) {
        throw new Error(
          `Unable to refresh access token for request due to token refresh error: ${error.message}`,
        );
      }
    }
  }

  // add token to headers
  if (accessToken) {
    return `${headerPrefix}${accessToken}`;
  }

  return null;
};

export default getCurrentAccessToken;
