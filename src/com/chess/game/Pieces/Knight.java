package com.chess.game.Pieces;

import com.chess.game.Board.Board;
import com.chess.game.Board.BoardUtils;
import com.chess.game.Board.Move;
import com.chess.game.Board.Move.AttackMove;
import com.chess.game.Board.Tile;
import com.chess.game.PieceColor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.chess.game.Board.Move.*;

public class Knight extends Piece{

    private final static int[] POSSIBLE_MOVES_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};

    public Knight(int piecePosition , PieceColor pieceColor){
        super(PieceType.KNIGHT, piecePosition, pieceColor);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board){
        List<Move> legalMoves = new ArrayList<>();
        int destinationCoordinate;
        for (int moveCoordinate : POSSIBLE_MOVES_COORDINATES){
            destinationCoordinate = this.piecePosition + moveCoordinate;
            if(BoardUtils.isValidCoordinate(destinationCoordinate)){
                //Make sure the piece is not at a prohibited position
                if(isAtFirstColumnExcludedPosition(this.piecePosition, destinationCoordinate)
                        || isAtSecondColumnExcludedPosition(this.piecePosition, destinationCoordinate)
                        || isAtSeventhColumnExcludedPosition(this.piecePosition, destinationCoordinate)
                        || isAtEighthColumnExcludedPosition(this.piecePosition, destinationCoordinate))
                {
                    continue;
                }
                Tile possibleTargetTile = board.getTile(destinationCoordinate);
                if(!possibleTargetTile.isTileOccupied()){
                    legalMoves.add(new NonAttackMove(board, this, destinationCoordinate));
                }else {
                    Piece pieceAtTargetDestination = possibleTargetTile.getPiece();
                    PieceColor colorofTargetedPiece = pieceAtTargetDestination.getPieceColor();
                    if(this.getPieceColor() != colorofTargetedPiece){
                        /*This an enemy piece*/
                        legalMoves.add(new AttackMove(board, this, destinationCoordinate, pieceAtTargetDestination)); // the move should be different in this case
                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    @Override
    public Knight movedPiece(Move move) {
        return new Knight(move.getDestinationCoordinate(), move.getToBeMovedPiece().getPieceColor());
    }

    public static boolean isAtFirstColumnExcludedPosition(int currentPos, int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPos] &&
                ((candidateOffset==6) ||(candidateOffset==15) || (candidateOffset==-10) || (candidateOffset==-17));
    }

    public static boolean isAtSecondColumnExcludedPosition(int currentPos, int candidateOffset){
        return BoardUtils.SECOND_COLUMN[currentPos] &&
                ((candidateOffset == -10) || (currentPos == 6));
    }

    public static boolean isAtSeventhColumnExcludedPosition(int currentPos, int candidateOffset){
        return BoardUtils.SEVENTH_COLUMN[currentPos] &&
                ((candidateOffset == 10) || (candidateOffset == -6));
    }

    public static boolean isAtEighthColumnExcludedPosition(int currentPos, int candidateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPos] &&
                ((candidateOffset==10) || (candidateOffset==17) || (candidateOffset==-6) || (candidateOffset == -15));
    }

    @Override
    public String toString() {
        return PieceType.KNIGHT.toString();
    }
}
