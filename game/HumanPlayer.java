package game;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;

    public HumanPlayer(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;
    }

    public HumanPlayer() {
        this(System.out, new Scanner(System.in));
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        out.println("Position");
        out.println(position);
        out.println(cell + "'s move");
        out.println("Enter row and column");
        while (true) {
            final String first = in.next();
            final String second = in.next();
            try {
                // :NOTE: scanner + parseInt
                final int row = Integer.parseInt(first) - 1;
                final int column = Integer.parseInt(second) - 1;
                final Move move = new Move(row, column, cell);
                if (!position.isValid(move)) {
                    out.println("Input error");
                    out.println("Enter row and column");
                    continue;
                }
                return new Move(row, column, cell);
            } catch (final NumberFormatException e) {
                out.println("Error");
                return new Move(-1, -1, cell);
            }
        }
    }
}
