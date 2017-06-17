package com.example.game.helpers;

import com.example.game.interfaces.GameScoring;
import com.example.game.interfaces.GameState;
import com.example.game.interfaces.Player;

import java.util.Collection;

/**
 * Created by juan.villa on 17/06/17.
 */

class JVGameScore implements GameScoring {

  @Override
  public int score(GameState state, Player player) {
    int score = score((JVGameState) state, (JVPlayer) player);
    return score;
  }

  private int score(JVGameState state, JVPlayer player) {
    if (state.isWin()) {
      if (state.isWinner(player)) {
        return Integer.MAX_VALUE;
      }
      return Integer.MIN_VALUE;

    }

    Collection<Board.Tuple> tuples = state.board.toTuples();

    int myscore = 0;
    int oppscore = 0;
    for (Board.Tuple tuple : tuples) {
      myscore += canScore(tuple, player.getCell());
      oppscore += canScore(tuple, getOpponent(player.getCell()));
    }

    return myscore - oppscore;
  }

  private int canScore(Board.Tuple tuple, Cell cell) {
    Cell opp = getOpponent(cell);
    for (Cell t : tuple) {
      if (t == opp) {
        return 0;
      }
    }
    return 1;
  }

  private Cell getOpponent(Cell cell) {
    return cell == Cell.X ? Cell.O : Cell.X;
  }

}
