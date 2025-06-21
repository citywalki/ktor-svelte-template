<script lang="ts">
	import '../app.css';
	import { Toaster } from '$lib/components/ui/sonner';
	import { onMount } from 'svelte';
	import * as v from 'valibot';
	import '@valibot/i18n/zh-CN';
	import { QueryClient, QueryClientProvider } from '@tanstack/svelte-query';
	import { browser } from '$app/environment';

	let { children } = $props();

	onMount(async () => {
		v.setGlobalConfig({ lang: 'zh-CN' });
	});

	const queryClient = new QueryClient({
		defaultOptions: {
			queries: {
				enabled: browser
			}
		}
	});
</script>

<Toaster />
<div class="app h-full">
	<main class="h-full">
		<QueryClientProvider client={queryClient}>
			{@render children()}
		</QueryClientProvider>
	</main>
</div>

<svelte:head>
	<!--    <link rel="icon" href="/assets/favicon/favicon.ico" />-->
	<!--    <link rel="apple-touch-icon" sizes="180x180" href="/assets/favicon/apple-touch-icon.png" />-->
	<!--    <link rel="icon" type="image/png" sizes="32x32" href="/assets/favicon/favicon-32x32.png" />-->
	<!--    <link rel="icon" type="image/png" sizes="16x16" href="/assets/favicon/favicon-16x16.png" />-->
	<!--    <link rel="manifest" href="/assets/favicon/site.webmanifest" />-->
	<title>memos</title>
</svelte:head>
