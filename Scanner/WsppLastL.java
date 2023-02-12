import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class WsppLastL {
    public static void main(String[] args) {
        final String UTF = "UTF8";
        Map<String, IntList> map = new LinkedHashMap<String, IntList>(16, 0.75f, false);
        Map<String, IntPair> mapNow = new LinkedHashMap<String, IntPair>();

        try {
            Scanner scan = new Scanner(new FileInputStream(args[0]));
            scan.setToken(new TokenInput());
            int numberLine = 1;
            int count = 1;
            while (scan.hasNext()){
                String word = scan.next(true);
                if (numberLine > scan.getLineCount()){
                    addMiddleValue(word, mapNow, count);
                    count += 1;
                } else {
                    count = 1;
                    numberLine++;
                    mergeMap(map, mapNow);
                    mapNow.clear();
                    addMiddleValue(word, mapNow, count);
                    count += 1;
                }
            }
            mergeMap(map, mapNow);
            scan.close();
        } catch (FileNotFoundException e){
            System.out.println("Not file" + e.getMessage());
        } catch (IndexOutOfBoundsException e){
            System.out.println("Don't get name of input file" + e.getMessage());
        }

        try {

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(args[1]),
                    UTF
            ));
            try {
                for (String lineNow : map.keySet()) {
                    writer.write(lineNow + " ");
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
            System.out.println(e.getMessage());
        }
    }

    public static void mergeMap(Map<String, IntList> map, Map<String, IntPair> middleMap){
        for (String key : middleMap.keySet()) {
            IntPair pair = middleMap.get(key);
            if (map.containsKey(key)){
                IntList list = map.get(key);
                list.addInIndex(0, pair.getFirst());
                list.addNumber(pair.getSecond());
            } else {
                IntList list = new IntList(pair.getFirst());
                list.addNumber(pair.getSecond());
                map.put(key, list);
            }
        }
    }

    private static int addMiddleValue(String key, Map<String, IntPair> map, int number){
        if (key.isEmpty()){
            return 0;
        }
        key = key.toLowerCase();
        if (map.containsKey(key)) {
            IntPair pair = map.get(key);
            pair.addFirst(1);
            pair.replaceSecond(number);
        } else {
            map.put(key, new IntPair(1, number));
        }
        return 1;
    }
}