package com.example.game.interfaces;

/**
 *
 * @author Juan Villa marcbiaggini@gmail.com
 * @date 17/06/17
 */
public interface GameState {

	boolean isDraw();

	boolean isWin();

	GameState copy();
}
