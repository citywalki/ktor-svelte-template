<script lang="ts">
	import SignupForm from './signup-form.svelte';
	import { goto } from '$app/navigation';
</script>

<div
	class="mx-auto flex min-h-svh w-90 max-w-full flex-col items-center justify-start py-4 sm:py-8"
>
	<div class="flex w-full grow flex-col items-center justify-center py-4">
		<div class="mb-6 flex w-full flex-row items-center justify-center">
			<p class="ml-2 text-5xl text-black opacity-80 dark:text-gray-200">Memos</p>
		</div>
		<SignupForm
			onSuccess={async () => {
				await goto('/');
			}}
		/>
	</div>
</div>
