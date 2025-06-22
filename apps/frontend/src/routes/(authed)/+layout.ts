import type { LayoutLoad } from './$types';
import { redirect } from '@sveltejs/kit';

export const ssr = false;

export const load: LayoutLoad = async (event) => {
	const me = await event.fetch('/api/me');
	if (me.redirected) {
		throw redirect(301, me.url);
	}

	return {
		me: await me.json()
	};
};
