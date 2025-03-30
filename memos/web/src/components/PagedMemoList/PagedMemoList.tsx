import { observer } from "mobx-react-lite";
import {
  ResizableHandle,
  ResizablePanel,
  ResizablePanelGroup,
} from "../ui/resizable";

interface Props {
  filter?: string;
  oldFilter?: string;
  pageSize?: number;
}

const PagedMemoList = observer((props: Props) => {
  return (
    <ResizablePanelGroup
      direction="horizontal"
      className="w-full h-full  border md:min-w-[450px]"
    >
      <ResizablePanel defaultSize={40}>
        <div className="flex h-full items-center justify-center p-6">
          <span className="font-semibold">Sidebar</span>
        </div>
      </ResizablePanel>
      <ResizablePanel defaultSize={60}>
        <div className="flex h-full items-center justify-center p-6">
          <span className="font-semibold">Content</span>
        </div>
      </ResizablePanel>
    </ResizablePanelGroup>
  );
});

export default PagedMemoList;
