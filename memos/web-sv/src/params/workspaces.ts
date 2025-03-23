import type { ParamMatcher } from '@sveltejs/kit';

export const match = ((param: string) => {
    return param === 'wp1';
}) satisfies ParamMatcher;
