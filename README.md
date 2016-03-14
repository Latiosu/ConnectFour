# ConnectFour
Create your own AI bot to battle others in this simple text-based <a href="https://en.wikipedia.org/wiki/Connect_Four" target="_blank">Connect Four</a> game!

## How the game runs
1. The game creates a 2D array and initializes each cell with a `Tile` of type `NONE` to represent the empty game state. 
2. Each AI takes turns selecting a column to make their move. After deciding on a column, the `makeMove(Tile tile, int column)` method is called to insert each tile (`PLAYER` or `COMPUTER`).
3. The game will repeat step two until either AI wins. Note: Victory condition is automatically checked after each move.

## How to create your own AI bot
1. [Fork](https://github.com/Latiosu/ConnectFour/fork) this repository or [download](https://github.com/Latiosu/ConnectFour/archive/bots.zip) the source.
2. Create a new Java class and extend the `Bot` class.
3. Override the `chooseColumn()` method and add in your AI logic.
4. Replace the default Bot initialization in the `ConnectFour` class with your new bot:

```java
public ConnectFour(boolean isBotsOnly, int columns, int rows) {

    this.isBotsOnly = isBotsOnly;
    if (isBotsOnly) {
        botA = new Bot(this);   // <---- Replace this when playing AI vs AI
        botB = new Bot(this);   // <---- Replace this when playing AI vs AI
    }
    ...
}
```

## Gameplay
![Screenshot of gameplay](http://puu.sh/nG6op/873f09117b.png)
