package com.chess.gui;

import com.chess.game.Board.Board;
import com.chess.game.Board.BoardUtils;
import com.chess.game.Pieces.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Table {
    private final Color lightTileColor = Color.decode("#f0dab5");
    private final Color darkTileColor = Color.decode("#b58763");
    private static  Dimension OUTER_FRAME_DIMENSION = new Dimension(600,600);
    private final JFrame chessGameFrame;
    private Board chessBoard;
    private BoardPanel boardPanel;
    private static Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private static Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);
    private static String piecesIconsPath = "C:\\Users\\Youcode\\Desktop\\Java\\Chess\\chess_pieces\\";
    public Table(){
        this.chessBoard = Board.createInitialBoard();

        this.chessGameFrame = new JFrame("CHESS");
        this.chessGameFrame.setLayout(new BorderLayout());
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
    }

    private class TilePanel extends JPanel{
        private int tileId;

        TilePanel(BoardPanel boardPanel, int tileId){
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignColorToTile();
            assignTilePieceIcon(chessBoard);
            validate();
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
                            System.out.println("path: " + piecesIconsPath + piece.getPieceColor().toString().substring(0, 1) + piece.toString() + ".png");
                            BufferedImage pieceImage = ImageIO.read(new File("chess_pieces\\resized\\" + piece.getPieceColor().toString().substring(0, 1) + piece.toString() + ".png"));
                            JLabel pieceLabel = new JLabel(new ImageIcon(pieceImage));
                            add(pieceLabel);
                            System.out.println("Piece icon assigned successfully");

                            // Repaint the frame to reflect the changes
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


    }


}
