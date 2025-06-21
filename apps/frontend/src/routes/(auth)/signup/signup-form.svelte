<script lang="ts">
	import { m } from '$lib/paraglide/messages';
	import * as Card from '$lib/components/ui/card/index.js';
	import * as Form from '$lib/components/ui/form/index.js';
	import { CardHeader, CardTitle, Root } from '$lib/components/ui/card';
	import { CardContent } from '$lib/components/ui/card/index.js';
	import { Input } from '$lib/components/ui/input';
	import * as v from 'valibot';
	import { defaults, setError, superForm } from 'sveltekit-superforms';
	import { valibot } from 'sveltekit-superforms/adapters';
	import frontendApi from '$lib/frontend-api';
	import { createMutation } from '@tanstack/svelte-query';
	import { LoaderCircleIcon } from 'lucide-svelte';
	import { type IAuthTokens, setAuthTokens } from 'axios-jwt';

	export let onSuccess: () => Promise<void> = async () => {};

	const SignupSchema = v.object({
		username: v.pipe(v.string(), v.nonEmpty()),
		password: v.pipe(v.string(), v.minLength(8))
	});

	type SignupOutput = v.InferOutput<typeof SignupSchema>; // { email: string; password: string }

	const signupMutation = createMutation({
		mutationFn: async (input: SignupOutput) => {
			return frontendApi.post({
				url: 'api/auth/signup',
				data: JSON.stringify(input)
			});
		},
		async onSuccess(data: IAuthTokens, variables, context) {
			await setAuthTokens(data);
			await onSuccess();
		}
	});

	const form = superForm(defaults(valibot(SignupSchema)), {
		SPA: true,
		resetForm: false,
		clearOnSubmit: 'errors-and-message',
		validators: valibot(SignupSchema),
		async onUpdate({ form }) {
			if (!form.valid) return;

			try {
				await $signupMutation.mutateAsync(form.data);
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

<Root class="w-full">
	<CardHeader>
		<CardTitle class="text-2xl">{m['auth.signup']()}</CardTitle>
	</CardHeader>
	<CardContent>
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
				disabled={$signupMutation.isPending}
			>
				{#if $signupMutation.isPending}
					<LoaderCircleIcon class="mr-2 h-5 w-5 animate-spin" />
				{/if}
				{m['auth.signup']()}
			</Form.Button>
		</form>
	</CardContent>
</Root>
