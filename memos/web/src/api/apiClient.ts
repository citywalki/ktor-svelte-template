import { Result } from "@/types/api";
import axios, { AxiosError, AxiosRequestConfig, AxiosResponse } from "axios";
import { cacheExchange, Client, fetchExchange } from "urql";
import { authExchange } from "@urql/exchange-auth";
import getCurrentAccessToken from "@/api/jwt.ts";

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
    authExchange(async (utils) => {
      const token = await getCurrentAccessToken();
      return {
        addAuthToOperation(operation) {
          if (!token) return operation;
          return utils.appendHeaders(operation, {
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
    const { rawResponse, hideMsg } = error.config ?? {};
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
