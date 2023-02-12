import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.Map;
import java.util.Set;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

public class WordStatWordsPrefix{
	public static void main(String[] args){
		Map<String, Integer> map = new HashMap<String, Integer>();
		Set<String> set = new TreeSet<String>();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(args[0]),
				"UTF8"
			), 1024);
			
			try {
				char[] buffer = new char[1024];
				int read = reader.read(buffer);
				String line = "";
				while (read >= 0){
					for (int i = 0; i < read; i++){
						char now = buffer[i];
						if (Character.getType(now) == Character.DASH_PUNCTUATION ||  now == '\'' || Character.isLetter(now)){
							if (line.length() < 3){
								line += now;
							}
						} else {
							addValue(line, map, set);
							line = "";
						}
					}
					read = reader.read(buffer);
				}
				addValue(line, map, set);
			} finally {
				reader.close();
			}
		} catch (FileNotFoundException e){
			System.out.println("Input file not found" + e.getMessage());
		} catch (IndexOutOfBoundsException e){
			System.out.println("Don't get name of input file" + e.getMessage());
		} catch (IOException e){
			System.out.println(e.getMessage());
		}
		
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(args[1]),
				"UTF8"
			));
			try {
				for (String lineNow : set) {
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
	
	
	public static void addValue(String key, Map<String, Integer> hMap, Set<String> tree){
		if (key.isEmpty()){
			return;
		}
		key = key.toLowerCase();
		hMap.merge(key, 1, Integer::sum);
		tree.add(key);
	}
}