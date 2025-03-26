import apiClient from "@/api/apiClient.ts";
import { User } from "@/api/services/user_service.ts";

interface SignUpRequest {
  username: string;
  password: string;
}

interface SignInRequest {
  username: string;
  password: string;
}

const authService = (() => {
  function getAuthStatus() {
    return apiClient.post<User>({
      url: "/auth/status",
    });
  }
  function signUp(request: SignUpRequest) {
    return apiClient.post<User>({
      url: "/auth/signup",
      params: request,
    });
  }

  function signIn(request: SignInRequest) {
    return apiClient.post<User>({
      url: "/auth/signIn",
      params: request,
    });
  }

  return {
    getAuthStatus,
    signUp,
    signIn,
  };
})();

export default authService;
