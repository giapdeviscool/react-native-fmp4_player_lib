import { TurboModuleRegistry, type TurboModule } from 'react-native';

export interface Spec extends TurboModule {
  multiply(a: number, b: number): number;
  startStreaming(): void;
  stopStreaming(): void;
}

export default TurboModuleRegistry.getEnforcing<Spec>('NativeFmp4PlayerLib');
