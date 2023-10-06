package com.chess.gui;

import com.chess.game.Board.Board;
import com.chess.game.Board.BoardUtils;
import com.chess.game.Board.Move;
import com.chess.game.Board.Tile;
import com.chess.game.Pieces.Piece;
import com.chess.game.Player.TransitionMove;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Table {
    private final Color lightTileColor = Color.decode("#f0dab5");
    private final Color darkTileColor = Color.decode("#b58763");

    private Tile sourceTile;
    private Tile destinationTile;
    private Piece humanMovedPiece;
    private static  Dimension OUTER_FRAME_DIMENSION = new Dimension(600,600);
    private final JFrame chessGameFrame;
    private Board chessBoard;
    private BoardPanel boardPanel;
    private static Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private static Dimension TILE_PANEL_DIMENSION = new Dimension(5, 5);
    private static String piecesIconsPath = "chess_pieces/resized/";
    public Table(){
        this.chessBoard = Board.createInitialBoard();

        this.chessGameFrame = new JFrame("CHESS");
        this.chessGameFrame.setLayout(new BorderLayout());
        this.chessGameFrame.setLocationRelativeTo(null);
        this.chessGameFrame.setVisible(true);
        this.chessGameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.boardPanel = new BoardPanel();
        this.chessGameFrame.add(this.boardPanel, BorderLayout.CENTER);
    }

    private class BoardPanel extends JPanel {
        List<TilePanel> boardTiles;
        BoardPanel(){
            super(new GridLayout(8,8));
            this.boardTiles = new ArrayList<>();
            for (int i=0 ; i< BoardUtils.NUM_TILES ; i++){
                TilePanel tilePanel = new TilePanel(this, i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
        }

        public void drawBoard(Board board){
            removeAll();
            for(TilePanel tilePanel : boardTiles){
                tilePanel.drawTile(board);
                add(tilePanel);
            }
            validate();
            repaint();
        }
    }

    private class TilePanel extends JPanel{
        private int tileId;

        TilePanel(BoardPanel boardPanel, int tileId){
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignColorToTile();
            assignTilePieceIcon(chessBoard);
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(isRightMouseButton(e)){
                        sourceTile = null;
                        destinationTile = null;
                        humanMovedPiece = null;
                    }
                    else if(isLeftMouseButton(e)){

                        System.out.println("clicked the left");
                        if(sourceTile == null){
                            System.out.println("source tile is null");
                            sourceTile = chessBoard.getTile(tileId);
                            humanMovedPiece = sourceTile.getPiece();
                            if(humanMovedPiece == null){
                                sourceTile = null;
                            }
                        }
                        else {
                            destinationTile = chessBoard.getTile(tileId);
                            Move move = Move.MoveFactory.createMove(chessBoard, sourceTile.getTileCoordinate(), destinationTile.getTileCoordinate());
                            TransitionMove transitionMove = chessBoard.getCurrentPlayer().makeMove(move);
                            if (transitionMove.getMoveStatus().isDone()){
                                chessBoard = transitionMove.getTransitionBoard();
                                System.out.println(chessBoard.getTile(destinationTile.getTileCoordinate()).getPiece().getPosition());
                                }
                            if(!transitionMove.getMoveStatus().isDone()){
                                System.out.println("the move wasnt done");
                            }
                            sourceTile = null;
                            destinationTile = null;
                            humanMovedPiece = null;
                        }
                        SwingUtilities.invokeLater(()->boardPanel.drawBoard(chessBoard));
                    }

                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });


            validate();
        }

        public void drawTile(Board board){
            assignColorToTile();
            assignTilePieceIcon(board);
            highlightLegalMoves(board);
            validate();
            repaint();
        }

        private void assignColorToTile() {
            if(BoardUtils.FIRST_ROW[tileId] ||
                    BoardUtils.THIRD_ROW[tileId] ||
                    BoardUtils.FIFTH_ROW[tileId] ||
                    BoardUtils.SEVENTH_ROW[tileId]){
                setBackground(tileId%2 == 0 ? lightTileColor : darkTileColor);
            }
            if(BoardUtils.SECOND_ROW[tileId] ||
                    BoardUtils.FOURTH_ROW[tileId] ||
                    BoardUtils.SIXTH_ROW[tileId] ||
                    BoardUtils.EIGHTH_ROW[tileId]){
                setBackground(tileId%2 != 0 ? lightTileColor : darkTileColor);
            }
        }
        private void assignTilePieceIcon(Board board) {
            this.removeAll();
            if (board.getTile(this.tileId).isTileOccupied()) {
                Piece piece = board.getTile(tileId).getPiece();
                try {
                    SwingUtilities.invokeLater(() -> {
                        try {
                            BufferedImage pieceImage = ImageIO.read(new File(piecesIconsPath + piece.getPieceColor().toString().substring(0, 1) + piece.toString() + ".png"));
                            JLabel pieceLabel = new JLabel(new ImageIcon(pieceImage));
                            add(pieceLabel);
                            chessGameFrame.revalidate();
                            chessGameFrame.repaint();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private void highlightLegalMoves(Board board){
            for(Move move : pieceLegalMoves(board)){
                if(move.getDestinationCoordinate() == tileId){
                    try {
                        add(new JLabel(new ImageIcon(ImageIO.read(new File("chess_pieces/dot/green_dot.png")))));
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }

        private Collection<Move> pieceLegalMoves(Board board) {
            if(humanMovedPiece !=null && humanMovedPiece.getPieceColor() == board.getCurrentPlayer().getSideColorOfPlayer()){
                return humanMovedPiece.calculateLegalMoves(board);
            }
            return Collections.emptyList();
        }


    }


}
