import type { LayoutLoad } from './$types';
import frontendApi from '$lib/frontend-api';
import { redirect } from '@sveltejs/kit';

export const ssr = false;

export const load: LayoutLoad = async () => {
	const me = await frontendApi.get<any>('api/user/me');
	if (me.status == 301) {
		throw redirect(301, me.url);
	}
	return {
		me: await me.data
	};
};
