import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class WordStatInput{
    public static void main(String[] args) throws FileNotFoundException {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>(16, 0.75f, false);

        Scanner scan = new Scanner(new File(args[0]));
        Token tk = new TokenInput();
        scan.setToken(tk);
        while (scan.hasNext()){
            String now = scan.next(true);
            addValue(now, map);
        }
        scan.close();

        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(args[1]),
                    "UTF8"
            ));
            try {
                Iterator<String> itr = map.keySet().iterator();
                while(itr.hasNext()){
                    String lineNow = itr.next();
                    //System.err.println(lineNow);
                    writer.write(lineNow + " ");
                    writer.write(String.valueOf(map.get(lineNow)));
                    writer.newLine();
                }
            } finally {
                writer.close();
            }
        } catch (IndexOutOfBoundsException e){
            System.out.println("Don't get name of output file" + e.getMessage());
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static void addValue(String key, Map<String, Integer> linMap){
        if (key.isEmpty()){
            return;
        }

        key = key.toLowerCase();
        if (linMap.containsKey(key)){
            Integer count = linMap.get(key) + 1;
            linMap.put(key, count);
        } else {
            linMap.put(key, 1);
        }
    }
}