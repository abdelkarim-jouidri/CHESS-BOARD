package com.chess.game.Pieces;

import com.chess.game.Board.Board;
import com.chess.game.Board.Move;
import com.chess.game.PieceColor;

import java.util.Collection;

public abstract class Piece {

    protected final int piecePosition;
    protected final PieceColor pieceColor;

    public Piece(final int piecePosition, final PieceColor pieceColor) {
        this.piecePosition = piecePosition;
        this.pieceColor = pieceColor;
    }



    public abstract Collection<Move> calculateLegalMoves(Board board);

    public  PieceColor getPieceColor(){
        return this.pieceColor;
    }


}
