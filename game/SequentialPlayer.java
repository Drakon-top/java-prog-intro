package game;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class SequentialPlayer implements Player {

    private final int n;
    private final int m;

    public SequentialPlayer(int m, int n) {
        this.n = n;
        this.m = m;
    }
    @Override
    public Move move(final Position position, final Cell cell) {
        Board board = (Board) position;
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                final Move move = new Move(r, c, cell);
                if (position.isValid(move)) {
                    if (r == m - 1) {
                        return move;
                    } else {
                        board.makeMove(move);
                    }
                }
            }
        }
        throw new IllegalStateException("No valid moves");
    }
}
