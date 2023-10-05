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

public class BlackSidePlayer extends  Player{
    private PieceColor playersSide;

    public BlackSidePlayer(Board board, Collection<Move> blackLegalMoves, Collection<Move> whiteLegalMoves) {
        super(board, blackLegalMoves, whiteLegalMoves);
        this.playersSide = PieceColor.BLACK;
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getActiveBlackPieces();
    }

    @Override
    public PieceColor getSideColorOfPlayer() {
        return PieceColor.BLACK;
    }

    @Override
    public Player getOpponent() {
        return this.board.getWhiteSidePlayer();
    }

    @Override
    public Collection<Move> calculateKingsCastles(Collection<Move> playerLegalMoves, Collection<Move> opponentLegalMoves) {
        List<Move> kingsCastleMoves = new ArrayList<>();
        if (this.getPlayerKing().isFirstMove() && !this.inCheck()) {
            //king's side
            if (!this.board.getTile(5).isTileOccupied() && !this.board.getTile(6).isTileOccupied()) {
                Tile rookTile = this.board.getTile(7);
                Rook rook = (Rook) rookTile.getPiece();
                if(rookTile.isTileOccupied() && rookTile.getPiece().getPieceType().isRook()){
                    if (Player.calculateAttackMovesOnTile(5, opponentLegalMoves).isEmpty()
                            && Player.calculateAttackMovesOnTile(6, opponentLegalMoves).isEmpty())
                    {
                        kingsCastleMoves.add(new Move.KingsSideCastleMove(this.board,
                                this.getPlayerKing(),
                                6,
                                rook,
                                rook.getPosition(),
                                5
                                ));
                    }
                }
            }

            //queen's side
            if (!this.board.getTile(1).isTileOccupied()
                    && !this.board.getTile(2).isTileOccupied()
                    && !this.board.getTile(3).isTileOccupied()) {
                Tile rookTile = this.board.getTile(0);
                Rook rook = (Rook) rookTile.getPiece();
                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove() && rookTile.getPiece().getPieceType().isRook()) {
                    if (Player.calculateAttackMovesOnTile(1, opponentLegalMoves).isEmpty()
                            && Player.calculateAttackMovesOnTile(2, opponentLegalMoves).isEmpty()
                            && Player.calculateAttackMovesOnTile(2, opponentLegalMoves).isEmpty()) {
                        kingsCastleMoves.add(new Move.QueensSideCastleMove(this.board,
                                this.getPlayerKing(),
                                2,
                                rook,
                                rook.getPosition(),
                                3));
                    }
                }
            }
        }
        return kingsCastleMoves;
    }
}
