package com.capgemini.chess.algorithms.chesspieces;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

/**
 * @author JOANNANO
 * Class implementing specific movements of King chess piece.
 */
public class King extends Piece {
	
	final PieceType type = PieceType.KING;

	/**
	 * Sole constructor to ensure piece always has its
	 * @param color
	 */
	public King(Color color) {
		super(color);
	}
	
	public PieceType getType() {
		return this.type;
	}

	@Override
	public void isAttackPossible(Coordinate from, Coordinate to) throws InvalidMoveException {		
		if (!isMovePossible (from, to))
			throw new InvalidMoveException("King tried to attack too far");
	}

	@Override
	public void isCapturePossible(Coordinate from, Coordinate to) throws InvalidMoveException {
		if (!isMovePossible (from, to))
			throw new InvalidMoveException("King tried to capture too far");		
	}

	private boolean isMovePossible(Coordinate from, Coordinate to) throws InvalidMoveException {	
		if (Math.abs(from.getX() - to.getX()) <= 1 && Math.abs(from.getY() - to.getY()) <= 1)
			return true;
		else
			return false;
	}
}
