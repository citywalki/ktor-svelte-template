import type { PageLoad } from './$types';

export const load: PageLoad = async ({ fetch, depends }) => {

    const signin = async () => {
        await fetch('/auth/signin', {
            method: 'post'
        })
    }
    return {
        signin
    }
}
