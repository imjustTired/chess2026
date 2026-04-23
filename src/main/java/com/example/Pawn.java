
//Mr. M -
//This is a pawn that follows regular pawn rules. It does not do "en passant" or promotion
//If you would like to implement these two things you will need to make some changes to 
//the "mouseReleased" section of your Board.java class. Don't forget to use "instanceof Pawn" if you'd like to 
//take up this challenge!
package com.example;

import java.util.ArrayList;
public class Pawn extends Piece{
	public Pawn(boolean color, String img) {
		super(color, img);
	}
	
	public ArrayList<Square> getLegalMoves(Board b, Square currentSquare) {
		ArrayList<Square> moves = new ArrayList<Square>();
		for(Square s: getControlledSquares(b.getSquareArray(), currentSquare)) {
			if(s.isOccupied() && s.getOccupyingPiece().getColor() != getColor()) {
				moves.add(s);
			}
		}
		if(getColor()) {
			if(currentSquare.getRow()-1>=0  && !b.getSquareArray()[currentSquare.getRow()-1][currentSquare.getCol()].isOccupied()) {
				moves.add(b.getSquareArray()[currentSquare.getRow()-1][currentSquare.getCol()]);
			}
			if(currentSquare.getRow() == 6 && !b.getSquareArray()[currentSquare.getRow()-1][currentSquare.getCol()].isOccupied() 
					&& !b.getSquareArray()[currentSquare.getRow()-2][currentSquare.getCol()].isOccupied()) {
				moves.add(b.getSquareArray()[currentSquare.getRow()-2][currentSquare.getCol()]);
			}
		}
		else {
			if(currentSquare.getRow()+1<8  && !b.getSquareArray()[currentSquare.getRow()+1][currentSquare.getCol()].isOccupied()) {
				moves.add(b.getSquareArray()[currentSquare.getRow()+1][currentSquare.getCol()]);
			}
			if(currentSquare.getRow() == 1 && !b.getSquareArray()[currentSquare.getRow()+1][currentSquare.getCol()].isOccupied() 
					&& !b.getSquareArray()[currentSquare.getRow()+2][currentSquare.getCol()].isOccupied()) {
				moves.add(b.getSquareArray()[currentSquare.getRow()+2][currentSquare.getCol()]);
			}
		}
		return moves;
	}
	
	
	public ArrayList<Square> getControlledSquares(Square[][] board, Square currentSquare) {
		ArrayList<Square> controlled = new ArrayList<Square>();
	
		int drow = getColor()? -1 : 1;
		if(currentSquare.getRow()+drow>=0 && currentSquare.getRow()+drow<8) {
			if(currentSquare.getCol()+1<8) {
				controlled.add(board[currentSquare.getRow()+drow][currentSquare.getCol()+1]);
			}
			if(currentSquare.getCol()-1>=0) {
				controlled.add(board[currentSquare.getRow()+drow][currentSquare.getCol()-1]);
			}
		}
		return controlled;
	}
	

}
