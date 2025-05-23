// @formatter:off
/**
 * Copyright 2021 Bernard Ladenthin bernard.ladenthin@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
// @formatter:on
package net.ladenthin.bitcoinaddressfinder;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;
import org.jspecify.annotations.NonNull;
import org.lmdbjava.LmdbException;

public abstract class AbstractPlaintextFile implements Interruptable {
    
    @NonNull
    protected final File file;
    @NonNull
    protected final ReadStatistic readStatistic;
    @NonNull
    private final AtomicBoolean shouldRun = new AtomicBoolean(true);
    
    public AbstractPlaintextFile(@NonNull File file, @NonNull ReadStatistic readStatistic) {
        this.file = file;
        this.readStatistic = readStatistic;
    }
    
    protected double calculateFileProgress(@NonNull RandomAccessFile raf) throws IOException {
        return ((double)(Math.max(raf.getFilePointer(),1)) / (double)raf.length()) * 100.0d;
    }
    
    protected abstract void processLine(String line);
    
    public void readFile() throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            while(shouldRun.get()) {
                String line = raf.readLine();
                if (line == null) {
                    return;
                }
                String utf8 = new String(line.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                readStatistic.currentFileProgress = calculateFileProgress(raf);
                try {
                    processLine(utf8);
                } catch(LmdbException e) {
                    // do not catch expections from LMDB (e. g. MapFullException).
                    throw e;
                } catch (Exception e) {
                    System.err.println("Error in line: " + utf8);
                    e.printStackTrace();
                    readStatistic.errors.add(utf8);
                }
            }
        }
    }

    @Override
    public void interrupt() {
        shouldRun.set(false);
    }
    
}
