import apiClient from "@/api/apiClient.ts";
import { User } from "@/api/services/user_service.ts";

const authService = (() => {
  function getAuthStatus() {
    return apiClient.get<User>({
      url: "/auth/status",
    });
  }

  return {
    getAuthStatus,
  };
})();

export default authService;
