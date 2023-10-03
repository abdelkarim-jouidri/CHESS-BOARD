package com.chess.game.Player;

public enum MoveStatus {
    DONE {
        @Override
        boolean isDone() {
            return true;
        }
    },

    ILLEGAL_MOVE{
        @Override
        boolean isDone(){
            return false;
        }
    },

    PLAYER_IS_IN_CHECK {
        @Override
        boolean isDone(){
            return false;
        }
    };

   abstract boolean isDone();
}
