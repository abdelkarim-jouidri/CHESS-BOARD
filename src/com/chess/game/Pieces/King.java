package com.chess.game.Pieces;

import com.chess.game.Board.Board;
import com.chess.game.Board.BoardUtils;
import com.chess.game.Board.Move;
import com.chess.game.Board.Tile;
import com.chess.game.PieceColor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class King extends Piece{

    int [] POSSIBLE_MOVES_COORDINATE = {-9, -8, -7, -1, 1, 7, 8, 9};
    public King(int piecePosition, PieceColor pieceColor) {
        super(PieceType.KING, piecePosition, pieceColor);
    }

    @Override
    public String toString() {
        return PieceType.KING.toString();
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();

        for (int moveOffset : POSSIBLE_MOVES_COORDINATE){
            int destinationCoodinate = this.piecePosition + moveOffset;
            if (isAtEighthColumnExcludedPosition(this.piecePosition, moveOffset) ||isAtFirstColumnExcludedPosition(this.piecePosition, moveOffset)){
                continue;
            }
            if(BoardUtils.isValidCoordinate(destinationCoodinate)){
                Tile targetTile = board.getTile(destinationCoodinate);
                Piece pieceAtTargetTile = targetTile.getPiece();

                if(!targetTile.isTileOccupied()){
                    legalMoves.add(new Move.NonAttackMove(board, this, destinationCoodinate));
                }
                else {
                    if(this.pieceColor != pieceAtTargetTile.getPieceColor()){
                        legalMoves.add(new Move.AttackMove(board, this, destinationCoodinate, pieceAtTargetTile));
                    }
                }
            }
        }

        return legalMoves;
    }

    @Override
    public King movedPiece(Move move) {
        return new King(move.getDestinationCoordinate(), move.getToBeMovedPiece().getPieceColor());
    }

    public static boolean isAtFirstColumnExcludedPosition(int currentPos, int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPos] && (candidateOffset == 7 || candidateOffset == -1 || candidateOffset == -9);
    }
    public static boolean isAtEighthColumnExcludedPosition(int currentPos, int candidateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPos] && (candidateOffset == 9 || candidateOffset == 1 || candidateOffset == -7);
    }
}
