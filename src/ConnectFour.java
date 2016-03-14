import java.util.Scanner;

public class ConnectFour extends Thread {

    Scanner sc;         // To retrieve player input
    Tile[][] grid;      // [rows][columns]
    int rows, columns;  // Size of grid
    int turn;           // Even turn = COMPUTER, Odd turn = PLAYER

    /**
     * Identification system for each tile in the grid.
     */
    enum Tile {
        NONE,       // Unoccupied tile
        PLAYER,     // Tile occupied by player
        COMPUTER    // Tile occupied by computer
    }

    /**
     * Creates a standard 7x6 game of Connect Four.
     */
    public ConnectFour() {
        this(7, 6);
    }

    /**
     * Creates a grid with specified number of columns and rows for a game of Connect Four.
     */
    public ConnectFour(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
        this.turn = 1;          // Player always goes first (odd number)

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
                printGrid();
                // You win if game ends on the computer's turn, loss otherwise
                if (turn % 2 == 0) {    // Computer's turn
                    System.out.println("Connect Four! You win!!");
                } else {                // Your turn
                    System.out.println("Computer got Connect Four. You lost.");
                }
                break;
            }

            if (turn % 2 == 0) {        // Computers's turn
                resolveComputerMove();
            } else {                    // Your turn
                printGrid();            // Show grid before every move
                System.out.println("[Turn " + (turn/2 + 1) + "] Your move!");
                resolvePlayerMove();
            }
            turn++;
        }
    }

    /**
     * =========================================================
     * This method is called every time it's the computer's turn
     * =========================================================
     * Add your computer's AI logic to this method!
     */
    public void resolveComputerMove() {
        // You should comment this out!
        // ---------------------------------------------------
        // Default behaviour: Choose a random column
        while (true) {
            int randomColumn = (int)(columns * Math.random());
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
    public void resolvePlayerMove() {
        int column = -1;
        boolean retry = true;

        // Await a valid move from standard input
        while (retry) {
            System.out.print("Enter a column (0-" + (columns-1) + "): ");
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
     * Attempts to make a move in the given column and returns whether it was successful or not.
     *
     * E.g. the computer wants to make a move in column 0:
     * makeMove(Tile.COMPUTER, 0);
     */
    public boolean makeMove(Tile tile, int column) {
        // Validate is there is space to make the move
        if (column >= 0 &&
                column < columns &&
                grid[rows - 1][column] == Tile.NONE) {
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
     * Returns true if the specified column and row fits within the grid.
     */
    public boolean isValid(int column, int row) {
        return column >= 0 && column < columns && row >= 0 && row < rows;
    }

    /**
     * Checks whether the victory condition is met (Connect Four) for the given grid state.
     */
    public boolean isGameFinished(Tile[][] grid) {
        // Check every tile and it's cardinal directions for victory
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns ; j++) {
                Tile tile;

                // Skip blanks
                if (grid[i][j] == Tile.NONE)
                    continue;
                else
                    tile = grid[i][j];

                // Up
                if (isValid(j, i+3) &&
                        grid[i+1][j] == tile &&
                        grid[i+2][j] == tile &&
                        grid[i+3][j] == tile)
                    return true;

                // Up-right
                if (isValid(j+3, i+3) &&
                        grid[i+1][j+1] == tile &&
                        grid[i+2][j+2] == tile &&
                        grid[i+3][j+3] == tile)
                    return true;

                // Right
                if (isValid(j+3, i) &&
                        grid[i][j+1] == tile &&
                        grid[i][j+2] == tile &&
                        grid[i][j+3] == tile)
                    return true;

                // Down-right
                if (isValid(j+3, i-3) &&
                        grid[i-1][j+1] == tile &&
                        grid[i-2][j+2] == tile &&
                        grid[i-3][j+3] == tile)
                    return true;

                // Down
                if (isValid(j, i-3) &&
                        grid[i-1][j] == tile &&
                        grid[i-2][j] == tile &&
                        grid[i-3][j] == tile)
                    return true;

                // Down-left
                if (isValid(j-3, i-3) &&
                        grid[i-1][j-1] == tile &&
                        grid[i-2][j-2] == tile &&
                        grid[i-3][j-3] == tile)
                    return true;

                // Left
                if (isValid(j-3, i) &&
                        grid[i][j-1] == tile &&
                        grid[i][j-2] == tile &&
                        grid[i][j-3] == tile)
                    return true;

                // Up-left
                if (isValid(j-3, i+3) &&
                        grid[i+1][j-1] == tile &&
                        grid[i+2][j-2] == tile &&
                        grid[i+3][j-3] == tile)
                    return true;
            }
        }
        return false;
    }

    /**
     * Prints the state of the grid to the console
     */
    public void printGrid() {
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
     * Starts a default Connect Four game in a new thread.
     */
    public static void main(String[] args) {
        new ConnectFour().start();
    }
}
