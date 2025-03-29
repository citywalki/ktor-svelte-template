import { Result } from "@/types/api";
import axios, { AxiosError, AxiosRequestConfig, AxiosResponse } from "axios";
import {
  cacheExchange,
  Client,
  CombinedError,
  fetchExchange,
  Operation,
} from "urql";
import { AuthConfig, authExchange, AuthUtilities } from "@urql/exchange-auth";
import getCurrentAccessToken, { refreshTokenIfNeeded } from "@/api/jwt.ts";

const BASE_URL = import.meta.env.VITE_APP_BASE_API;

// 创建 axios 实例
const axiosInstance = axios.create({
  baseURL: BASE_URL,
  timeout: 50000,
  headers: { "Content-Type": "application/json;charset=utf-8" },
});

export const graphqlClient = new Client({
  url: "/graphql",
  exchanges: [
    cacheExchange,
    authExchange(async (utilities: AuthUtilities): Promise<AuthConfig> => {
      const token = await getCurrentAccessToken();
      return {
        didAuthError(error: CombinedError): boolean {
          console.error(error);
          return false;
        },
        async refreshAuth(): Promise<void> {
          await refreshTokenIfNeeded();
        },
        addAuthToOperation(operation: Operation): Operation {
          if (!token) return operation;
          return utilities.appendHeaders(operation, {
            Authorization: token,
          });
        },
      };
    }),
    fetchExchange,
  ],
});

axiosInstance.interceptors.response.use(
  (response: AxiosResponse<Result | any>) => {
    return Promise.resolve(response.data);
  },
  (error: AxiosError<Result | any>) => {
    // eslint-disable-next-line @typescript-eslint/ban-ts-comment
    // @ts-expect-error
    const { rawResponse } = error.config ?? {};
    const { status, message } = error.response?.data || {};
    const response: Result = {
      status: status,
      message: message,
    };

    return rawResponse ? Promise.reject(error) : Promise.reject(response);
  },
);

class APIClient {
  get<T = any>(config: AxiosRequestConfig): Promise<T> {
    return this.request({ ...config, method: "GET" });
  }

  post<T = any>(config: AxiosRequestConfig): Promise<T> {
    return this.request({ ...config, method: "POST" });
  }

  put<T = any>(config: AxiosRequestConfig): Promise<T> {
    return this.request({ ...config, method: "PUT" });
  }

  delete<T = any>(config: AxiosRequestConfig): Promise<T> {
    return this.request({ ...config, method: "DELETE" });
  }

  request<T = any>(config: AxiosRequestConfig): Promise<T> {
    return new Promise((resolve, reject) => {
      axiosInstance
        .request<any, AxiosResponse<Result>>(config)
        .then((res: AxiosResponse<Result>) => {
          resolve(res as unknown as Promise<T>);
        })
        .catch((e: Error | AxiosError) => {
          reject(e);
        });
    });
  }
}

export default new APIClient();
