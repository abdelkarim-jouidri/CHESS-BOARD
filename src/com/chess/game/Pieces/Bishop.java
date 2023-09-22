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
        super(piecePosition, pieceColor);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        List<Move> legalMoves = new ArrayList<Move>();
        for(int moveCoordinate : POSSIBLE_MOVES_DIRECTION_COORDINATES){
            if(isAtFirstColumnExcludedPosition(this.piecePosition, moveCoordinate) ||isAtEighthColumnExcludedPosition(this.piecePosition, moveCoordinate)){
                continue;
            };
                int destinationCoordinate = this.piecePosition + moveCoordinate;
            while (BoardUtils.isValidCoordinate(destinationCoordinate)){
                    Tile tileToMakeTheMoveTo = Board.getTile(destinationCoordinate);
                    if (tileToMakeTheMoveTo.isTileOccupied() && this.pieceColor!=tileToMakeTheMoveTo.getPiece().pieceColor){
                            legalMoves.add(new Move.AttackMove(board, this, destinationCoordinate, tileToMakeTheMoveTo.getPiece()));
                    }
                    else if(!tileToMakeTheMoveTo.isTileOccupied()){
                            legalMoves.add(new Move.NonAttackMove(board, this, destinationCoordinate));
                    }
                    else {
                            break;
                    }
                destinationCoordinate = destinationCoordinate + moveCoordinate;
            }
        }
        return legalMoves;
    }

    private static boolean isAtFirstColumnExcludedPosition(int currentPos, int candidateOffset){

        return BoardUtils.FIRST_COLUMN[currentPos] && (candidateOffset == 7 ||candidateOffset == -9);

    }

    private static boolean isAtEighthColumnExcludedPosition(int currentPos, int candidateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPos] && (candidateOffset == 9 ||candidateOffset == -7);
    }
}