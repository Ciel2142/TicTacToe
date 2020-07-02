package tictactoe;

public class PlayerFactory {

    public static Player makePlayer(String player, TicTacToeNumeric game) {

        switch (player.toLowerCase()) {
            case "user":
                return new HumanPlayer(game);
            case "easy":
                return new EasyAi(game);
            case "medium":
                return new MediumAi(game);
            case "hard":
                return new HighAi(game);
            default:
                return null;
        }
    }
}
