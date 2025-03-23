<script lang="ts">
	import '../app.css';
	let { children } = $props();
</script>

<div class="app h-full">
    <main class="h-full">
        {@render children()}
    </main>
</div>

<svelte:head>
    <title>memos</title>
</svelte:head>
