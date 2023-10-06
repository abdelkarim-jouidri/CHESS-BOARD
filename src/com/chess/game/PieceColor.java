package com.chess.game;

import com.chess.game.Player.BlackSidePlayer;
import com.chess.game.Player.Player;
import com.chess.game.Player.WhiteSidePlayer;

public enum PieceColor {
    WHITE {
        @Override
        public int getDirection() {
            return -1;
        }

        @Override
        public boolean isWhite() {
            return true;
        }

        @Override
        public boolean isBlack() {
            return false;
        }

        @Override
        public Player choosePlayer(WhiteSidePlayer whiteSidePlayer, BlackSidePlayer blackSidePlayer) {
            return whiteSidePlayer;
        }
    },
    BLACK{
        @Override
        public int getDirection() {
            return 1;
        }

        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public boolean isBlack() {
            return true;
        }

        @Override
        public Player choosePlayer(WhiteSidePlayer whiteSidePlayer, BlackSidePlayer blackSidePlayer) {
            return blackSidePlayer;
        }
    };

    public abstract int getDirection();
    public abstract boolean isWhite();
    public abstract boolean isBlack();

    public abstract Player choosePlayer(WhiteSidePlayer whiteSidePlayer, BlackSidePlayer blackSidePlayer);
}
