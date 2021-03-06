package com.capgemini.chess.algorithms.chesspieces;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

public class Knight extends Piece {

	final PieceType type = PieceType.KNIGHT;

	public Knight(Color color) {
		super(color);
	}
	
	public PieceType getType() {
		return this.type;
	}

	@Override
	public void isAttackPossible(Coordinate from, Coordinate to) throws InvalidMoveException {
		if (!isMovePossible (from, to))
			throw new InvalidMoveException("Knight illegal attack");

	}

	@Override
	public void isCapturePossible(Coordinate from, Coordinate to) throws InvalidMoveException {
		if (!isMovePossible (from, to))
			throw new InvalidMoveException("Knight illegal capture");

	}

	private boolean isMovePossible(Coordinate from, Coordinate to) {
		if (Math.abs(from.getX() - to.getX()) == 2 && Math.abs(from.getY() - to.getY()) == 1)
			return true;
		if (Math.abs(from.getX() - to.getX()) == 1 && Math.abs(from.getY() - to.getY()) == 2)
			return true;
		else
			return false;
	}
}
