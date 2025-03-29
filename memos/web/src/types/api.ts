export interface Result<T = any> {
  status: number;
  message: string;
  data?: T;
}

export type Token = string;
export interface IAuthTokens {
  accessToken: Token;
  refreshToken: Token;
}
