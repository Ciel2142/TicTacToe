package tictactoe;

import java.util.Random;

public interface Player {
    void move();
}

interface Ai extends Player {

    default void randomMove(int[][] table, int move) {
        Random gen = new Random();
        int row;
        int column;
        while (true) {
            row = gen.nextInt(table.length);
            column = gen.nextInt(table.length);
            if (table[row][column] == 0) {
                table[row][column] = move;
                return;
            }
        }
    }
}

class HumanPlayer implements Player {

    TicTacToeNumeric game;

    HumanPlayer(TicTacToeNumeric game) {
        this.game = game;
    }

    @Override
    public void move() {
        this.makingMove();
        game.swapPlayer();
    }

    private static boolean assertInput(String input) {
        if (!input.matches("[\\d]")) {
            System.out.println("You should enter numbers!");
            return true;
        } else if (!input.matches("[1-3]")) {
            System.out.println("Coordinates should be from 1 to 3!");
            return true;
        }
        return false;
    }

    private void makingMove() {

        while (true) {
            int[][] table = game.getTable();
            System.out.println("Enter the coordinates:");
            String inputI = game.getInput().next();
            if (assertInput(inputI)) {
                continue;
            }
            String inputJ = game.getInput().next();
            if (assertInput(inputJ)) {
                continue;
            }
            int i = Integer.parseInt(inputI) - 1;
            int j = Integer.parseInt(inputJ);
            if (table[3 - j][i] == 0) {
                table[3 - j][i] = game.getMove();
                break;
            } else {
                System.out.println("This cell is occupied! Choose another one!");
            }
        }
    }
}

class EasyAi implements Ai {

    TicTacToeNumeric game;

    public EasyAi(TicTacToeNumeric game) {
        this.game = game;
    }

    @Override
    public void move() {
        System.out.println("Making move level \"easy\"");
        randomMove(game.getTable(), game.getMove());
        game.swapPlayer();
    }
}

class MediumAi implements Ai {
    TicTacToeNumeric game;
    final int[] movesW = new int[2];
    final int[] movesD = new int[2];
    final int[] movesS = new int[2];
    private boolean defence = false;
    private boolean attack = false;


    public MediumAi(TicTacToeNumeric game) {
        this.game = game;
    }

    @Override
    public void move() {
        System.out.println("Making move level \"medium\"");
        if (!findPossibleMove()) {
            randomMove(game.getTable(), game.getMove());
        } else if (!getAttack()) {
            game.table[movesS[0]][movesS[1]] = game.getMove();
        }
        game.swapPlayer();
    }


    private boolean findPossibleMove() {
        this.defence = false;
        int[][] table = game.getTable();
        int countX;
        int countY;
        for (int i = 0; i < table.length; i++) {
            countX = 0;
            countY = 0;
            for (int j = 0; j < table.length; j++) {
                countX += table[i][j];
                countY += table[j][i];
                if (table[i][j] == 0) {
                    this.setMoves(movesW, i, j);
                }
                if (table[j][i] == 0) {
                    this.setMoves(movesD, j, i);
                }
            }
            if (checkResult(countX, countY)) {
                return true;
            }
        }
        if (checkDial()) {
            return true;
        }
        return getDefence();

    }

    private boolean checkResult(int countX, int countY) {
        if (game.getMove() == 1) {
            if (countX == game.getBaseSize() - 1) {
                setAttack();
                game.table[movesW[0]][movesW[1]] = game.getMove();
                return true;
            } else if (countY == game.getBaseSize() - 1) {
                setAttack();
                game.table[movesD[0]][movesD[1]] = game.getMove();
                return true;
            }
            if (!getDefence() && countX == -(game.getBaseSize() -1)) {
                this.setMoves(this.movesS, movesW[0], movesW[1]);
                this.setDefence();
            } else if (!getDefence() && countY == -(game.getBaseSize() - 1)) {
                this.setMoves(this.movesS, movesD[0], movesD[1]);
                this.setDefence();
            }
        }
        if (game.getMove() == -1) {
            if (countX == -(game.getBaseSize() - 1)) {
                setAttack();
                game.table[movesW[0]][movesW[1]] = game.getMove();
                return true;
            } else if (countY == -(game.getBaseSize() - 1)) {
                setAttack();
                game.table[movesD[0]][movesD[1]] = game.getMove();
                return true;
            }
            if (!getDefence() && countX == game.getBaseSize() -1) {
                this.setMoves(this.movesS, movesW[0], movesW[1]);
                this.setDefence();
            } else if (!getDefence() && countY == game.getBaseSize() - 1) {
                this.setMoves(this.movesS, movesD[0], movesD[1]);
                this.setDefence();
            }
        }
        return false;
    }
    public boolean getDefence() {
        return this.defence;
    }

    private void setDefence() {
        this.defence = true;
    }

    public void setAttack() {
        this.attack = true;
    }

    public boolean getAttack() {
        return this.attack;
    }

    private boolean checkDial() {
        int[][] table = game.getTable();
        int countX = 0;
        int countY = 0;
        for (int i = 0, j = 2; i < 3; i++, j--) {
            if (table[i][i] == 0) {
                this.setMoves(this.movesW, i, i);
            } else if (1 == table[i][i]) {
                countX++;
            } else {
                countX--;
            }
            if (table[i][j] == 0) {
                this.setMoves(this.movesD, i, j);
            } else if (1 == table[i][j]) {
                countY++;
            } else {
                countY--;
            }
        }
        return checkResult(countX, countY);
    }

    private void setMoves(int[] ms, int row, int column) {
        ms[0] = row;
        ms[1] = column;
    }

}