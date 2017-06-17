package com.example.game.interfaces;

/**
 * Created by juan.villa on 16/06/17.
 *
 * @author Juan Villa marcbiaggini@gmail.com
 * @date 16/06/17
 */
public interface Evaluation {

	GameMove bestMove(GameState state, Player player, Player opponent);
}
