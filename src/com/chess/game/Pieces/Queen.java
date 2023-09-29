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
        super(piecePosition, pieceColor);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        List<Move>legalMoves = new ArrayList<>();
        for (int moveCoordinateOffset : POSSIBLE_MOVES_COORDINATES){
            int destinationCoordinate = this.piecePosition;
            while (BoardUtils.isValidCoordinate(destinationCoordinate)){
                destinationCoordinate += moveCoordinateOffset;
                if(isAtEighthColumnExcludedPosition(destinationCoordinate, moveCoordinateOffset) || isAtFirstColumnExcludedPosition(destinationCoordinate, moveCoordinateOffset)){
                    break;
                }
                if (BoardUtils.isValidCoordinate(destinationCoordinate)){
                    Tile targetTile = Board.getTile(destinationCoordinate);
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

    static boolean isAtFirstColumnExcludedPosition(int currentPos , int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPos] && (candidateOffset == -1 || candidateOffset == -9 || candidateOffset == 7);
    }

    static boolean isAtEighthColumnExcludedPosition(int currentPos, int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPos] && (candidateOffset == 1 || candidateOffset == 9 || candidateOffset == -7);
    }

}
