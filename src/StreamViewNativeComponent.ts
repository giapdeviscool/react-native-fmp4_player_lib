import type {
    ViewProps
} from "react-native";

import { codegenNativeComponent } from "react-native";

export interface NativeProps extends ViewProps {
    streamId: string;
}

export default codegenNativeComponent<NativeProps>(
    "StreamView"
) 