package com.chess.game.Pieces;

import com.chess.game.Board.Board;
import com.chess.game.Board.Move;
import com.chess.game.PieceColor;
import com.sun.nio.sctp.NotificationHandler;

import java.util.Collection;
import java.util.Objects;

public abstract class Piece {

    protected final int piecePosition;
    protected final PieceColor pieceColor;

    protected final PieceType pieceType;

    protected final boolean isFirstMove ;

    public Piece(PieceType pieceType, int piecePosition, PieceColor pieceColor) {
        this.pieceType = pieceType;
        this.piecePosition = piecePosition;
        this.pieceColor = pieceColor;
        this.isFirstMove = true;
    }



    @Override
    public boolean equals(Object other) {
        if(this == other){
            return true;
        }
        if(!(other instanceof Piece)){
            return false;
        }
        Piece otherPiece = (Piece) other;

        return this.pieceColor == otherPiece.getPieceColor()
                && this.piecePosition == otherPiece.getPosition()
                && this.pieceType == otherPiece.getPieceType();
    }

        @Override
        public int hashCode() {
            return Objects.hash(pieceColor, pieceColor, pieceType, isFirstMove);
        }

    public abstract Collection<Move> calculateLegalMoves(Board board);
    public abstract Piece movedPiece(Move move);

    public  PieceColor getPieceColor(){
        return this.pieceColor;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public boolean isFirstMove() {
        return true;
    }

    public int getPosition() {
        return this.piecePosition;
    }



    public enum PieceType{

        PAWN("♙") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        KNIGHT("♘") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        BISHOP("♗") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        ROOK("♖") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return true;
            }
        },
        KING("♚") {
            @Override
            public boolean isKing() {
                return true;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        QUEEN("♔") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        };

        public abstract boolean isKing();
        public abstract boolean isRook();
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
