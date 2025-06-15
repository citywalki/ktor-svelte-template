<script>
	import { Button } from '$lib/components/ui/button/index.js';
	import { toast } from 'svelte-sonner';

</script>

<h1>Welcome to SvelteKit</h1>
<p>Visit <a href="https://svelte.dev/docs/kit">svelte.dev/docs/kit</a> to read the documentation</p>
<Button>Click me</Button>
<Button onclick={() => toast('Hello world')}>Show toast</Button>
