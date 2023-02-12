package game;

import java.util.Arrays;

public class TileMatching implements Board, Position {

    public static final int[] ROW_DELTA = new int[] {1, 0, 1, -1};
    public static final int[] COL_DELTA = new int[] {0, 1, 1, 1};
    private final Cell[][] cells;
    private Cell turn;
    private final int numberK;
    private final int numberN;
    private final int numberM;

    private int countEmpty;

    public TileMatching(int m, int n, int k) {
        this.cells = new Cell[m][n];
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
        countEmpty = m * n;
        numberK = k;
        numberM = m;
        numberN = n;
    }

    public TileMatching(int m, int n, int k, boolean[][] numberDead) {
        this(m, n, k);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (numberDead[i][j]) {
                    countEmpty--;
                    cells[i][j] = Cell.D;
                }
            }
        }
    }

    @Override
    public Position getPosition() {
        return new MNKPosition(numberM, numberN, turn, cells);
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    @Override
    public Result makeMove(final Move move) {
        if (!isValid(move)) {
            return Result.LOSE;
        }
        countEmpty--;

        int row = move.getRow();
        int col = move.getColumn();
        cells[row][col] = move.getValue();

        int countMax = 0;
        for (int j = 0; j < ROW_DELTA.length; j++) {
            int countNow = 0;
            for (int i = -numberK; i < numberK; i++) {
                int rowIndex = row + ROW_DELTA[j] * i;
                int colIndex = col + COL_DELTA[j] * i;
                if (0 <= rowIndex && rowIndex < numberM && 0 <= colIndex && colIndex < numberN) {
                    if (cells[rowIndex][colIndex] == move.getValue()) {
                        countNow++;
                    } else {
                        countMax = Math.max(countNow, countMax);
                        countNow = 0;
                    }
                }
            }
            countMax = Math.max(countNow, countMax);
        }
        if (countMax >= numberK) {
            return Result.WIN;
        }
        if (countEmpty == 0) {
            return Result.DRAW;
        }

        turn = turn == Cell.X ? Cell.O : Cell.X;
        return Result.UNKNOWN;
    }

    @Override
    public boolean isValid(final Move move) {
        return 0 <= move.getRow() && move.getRow() < numberM
                && 0 <= move.getColumn() && move.getColumn() < numberN
                && cells[move.getRow()][move.getColumn()] == Cell.E
                && turn == getCell();
    }

    @Override
    public Cell getCell(final int r, final int c) {
        return cells[r][c];
    }
}
