package com.chess.game.Player;

import com.chess.game.Board.Board;
import com.chess.game.Board.Move;
import com.chess.game.PieceColor;
import com.chess.game.Pieces.King;
import com.chess.game.Pieces.Piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Player {
    protected Board board;
    protected King playerKing; // we have to keep track checkmate status to keep track of the winner
    protected Collection<Move> legalMoves;

    private boolean isKingInCheck;


    public Player(Board board,  Collection<Move> legalMoves, Collection<Move> opponentLegalMoves) {
        this.board = board;
        this.playerKing = getKingPiece();
        this.legalMoves = legalMoves;
        this.legalMoves.addAll(calculateKingsCastles(legalMoves, opponentLegalMoves));
        this.isKingInCheck =  !calculateAttackMovesOnTile(this.playerKing.getPosition(),opponentLegalMoves ).isEmpty();
    }

    public Collection<Move> getLegalMoves(){
        return this.legalMoves;
    }

    public King getPlayerKing() {
        return playerKing;
    }

    private King getKingPiece() {
        for (Piece piece : getActivePieces()){
            if(piece.getPieceType().isKing()){
                return (King) piece;
            }
        };
    throw new RuntimeException("INVALID BOARD, missing king");
    }



    public boolean inCheck(){
        //for when the king is under attack
        return this.isKingInCheck;
    }

    public boolean inCheckMate(){
        //end of game
        //with more logic to go in here
        return this.isKingInCheck && !kingHasEscapeMove();
    }

    protected boolean kingHasEscapeMove() {
        for(Move move : this.legalMoves){
            TransitionMove transitionMove = makeMove(move);
            if (transitionMove.getMoveStatus().isDone());
        }
        return true;
    }

    public TransitionMove makeMove(Move move) {
        if(!isMoveLegal(move)){
            System.out.println("ILLEGAL MOVE");
            return new TransitionMove(this.board, move, MoveStatus.ILLEGAL_MOVE);
        }

        Board transitionBoard = move.execute();
        Collection<Move> attackMovesOnKing = calculateAttackMovesOnTile(transitionBoard.getCurrentPlayer().getOpponent().getPlayerKing().getPosition()
                , transitionBoard.getCurrentPlayer().getLegalMoves());

        if (!attackMovesOnKing.isEmpty()){
            System.out.println("players is in check");

            return new TransitionMove(this.board, move, MoveStatus.PLAYER_IS_IN_CHECK);
        }



        return new TransitionMove(transitionBoard, move, MoveStatus.DONE);

    }


    public boolean isKingCastled(){
        // did the king consume the castling option ?
        return false;
    }


    public boolean isMoveLegal(Move move){
        return this.legalMoves.contains(move);
    }


    public static Collection<Move> calculateAttackMovesOnTile(int piecePositionCoordinate , Collection<Move> moves ) {
        List<Move> attackMoves = new ArrayList<>();
        for (Move move : moves){
            if(piecePositionCoordinate == move.getDestinationCoordinate()){
                attackMoves.add(move);
            }
        }
        return attackMoves;
    }

    public abstract Collection<Piece> getActivePieces();

    public abstract PieceColor getSideColorOfPlayer();
    public abstract Player getOpponent();


    public abstract Collection<Move> calculateKingsCastles(Collection<Move> playerLegalMoves, Collection<Move> opponentLegalMoves);
}
