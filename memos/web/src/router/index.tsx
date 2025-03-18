import { createBrowserRouter } from "react-router";
import App from "@/App.tsx";
import { Suspense } from "react";
import Loading from "@/pages/Loading.tsx";
import SignIn from "@/pages/SignIn.tsx";

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
        ],
      },
    ],
  },
]);

export default router;
