package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Md2Html {
    public static void main(String[] args) {

        AbstractText page = new Page(new ArrayList<>());
        Stack<Mark> stack = new Stack<>();

        try {
            StringBuilder nowText = new StringBuilder();
            FileInputStream file = new FileInputStream(args[0]);
            Scanner scannerMain = new Scanner(file, StandardCharsets.UTF_8);
            StringBuilder blockMark = new StringBuilder();
            boolean readEnd = false;
            String line = "";
            while (scannerMain.hasNextLine() || !readEnd) {
                boolean nextHave = scannerMain.hasNextLine();
                if (nextHave) {
                    line = scannerMain.nextLine();
                }
                if (!nextHave || line.isEmpty()) {
                    if (!nextHave) {
                        readEnd = true;
                    }
                    if (blockMark.isEmpty()) {
                        continue;
                    }
                    ScannerMini sc = new ScannerMini(blockMark.toString());
                    boolean readNext = true;
                    char future;
                    char now = addStartElement(sc, stack, nowText);

                    if (now != '#') {
                        readNext = false;
                    }

                    while (sc.hasNext() || !readNext) {
                        if (readNext) {
                            now = sc.next();
                        }
                        readNext = true;

                        if (now == '\\') {
                            now = sc.next();
                            nowText.append(now);
                        } else if (now == '*' || now == '_') {
                            future = sc.next();
                            now = addMark(now, future, stack, nowText);
                            if (now != '*') {
                                readNext = false;
                            }
                        } else if (now == '-') {
                            future = sc.next();
                            now = addMark(now, future, stack, nowText);
                            if (now != '*') {
                                readNext = false;
                            }
                        } else if (now == '`') {
                            addMark(now, now, stack, nowText);
                        } else if (now == '&') {
                            nowText.append("&amp;");
                        } else if (now == '<') {
                            nowText.append("&lt;");
                        } else if (now == '>') {
                            nowText.append("&gt;");
                        } else if (now == '!') {
                            future = sc.next();
                            if (future == '[') {
                                ((AbstractText) stack.peek()).addElement(new Text(nowText));
                                nowText.setLength(0);

                                addImage(sc, stack);
                            } else {
                                nowText.append(now);
                                now = future;
                                readNext = false;
                            }
                        } else {
                            nowText.append(now);
                        }
                    }


                    AbstractText nowEl = (AbstractText) stack.pop();
                    if (!nowText.isEmpty()) {
                        nowText.deleteCharAt(nowText.length() - 1);
                        nowEl.addElement(new Text(nowText.toString()));
                        nowText.setLength(0);
                    }
                    page.addElement(nowEl);
                    blockMark.setLength(0);
                } else {
                    blockMark.append(line).append("\n");
                }
            }
            scannerMain.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IndexOutOfBoundsException e){
            System.out.println("Don't get name of input file" + e.getMessage());
        }

        try {

            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(args[1]),
                    StandardCharsets.UTF_8
            ))) {
                StringBuilder sb = new StringBuilder();
                page.toHtml(sb);
                writer.write(sb.toString());
            }
        } catch (IndexOutOfBoundsException e){
            System.out.println("Don't get name of output file" + e.getMessage());
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static void addImage(ScannerMini scannerMini, Stack<Mark> stack) {
        StringBuilder image = new StringBuilder();
        char now = scannerMini.next();
        int index = -1;
        int i = 0;
        boolean wasStartSrc = false;
        while (true) {
            if (now == ']') {
                index = i;
            } else if (now == ')') {
                if (index >= 0 && wasStartSrc) {
                    break;
                }
            } else if (now == '(') {
                wasStartSrc = true;
            }

            image.append(now);
            i++;
            now = scannerMini.next();
        }
        String im = image.toString();
        String alt = im.substring(0, index);
        String src = im.substring(index + 2, image.length());
        Image img = new Image(alt, src);
        ((AbstractText) stack.peek()).addElement(img);
    }

    /// return last symbol for processing
    public static char addStartElement(ScannerMini scannerMini, Stack<Mark> stack, StringBuilder nowText) {
        char now = scannerMini.next();
        if (now == '#') {
            int count = 1;
            nowText.append(now);
            now = scannerMini.next();
            while (now == '#') {
                nowText.append(now);
                count++;
                now = scannerMini.next();
            }
            if (Character.isWhitespace(now)) {
                stack.add(new Heading(new ArrayList<>(), count));
                nowText.setLength(0);
                return '#';
            } else {
                stack.add(new Paragraph(new ArrayList<>()));
                return now;
            }
        }
        stack.add(new Paragraph(new ArrayList<>()));
        return now;
    }

    /// if last symbol use return * how special symbol
    public static char addMark(char now, char future, Stack<Mark> stack, StringBuilder nowText) {
        if (now == future) {
            AbstractText nextEl = null;
            Mark stackPeek = stack.peek();
            if ((now == '*' || now == '_') && stackPeek instanceof Strong) {
                nextEl = (Strong) stack.pop();
            } else if (now == '-' && stackPeek instanceof Strikeout) {
                nextEl = (Strikeout) stack.pop();
            } else if (now == '`' && stackPeek instanceof CodeTag) {
                nextEl = (CodeTag) stack.pop();
            } else if (now == '*' || now == '_') {
                ((AbstractText) stackPeek).addElement(new Text(nowText));
                stack.add(new Strong(new ArrayList<>()));
            } else if (now == '-') {
                ((AbstractText) stackPeek).addElement(new Text(nowText));
                stack.add(new Strikeout(new ArrayList<>()));
            } else if (now == '`') {
                ((AbstractText) stackPeek).addElement(new Text(nowText));
                stack.add(new CodeTag(new ArrayList<>()));
            }
            if (nextEl != null) {
                nextEl.addElement(new Text(nowText));
                ((AbstractText) stack.peek()).addElement(nextEl);
            }
            nowText.setLength(0);
        } else if (Character.isWhitespace(future) && !(stack.peek() instanceof Emphasis)) {
            nowText.append(now).append(future);
        } else {
            if (now == '*' || now == '_') {
                if (stack.peek() instanceof Emphasis) {
                    Emphasis nextEl = (Emphasis) stack.pop();
                    nextEl.addElement(new Text(nowText));
                    ((AbstractText) stack.peek()).addElement(nextEl);
                } else {
                    ((AbstractText) stack.peek()).addElement(new Text(nowText));
                    stack.add(new Emphasis(new ArrayList<>()));
                }
                nowText.setLength(0);
            } else if (now == '-') {
                nowText.append(now);
            }
            return future;
        }
        return '*';
    }
}
