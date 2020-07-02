package tictactoe;

public class HighAi implements Ai {

    TicTacToeNumeric game;

    public HighAi(TicTacToeNumeric game) {
        this.game = game;
    }

    @Override
    public void move() {
        System.out.println("Making move level \"hard\"");
        int ai = game.getMove();
        int enemy = game.getMove() * -1;
        int bestScore = Integer.MIN_VALUE;
        int[] move = new int[2];
        int[][] table = game.getTable();
        int score;
        int moves = game.getMoves();

        for (int row = 0; row < table.length; row++) {
            for (int column = 0; column < table.length; column++) {
                if (table[row][column] == 0) {
                    table[row][column] = game.getMove();
                    score = miniMax(table.clone(), false, ai, enemy, moves-1);
                    table[row][column] = 0;
                    if (score > bestScore) {
                        System.out.println(score);
                        bestScore = score;
                        move[0] = row;
                        move[1] = column;
                    }
                }
            }
        }
        game.table[move[0]][move[1]] = game.getMove();
        game.swapPlayer();
    }

    private boolean winner(int[][] board, int player) {
        return board[0][0] == player && board[0][1] == player && board[0][2] == player ||
                board[1][0] == player && board[1][1] == player && board[1][2] == player ||
                board[2][0] == player && board[2][1] == player && board[2][2] == player ||
                board[0][0] == player && board[1][0] == player && board[2][0] == player ||
                board[0][1] == player && board[1][1] == player && board[2][1] == player ||
                board[0][2] == player && board[1][2] == player && board[2][2] == player ||
                board[0][0] == player && board[1][1] == player && board[2][2] == player ||
                board[0][2] == player && board[1][1] == player && board[2][0] == player;
    }

    private int miniMax(int[][] table, boolean maximizer, int ai, int enemy, int moves) {
        if (winner(table, ai)) {
            return 10;
        } else if (winner(table, enemy)) {
            return -10;
        }
        if (moves == 0) {
            return 0;
        }
        int score;
        if (maximizer) {
            int bestScore = Integer.MIN_VALUE;
            for (int row = 0; row < table.length; row++) {
                for (int column = 0; column < table.length; column++) {
                    if (table[row][column] == 0) {
                        table[row][column] = ai;
                        score = miniMax(table, false, ai, enemy, moves - 1);
                        table[row][column] = 0;
                        bestScore = Math.max(bestScore, score);
                    }
                }
            }
            if (bestScore == Integer.MIN_VALUE) {
                System.out.println(moves);
            }
            return bestScore;
        } else {
            int worstScore = Integer.MAX_VALUE;
            for (int row = 0; row < table.length; row++) {
                for (int column = 0; column < table.length; column++) {
                    if (table[row][column] == 0) {
                        table[row][column] = enemy;
                        score = miniMax(table, true, ai, enemy, moves - 1);
                        table[row][column] = 0;
                        worstScore = Math.min(worstScore, score);
                    }
                }
            }
            if (worstScore == Integer.MAX_VALUE) {
                System.out.println(moves);
            }
            return worstScore;
        }
    }

}
