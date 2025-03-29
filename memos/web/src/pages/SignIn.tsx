import { workspaceStore } from "@/store";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card.tsx";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form.tsx";
import { Input } from "@/components/ui/input.tsx";
import { Button } from "@/components/ui/button.tsx";
import { Loader2 } from "lucide-react";
import { useTranslate } from "@/utils/i18n.ts";
import { z } from "zod";
import { SubmitHandler, useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import useLoading from "@/hooks/useLoading.ts";
import useNavigateTo from "@/hooks/useNavigateTo.ts";
import authService from "@/api/services/auth_service.ts";
import { initialUserStoreFromGraphql } from "@/store/mobx/user.ts";

const SignIn = () => {
  const t = useTranslate();
  const navigateTo = useNavigateTo();
  const actionBtnLoadingState = useLoading(false);

  const workspaceGeneralSetting = workspaceStore.state.generalSetting;

  const formSchema = z.object({
    username: z.string(),
    password: z.string(),
  });
  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      username: "",
      password: "",
    },
  });

  const handleSubmit: SubmitHandler<z.infer<typeof formSchema>> = async (
    data,
  ): Promise<void> => {
    if (actionBtnLoadingState.isLoading) {
      return;
    }
    try {
      actionBtnLoadingState.setLoading();
      await authService.signIn({
        username: data.username,
        password: data.password,
      });
      await initialUserStoreFromGraphql();
      navigateTo("/");
    } catch (error: any) {
      console.error(error);
    }
    actionBtnLoadingState.setFinish();
  };

  return (
    <div className="flex min-h-svh w-full items-center justify-center p-6 md:p-10">
      <div className="flex flex-col w-full max-w-sm">
        <p className="w-full text-center text-5xl text-black opacity-80">
          {workspaceGeneralSetting.customProfile?.title || "Memos"}
        </p>
        <Card className="w-full mt-6 border-none shadow-none">
          <CardHeader>
            <CardTitle className="text-2xl">{t("auth.sign-in")}</CardTitle>
            {!workspaceStore.state.profile.owner && (
              <CardDescription>{t("auth.host-tip")}</CardDescription>
            )}
          </CardHeader>
          <CardContent>
            <Form {...form}>
              <form
                className="space-y-6"
                onSubmit={form.handleSubmit(handleSubmit)}
              >
                <FormField
                  name="username"
                  control={form.control}
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>{t("common.username")}</FormLabel>
                      <FormControl>
                        <Input
                          {...field}
                          readOnly={actionBtnLoadingState.isLoading}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  name="password"
                  control={form.control}
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>{t("common.password")}</FormLabel>
                      <FormControl>
                        <Input
                          readOnly={actionBtnLoadingState.isLoading}
                          type={"password"}
                          {...field}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <Button
                  type="submit"
                  className="w-full"
                  disabled={actionBtnLoadingState.isLoading}
                >
                  {actionBtnLoadingState.isLoading && (
                    <Loader2 className="w-5 h-auto ml-2 animate-spin opacity-60" />
                  )}
                  {t("common.sign-in")}
                </Button>
              </form>
            </Form>
          </CardContent>
        </Card>
      </div>
    </div>
  );
};

export default SignIn;
