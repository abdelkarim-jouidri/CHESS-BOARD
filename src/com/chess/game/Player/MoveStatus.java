package com.chess.game.Player;

public enum MoveStatus {
    DONE {
        @Override
        public boolean isDone() {
            return true;
        }
    },

    ILLEGAL_MOVE{
        @Override
       public boolean isDone(){
            return false;
        }
    },

    PLAYER_IS_IN_CHECK {
        @Override
        public boolean isDone(){
            return false;
        }
    };

   public abstract boolean isDone();
}
