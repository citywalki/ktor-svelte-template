import { createRoot } from "react-dom/client";
import "./index.css";
import { RouterProvider } from "react-router/dom";
import router from "@/router";
import { observer } from "mobx-react-lite";
import { initialWorkspaceStore } from "./store/mobx/workspace";
import { initialUserStore } from "./store/mobx/user";
import { HelmetProvider } from "react-helmet-async";

const Main = observer(() => {
  return (
    <HelmetProvider>
      <RouterProvider router={router} />
    </HelmetProvider>
  );
});

(async () => {
  await initialWorkspaceStore();
  await initialUserStore();

  const container = document.getElementById("root");
  const root = createRoot(container as HTMLElement);
  root.render(<Main />);
})();
