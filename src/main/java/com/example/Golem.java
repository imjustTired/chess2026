 // Name: Rodwell Alfred
 // Moves: Golem moves just like a king however it can't be take by knights or pawns
package com.example;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

//you will need to implement two functions in this file.
public class Golem extends Piece {

    
    public Golem(boolean isWhite, String img_file) {
        super(isWhite, img_file);

    }
    
    
    
    
    // TO BE IMPLEMENTED!
    //return a list of every square that is "controlled" by this piece. A square is controlled
    //if the piece capture into it legally.
    // PRE: start is a valid square on the board and board is an 8x8 array
    // POST: returns all squares this piece could capture into (controlled squares) without going off the board
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
        ArrayList<Square> moves = new ArrayList<Square>();
        for(int row = start.getRow()-1; row<start.getRow()+2 && row<=7; row++){
            for(int col = start.getCol()-1; col<start.getCol()+2 && col<=7; col++){
                if(row>=0 && col>=0){
                    Square move = board[row][col];
                    if (!move.isOccupied()){
                        moves.add(move);
                    }
                }
            }
        }

         return moves;
    }
    

    //TO BE IMPLEMENTED!
    //implement the move function here
    //it's up to you how the piece moves, but at the very least the rules should be logical and it should never move off the board!
    //returns an arraylist of squares which are legal to move to
    //please note that your piece must have some sort of logic. Just being able to move to every square on the board is not
    //going to score any points.
    // PRE: start is a valid square containing this piece, and board is initialized
    // POST: returns all valid squares this piece can legally move to (no out-of-bounds, and does not include squares occupied by same-color pieces)
    public ArrayList<Square> getLegalMoves(Board b, Square start){
        ArrayList<Square> moves = new ArrayList<Square>();
        for(int row = start.getRow()-1; row<start.getRow()+2 && row<=7; row++){
            for(int col = start.getCol()-1; col<start.getCol()+2 && col<=7; col++){
                if(row>=0 && col>=0){
                    Square move = b.getSquareArray()[row][col];
                    if (!move.isOccupied()){
                        moves.add(move);
                    }
                }
            }
        }

         return moves;
    }
        
    public String toString(){
    String color;

    if (this.getColor() == true){
        color = "White";
    } else {
        color = "Black";
    }

    return color + " Golem";
}
           
}