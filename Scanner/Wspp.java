import java.io.*;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Wspp {
    public static void main(String[] args) {
        final String UTF = "UTF8";
        Map<String, IntList> map = new LinkedHashMap<String, IntList>(16, 0.75f, false);

        try {
            Scanner scan = new Scanner(new FileInputStream(args[0]));
            scan.setToken(new TokenInput());
            int count = 1;
            while (scan.hasNext()){
                String word = scan.next(true);
                count += addValue(word, map, count);
            }
            scan.close();
        } catch (FileNotFoundException e){
            System.out.println("Not file" + e.getMessage());
        } catch (IndexOutOfBoundsException e){
            System.out.println("Don't get name of output file" + e.getMessage());
        }

        try {
            //System.err.println("-----");
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(args[1]),
                    UTF
            ));
            //System.err.println("-----");
            try {
                Iterator<String> itr = map.keySet().iterator();
                while(itr.hasNext()){
                    String lineNow = itr.next();
                    writer.write(lineNow + " ");
                    writer.write(map.get(lineNow).getLength() + " ");
                    int[] listPrint = map.get(lineNow).getList();
                    for (int i = 0; i < listPrint.length - 1; i++){
                        writer.write(String.valueOf(listPrint[i]) + " ");
                    }
                    writer.write(String.valueOf(listPrint[listPrint.length - 1]));

                    writer.newLine();
                }
            } finally {
                writer.close();
            }
        } catch (IndexOutOfBoundsException e){
            System.out.println("Don't get name of output file" + e.getMessage());
        } catch (IOException e){
            System.err.println("-----");
            System.out.println(e.getMessage());
        }
    }

    private static int addValue(String key, Map<String, IntList> map, int count) {
        if (key.isEmpty()){
            return 0;
        }

        key = key.toLowerCase();
        if (map.containsKey(key)){
            map.get(key).addNumber(count);
        } else {
            map.put(key, new IntList(count));
        }
        return 1;
    }
}