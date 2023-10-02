package com.chess.game.Player;

import com.chess.game.Board.Board;
import com.chess.game.Board.Move;
import com.chess.game.PieceColor;
import com.chess.game.Pieces.Piece;

import java.util.Collection;

public class BlackSidePlayer extends  Player{
    public BlackSidePlayer(Board board, Collection<Move> blackLegalMoves, Collection<Move> whiteLegalMoves) {
        super(board, blackLegalMoves, whiteLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getActiveBlackPieces();
    }

    @Override
    public PieceColor getSideColorOfPlayer() {
        return PieceColor.BLACK;
    }
}
