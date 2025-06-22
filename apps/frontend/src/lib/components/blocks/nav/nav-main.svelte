<script lang="ts">
	import { Button } from '$lib/components/ui/button/index.js';
	import * as Sidebar from '$lib/components/ui/sidebar/index.js';
	import { Icon } from 'lucide-svelte';

	let { items }: { items: { title: string; url: string }[] } = $props();
</script>

<Sidebar.Group>
	<Sidebar.GroupContent class="flex flex-col gap-2">
		<Sidebar.Menu>
			{#each items as item (item.title)}
				<Sidebar.MenuItem>
					<Sidebar.MenuButton tooltipContent={item.title}>
						<span>{item.title}</span>
					</Sidebar.MenuButton>
				</Sidebar.MenuItem>
			{/each}
		</Sidebar.Menu>
	</Sidebar.GroupContent>
</Sidebar.Group>
