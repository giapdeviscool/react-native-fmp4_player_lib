import React, { useState } from "react";
import { Button, StyleSheet, TextInput } from "react-native";
import { startStreaming, stopStreaming, StreamView } from "react-native-fmp4_player_lib"

export default function Content() {
    const [text, setText] = useState('');
    return (
        <>
            <StreamView streamId={text != '' ? text : ""}  style={{width:'100%', height:'30%'}}/>
            <Button title="Start Streaming" onPress={() => startStreaming()} />
            <Button title="Stop Streaming" onPress={() => stopStreaming()} />
            <TextInput
                  style={styles.input}
                  placeholder="Enter StreamId here"
                  onChangeText={newText => setText(newText)}
                  value={text}
                />
        </>
    )
}

const styles = StyleSheet.create({
  input: {
    height: 40,
    borderColor: 'gray',
    borderWidth: 1,
    width: '80%',
    alignSelf:'center',
    paddingLeft:15,
    paddingRight:15
  },
});