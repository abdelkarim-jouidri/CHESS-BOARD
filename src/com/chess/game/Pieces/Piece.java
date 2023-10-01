package com.chess.game.Pieces;

import com.chess.game.Board.Board;
import com.chess.game.Board.Move;
import com.chess.game.PieceColor;

import java.util.Collection;

public abstract class Piece {

    protected final int piecePosition;
    protected final PieceColor pieceColor;

    protected final boolean isFirstMove ;

    public Piece(int piecePosition, PieceColor pieceColor) {
        this.piecePosition = piecePosition;
        this.pieceColor = pieceColor;
        this.isFirstMove = false;
    }

    public abstract Collection<Move> calculateLegalMoves(Board board);

    public  PieceColor getPieceColor(){
        return this.pieceColor;
    }


    protected boolean isFirstMove() {
        return true;
    }

    public int getPosition() {
        return this.piecePosition;
    }

    public enum PieceType{

        PAWN("P"),
        KNIGHT("N"),
        BISHOP("B"),
        ROOK("R"),
        KING("K"),
        QUEEN("Q");


        private String pieceName;
        PieceType(String pieceName){
            this.pieceName = pieceName;
        }


        @Override
        public String toString() {
            return this.pieceName.toString();
        }
    }
}
