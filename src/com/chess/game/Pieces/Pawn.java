package com.chess.game.Pieces;

import com.chess.game.Board.Board;
import com.chess.game.Board.BoardUtils;
import com.chess.game.Board.Move;
import com.chess.game.Board.Tile;
import com.chess.game.PieceColor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pawn extends Piece{

    int [] POSSIBLE_MOVES_COORDINATE = {7, 8, 9, 16};
    public Pawn(int piecePosition, PieceColor pieceColor) {
        super(piecePosition, pieceColor);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();
        for (int moveOffset : POSSIBLE_MOVES_COORDINATE){
            int destinationCoordinate = this.piecePosition + (this.getPieceColor().getDirection()*moveOffset);
            if(!BoardUtils.isValidCoordinate(destinationCoordinate)){
                continue;
            }
            else {
                Tile targetTile = Board.getTile(destinationCoordinate);
                if (!targetTile.isTileOccupied() && moveOffset == 8){
                    // MORE WORK TO DO HERE (promotions) !!!
                    legalMoves.add(new Move.NonAttackMove(board, this, destinationCoordinate));
                }
                else if (moveOffset == 16 && this.isFirstMove() &&
                        (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.pieceColor.isBlack())
                        || BoardUtils.SEVENTH_ROW[this.piecePosition] && this.pieceColor.isWhite() ){
                    int behindDestinationCoordinate = this.piecePosition + this.pieceColor.getDirection()*8;
                    if(!Board.getTile(destinationCoordinate).isTileOccupied() && !Board.getTile(behindDestinationCoordinate).isTileOccupied()){
                        legalMoves.add(new Move.NonAttackMove(board, this, destinationCoordinate));
                    }
                }

                else if (!isAtFirstColumnExcludedPosition(this.piecePosition, moveOffset) && moveOffset == 9){
                        legalMoves.add(new Move.AttackMove(board, this, destinationCoordinate, Board.getTile(destinationCoordinate).getPiece()));
                }
                else if (!isAtEighthColumnExcludedPosition(this.piecePosition, moveOffset) && moveOffset == 7){
                    legalMoves.add(new Move.AttackMove(board, this, destinationCoordinate, Board.getTile(destinationCoordinate).getPiece()));
                }

            }
        }
        return legalMoves;
    }

    static boolean isAtFirstColumnExcludedPosition(int currentPos , int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPos] && candidateOffset == 7;
    }

    static boolean isAtEighthColumnExcludedPosition(int currentPos , int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPos] && candidateOffset == 9;
    }
}