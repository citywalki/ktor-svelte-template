import type { ParamMatcher } from '@sveltejs/kit';

export const match = ((param: string) => {
    return param === 'walkin';
}) satisfies ParamMatcher;
