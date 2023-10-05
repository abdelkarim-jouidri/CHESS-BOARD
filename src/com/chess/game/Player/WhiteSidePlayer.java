package com.chess.game.Player;

import com.chess.game.Board.Board;
import com.chess.game.Board.Move;
import com.chess.game.Board.Tile;
import com.chess.game.PieceColor;
import com.chess.game.Pieces.Piece;
import com.chess.game.Pieces.Rook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WhiteSidePlayer extends Player {
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
        return this.board.getBlackSidePlayer();
    }

    @Override
    public Collection<Move> calculateKingsCastles(Collection<Move> playerLegalMoves, Collection<Move> opponentLegalMoves) {
        List<Move> kingsCastleMoves = new ArrayList<>();
        if (this.getPlayerKing().isFirstMove() && !this.inCheck()) {
            //king's side
            if (!this.board.getTile(62).isTileOccupied() && !this.board.getTile(61).isTileOccupied()) {
                Tile rookTile = this.board.getTile(63);
                Rook rook = (Rook) rookTile.getPiece();
                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove() && rookTile.getPiece().getPieceType().isRook()) {

                    kingsCastleMoves.add(new Move.KingsSideCastleMove(this.board, this.getPlayerKing(),62, rook, rook.getPosition(),61));
                }
            }

            //queen's side
            if (!this.board.getTile(59).isTileOccupied()
                    && !this.board.getTile(58).isTileOccupied()
                    && !this.board.getTile(57).isTileOccupied()) {
                Tile rookTile = this.board.getTile(56);
                Rook rook = (Rook) rookTile.getPiece();
                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove() && rookTile.getPiece().getPieceType().isRook()) {
                    if (Player.calculateAttackMovesOnTile(59, opponentLegalMoves).isEmpty()
                            && Player.calculateAttackMovesOnTile(58, opponentLegalMoves).isEmpty()
                            && Player.calculateAttackMovesOnTile(57, opponentLegalMoves).isEmpty()) {

                        kingsCastleMoves.add(new Move.QueensSideCastleMove(this.board,
                                this.getPlayerKing(),
                                58,
                                rook,
                                rook.getPosition(),
                                59
                                ));
                    }
                }
            }
        }
        return kingsCastleMoves;
    }
}