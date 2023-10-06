package com.chess.game.Board;

import com.chess.game.Pieces.Piece;
import com.chess.game.Pieces.Rook;

import java.util.List;
import java.util.Objects;

public abstract class Move {

    protected Board board;
    protected Piece toBeMovedPiece;
    protected int destinationCoordinate;
    private static final Move NULL_MOVE = new NullMove();


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

    public int getCurrentCoordinate(){
        return getToBeMovedPiece().getPosition();
    }

    public Board execute() {
        Board.Builder builder = new Board.Builder();
        for(Piece piece : this.board.getCurrentPlayer().getActivePieces()){
            if(!this.toBeMovedPiece.equals(piece)){
                builder.Piece(piece);
            }
        }
        System.out.println("current player  :"+this.board.getCurrentPlayer().toString()+ "\n");
        for (Piece piece : this.board.getCurrentPlayer().getOpponent().getActivePieces()){
            System.out.println(piece.getPosition());
            builder.Piece(piece);
        }
        builder.Piece(this.toBeMovedPiece.movedPiece(this));
        builder.nextMovePlayer(this.board.getCurrentPlayer().getOpponent().getSideColorOfPlayer());
        return builder.build();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + this.destinationCoordinate;
        if (this.toBeMovedPiece != null) {
            result = 31 * result + this.toBeMovedPiece.hashCode();
            result = 31 * result + this.toBeMovedPiece.getPosition();
        }
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Move)) {
            return false;
        }

        Move otherMove = (Move) obj;
        Piece thisPiece = getToBeMovedPiece();
        Piece otherPiece = otherMove.getToBeMovedPiece();

        // Ensure that both pieces are not null before comparing
        if (thisPiece == null || otherPiece == null) {
            return false;
        }

        return otherMove.getCurrentCoordinate() == getCurrentCoordinate()
                && getDestinationCoordinate() == otherMove.getDestinationCoordinate()
                && Objects.equals(thisPiece, otherPiece);
    }


    public boolean isAttackMove(){
        return false;
    }

    public boolean isCastlingMove(){
        return true;
    }

    public Piece getAttackedPiece(){
        return null;
    }

    public static class AttackMove extends Move{

        private Piece attackedPiece;

        public AttackMove(Board board, Piece toBeMovedPiece, int destinationCoordinate, Piece attackedPiece) {
            super(board, toBeMovedPiece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public boolean isAttackMove() {
            return true;
        }

        @Override
        public Piece getAttackedPiece() {
            return this.attackedPiece;
        }
    }

    public static class NonAttackMove extends Move {
        public NonAttackMove(Board board, Piece toBeMovedPiece, int destinationCoordinate) {
            super(board, toBeMovedPiece, destinationCoordinate);
        }

    }



    public static class PawnNonAttackMove extends Move {
        public PawnNonAttackMove(Board board, Piece toBeMovedPiece, int destinationCoordinate) {
            super(board, toBeMovedPiece, destinationCoordinate);
        }

    }

    public static class PawnAttackMove extends AttackMove {
        public PawnAttackMove(Board board, Piece toBeMovedPiece, int destinationCoordinate, Piece attackedPiece) {
            super(board, toBeMovedPiece, destinationCoordinate, attackedPiece);
        }

    }


     static abstract class CastleMove extends Move {
        protected Rook castleRook;
        protected int castleRookStartingPositionCoordinate;
        protected int castleRookDestinationPositionCoordinate;
        public CastleMove(Board board,
                          Piece toBeMovedPiece,
                          int destinationCoordinate,
                          Rook castleRook,
                          int castleRookStartingPositionCoordinate,
                          int castleRookDestinationPositionCoordinate) {
            super(board, toBeMovedPiece, destinationCoordinate);
            this.castleRook = castleRook;
            this.castleRookStartingPositionCoordinate = castleRookStartingPositionCoordinate;
            this.castleRookDestinationPositionCoordinate = castleRookDestinationPositionCoordinate;
        }

         public Rook getCastleRook() {
             return castleRook;
         }

         @Override
         public Board execute() {

             Board.Builder builder = new Board.Builder();
             for(Piece piece : this.board.getCurrentPlayer().getActivePieces()){
                 if(!this.toBeMovedPiece.equals(piece)
                         && !piece.equals(this.getCastleRook()))
                 {
                     builder.Piece(piece);
                 }
             }
             builder.Piece(this.toBeMovedPiece.movedPiece(this));
             builder.Piece(new Rook(this.castleRookDestinationPositionCoordinate, this.board.getCurrentPlayer().getSideColorOfPlayer()));
             builder.nextMovePlayer(this.board.getCurrentPlayer().getOpponent().getSideColorOfPlayer());
            return builder.build();
         }

         @Override
         public boolean isCastlingMove() {
             return true;
         }

     }

    public static class KingsSideCastleMove extends CastleMove {
        public KingsSideCastleMove(Board board,
                                   Piece toBeMovedPiece,
                                   int destinationCoordinate,
                                   Rook castleRook,
                                   int castleRookStartingPositionCoordinate,
                                   int castleRookDestinationPositionCoordinate) {
            super(board,
                    toBeMovedPiece,
                    destinationCoordinate,
                    castleRook,
                    castleRookStartingPositionCoordinate,
                    castleRookDestinationPositionCoordinate);
        }
    }

    public static class QueensSideCastleMove extends CastleMove {
        public QueensSideCastleMove(Board board,
                                    Piece toBeMovedPiece,
                                    int destinationCoordinate,
                                    Rook castleRook,
                                    int castleRookStartingPositionCoordinate,
                                    int castleRookDestinationPositionCoordinate) {
            super(board,
                    toBeMovedPiece,
                    destinationCoordinate,
                    castleRook,
                    castleRookStartingPositionCoordinate,
                    castleRookDestinationPositionCoordinate);

        }
    }

    public static class NullMove extends Move{
        public NullMove() {
            super(null, null, -1);
        }

        @Override
        public Board execute() {
            throw new RuntimeException("CANNOT EXCUTE THE NULL MOVE");
        }
    }

    public static class MoveFactory {

        private MoveFactory(){
            throw  new RuntimeException("You can't instatiate this class");
        }

        public static Move createMove(Board board, int currentCoordinate, int desctinationCoordinate){
            for (Move move : board.getAllLegalMoves()){
                if(move.getToBeMovedPiece().getPosition() == currentCoordinate
                        && move.getDestinationCoordinate() == desctinationCoordinate){
                    System.out.println("success in create move method");
                    return move;
                }

            }
            return NULL_MOVE;
        }
    }



}
