package com.example.game.helpers;


import com.example.game.interfaces.GameMove;
import com.example.game.interfaces.GameScoring;
import com.example.game.interfaces.GameState;
import com.example.game.interfaces.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by juan.villa on 16/06/17.
 */

public class JVPlayer implements Player {

  private final Cell cell;

  private GameScoring scoring = new JVGameScore();

  public JVPlayer(Cell cell) {
    this.cell = cell;
  }

  public Cell getCell() {
    return this.cell;
  }

  @Override
  public void setScoring(GameScoring scoring) {
    this.scoring = scoring;
  }

  @Override
  public int eval(GameState state) {
    return scoring.score(state, this);
  }

  @Override
  public Collection<GameMove> validMoves(GameState state) {

    List<GameMove> moves = new ArrayList<>();

    JVGameState ttstate = (JVGameState) state;
    final int size = ttstate.getSize();
    for (int r = 0; r < size; r++) {
      for (int c = 0; c < size; c++) {
        if (ttstate.board.isEmpty(r, c)) {
          moves.add(new JVGameMove(cell, r, c));
        }
      }
    }

    return moves;
  }

  @Override
  public String toString() {
    return super.toString() + "[cell=" + cell + "]";
  }
}
