package com.chess.game;

import com.chess.game.Board.Board;

public class JChess {
    public static void main(String[] args) {
        Board board = Board.createInitialBoard();
        System.out.println(board);
    }
}
