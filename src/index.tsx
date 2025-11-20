import Fmp4PlayerLib from './NativeFmp4PlayerLib';
export function multiply(a: number, b: number): number {
  return Fmp4PlayerLib.multiply(a, b);
}

export function startStreaming(): void {
  Fmp4PlayerLib.startStreaming();
}

export function stopStreaming(): void {
  Fmp4PlayerLib.stopStreaming();
}

export { default as StreamView } from './StreamViewNativeComponent';
export * from './StreamViewNativeComponent';