<script lang="ts">
    import {Button} from "$lib/components/ui/button/index.js";
    import * as Card from "$lib/components/ui/card/index.js";
    import * as Form from "$lib/components/ui/form/index.js";
    import {Input} from "$lib/components/ui/input/index.js";
    import {Label} from "$lib/components/ui/label/index.js";
    import {m} from '$lib/paraglide/messages'
    import {
        superForm,
        defaults
    } from "sveltekit-superforms";
    import {valibot} from "sveltekit-superforms/adapters";
    import {sendUnauthorizedRequest} from "$lib/frontend-api";
    import * as v from "valibot";

    const schema = v.object({
        username: v.pipe(v.string(), v.nonEmpty()),
        password: v.pipe(v.string(), v.minLength(8)),
    });

    const form = superForm(defaults(valibot(schema)), {
        SPA: true,
        resetForm: false,
        clearOnSubmit: 'errors-and-message',
        validators: valibot(schema),
        async onUpdate({form}) {
            if (!form.valid) return;

            await sendUnauthorizedRequest('POST', "/auth/signin", form.data)
        }
    });

    const {form: formData, enhance} = form;
</script>

<Card.Root class="w-full mt-2">
    <Card.Header>
        <Card.Title class="text-2xl">{m["login.login"]()}</Card.Title>
    </Card.Header>
    <Card.Content>
        <form method="POST" use:enhance>
            <Form.Field {form} name="username">
                <Form.Control>
                    {#snippet children({props})}
                        <Form.Label>{m["login.username"]()}</Form.Label>
                        <Input {...props} bind:value={$formData.username} />
                    {/snippet}
                </Form.Control>
                <Form.FieldErrors/>
            </Form.Field>
            <Form.Field {form} name="password">
                <Form.Control>
                    {#snippet children({props})}
                        <Form.Label>{m["login.password"]()}</Form.Label>
                        <Input {...props} type="password" bind:value={$formData.password}/>
                    {/snippet}
                </Form.Control>
                <Form.FieldErrors/>
            </Form.Field>
            <Form.Button class="w-full active:scale-[0.98] active:transition-all">{m["login.login"]()}</Form.Button>
        </form>
        <div class="mt-4 text-center text-sm">
            {m["login.no_have_account"]()}
            <a href="##" class="underline"> {m["login.signup"]()} </a>
        </div>
    </Card.Content>
</Card.Root>
