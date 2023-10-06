package com.chess.game.Player;

import com.chess.game.Board.Board;
import com.chess.game.Board.Move;

// it will be usefull when we make a move, and we want to check for different scenarios
public class TransitionMove {

    private Board transitionBoard;
    private Move move;
    private MoveStatus moveStatus;

    public TransitionMove(Board transitionBoard, Move move, MoveStatus moveStatus) {
        this.transitionBoard = transitionBoard;
        this.move = move;
        this.moveStatus = moveStatus;
    }

    public Board getTransitionBoard() {
        return transitionBoard;
    }

    public MoveStatus getMoveStatus() {
        return this.moveStatus;
    }
}
