# ConnectFour
Add your own AI logic to this simple text-based <a href="https://en.wikipedia.org/wiki/Connect_Four" target="_blank">Connect Four</a> game!

## How the game runs
1. The game creates a 2D array and initializes each cell with a `Tile` of type `NONE` to represent the empty game state. 
2. Each player (human vs. computer) takes turns selecting a column to make their move. After deciding on a column, you can use the `makeMove(Tile tile, int column)` method to insert each tile (`PLAYER` or `COMPUTER`).
3. The game will repeat step two until a player wins. Note: Victory condition is automatically checked after each move (`resolveComputerMove()` and `resolvePlayerMove()`). First player to connect four of their own tiles wins!

## How to add your own AI
1. [Fork](https://github.com/Latiosu/ConnectFour/fork) this repository or [download](https://github.com/Latiosu/ConnectFour/archive/master.zip) the source.
2. Add your AI logic the `resolveComputerMove()` method.
3. Compile, run and have fun!

```java
public void resolveComputerMove() {
  // You should comment this out!
  // ---------------------------------------------------
  // Default behaviour: Choose a random column
  while (true) {
      int randomColumn = (int)(columns * Math.random());
      if (makeMove(Player.COMPUTER, randomColumn)) {
          break;
      }
  }
  // ---------------------------------------------------
}
```

## Gameplay
![Screenshot of gameplay](http://puu.sh/nG19i/01c3807ef8.png)
