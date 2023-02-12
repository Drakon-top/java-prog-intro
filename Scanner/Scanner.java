import java.io.*;

public class Scanner {
    private final int SIZE_BUFFER = 1024;
    private final String COD_NAME = "UTF8";
    private Token token = new TokenDefault();
    private final int countLineSep = System.lineSeparator().length();
    private BufferedReader reader;
    private StringBuilder builder = new StringBuilder();
    private int read = -1;
    private int indexNow = 0;
    private int lineCount = 0;
    private int countSepWas = 0;
    private char[] buffer = new char[SIZE_BUFFER];




    public Scanner(InputStream in){
        try {
            reader = new BufferedReader(new InputStreamReader(
                    in, COD_NAME
            ), SIZE_BUFFER);
            read = reader.read(buffer);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public Scanner(String text){
        try {
            reader = new BufferedReader(new StringReader(text), SIZE_BUFFER);
            read = reader.read(buffer);
        } catch (FileNotFoundException e){
            System.out.println("Input file not found" + e.getMessage());
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public Scanner(File file){
        try {
            reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file),
                    COD_NAME
            ), SIZE_BUFFER);
            read = reader.read(buffer);
        } catch (FileNotFoundException e){
            System.out.println("Input file not found" + e.getMessage());
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private void readInBuffer(){
        try {
            read = reader.read(buffer);
            indexNow = 0;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getLineCount(){
        return lineCount;
    }

    public boolean hasNext(){
        if (!builder.isEmpty()){
            return true;
        }

        if (next(false).isEmpty() && read < 0) {
            return false;
        }
        return true;
    }

    /// if refund == true clear builder, else not clear
    public String next(boolean refund) {
        if (!builder.isEmpty()){
            return returnString(refund);
        }
        while (read >= 0) {
            for (int i = indexNow; i < read; i++) {
                char now = buffer[i];
                if (now == '\n' || now == '\r') {
                    if (!builder.isEmpty()){
                        indexNow = i;
                        return returnString(refund);
                    }
                    if (countSepWas == 0){
                        lineCount++;
                    }
                    countSepWas = 0;
                    while (i < read && (buffer[i] == '\n' || buffer[i] == '\r') && countSepWas < countLineSep) {
                        i++;
                        countSepWas++;
                    }
                    if (i < read || countSepWas == countLineSep){
                        countSepWas = 0;
                    }
                    indexNow = i;
                    return returnString(refund);
                } else {
                    countSepWas = 0;
                }
                if (!token.symbolType(now)) {
                    if (i - indexNow > 0 || !builder.isEmpty()){
                        indexNow = i;
                        return returnString(refund);
                    } else {
                        indexNow++;
                    }
                } else {
                    builder.append(now);
                }
            }
            readInBuffer();
        }
        return returnString(refund);
    }

    public boolean hasNextInt(){
        try {
            String number;
            if (!builder.isEmpty()) {
                number = returnString(false);
            } else {
                number = next(false);
            }
            if (number.isEmpty()) {
                int pars = (int) Long.parseLong(number);
            }
            number = number.toLowerCase();
            if (number.charAt(number.length() - 1) == 'o') {
                int pars = (int) Long.parseLong(number.substring(0, number.length() - 1), 8);
            } else {
                int pars = (int) Long.parseLong(number);
            }
            //int pars = (int) Long.parseLong(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public int nextInt(){
        int pars;
        String number;
        if (!builder.isEmpty()) {
            number = returnString(true);
        } else {
            number = next(true);
        }
        number = number.toLowerCase();
        if (number.charAt(number.length() - 1) == 'o') {
            pars = (int) Long.parseLong(number.substring(0, number.length() - 1), 8);
        } else {
            pars = (int) Long.parseLong(number);
        }
        return pars;
    }

    public void setToken(Token token){
        this.token = token;
    }

    /// if refund == true clear builder, else not clear
    private String returnString(boolean refund){
        String ret = builder.toString();
        if (refund) {
            builder.setLength(0);
        }
        return ret;
    }

    public void close(){
        try {
            reader.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
