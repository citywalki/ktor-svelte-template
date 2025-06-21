import {writable} from "svelte/store";

type IUser = Record<string, string | number | null>

export const userData = writable<IUser>({})

