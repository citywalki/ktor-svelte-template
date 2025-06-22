<script lang="ts">
	import { Button } from '$lib/components/ui/button';
	import * as Sidebar from '$lib/components/ui/sidebar/index.js';
	import {
		CirclePlusIcon,
		SearchIcon,
		SettingsIcon,
		PanelLeftCloseIcon,
		PackageIcon,
		LayoutDashboardIcon
	} from 'lucide-svelte';
	import NavMain from './nav-main.svelte';
	import type { ComponentProps } from 'svelte';

	let { ...restProps }: ComponentProps<typeof Sidebar.Root> = $props();

	const data = {
		navMain: [
			{
				title: 'Dashboard',
				url: ''
			}
		]
	};
</script>

<Sidebar.Root collapsible="offcanvas" {...restProps}>
	<Sidebar.Header>
		<Sidebar.Menu>
			<Sidebar.MenuItem>
				<Sidebar.MenuButton class="data-[slot=sidebar-menu-button]:!p-1.5">
					{#snippet child({ props })}
						<a href="##" {...props}>
							<span class="text-base font-semibold">App</span>
						</a>
					{/snippet}
				</Sidebar.MenuButton>
			</Sidebar.MenuItem>
		</Sidebar.Menu>
	</Sidebar.Header>
	<Sidebar.Content>
		<NavMain items={data.navMain} />
		<!--		<NavDocuments items={data.documents} />-->
		<!--		<NavSecondary items={data.navSecondary} class="mt-auto" />-->
	</Sidebar.Content>
</Sidebar.Root>
