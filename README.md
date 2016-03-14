# ConnectFour
Add your own AI logic to this simple text-based <a href="https://en.wikipedia.org/wiki/Connect_Four" target="_blank">Connect Four</a> game!

The game uses a 2D array to store the current state. Each player (human vs. computer) takes turns selecting a column to make their next move. The first player to connect four of their own tiles wins!

## How to add your own AI
1. Fork this repository or download the source.
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
