package com.example.juanvilla.jogodavelha;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.game.interfaces.Evaluation;
import com.example.game.interfaces.GameMove;
import com.example.game.helpers.MiniMaxEvaluation;
import com.example.game.helpers.Cell;
import com.example.game.helpers.JVGameMove;
import com.example.game.helpers.JVGameState;
import com.example.game.helpers.JVPlayer;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;
import java.util.Map;

@EActivity(R.layout.activity_board)
public class BoardActivity extends AppCompatActivity implements View.OnClickListener {

  private static final int EVALUATION_PLY = 2;

  protected int[] imageIds = new int[]{R.id.celula1, R.id.celula2, R.id.celula3, R.id.celula4,
      R.id.celula5, R.id.celula6, R.id.celula7, R.id.celula8, R.id.celula9};

  protected ImageView[] tileViews = new ImageView[imageIds.length];

  protected Evaluation eval;

  protected JVPlayer human;

  protected JVPlayer computer;

  protected JVGameState state;

  @ViewById(R.id.btnPlayAgain)
  View btnPlayAgain;

  @ViewById(R.id.statusText)
  TextView statusBar;

  @AfterViews
  public void init() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    toolbar.setVisibility(View.GONE);

    Map<Cell, Integer> resourceMap = new HashMap<>();
    resourceMap.put(Cell.X, R.drawable.x);
    resourceMap.put(Cell.O, R.drawable.o);
    resourceMap.put(null, R.drawable.empty);

    Map<Cell, Cell> nextTileMap = new HashMap<>();
    nextTileMap.put(null, Cell.X);
    nextTileMap.put(Cell.X, Cell.O);
    nextTileMap.put(Cell.O, null);

    eval = new MiniMaxEvaluation(EVALUATION_PLY);
    human = new JVPlayer(Cell.X);
    computer = new JVPlayer(Cell.O);
    state = new JVGameState();

    for (int i = 0; i < imageIds.length; i++) {
      int id = imageIds[i];
      tileViews[i] = (ImageView) findViewById(id);
      tileViews[i].setOnClickListener(this);
    }

    btnPlayAgain.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        resetGame();
      }
    });
  }

  @Background
  public void EvaluateMoves() {
    JVGameMove oppMove = (JVGameMove) eval.bestMove(state, computer, human);
    oppMove.execute(state);
    EvaluateMoves(oppMove);
  }

  @UiThread
  public void EvaluateMoves(JVGameMove result) {
    final int id = result.getRow() + result.getColumn() * 3;
    ImageView oppView = (ImageView) findViewById(imageIds[id]);
    oppView.setImageResource(R.drawable.o);
    statusBar.setText(R.string.status_yourmove);
    checkForGameOver();
  }

  @Override
  public void onClick(View v) {
    ImageView thisImageView = (ImageView) v;

    int ix = -1;
    for (int i = 0; i < imageIds.length; i++) {
      if (imageIds[i] == v.getId()) {
        ix = i;
        break;
      }
    }

    if (ix == -1) return;

    int row = ix % 3;
    int col = ix / 3;

    GameMove move = new JVGameMove(human.getCell(), row, col);
    if (move.isValid(state)) {
      move.execute(state);
      thisImageView.setImageResource(R.drawable.x);
      checkForGameOver();

      if (!state.isDraw() && !state.isWin()) {
        statusBar.setText(R.string.status_computer_thinking);
        EvaluateMoves();
      }
    }
  }

  protected void resetGame() {
    for (ImageView tile : tileViews) {
      tile.setImageResource(R.drawable.empty);
      tile.setClickable(true);
    }
    statusBar.setText(R.string.status_start);
    btnPlayAgain.setVisibility(View.INVISIBLE);

    state = new JVGameState();
  }

  protected void checkForGameOver() {
    boolean gameOver = false;

    if (state.isDraw()) {
      statusBar.setText(R.string.status_draw);
      gameOver = true;
    } else if (state.isWin()) {
      gameOver = true;
      if (state.isWinner(human)) {
        statusBar.setText(R.string.status_won);
      } else {
        statusBar.setText(R.string.status_lost);
      }
    }

    if (gameOver) {
      for (int id : imageIds) {
        findViewById(id).setClickable(false);
      }
      btnPlayAgain.setVisibility(View.VISIBLE);
    }

  }
}
