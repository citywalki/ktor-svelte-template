import type { HandleFetch } from '@sveltejs/kit';

export const handleFetch: HandleFetch = async ({ event, request, fetch }) => {
	if (request.url.startsWith('https://api.my-domain.com/')) {
		request.headers.set('cookie', <string>event.request.headers.get('cookie'));
	}

	debugger

	const response = await fetch(request);

	return response;
};
