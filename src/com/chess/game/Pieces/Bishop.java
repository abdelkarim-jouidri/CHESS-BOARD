package com.chess.game.Pieces;

import com.chess.game.Board.Board;
import com.chess.game.Board.BoardUtils;
import com.chess.game.Board.Move;
import com.chess.game.Board.Tile;
import com.chess.game.PieceColor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Bishop extends Piece{
    private static int[] POSSIBLE_MOVES_DIRECTION_COORDINATES = {-7,-9, 7, 9 };

    public Bishop(int piecePosition, PieceColor pieceColor) {
        super(PieceType.BISHOP, piecePosition, pieceColor);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        List<Move> legalMoves = new ArrayList<Move>();
        for(int moveCoordinate : POSSIBLE_MOVES_DIRECTION_COORDINATES){
                int destinationCoordinate = this.piecePosition;
                while (BoardUtils.isValidCoordinate(destinationCoordinate)){
                    if(isAtEighthColumnExcludedPosition(destinationCoordinate, moveCoordinate) || isAtFirstColumnExcludedPosition(destinationCoordinate, moveCoordinate)){
                        break;
                    }
                    Tile possibleTargetTile = board.getTile(destinationCoordinate);
                    if(!possibleTargetTile.isTileOccupied()){
                        legalMoves.add(new Move.NonAttackMove(board, this, destinationCoordinate));
                    }
                    else {
                        Piece pieceAtOccupiedTile = possibleTargetTile.getPiece();
                        PieceColor colorOfPieceAtTile = pieceAtOccupiedTile.getPieceColor();
                        if(this.getPieceColor() != colorOfPieceAtTile){
                            legalMoves.add(new Move.AttackMove(board, this, destinationCoordinate, pieceAtOccupiedTile));
                        }
                        break; // because this is the only possible move on this direction
                    }
                }
        }
        return legalMoves;
    }

    @Override
    public Bishop movedPiece(Move move) {
        return new Bishop(move.getDestinationCoordinate(), move.getToBeMovedPiece().getPieceColor());
    }

    private static boolean isAtFirstColumnExcludedPosition(int currentPos, int candidateOffset){

        return BoardUtils.FIRST_COLUMN[currentPos] && (candidateOffset == 7 ||candidateOffset == -9);

    }

    private static boolean isAtEighthColumnExcludedPosition(int currentPos, int candidateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPos] && (candidateOffset == 9 ||candidateOffset == -7);
    }

    @Override
    public String toString() {
        return PieceType.BISHOP.toString();
    }
}
