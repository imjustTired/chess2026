package com.example;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.*;

@SuppressWarnings("serial")
public class Board extends JPanel implements MouseListener, MouseMotionListener {

    // PRE-condition: image files exist at these paths
    // POST-condition: constants store resource paths for all piece images
    public static final String PICTURE_PATH = "/workspaces/chess/src/main/java/com/example/Pictures/";
    public static final String RESOURCES_WGOLEM_PNG = PICTURE_PATH + "New Piskel.png";
    public static final String RESOURCES_BGOLEM_PNG = PICTURE_PATH + "BGolem.png";
    private static final String RESOURCES_WBISHOP_PNG = PICTURE_PATH + "wbishop.png";
    private static final String RESOURCES_BBISHOP_PNG = PICTURE_PATH + "bbishop.png";
    private static final String RESOURCES_WKNIGHT_PNG = PICTURE_PATH + "wknight.png";
    private static final String RESOURCES_BKNIGHT_PNG = PICTURE_PATH + "bknight.png";
    private static final String RESOURCES_WROOK_PNG = PICTURE_PATH + "wrook.png";
    private static final String RESOURCES_BROOK_PNG = PICTURE_PATH + "brook.png";
    private static final String RESOURCES_WKING_PNG = PICTURE_PATH + "wking.png";
    private static final String RESOURCES_BKING_PNG = PICTURE_PATH + "bking.png";
    private static final String RESOURCES_BQUEEN_PNG = PICTURE_PATH + "bqueen.png";
    private static final String RESOURCES_WQUEEN_PNG = PICTURE_PATH + "wqueen.png";
    private static final String RESOURCES_WPAWN_PNG = PICTURE_PATH + "wpawn.png";
    private static final String RESOURCES_BPAWN_PNG = PICTURE_PATH + "bp.png";

    private final Square[][] board;
    private final GameWindow g;

    private boolean whiteTurn;

    Piece currPiece;
    private Square fromMoveSquare;

    private int currX;
    private int currY;

    // PRE-condition: GameWindow g is not null
    // POST-condition: Board is initialized with 8x8 grid, pieces placed, whiteTurn
    // = true
    public Board(GameWindow g) {
        this.g = g;
        board = new Square[8][8];
        setLayout(new GridLayout(8, 8, 0, 0));

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                boolean isWhiteSquare = (row + col) % 2 == 0;
                board[row][col] = new Square(this, isWhiteSquare, row, col);
                this.add(board[row][col]);
            }
        }

        initializePieces();

        this.setPreferredSize(new Dimension(400, 400));
        this.setMaximumSize(new Dimension(400, 400));
        this.setMinimumSize(this.getPreferredSize());
        this.setSize(new Dimension(400, 400));

        whiteTurn = true;
    }

    public void initializePieces() {

        // White Golem
        board[7][5].put(new Golem(true, RESOURCES_WGOLEM_PNG));

        // Black Golem
        board[0][5].put(new Golem(false, RESOURCES_BGOLEM_PNG));

        // white king
        board[7][4].put(new King(true, RESOURCES_WKING_PNG));

        // black king
        board[0][4].put(new King(false, RESOURCES_BKING_PNG));

        // white knight
        board[7][7].put(new Knight(true, RESOURCES_WKNIGHT_PNG));
        board[7][2].put(new Knight(true, RESOURCES_WKNIGHT_PNG));

        // black knight
        board[0][7].put(new Knight(false, RESOURCES_BKNIGHT_PNG));
        board[0][2].put(new Knight(false, RESOURCES_BKNIGHT_PNG));

        // white bishop
        board[7][6].put(new Bishop(true, RESOURCES_WBISHOP_PNG));
        board[7][3].put(new Bishop(true, RESOURCES_WBISHOP_PNG));

        // black bishop
        board[0][6].put(new Bishop(false, RESOURCES_BBISHOP_PNG));
        board[0][3].put(new Bishop(false, RESOURCES_BBISHOP_PNG));

        // white pawns
        for (int m = 0; m < 8; m++) {
            board[6][m].put(new Pawn(true, RESOURCES_WPAWN_PNG));
        }
        // black pawns
        for (int k = 0; k < 8; k++) {
            board[1][k].put(new Pawn(false, RESOURCES_BPAWN_PNG));
        }

    }

    public Square[][] getSquareArray() {
        return this.board;
    }

    public boolean getTurn() {
        return whiteTurn;
    }

    public void setCurrPiece(Piece p) {
        this.currPiece = p;
    }

    public Piece getCurrPiece() {
        return this.currPiece;
    }

    // PRE: board squares exist and may contain pieces
    // POST: board and dragged piece (if any) are drawn to screen
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Square sq = board[x][y];
                if (sq == fromMoveSquare)
                    sq.setBorder(BorderFactory.createLineBorder(Color.blue));
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); // paints Board + all Squares FIRST

        // NOW draw dragged piece on top
        if (currPiece != null) {
            Image img = currPiece.getImage();
            g.drawImage(img, currX - 24, currY - 24, 48, 48, null);
        }
    }

    public boolean isInCheck(boolean kingColor) {
        Square kingSquare = null;

        // Find the king's square
        for (Square[] row : board) {
            for (Square s : row) {
                if (s.isOccupied() && s.getOccupyingPiece() instanceof King &&
                        s.getOccupyingPiece().getColor() == kingColor) {
                    kingSquare = s;
                    break;
                }
            }
            if (kingSquare != null)
                break;
        }

        if (kingSquare == null)
            return false; // King not found, should not happen

        // Check if any enemy piece can move to the king's square
        for (Square[] row : board) {
            for (Square s : row) {
                if (s.isOccupied() && s.getOccupyingPiece().getColor() != kingColor) {
                    Piece enemyPiece = s.getOccupyingPiece();
                    if (enemyPiece.getLegalMoves(this, s).contains(kingSquare)) {
                        return true; // King is in check
                    }
                }
            }
        }

        return false; // King is not in check

    }

    // PRE-condition: user clicks within board
    // POST-condition: if valid piece selected (correct turn), legal moves are shown
    // and piece is readied for dragging
    @Override
    public void mousePressed(MouseEvent e) {
        currX = e.getX();
        currY = e.getY();

        Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        if (sq.isOccupied()) {
            Piece p = sq.getOccupyingPiece();

            if (whiteTurn != p.getColor())
                return;

            currPiece = p;
            fromMoveSquare = sq;

            for (Square s : currPiece.getLegalMoves(this, sq)) {
                s.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.RED));
            }

            sq.setDisplay(false);
        }
        repaint();
    }

    // PRE-condition: user releases mouse, piece may be selected
    // POST-condition: if move is legal, piece moves and turn switches; otherwise
    // piece returns to original square

    @Override
    public void mouseReleased(MouseEvent e) {

        if (fromMoveSquare != null)
            fromMoveSquare.setDisplay(true);

        for (Square[] row : board) {
            for (Square s : row) {
                s.setBorder(null);
            }
        }

        if (currPiece == null)
            return;

        Square endSquare = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));
        ArrayList<Square> legal = currPiece.getLegalMoves(this, fromMoveSquare);

        if (endSquare != null && legal.contains(endSquare)) {
             if(!(endSquare.isOccupied() && endSquare.getOccupyingPiece() instanceof Golem
             && (currPiece intanceof Pawn || currPiece instance of Knight))){

            Piece captured = endSquare.getOccupyingPiece();
            endSquare.put(currPiece);
            fromMoveSquare.put(null);

            if (isInCheck(whiteTurn)) {
                fromMoveSquare.put(currPiece);
                endSquare.put(captured);
            } else {
                whiteTurn = !whiteTurn;
                fromMoveSquare.setDisplay(true);
            }
        }

        }

        currPiece = null;
        repaint();
    }

    // PRE-condition: mouse is dragging while a piece is active
    // POST-condition: updates drag position and repaints board
    @Override
    public void mouseDragged(MouseEvent e) {
        currX = e.getX();
        currY = e.getY();
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
