import { PUBLIC_BASE_URL } from '$env/static/public';
import axios, { AxiosError, type AxiosRequestConfig, type AxiosResponse } from 'axios';
import {
	applyAuthTokenInterceptor,
	getBrowserLocalStorage,
	type IAuthTokens,
	type TokenRefreshRequest
} from 'axios-jwt';

// 创建 axios 实例
const axiosInstance = axios.create({
	baseURL: PUBLIC_BASE_URL,
	timeout: 50000,
	headers: { 'Content-Type': 'application/json;charset=utf-8' }
});

const requestRefresh: TokenRefreshRequest = async (
	refreshToken: string
): Promise<IAuthTokens | string> => {
	// Important! Do NOT use the axios instance that you supplied to applyAuthTokenInterceptor (in our case 'axiosInstance')
	// because this will result in an infinite loop when trying to refresh the token.
	// Use the global axios client or a different instance
	const response = await axios.post(`${PUBLIC_BASE_URL}/auth/refresh_token`, {
		token: refreshToken
	});

	// If your backend supports rotating refresh tokens, you may also choose to return an object containing both tokens:
	// return {
	//  accessToken: response.data.access_token,
	//  refreshToken: response.data.refresh_token
	//}

	return response.data.access_token;
};

// 添加响应拦截器
axiosInstance.interceptors.response.use(
	function (response) {
		// 2xx 范围内的状态码都会触发该函数。
		return response.data;
	},
	function (err) {
		let message = '';
		switch (err.response.status) {
			case 400:
				message = '请求错误(400)';
				break;
			case 401:
				message = '未授权，请重新登录(401)';
				// 这里可以做清空storage并跳转到登录页的操作
				break;
			case 403:
				message = '拒绝访问(403)';
				break;
			case 404:
				message = '请求出错(404)';
				break;
			case 408:
				message = '请求超时(408)';
				break;
			case 500:
				message = err.response.data ?? '服务器错误(500)';
				break;
			case 501:
				message = '服务未实现(501)';
				break;
			case 502:
				message = '网络错误(502)';
				break;
			case 503:
				message = '服务不可用(503)';
				break;
			case 504:
				message = '网络超时(504)';
				break;
			case 505:
				message = 'HTTP版本不受支持(505)';
				break;
			default:
				message = `连接出错(${err.response.status})!`;
		}

		return Promise.reject(err.response);
	}
);

// 3. Add interceptor to your axios instance
applyAuthTokenInterceptor(axiosInstance, {
	requestRefresh,
	header: 'Authorization',
	headerPrefix: 'Bearer '
});

// New to 2.2.0+: initialize with storage: localStorage/sessionStorage/nativeStorage. Helpers: getBrowserLocalStorage, getBrowserSessionStorage
const getStorage = getBrowserLocalStorage;

// You can create you own storage, it has to comply with type StorageType
applyAuthTokenInterceptor(axiosInstance, { requestRefresh, getStorage });

class APIClient {
	get<T = any>(config: AxiosRequestConfig): Promise<AxiosResponse<T>> {
		return this.request({ ...config, method: 'GET' });
	}

	post<T = any>(config: AxiosRequestConfig): Promise<AxiosResponse<T>> {
		return this.request({ ...config, method: 'POST' });
	}

	put<T = any>(config: AxiosRequestConfig): Promise<AxiosResponse<T>> {
		return this.request({ ...config, method: 'PUT' });
	}

	delete<T = any>(config: AxiosRequestConfig): Promise<AxiosResponse<T>> {
		return this.request({ ...config, method: 'DELETE' });
	}

	request<T = any>(config: AxiosRequestConfig): Promise<AxiosResponse<T>> {
		return new Promise((resolve, reject) => {
			axiosInstance
				.request<any, AxiosResponse<T>>(config)
				.then((res: AxiosResponse<T>) => {
					resolve(res as unknown as Promise<AxiosResponse<T>>);
				})
				.catch((e: Error | AxiosError) => {
					reject(e);
				});
		});
	}
}

export default new APIClient();
