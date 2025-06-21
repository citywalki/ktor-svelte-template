<script lang="ts">
	import * as Resizable from '$lib/components/ui/resizable';
	import type { PaneAPI } from 'paneforge';

	let { children } = $props();
	let panelLeft: PaneAPI;
</script>

<Resizable.PaneGroup direction="horizontal">
	<Resizable.Pane minSize={15} maxSize={30}>
		<div class="flex h-full max-h-screen flex-col gap-2"></div>
	</Resizable.Pane>
	<Resizable.Handle />
	<Resizable.Pane class="grid h-screen">
		{@render children()}
	</Resizable.Pane>
</Resizable.PaneGroup>
