<script lang="ts">
	import { Button } from '$lib/components/ui/button/index.js';
	import * as Card from '$lib/components/ui/card/index.js';
	import * as Form from '$lib/components/ui/form/index.js';
	import { Input } from '$lib/components/ui/input/index.js';
	import { Label } from '$lib/components/ui/label/index.js';
	import { m } from '$lib/paraglide/messages';
	import { superForm, defaults, setError } from 'sveltekit-superforms';
	import { valibot } from 'sveltekit-superforms/adapters';
	import frontendApi from '$lib/frontend-api';
	import * as v from 'valibot';
	import { createMutation } from '@tanstack/svelte-query';
	import { LoaderCircleIcon } from 'lucide-svelte';

	export let onSuccess: () => Promise<void> = async () => {};

	const LoginSchema = v.object({
		username: v.pipe(v.string(), v.nonEmpty()),
		password: v.pipe(v.string(), v.nonEmpty())
	});

	type LoginOutput = v.InferInput<typeof LoginSchema>; // { email: string; password: string }

	const loginMutation = createMutation({
		mutationFn: async (input: LoginOutput) => {
			return await frontendApi.post({
				url: 'api/auth/signin',
				data: JSON.stringify(input)
			});
		},
		async onSuccess(data, variables, context) {
			await onSuccess();
		}
	});

	const form = superForm(defaults(valibot(LoginSchema)), {
		SPA: true,
		resetForm: false,
		clearOnSubmit: 'errors-and-message',
		validators: valibot(LoginSchema),
		async onUpdate({ form }) {
			if (!form.valid) return;

			try {
				await $loginMutation.mutateAsync(form.data);
			} catch (e) {
				const response = e.data;
				if (response.fields) {
					response.fields.forEach((value) => {
						setError(form, value.field, value.message);
					});
				}
			}
		}
	});

	const { form: formData, enhance } = form;
</script>

<Card.Root class="w-full">
	<Card.Header>
		<Card.Title class="text-2xl">{m['auth.login']()}</Card.Title>
	</Card.Header>
	<Card.Content>
		<form method="POST" use:enhance>
			<Form.Field {form} name="username">
				<Form.Control>
					{#snippet children({ props })}
						<Form.Label>{m['auth.username']()}</Form.Label>
						<Input {...props} bind:value={$formData.username} />
					{/snippet}
				</Form.Control>
				<Form.FieldErrors />
			</Form.Field>
			<Form.Field {form} name="password">
				<Form.Control>
					{#snippet children({ props })}
						<Form.Label>{m['auth.password']()}</Form.Label>
						<Input {...props} type="password" bind:value={$formData.password} />
					{/snippet}
				</Form.Control>
				<Form.FieldErrors />
			</Form.Field>
			<Form.Button
				type="submit"
				class="w-full active:scale-[0.98] active:transition-all"
				disabled={$loginMutation.isPending}
			>
				{#if $loginMutation.isPending}
					<LoaderCircleIcon class="mr-2 h-5 w-5 animate-spin" />
				{/if}
				{m['auth.login']()}
			</Form.Button>
		</form>
		<div class="mt-4 text-center text-sm">
			{m['auth.no_have_account']()}
			<a href="/signup" class="underline"> {m['auth.signup']()} </a>
		</div>
	</Card.Content>
</Card.Root>
