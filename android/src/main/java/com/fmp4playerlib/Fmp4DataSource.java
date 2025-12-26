// Custom DataSource implementation

package com.fmp4playerlib;

import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.DataSource.Factory;
import androidx.media3.datasource.DataSpec;
import androidx.media3.datasource.TransferListener;


import android.net.Uri;
import java.io.IOException;
import java.io.PipedInputStream;

public class Fmp4DataSource implements DataSource {
        private PipedInputStream pipedInputStream;

        private long bytesRemaining = Long.MAX_VALUE;

        public Fmp4DataSource(PipedInputStream pipedInputStream) {
            this.pipedInputStream = pipedInputStream;
           
        }

        @Override
        public long open(DataSpec dataSpec) throws IOException {
            return bytesRemaining;
        }

        @Override
        public int read(byte[] buffer, int offset, int length) throws IOException {
            if (bytesRemaining == 0) {
                return -1;
            }
            int bytesRead = pipedInputStream.read(buffer, offset, length);
            if (bytesRead > 0) {
                bytesRemaining -= bytesRead;
            }
           
            return bytesRead;
        }

        @Override
        public Uri getUri() {
            return Uri.parse("fmp4://stream");
        }

        @Override
        public void close() throws IOException {}

        @Override
        public void addTransferListener(TransferListener transferListener) {}
    }