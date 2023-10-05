package com.chess.game.Pieces;

import com.chess.game.Board.Board;
import com.chess.game.Board.BoardUtils;
import com.chess.game.Board.Move;
import com.chess.game.Board.Tile;
import com.chess.game.PieceColor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Queen extends Piece{

    int [] POSSIBLE_MOVES_COORDINATES = {-9, -8, -7,  -1, 1, 7, 8, 9 };
    public Queen(int piecePosition, PieceColor pieceColor) {
        super(PieceType.QUEEN, piecePosition, pieceColor);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        List<Move>legalMoves = new ArrayList<>();
        for (int moveCoordinateOffset : POSSIBLE_MOVES_COORDINATES){
            int destinationCoordinate = this.piecePosition;
            while (BoardUtils.isValidCoordinate(destinationCoordinate)){
                destinationCoordinate += moveCoordinateOffset;

                if (BoardUtils.isValidCoordinate(destinationCoordinate)){
                    if(isAtEighthColumnExcludedPosition(destinationCoordinate, moveCoordinateOffset) || isAtFirstColumnExcludedPosition(destinationCoordinate, moveCoordinateOffset)){
                        break;
                    }
                    Tile targetTile = board.getTile(destinationCoordinate);
                    if (!targetTile.isTileOccupied()){
                        legalMoves.add(new Move.NonAttackMove(board, this, destinationCoordinate));
                    }
                    else {
                        Piece pieceAtTargetTile = targetTile.getPiece();
                        PieceColor colorOfPieceAtTargetTile = pieceAtTargetTile.getPieceColor();
                        if(this.pieceColor != colorOfPieceAtTargetTile){
                            legalMoves.add(new Move.AttackMove(board, this, destinationCoordinate, pieceAtTargetTile));
                        }
                        break;
                    }
                }
            }
        }
        return legalMoves;

    }

    @Override
    public Queen movedPiece(Move move) {
        return new Queen(move.getDestinationCoordinate(), move.getToBeMovedPiece().getPieceColor());

    }

    static boolean isAtFirstColumnExcludedPosition(int currentPos , int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPos] && (candidateOffset == -1 || candidateOffset == -9 || candidateOffset == 7);
    }

    @Override
    public String toString() {
        return PieceType.QUEEN.toString();
    }

    static boolean isAtEighthColumnExcludedPosition(int currentPos, int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPos] && (candidateOffset == 1 || candidateOffset == 9 || candidateOffset == -7);
    }

}
