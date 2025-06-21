import type { IAuthTokens } from 'axios-jwt';
import type { Token } from 'axios-jwt/src/Token';

const STORAGE_KEY = 'jwt-token';

export const setAuthTokens = (tokens: IAuthTokens) => {
	localStorage.setItem(STORAGE_KEY, JSON.stringify(tokens));
};
export const clearAuthTokens = () => {
	localStorage.removeItem(STORAGE_KEY);
};
export const setAccessToken = (token: Token): void => {
	const tokens = getAuthTokens();
	if (!tokens) {
		throw new Error('Unable to update access token since there are no tokens currently stored');
	}

	tokens.accessToken = token;
	setAuthTokens(tokens);
};

export const getAccessToken = async (): Promise<Token | undefined> => {
	const tokens = getAuthTokens();
	return tokens ? tokens.accessToken : undefined;
};

export const getRefreshToken = async (): Promise<Token | undefined> => {
	const tokens = getAuthTokens();
	return tokens ? tokens.refreshToken : undefined;
};

const getAuthTokens = (): IAuthTokens | undefined => {
	const rawTokens = localStorage.getItem(STORAGE_KEY);
	if (!rawTokens) return;

	try {
		// parse stored tokens JSON
		return JSON.parse(rawTokens);
	} catch (error: unknown) {
		if (error instanceof SyntaxError) {
			error.message = `Failed to parse auth tokens: ${rawTokens}`;
			throw error;
		}
	}
};
