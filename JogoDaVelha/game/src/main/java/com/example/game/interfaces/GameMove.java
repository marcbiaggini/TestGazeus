package com.example.game.interfaces;

/**
 *
 * @author Juan Villa marcbiaggini@gmail.com
 * @date 16/06/17
 */
public interface GameMove {

	boolean isValid(GameState state);

	void execute(GameState state);

	void undo(GameState state);
}
