<script lang="ts">
	import * as Resizable from '$lib/components/ui/resizable';
	import * as Sidebar from '$lib/components/ui/sidebar/index.js';
	import AppSidebar from '$lib/components/blocks/nav/app-sidebar.svelte';
	import type { PaneAPI } from 'paneforge';
	import type { LayoutProps } from './$types';

	let { data, children }: LayoutProps = $props();

	let me = data.me;
	let panelLeft: PaneAPI;
</script>

<Sidebar.Provider
	style="--sidebar-width: calc(var(--spacing) * 72); --header-height: calc(var(--spacing) * 12);"
>
	<AppSidebar />
	<Sidebar.Inset>
		<!--		<SiteHeader />-->
		<div class="flex flex-1 flex-col">
			<div class="@container/main flex flex-1 flex-col gap-2">
				<div class="flex flex-col gap-4 py-4 md:gap-6 md:py-6">
					{@render children()}
					<!--					<SectionCards />-->
					<div class="px-4 lg:px-6">
						<!--						<ChartAreaInteractive />-->
					</div>
					<!--					<DataTable {data} />-->
				</div>
			</div>
		</div>
	</Sidebar.Inset>
</Sidebar.Provider>

<!--<Resizable.PaneGroup direction="horizontal">-->
<!--	<Resizable.Pane minSize={15} maxSize={30}>-->
<!--		<div class="flex h-full max-h-screen flex-col gap-2">-->
<!--			<div class="border-b px-4 py-2">-->
<!--				<NavTools me={data.me.user} />-->
<!--			</div>-->
<!--			&lt;!&ndash;			<div class="w-full flex-1 overflow-y-auto">&ndash;&gt;-->
<!--			&lt;!&ndash;				<TablesNav tables={$tables} bases={$bases} dashboards={$dashboards} {isLoading} />&ndash;&gt;-->
<!--			&lt;!&ndash;			</div>&ndash;&gt;-->

<!--			&lt;!&ndash;			<div class="border-t px-4 py-2">&ndash;&gt;-->
<!--			&lt;!&ndash;				<MemberMenu user={data.me.user} />&ndash;&gt;-->
<!--			&lt;!&ndash;			</div>&ndash;&gt;-->
<!--		</div>-->
<!--	</Resizable.Pane>-->
<!--	<Resizable.Handle />-->
<!--	<Resizable.Pane class="grid h-screen">-->
<!--		{@render children()}-->
<!--	</Resizable.Pane>-->
<!--</Resizable.PaneGroup>-->
