package com.example.game.helpers;


import com.example.game.interfaces.GameState;

/**
 * Created by juan.villa on 16/06/17.
 */

public class JVGameState implements GameState {

  private final static int DEFAULT_SIZE = 3;

  protected final Board board;

  private final int size;

  public JVGameState() {
    this(DEFAULT_SIZE);
  }

  public JVGameState(int size) {
    this.size = size;
    board = new Board(size);
  }

  public JVGameState(Board board) {
    this.size = board.getSize();
    this.board = board;
  }

  public int getSize() {
    return this.size;
  }

  protected void place(Cell cell, int r, int c) {
    board.place(cell, r, c);
  }

  @Override
  public GameState copy() {
    return new JVGameState(board);
  }

  @Override
  public boolean isDraw() {

    if (isWin()) return false;

    BoardTest testForDraw = new BoardTest() {

      @Override
      public boolean test(Board.Tuple tuple) {

        final boolean containsX = tuple.contains(Cell.X);
        final boolean containsY = tuple.contains(Cell.O);
        return containsX && containsY;
      }
    };

    boolean isDraw = true;
    for (Board.Tuple tuple : board.toTuples()) {
      if (!testForDraw.test(tuple)) {
        isDraw = false;
        break;
      }
    }
    return isDraw;
  }

  @Override
  public boolean isWin() {

    return testEveryTuple(new BoardTest() {

      @Override
      public boolean test(Board.Tuple tuple) {
        Cell start = tuple.getValue(0);
        if (start == null) return false;

        for (Cell t : tuple) {
          if (t != start) return false;
        }
        return true;
      }
    });
  }


  private boolean testEveryTuple(BoardTest boardTest) {
    for (Board.Tuple tuple : board.toTuples()) {
      if (boardTest.test(tuple)) return true;
    }
    return false;
  }

  public boolean isWinner(final JVPlayer player) {
    if (!isWin()) return false;


    return testEveryTuple(new BoardTest() {

      @Override
      public boolean test(Board.Tuple tuple) {
        for (Cell cell : tuple) {
          if (cell != player.getCell()) return false;
        }
        return true;
      }
    });
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(super.toString() + "[board=");

    for (int i = 0; i < board.getNumTiles(); i++) {
      sb.append(i + "=" + board.getValue(i));
      if (i < size * size - 1) sb.append(",");
    }

    return sb.append("]").toString();
  }


  private interface BoardTest {

    boolean test(Board.Tuple tuple);
  }
}
