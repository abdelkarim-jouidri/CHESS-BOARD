package com.chess.gui;

import com.chess.game.Board.BoardUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Table {
    private static  Dimension OUTER_FRAME_DIMENSION = new Dimension(600,600);
    private final JFrame chessGameFrame;
    private BoardPanel boardPanel;
    private static Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private static Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);
    public Table(){
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
            validate();
        }

        private void assignColorToTile() {
            
        }

    }


}
