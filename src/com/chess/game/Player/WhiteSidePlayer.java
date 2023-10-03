package com.chess.game.Player;

import com.chess.game.Board.Board;
import com.chess.game.Board.Move;
import com.chess.game.PieceColor;
import com.chess.game.Pieces.Piece;

import java.util.Collection;

public class WhiteSidePlayer extends Player{
    private PieceColor playersSide;

    public WhiteSidePlayer(Board board, Collection<Move> whiteLegalMoves, Collection<Move> blackLegalMoves) {
        super(board, whiteLegalMoves, blackLegalMoves);
        this.playersSide = PieceColor.WHITE;
    }
    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getActiveWhitePieces();
    }

    @Override
    public PieceColor getSideColorOfPlayer() {
        return PieceColor.WHITE;
    }

    @Override
    public Player getOpponent() {
        return null;
    }
}
