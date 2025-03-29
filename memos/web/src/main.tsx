import { createRoot } from "react-dom/client";
import "./index.css";
import { RouterProvider } from "react-router/dom";
import router from "@/router";
import { HelmetProvider } from "react-helmet-async";
import { Provider } from "urql";
import { initialUserStoreFromGraphql } from "./store/mobx/user";
import { initialWorkspaceStoreFromGraphql } from "@/store/mobx/workspace.ts";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { graphqlClient } from "./api/apiClient";

const Main = () => {
  return (
    <HelmetProvider>
      <RouterProvider router={router} />
    </HelmetProvider>
  );
};

// Create a client
const queryClient = new QueryClient();

(async () => {
  await initialUserStoreFromGraphql();
  await initialWorkspaceStoreFromGraphql();
  const container = document.getElementById("root");
  const root = createRoot(container as HTMLElement);
  root.render(
    <Provider value={graphqlClient}>
      <QueryClientProvider client={queryClient}>
        <Main />
      </QueryClientProvider>
    </Provider>,
  );
})();
