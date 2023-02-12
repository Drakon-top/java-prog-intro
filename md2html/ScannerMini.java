package md2html;

import java.io.*;

public class ScannerMini {
    private final int SIZE_BUFFER = 1024;
    private final String COD_NAME = "UTF8";
    private char[] buffer = new char[SIZE_BUFFER];
    private BufferedReader reader;
    private int read;
    private int indexNow = 0;

    ScannerMini(String text) {
        try {
            reader = new BufferedReader(new StringReader(text), SIZE_BUFFER);
            /*reader = new BufferedReader(new InputStreamReader(
                    text
                    COD_NAME
            ), SIZE_BUFFER);
             */
            read = reader.read(buffer);
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found" + e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void readInBuffer() {
        try {
            if (reader == null) return;
            read = reader.read(buffer);
            indexNow = 0;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public char next() {
        if (indexNow >= read) {
            readInBuffer();
        }
        return buffer[indexNow++];
    }

    public boolean hasNext() {
        if (indexNow >= read) {
            readInBuffer();
        }
        if (read < 0) {
            return false;
        }
        return true;
    }
}
