package com.example.game.interfaces;

import java.util.Collection;

/**
 * @author Juan Villa marcbiaggini@gmail.com
 * @date 17/06/17
 */
public interface Player {

	int eval(GameState state);

	void setScoring(GameScoring score);

	Collection<GameMove> validMoves(GameState state);
}
