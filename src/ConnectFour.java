import java.util.Scanner;

public class ConnectFour extends Thread {

    // Standard mode
    private Scanner sc;         // To retrieve player input
    private Tile[][] grid;      // [rows][columns]
    private int rows, columns;  // Size of grid
    private int turn;           // Even turn = COMPUTER, Odd turn = PLAYER

    // AI vs AI mode
    private boolean isBotsOnly; // For AI vs AI competition
    private Bot botA, botB;     // BotA goes first
    private int lastMoveBotA;   // Last column used by this bot
    private int lastMoveBotB;

    /**
     * Creates a standard 7x6 game of Connect Four. If isBotsOnly is true, then match will use Bot interface.
     */
    public ConnectFour(boolean isBotsOnly) {
        this(isBotsOnly, 7, 6);
    }

    /**
     * Creates a grid with specified number of columns and rows for a game of Connect Four and if it is for bots.
     */
    public ConnectFour(boolean isBotsOnly, int columns, int rows) {
        this.isBotsOnly = isBotsOnly;
        if (isBotsOnly) {
            botA = new Bot(this);
            botB = new Bot(this);
        }

        this.columns = columns;
        this.rows = rows;
        this.turn = 1;          // Player or BotA always goes first (odd number)

        grid = new Tile[rows][columns];
        sc = new Scanner(System.in);

        // Initialize grid with NONE (unoccupied tiles)
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid[i][j] = Tile.NONE;
            }
        }
    }

    @Override
    public void run() {
        System.out.println("Let's play Connect Four!");

        while (true) {
            // Check if game has ended before handling moves
            if (isGameFinished(grid)) {
                printGrid();            // Show end game state

                // You win if game ends on the computer's turn, loss otherwise
                if (turn % 2 == 0) {    // Computer's turn / B's turn

                    if (isBotsOnly)
                        System.out.println("Bot A wins!!");
                    else
                        System.out.println("Connect Four! You win!!");

                } else {                // Your turn / A's turn

                    if (isBotsOnly)
                        System.out.println("Bot B wins!!");
                    else
                        System.out.println("Computer got Connect Four. You lost.");

                }
                break;
            }

            if (turn % 2 == 0) {        // Computers's turn / B's turn

                if (isBotsOnly) {
                    int botBMove = botB.chooseColumn(grid, turn, lastMoveBotA);
                    lastMoveBotB = botBMove;
                    makeMove(Tile.COMPUTER, botBMove);
                } else {
                    resolveComputerMove();
                }

            } else {                    // Your turn / A's turn

                if (isBotsOnly) {
                    int botAMove = botA.chooseColumn(grid, turn, lastMoveBotB);
                    lastMoveBotA = botAMove;
                    makeMove(Tile.PLAYER, botAMove);
                } else {
                    printGrid();        // Show grid before every move
                    System.out.println("[Turn " + (turn / 2 + 1) + "] Your move!");
                    resolvePlayerMove();
                }

            }
            turn++;
        }
    }

    /**
     * Attempts to make a move in the given column and returns whether it was successful or not.
     * <p/>
     * E.g. the computer wants to make a move in column 0:
     * makeMove(Tile.COMPUTER, 0);
     */
    private boolean makeMove(Tile tile, int column) {
        // Validate is there is space to make the move
        if (isValidMove(column)) {
            // Find top of column to add new move
            int row = 0;
            while (row < rows) {
                if (grid[row][column] == Tile.NONE) {
                    grid[row][column] = tile;
                    return true;
                }
                row++;
            }
        }
        return false;
    }

    /**
     * =========================================================
     * This method is called every time it's the computer's turn
     * =========================================================
     * Add your computer's AI logic to this method!
     */
    private void resolveComputerMove() {
        // You should comment this out!
        // ---------------------------------------------------
        // Default behaviour: Choose a random column
        while (true) {
            int randomColumn = (int) (columns * Math.random());
            if (makeMove(Tile.COMPUTER, randomColumn)) {
                break;
            }
        }
        // ---------------------------------------------------
    }

    /**
     * =========================================================
     * This method is called every time it's the player's turn
     * =========================================================
     * Awaits for the player to type a valid move and processes it.
     */
    private void resolvePlayerMove() {
        int column = -1;
        boolean retry = true;

        // Await a valid move from standard input
        while (retry) {
            System.out.print("Enter a column (0-" + (columns - 1) + "): ");
            if (sc.hasNextInt()) {
                column = sc.nextInt();
                if (makeMove(Tile.PLAYER, column))
                    retry = false;
                else
                    System.err.println("-- Out of range! (" + column + ") --");
            } else {
                System.err.println("-- Not a valid column (" + column + ") --");
            }
            sc.nextLine(); // Bugfix: Consumes extra input for any invalid text
        }
    }

    /**
     * Prints the state of the grid to the console
     */
    private void printGrid() {
        // Show state of grid
        for (int i = rows - 1; i >= 0; i--) {
            for (int j = 0; j < columns; j++) {
                switch (grid[i][j]) {
                    case NONE:
                        System.out.print("  ");
                        break;
                    case PLAYER:
                        System.out.print("O ");
                        break;
                    case COMPUTER:
                        System.out.print("X ");
                        break;
                }
            }
            System.out.println();
        }

        // Draw a line
        for (int i = 0; i < columns; i++) {
            System.out.print("-.");
        }
        System.out.println();

        // Show indices
        for (int i = 0; i < columns; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    /**
     * Returns true if the specified column and row fits within the grid.
     */
    public boolean isValid(int column, int row) {
        return column >= 0 && column < columns && row >= 0 && row < rows;
    }

    /**
     * Returns true if the column is free for a new tile to be inserted.
     */
    public boolean isValidMove(int column) {
        return column >= 0 && column < columns && grid[rows - 1][column] == Tile.NONE;
    }

    /**
     * Checks whether the victory condition is met (Connect Four) for the given grid state.
     */
    public boolean isGameFinished(Tile[][] grid) {
        // Check every tile and it's cardinal directions for victory
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                Tile tile;

                // Skip blanks
                if (grid[i][j] == Tile.NONE)
                    continue;
                else
                    tile = grid[i][j];

                // Up
                if (isValid(j, i + 3) &&
                        grid[i + 1][j] == tile &&
                        grid[i + 2][j] == tile &&
                        grid[i + 3][j] == tile)
                    return true;

                // Up-right
                if (isValid(j + 3, i + 3) &&
                        grid[i + 1][j + 1] == tile &&
                        grid[i + 2][j + 2] == tile &&
                        grid[i + 3][j + 3] == tile)
                    return true;

                // Right
                if (isValid(j + 3, i) &&
                        grid[i][j + 1] == tile &&
                        grid[i][j + 2] == tile &&
                        grid[i][j + 3] == tile)
                    return true;

                // Down-right
                if (isValid(j + 3, i - 3) &&
                        grid[i - 1][j + 1] == tile &&
                        grid[i - 2][j + 2] == tile &&
                        grid[i - 3][j + 3] == tile)
                    return true;

                // Down
                if (isValid(j, i - 3) &&
                        grid[i - 1][j] == tile &&
                        grid[i - 2][j] == tile &&
                        grid[i - 3][j] == tile)
                    return true;

                // Down-left
                if (isValid(j - 3, i - 3) &&
                        grid[i - 1][j - 1] == tile &&
                        grid[i - 2][j - 2] == tile &&
                        grid[i - 3][j - 3] == tile)
                    return true;

                // Left
                if (isValid(j - 3, i) &&
                        grid[i][j - 1] == tile &&
                        grid[i][j - 2] == tile &&
                        grid[i][j - 3] == tile)
                    return true;

                // Up-left
                if (isValid(j - 3, i + 3) &&
                        grid[i + 1][j - 1] == tile &&
                        grid[i + 2][j - 2] == tile &&
                        grid[i + 3][j - 3] == tile)
                    return true;
            }
        }
        return false;
    }

    /**
     * Starts a default Connect Four game in a new thread.
     */
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("bots")) {
            new ConnectFour(true).start();
        } else {
            new ConnectFour(false).start();
        }
    }
}
