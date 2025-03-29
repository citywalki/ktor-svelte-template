import apiClient from "@/api/apiClient.ts";
import { setAuthTokens } from "../tokensUtils";

interface SignUpRequest {
  username: string;
  password: string;
}

interface SignInRequest {
  username: string;
  password: string;
}

interface SignInResponse {
  accessToken: string;
  refreshToken: string;
}

const authService = (() => {
  function signUp(request: SignUpRequest) {
    return apiClient.post<string>({
      url: "/auth/signup",
      params: request,
    });
  }

  async function signIn(request: SignInRequest) {
    const result = await apiClient.post<SignInResponse>({
      url: "/login",
      data: request,
    });
    return setAuthTokens({
      accessToken: result.accessToken,
      refreshToken: result.refreshToken,
    });
  }

  return {
    signUp,
    signIn,
  };
})();

export default authService;
