import java.util.Arrays;

public class IntList {
    private int[] listInt;
    private int length;
    private int indexLast;

    IntList(int number) {
        listInt = new int[]{number};
        length = 1;
        indexLast = 1;
    }

    public void addNumber(int number) {
        if (indexLast >= length){
            length *= 2;
            listInt = Arrays.copyOf(listInt, length);
        }
        listInt[indexLast] = number;
        indexLast++;
    }

    public int getLength () {
        return indexLast;
    }

    public int[] getList() {
        return Arrays.copyOf(listInt, indexLast);
    }

    public String getStringList() {
        return Arrays.toString(getList());
    }

    public void replace(int index, int number) {
        if (index < length) {
            listInt[index] = number;
        }
    }

    public void addInIndex(int index, int add){
        if (index < length) {
            listInt[index] += add;
        }
    }
}