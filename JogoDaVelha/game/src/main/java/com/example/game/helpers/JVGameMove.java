package com.example.game.helpers;


import com.example.game.interfaces.GameMove;
import com.example.game.interfaces.GameState;

/**
 * Created by juan.villa on 16/06/17.
 */

public class JVGameMove implements GameMove {

  private final Cell cell;

  private final int r;

  private final int c;

  public JVGameMove(Cell cell, int r, int c) {
    this.cell = cell;
    this.r = r;
    this.c = c;
  }

  public Cell getCell() {
    return cell;
  }

  public int getRow() {
    return r;
  }

  public int getColumn() {
    return c;
  }

  @Override
  public void execute(GameState state) {
    ((JVGameState) state).place(cell, r, c);
  }

  @Override
  public boolean isValid(GameState state) {

    return ((JVGameState) state).board.isEmpty(r, c);
  }

  @Override
  public void undo(GameState state) {
    ((JVGameState) state).place(null, r, c);
  }

  @Override
  public String toString() {
    return super.toString() + "[cell=" + cell + ", r=" + r + ", c=" + c + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + c;
    result = prime * result + r;
    result = prime * result + ((cell == null) ? 0 : cell.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    JVGameMove other = (JVGameMove) obj;
    if (c != other.c) return false;
    if (r != other.r) return false;
    if (cell == null) {
      if (other.cell != null) return false;
    }
    else if (!cell.equals(other.cell)) return false;
    return true;
  }

}
