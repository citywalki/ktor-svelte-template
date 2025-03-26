import { createRoot } from "react-dom/client";
import "./index.css";
import { RouterProvider } from "react-router/dom";
import router from "@/router";
import { HelmetProvider } from "react-helmet-async";
import { Client, Provider, cacheExchange, fetchExchange } from "urql";
import { initialUserStore } from "./store/mobx/user";
import { initialWorkspaceStore } from "@/store/mobx/workspace.ts";

const Main = () => {
  return (
    <HelmetProvider>
      <RouterProvider router={router} />
    </HelmetProvider>
  );
};

const client = new Client({
  url: "/graphql",
  exchanges: [cacheExchange, fetchExchange],
});

(async () => {
  await initialUserStore();
  await initialWorkspaceStore();

  const container = document.getElementById("root");
  const root = createRoot(container as HTMLElement);
  root.render(
    <Provider value={client}>
      <Main />
    </Provider>,
  );
})();
