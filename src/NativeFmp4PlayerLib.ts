import { TurboModuleRegistry, type TurboModule } from 'react-native';

export interface Spec extends TurboModule {
  startStreaming(): void;
  stopStreaming(): void;
}

export default TurboModuleRegistry.getEnforcing<Spec>('NativeFmp4PlayerLib');
