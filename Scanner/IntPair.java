public class IntPair {
    private int first;
    private int second;

    IntPair (int first, int second) {
        this.first = first;
        this.second = second;
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }

    public void addFirst(int number) {
        first += number;
    }

    public void addSecond(int number) {
        second += number;
    }

    public void replaceFirst(int number) {
        first = number;
    }

    public void replaceSecond(int number) {
        second = number;
    }

}
