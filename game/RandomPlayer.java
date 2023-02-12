package game;

import java.util.Random;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class RandomPlayer implements Player {
    private final Random random;
    private final int n;
    private final int m;

    public RandomPlayer(int m, int n, final Random random) {
        this.n = n;
        this.m = m;
        this.random = random;
    }

    public RandomPlayer(int m, int n) {
        this.random = new Random();
        this.n = n;
        this.m = m;
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        while (true) {
            int r = random.nextInt(m) - 1;
            int c = random.nextInt(n) - 1;
            final Move move = new Move(r, c, cell);
            if (position.isValid(move)) {
                return move;
            }
        }
    }
}
