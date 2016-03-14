/**
 * Inherit from this class when creating your own bot.
 */
public class Bot {

    protected ConnectFour game;

    public Bot(ConnectFour game) {
        this.game = game;
    }

    /**
     * ===============================================
     * This method is called every time it's your turn
     * ===============================================
     * - You must return a valid column
     * - You may use the public getters in ConnectFour:
     *   > isValid(column, row) - checks if within grid bounds
     *   > isValidMove(column) - checks if enough space in column for a move
     *   > isGameFinished(grid) - checks if given grid has a connect-four
     */
    public int chooseColumn(Tile[][] grid, int turn, int opponentsMove) {
        while (true) {
            int randomColumn = (int) (grid[0].length * Math.random());
            if (game.isValidMove(randomColumn)) {
                return randomColumn;
            }
        }
    }

}
