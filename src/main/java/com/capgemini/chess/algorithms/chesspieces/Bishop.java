package com.capgemini.chess.algorithms.chesspieces;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

public class Bishop extends Piece {
	
	final PieceType type = PieceType.BISHOP;

	public Bishop(Color color) {
		super(color);
	}
	
	public PieceType getType() {
		return this.type;
	}

	@Override
	public void isAttackPossible(Coordinate from, Coordinate to) throws InvalidMoveException {
		if (!isMovePossible (from, to))
			throw new InvalidMoveException("Bishop illegal attack");

	}

	@Override
	public void isCapturePossible(Coordinate from, Coordinate to) throws InvalidMoveException {
		if (!isMovePossible (from, to))
			throw new InvalidMoveException("Bishop illegal capture");

	}

	private boolean isMovePossible(Coordinate from, Coordinate to) {
		if (Math.abs(from.getX() - to.getX()) == Math.abs(from.getY() - to.getY()))
			return true;
		else
			return false;
	}
}
