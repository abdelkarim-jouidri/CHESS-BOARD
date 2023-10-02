package com.chess.game.Player;

import com.chess.game.Board.Board;
import com.chess.game.Board.Move;
import com.chess.game.PieceColor;
import com.chess.game.Pieces.Piece;

import java.util.Collection;

public class WhiteSidePlayer extends Player{

    public WhiteSidePlayer(Board board, Collection<Move> whiteLegalMoves, Collection<Move> blackLegalMoves) {
        super(board, whiteLegalMoves, blackLegalMoves);
    }
    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getActiveWhitePieces();
    }

    @Override
    public PieceColor getSideColorOfPlayer() {
        return PieceColor.WHITE;
    }
}
