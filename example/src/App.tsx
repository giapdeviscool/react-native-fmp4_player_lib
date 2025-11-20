/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 */


import { StyleSheet, useColorScheme, View } from 'react-native';

import Content from './Content';

function App() {
  const isDarkMode = useColorScheme() === 'dark';
  return (
      <AppContent />
  );
}

function AppContent() {

  return (
    <View style={styles.container}>
      <Content />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
});

export default App;
