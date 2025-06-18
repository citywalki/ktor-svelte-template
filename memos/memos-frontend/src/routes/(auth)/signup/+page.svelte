<script lang="ts">
	import SignupForm from "./signup-form.svelte";


</script>

<div class="py-4 sm:py-8 w-90 max-w-full min-h-svh mx-auto flex flex-col justify-start items-center">
    <div class="w-full py-4 grow flex flex-col justify-center items-center">
        <div class="w-full flex flex-row justify-center items-center mb-6">
            <!--            <img class="h-14 w-auto rounded-full shadow" src={workspaceGeneralSetting.customProfile?.logoUrl || "/logo.webp"} alt="" />-->
            <p class="ml-2 text-5xl text-black opacity-80 dark:text-gray-200">
                Memos
            </p>
        </div>
        <SignupForm />
    </div>
</div>
