import { createBrowserRouter } from "react-router";
import App from "@/App.tsx";
import { lazy, Suspense } from "react";
import Loading from "@/pages/Loading.tsx";

const SignIn = lazy(() => import("@/pages/SignIn"));
const SignUp = lazy(() => import("@/pages/SignUp"));

export enum Routes {
  ROOT = "/",
  RESOURCES = "/resources",
  INBOX = "/inbox",
  ARCHIVED = "/archived",
  SETTING = "/setting",
  EXPLORE = "/explore",
  AUTH = "/auth",
}

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    children: [
      {
        path: Routes.AUTH,
        children: [
          {
            path: "",
            element: (
              <Suspense fallback={<Loading />}>
                <SignIn />
              </Suspense>
            ),
          },
          {
            path: "signup",
            element: (
              <Suspense fallback={<Loading />}>
                <SignUp />
              </Suspense>
            ),
          },
        ],
      },
    ],
  },
]);

export default router;
