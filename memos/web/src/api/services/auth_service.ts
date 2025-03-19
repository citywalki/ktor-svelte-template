import apiClient from "@/api/apiClient.ts";
import { User } from "@/api/services/user_service.ts";

interface SignUpRequest {
  username: string;
  password: string;
}

const authService = (() => {
  function getAuthStatus() {
    return apiClient.get<User>({
      url: "/auth/status",
    });
  }
  function signUp(request: SignUpRequest) {
    return apiClient.post<User>({
      url: "/auth/signup",
      params: request,
    });
  }

  return {
    getAuthStatus,
    signUp,
  };
})();

export default authService;
