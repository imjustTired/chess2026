package com.example;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

//you will need to implement two functions in this file.
public class Pawn extends Piece{
    private String imgFilePath;
    
    public Pawn(boolean isWhite, String img_file) {
        super(isWhite , img_file);     
      
    }
    @Override
    public String toString() {
        return "A" + super.toString() + " pawn";
    }
    
    public boolean isKing() {
        return imgFilePath.contains("king");
    }
    
    
    
    //precondition: g and currentSquare must be on-null valid objects.
    //postcondition: the image stored in the img property of this object is drawn to the screen.
    public void draw(Graphics g, Square currentSquare) {
        int x = currentSquare.getX();
        int y = currentSquare.getY();
        
        g.drawImage(this.img, x, y, null);
    }
    
    
    // TO BE IMPLEMENTED!
    //return a list of every square that is "controlled" by this piece. A square is controlled
    //if the piece capture into it legally.
    @Override
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
     ArrayList<Square> controlled = new ArrayList<>();
     int r = start.getRow();
     int c = start.getCol();
     // pawns control vertically forward one square
     int dir;
     if (this.color) {
         dir = -1; // white moves up (decreasing row)
     } else {
         dir = 1;  // black moves down
     }

     int dr = r + dir;
     if (dr >= 0 && dr < 8) {
         controlled.add(board[dr][c]);
     }

     return controlled;
    }
    

    //TO BE IMPLEMENTED!
    //implement the move function here
    //it's up to you how the piece moves, but at the very least the rules should be logical and it should never move off the board!
    //returns an arraylist of squares which are legal to move to
    //please note that your piece must have some sort of logic. Just being able to move to every square on the board is not
    //going to score any points.
    @Override
    public ArrayList<Square> getLegalMoves(Board b, Square start){
    	ArrayList<Square> moves = new ArrayList<>();
    	Square[][] board = b.getSquareArray();
    	int r = start.getRow();
    	int c = start.getCol();
    	int dir;
    	if (this.color) {
    		dir = -1; // white moves up
    	} else {
    		dir = 1;  // black moves down
    	}

// forward one or two squares
	int f1 = r + dir;
	int f2 = r + 2 * dir;
	// one square forward if empty
	if (f1 >= 0 && f1 < 8 && !board[f1][c].isOccupied()) {
		moves.add(board[f1][c]);
		// two squares forward only if first is empty and second is empty
		if (f2 >= 0 && f2 < 8 && !board[f2][c].isOccupied()) {
			moves.add(board[f2][c]);
		}
	}

	// Capture vertically forward one square (can eat king too)
    	if (f1 >= 0 && f1 < 8) {
    		if (board[f1][c].isOccupied() && board[f1][c].getOccupyingPiece().getColor() != this.color) {
    			moves.add(board[f1][c]);
    		}
    	}

    	return moves;
    }
}
