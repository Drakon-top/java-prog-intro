package game;

import java.util.Map;

public class MNKPosition implements Position {
    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.',
            Cell.D, 'D'
    );
    private final int numberM;
    private final int numberN;

    private final Cell turn;

    private final Cell[][] cells;

    MNKPosition(final int m, final int n, final Cell turn, final Cell[][] cells) {
        numberN = n;
        numberM = m;
        this.turn = turn;
        this.cells = cells;
    }

    @Override
    public boolean isValid(final Move move) {
        return 0 <= move.getRow() && move.getRow() < numberM
                && 0 <= move.getColumn() && move.getColumn() < numberN
                && cells[move.getRow()][move.getColumn()] == Cell.E
                && turn == move.getValue();
    }

    @Override
    public Cell getCell(final int r, final int c) {
        return cells[r][c];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("   ");
        final StringBuilder rzd = new StringBuilder("  +");
        for (int i = 1; i <= numberN; i++) {
            sb.append(i);
            if (i < 10) {
                sb.append(" ");
            }

            rzd.append("- ");
        }
        sb.append("\n").append(rzd);
        for (int r = 0; r < numberM; r++) {
            sb.append("\n");
            if (r < 9) {
                sb.append(r + 1).append(" |");
            } else {
                sb.append(r + 1).append("|");
            }

            for (int c = 0; c < numberN; c++) {
                sb.append(SYMBOLS.get(cells[r][c])).append(" ");
            }
        }
        return sb.toString();
    }
}
