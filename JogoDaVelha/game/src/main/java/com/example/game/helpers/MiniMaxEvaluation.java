package com.example.game.helpers;

import com.example.game.interfaces.Evaluation;
import com.example.game.interfaces.GameMove;
import com.example.game.interfaces.GameState;
import com.example.game.interfaces.Player;

import java.util.Collection;


/**
 * Created by juan.villa on 16/06/17.
 *
 * @author Juan Villa marcbiaggini@gmail.com
 * @date 16/06/17
 */
public class MiniMaxEvaluation implements Evaluation {

	private Player original;

	private int ply;

	public MiniMaxEvaluation(int ply) {
		this.ply = ply;
	}

	@Override
	public GameMove bestMove(GameState s, Player player, Player opponent) {

		this.original = player;
		MoveEvaluation best = minimax(s.copy(), ply, player, opponent);

		GameMove bestMove = best.move;

		if (best.move != null) {
			return bestMove;
		}
		Collection<GameMove> moves = player.validMoves(s);
		return moves.isEmpty() ? null : moves.iterator().next();
	}

	private MoveEvaluation minimax(GameState s, int p, Player player, Player opponent) {

		Collection<GameMove> moves = player.validMoves(s);

		if (p == 0 || moves.isEmpty()) {

			final int score = player.eval(s);
			final MoveEvaluation e = new MoveEvaluation(score);
			return e;
		}

		final boolean isPlayer = player == original;

		MoveEvaluation best = new MoveEvaluation(isPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE);

		for (GameMove move : moves) {
			move.execute(s);

			MoveEvaluation me = minimax(s, p - 1, opponent, player);

			move.undo(s);

			if (isPlayer) {
				if (me.score > best.score) {
					best = new MoveEvaluation(move, me.score);
				}
			}
			else {
				if (me.score < best.score) {
					best = new MoveEvaluation(move, me.score);
				}
			}
		}
		return best;
	}

	private static class MoveEvaluation {

		protected final GameMove move;

		protected final int score;

		protected MoveEvaluation(int score) {
			this(null, score);
		}

		protected MoveEvaluation(GameMove move, int score) {
			this.move = move;
			this.score = score;
		}

		@Override
		public String toString() {
			return super.toString() + "[move=" + move + ",score=" + score + "]";
		}
	}
}
