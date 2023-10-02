package com.chess.game.Player;

import com.chess.game.Board.Board;
import com.chess.game.Board.Move;
import com.chess.game.PieceColor;
import com.chess.game.Pieces.King;

import java.util.Collection;

public abstract class Player {
    protected Board board;
    protected King king; // we have to keep track checkmate status to keep track of the winner
    protected Collection<Move> legalMoves;

    public Player(Board board, King king, Collection<Move> legalMoves) {
        this.board = board;
        this.king = king;
        this.legalMoves = legalMoves;
    }




}
