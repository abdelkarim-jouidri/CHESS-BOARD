package com.chess.game.Board;

import com.chess.game.PieceColor;
import com.chess.game.Pieces.*;
import com.chess.game.Player.BlackSidePlayer;
import com.chess.game.Player.WhiteSidePlayer;

import java.text.CollationElementIterator;
import java.util.*;

public class Board {

    private static List<Tile> gameBoard;

    private Collection<Piece> activeWhitePieces;
    private WhiteSidePlayer whiteSidePlayer;
    private BlackSidePlayer blackSidePlayer;
    private Collection<Piece> activeBlackPieces;
    private Board(Builder builder){
        this.gameBoard = createGameBoard(builder);
        this.activeWhitePieces = calculateActivePieces( this.gameBoard, PieceColor.WHITE  );
        this.activeBlackPieces = calculateActivePieces( this.gameBoard, PieceColor.WHITE  );
        List<Move> whitePiecesLegalMoves = calculateLegalMoves(this.activeWhitePieces);
        List<Move> blackPiecesLegalMoves = calculateLegalMoves(this.activeBlackPieces);
        this.whiteSidePlayer = new WhiteSidePlayer(this, whitePiecesLegalMoves, blackPiecesLegalMoves);
        this.blackSidePlayer = new BlackSidePlayer(this, blackPiecesLegalMoves, whitePiecesLegalMoves);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0 ; i<BoardUtils.NUM_TILES ; i++){
            String tileText = this.gameBoard.get(i).toString();
            stringBuilder.append(String.format("%3s", tileText));
            if((i+1)%BoardUtils.NUM_TILES_PER_ROW == 0){
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    public Collection<Piece> getActiveWhitePieces(){
        return this.activeWhitePieces;
    }

    public Collection<Piece> getActiveBlackPieces(){
        return this.activeBlackPieces;
    }



    private List<Move> calculateLegalMoves(Collection<Piece> pieces) {
        List<Move> legalMoves = new ArrayList<>();
        for (Piece piece : pieces){
            legalMoves.addAll(piece.calculateLegalMoves(this));
        }

        return legalMoves;
    }

    private static Collection<Piece> calculateActivePieces(List<Tile> gameboard , PieceColor pieceColor){
        List<Piece> activePieces = new ArrayList<>();
            for (Tile tile : gameboard){
                if(tile.isTileOccupied()){
                    Piece pieceOnTile = tile.getPiece();
                    if(pieceOnTile != null && pieceOnTile.getPieceColor() == pieceColor){
                        activePieces.add(pieceOnTile);
                    }
                }
            }
            return activePieces;
    }

    public static Board createInitialBoard(){
        Builder builder = new Builder();
        //black pieces
        builder.Piece(new Rook(0, PieceColor.BLACK));
        builder.Piece(new Knight(1, PieceColor.BLACK));
        builder.Piece(new Bishop(2, PieceColor.BLACK));
        builder.Piece(new Queen(3, PieceColor.BLACK));
        builder.Piece(new King(4, PieceColor.BLACK));
        builder.Piece(new Bishop(5, PieceColor.BLACK));
        builder.Piece(new Knight(6, PieceColor.BLACK));
        builder.Piece(new Rook(7, PieceColor.BLACK));
        for (int i = 8; i<16 ; i++){
            builder.Piece(new Pawn(i, PieceColor.BLACK));
        }

        //white pieces
        for (int i = 48; i<56 ; i++){
            builder.Piece(new Pawn(i, PieceColor.WHITE));
        }
        builder.Piece(new Rook(56, PieceColor.WHITE));
        builder.Piece(new Knight(57, PieceColor.WHITE));
        builder.Piece(new Bishop(58, PieceColor.WHITE));
        builder.Piece(new Queen(59, PieceColor.WHITE));
        builder.Piece(new King(60, PieceColor.WHITE));
        builder.Piece(new Bishop(61, PieceColor.WHITE));
        builder.Piece(new Knight(62, PieceColor.WHITE));
        builder.Piece(new Rook(63, PieceColor.WHITE));

        //the game should start on the white side
        builder.nextMovePlayer(PieceColor.WHITE);
        return builder.build();
    }

    private static List<Tile> createGameBoard(Builder builder){

        Tile[] tiles = new Tile[BoardUtils.NUM_TILES];
        for (int i = 0 ; i<BoardUtils.NUM_TILES ; i++){
            tiles[i] = Tile.createTile(i, builder.boardConfiguration.get(i));
        }
    return List.of(tiles);
    }
    public static Tile getTile(int tilecoordinate){
        return gameBoard.get(tilecoordinate);
    }


    public static class  Builder {
        Map<Integer, Piece> boardConfiguration;
        PieceColor nextMovePlayer;

        public Builder() {
            this.boardConfiguration = new HashMap<>();
        }

        public Builder Piece(Piece piece){
            this.boardConfiguration.put(piece.getPosition(), piece);
            return this;
        }

        public Builder nextMovePlayer(PieceColor nextMovePlayer){
            this.nextMovePlayer = nextMovePlayer;
            return this;
        }

        public Board build(){
            return new Board(this);
        }

    }


}
