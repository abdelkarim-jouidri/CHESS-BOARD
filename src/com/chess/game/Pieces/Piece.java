package com.chess.game.Pieces;

import com.chess.game.Board.Board;
import com.chess.game.Board.Move;
import com.chess.game.PieceColor;

import java.util.Collection;

public abstract class Piece {

    protected final int piecePosition;
    protected final PieceColor pieceColor;

    protected final PieceType pieceType;

    protected final boolean isFirstMove ;

    public Piece(PieceType pieceType, int piecePosition, PieceColor pieceColor) {
        this.pieceType = pieceType;
        this.piecePosition = piecePosition;
        this.pieceColor = pieceColor;
        this.isFirstMove = false;
    }

    public abstract Collection<Move> calculateLegalMoves(Board board);

    public  PieceColor getPieceColor(){
        return this.pieceColor;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    protected boolean isFirstMove() {
        return true;
    }

    public int getPosition() {
        return this.piecePosition;
    }

    public enum PieceType{

        PAWN("P") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        KNIGHT("N") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        BISHOP("B") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        ROOK("R") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        KING("K") {
            @Override
            public boolean isKing() {
                return true;
            }
        },
        QUEEN("Q") {
            @Override
            public boolean isKing() {
                return false;
            }
        };

        public abstract boolean isKing();
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
