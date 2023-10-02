package com.chess.game.Board;

import com.chess.game.Pieces.Piece;

public abstract class Move {

    private Board board;
    private Piece toBeMovedPiece;
    private int destinationCoordinate;


    public Move(Board board, Piece toBeMovedPiece, int destinationCoordinate) {
        this.board = board;
        this.toBeMovedPiece = toBeMovedPiece;
        this.destinationCoordinate = destinationCoordinate;
    }

    public int getDestinationCoordinate() {
        return destinationCoordinate;
    }

    public static class AttackMove extends Move{

        private Piece attackedPiece;

        public AttackMove(Board board, Piece toBeMovedPiece, int destinationCoordinate, Piece attackedPiece) {
            super(board, toBeMovedPiece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }
    }

    public static class NonAttackMove extends Move {
        public NonAttackMove(Board board, Piece toBeMovedPiece, int destinationCoordinate) {
            super(board, toBeMovedPiece, destinationCoordinate);
        }
    }


}
