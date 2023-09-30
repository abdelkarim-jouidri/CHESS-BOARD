package com.chess.game.Board;

import com.chess.game.PieceColor;
import com.chess.game.Pieces.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Board {

    private List<Tile> gameBoard;

    private Collection<Piece> activeWhitePieces;
    private Collection<Piece> activeBlackPieces;
    private Board(Builder builder){
        this.gameBoard = createGameBoard(builder);
        this.activeWhitePieces = calculateActivePieces( this.gameBoard, PieceColor.WHITE  );
    }

    private static Collection<Piece> calculateActivePieces(List<Tile> gameboard , PieceColor pieceColor){
        List<Piece> activePieces = new ArrayList<>();
            for (Tile tile : gameboard){
                if(tile.isTileOccupied()){
                    Piece pieceOnTile = tile.getPiece();
                    if(pieceOnTile.getPieceColor() == pieceColor){
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
        return null;
    }


    public static class  Builder {
        Map<Integer, Piece> boardConfiguration;
        PieceColor nextMovePlayer;

        public Builder() {
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
