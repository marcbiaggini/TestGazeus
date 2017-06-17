package com.example.game.helpers;

import com.example.game.interfaces.Evaluation;
import com.example.game.interfaces.GameMove;
import com.example.game.interfaces.GameState;

import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by juan.villa on 16/06/17.
 */

public class JVRunner {

  private JVPlayer human;

  private JVPlayer computer;

  private Evaluation evaluation;

  private int size;

  public JVRunner(JVPlayer human, JVPlayer computer, Evaluation evaluation, int size) {
    this.human = human;
    this.computer = computer;
    this.evaluation = evaluation;
    this.size = size;

  }

  public static void main(String[] args) {

    JVPlayer human = new JVPlayer(Cell.X);
    JVPlayer computer = new JVPlayer(Cell.O);

    int ply = 2;
    int size = 3;

    Evaluation evaluation = new MiniMaxEvaluation(ply);

    JVRunner runner = new JVRunner(human, computer, evaluation, size);
    runner.runGame(System.in);

  }

  public void runGame(InputStream stream) {

    JVGameState state = new JVGameState(size);

    Scanner scanner = new Scanner(System.in);
    scanner.useDelimiter(System.getProperty("line.separator"));

    while (!state.isDraw() && !state.isWin()) {
      GameMove move = getNextPlayerMove(scanner, state);

      move.execute(state);
      state.board.prettyPrint(System.out);

      if (state.isDraw() || state.isWin()) break;

      GameMove opponentMove = evaluation.bestMove(state, computer, human);
      opponentMove.execute(state);
      state.board.prettyPrint(System.out);
    }

  }

  private GameMove getNextPlayerMove(Scanner scanner, GameState state) {

    GameMove move = null;

    boolean legal = false;
    while (!legal) {
      try {
        int pos = scanner.nextInt();
        legal = pos > 0 && pos <= (size * size);
        if (legal) {
          pos--;
          move = new JVGameMove(human.getCell(), pos / size, pos % size);
          if (!move.isValid(state)) {
            legal = false;
          }
        }
      }
      catch (InputMismatchException e) {
        scanner.next();
      }

    }

    return move;
  }
}
