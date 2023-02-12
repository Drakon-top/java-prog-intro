package game;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class Main {
    public static void main(final String[] args) {

        final int m = Integer.parseInt(args[0]);
        final int n = Integer.parseInt(args[1]);
        final int k = Integer.parseInt(args[2]);

        final int playerCount = Integer.parseInt(args[3]);
        final List<Player> player = new ArrayList<>();
        for (int i = 0; i < playerCount; i++) {
            player.add(new HumanPlayer());
        }

        final boolean[][] numberDead = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j || m - i - 1 == j) {
                    numberDead[i][j] = true;
                }
            }
        }

        final int[][] tableMatch = tournament(player, m, k, n, numberDead);
        printTable(tableMatch);
    }

    public static void printTable(final int[][] table) {
        System.out.print(" |");
        for (int i = 0; i < table.length; i++) {
            System.out.print(i + 1);
        }
        System.out.println();
        for (int i = 0; i < table.length; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < table.length; j++) {
                if (i == j) {
                    System.out.print("X");
                } else {
                    System.out.print(table[i][j]);
                }
            }
            System.out.println();
        }
    }

    public static int startGame(final Player player1, final Player player2, final Board board, final int i, final int j) {
        System.out.println("Play player1 - " + (i + 1) + " player2 - " + (j + 1));
        return playGame(player1, player2, board);
    }

    private static int playGame(final Player player1, final Player player2, final Board board) {
        final Game game = new Game(false, player1, player2);
        final int rez = game.play(board);
        System.out.println(board.getPosition());
        System.out.println("Game result: " + rez);
        System.out.println();
        return rez;
    }

    public static int[][] tournament(final List<Player> player, final int m, final int k, final int n, final boolean[][] numberDead) {
        int[][] tableMatch = new int[player.size()][player.size()];
        System.out.println("Start Game");
        for (int i = 0; i < player.size(); i++) {
            for (int j = 0; j < player.size(); j++) {
                System.out.println("Play player1 - " + (i + 1) + " player2 - " + (j + 1));
                final int rez = playGame(player.get(i), player.get(j), new TileMatching(m, n, k, numberDead));
                if (rez == 1) {
                    tableMatch[i][j] += 3;
                } else if (rez == 2) {
                    tableMatch[j][i] += 3;
                } else if (rez == 0) {
                    tableMatch[i][j] += 1;
                    tableMatch[i][j] += 1;
                }
            }
        }
        return tableMatch;
    }
}
