package com.chess.game.Board;

import com.chess.game.Pieces.Piece;

public abstract class Move {

    protected Board board;
    protected Piece toBeMovedPiece;
    protected int destinationCoordinate;


    public Move(Board board, Piece toBeMovedPiece, int destinationCoordinate) {
        this.board = board;
        this.toBeMovedPiece = toBeMovedPiece;
        this.destinationCoordinate = destinationCoordinate;
    }

    public int getDestinationCoordinate() {
        return destinationCoordinate;
    }

    public Piece getToBeMovedPiece() {
        return toBeMovedPiece;
    }

    public abstract Board execute();

    public static class AttackMove extends Move{

        private Piece attackedPiece;

        public AttackMove(Board board, Piece toBeMovedPiece, int destinationCoordinate, Piece attackedPiece) {
            super(board, toBeMovedPiece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public Board execute() {
            Board.Builder builder = new Board.Builder();
            return null;
        }
    }

    public static class NonAttackMove extends Move {
        public NonAttackMove(Board board, Piece toBeMovedPiece, int destinationCoordinate) {
            super(board, toBeMovedPiece, destinationCoordinate);
        }

        @Override
        public Board execute() {
            Board.Builder builder = new Board.Builder();
            for(Piece piece : this.board.getCurrentPlayer().getActivePieces()){
                if(!this.toBeMovedPiece.equals(piece)){
                    builder.Piece(piece);
                }
            }

            for (Piece piece : this.board.getCurrentPlayer().getOpponent().getActivePieces()){
                if(!this.toBeMovedPiece.equals(piece)){
                    builder.Piece(piece);
                }
            }
            //this handles moving the moved piece
            builder.Piece(null);
            builder.nextMovePlayer(this.board.getCurrentPlayer().getOpponent().getSideColorOfPlayer());
            builder.build();
            return this.board;
        }
    }


}
