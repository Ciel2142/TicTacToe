package tictactoe;

import java.util.Scanner;

public class TicTacToeNumeric {
    protected int baseSize;
    protected int[][] table;
    private int playerMove = 1;
    protected Scanner sc = new Scanner(System.in);
    Player p1;
    Player p2;
    int moves = 9;

    public TicTacToeNumeric() {
        this(3);
    }

    public TicTacToeNumeric(int baseSize) {
        this.baseSize = baseSize;
        this.table = createTable(baseSize);
    }
    private static int[][] createTable(int size) {
        return new int[size][size];
    }

    public int[][] getTable() {
        return table;
    }

    public int getMove() {
        return this.playerMove;
    }

    public int getBaseSize() {
        return this.baseSize;
    }

    public Scanner getInput() {
        return this.sc;
    }

    public int getMoves() {
        return moves;
    }

    public void decrementMoves() {
        this.moves--;
    }

    public String toString() {
        StringBuilder strTable = new StringBuilder();
        int[][] representation = this.getTable();
        strTable.append("---------\n");
        for (int[] row : representation) {
            strTable.append("| ");
            for (int num : row) {
                if (num == 0) {
                    strTable.append("  ");
                } else if (num == 1) {
                    strTable.append("X ");
                } else {
                    strTable.append("O ");
                }
            }
            strTable.append("|\n");
        }
        strTable.append("---------");
        return strTable.toString();
    }

    private void drawBoard() {
        System.out.println(this.toString());
    }

    private int checkGameDial(int[][] table) {
        int x = 0;
        int y = 0;
        for (int i = 0, j = 2; i < this.baseSize; i++, j--) {
            x += table[i][i];
            y += table[i][j];
        }
        if (x == this.baseSize || y == this.baseSize) {
            return 1;
        } else if (x == -this.baseSize || y == -this.baseSize) {
            return -1;
        }
        return -2;
    }

    public int checkGameState(int[][] table) {
        int x;
        int y;
        int moves = this.baseSize * this.baseSize;
        for (int i = 0; i < this.baseSize; i++) {
            y = 0;
            x = 0;
            for (int j = 0; j < this.baseSize; j++) {
                x += table[i][j];
                y += table[j][i];
                if (table[i][j] != 0) {
                    moves--;
                }
            }
            if (x == this.baseSize || y == this.baseSize) {
                return 1;
            } else if (x == -this.baseSize || y == -this.baseSize) {
                return -1;
            }
        }
        int dial = this.checkGameDial(table);
        if (dial == 1 || dial == -1) {
            return dial;
        }
        if (moves == 0) {
            return 0;
        } else {
            return -2;
        }
    }

    protected void swapPlayer() {
        this.playerMove *= -1;
    }

    private boolean initializePlayers() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Input command:");
            String initialization = sc.next();
            if (initialization.equals("exit")) {
                return true;
            } else if (initialization.equals("start")) {
                this.p1 = PlayerFactory.makePlayer(sc.next(), this);
                if (this.p1 == null) {
                    System.out.println("Bad parameters!");
                    continue;
                }
                this.p2 = PlayerFactory.makePlayer(sc.next(), this);
                if (this.p2 == null) {
                    System.out.println("Bad parameters!");
                    continue;
                }
                return false;

            } else {
                System.out.println("Bad parameters!");
            }
        }
    }

    private boolean readResults(int n) {
        if (n == 1) {
            drawBoard();
            System.out.println("X wins");
            return true;
        } else if (n == -1) {
            drawBoard();
            System.out.println("O wins");
            return true;
        } else if (n == 0) {
            drawBoard();
            System.out.println("Draw");
            return true;
        } else {
            return false;
        }
    }

    public void initializeGame() {
        if (initializePlayers()) {
            return;
        }
        while (true) {
            drawBoard();
            while (true) {
                p1.move();
                decrementMoves();
                if (readResults(checkGameState(this.getTable()))) {
                    break;
                }
                drawBoard();
                p2.move();
                decrementMoves();
                if (readResults(checkGameState(this.getTable()))) {
                    break;
                }
                drawBoard();
            }
            if (initializePlayers()) {
                return;
            } else {
                this.table = createTable(this.baseSize);
            }
        }
    }
}
