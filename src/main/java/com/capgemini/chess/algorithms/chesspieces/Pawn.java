package com.capgemini.chess.algorithms.chesspieces;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

public class Pawn extends Piece {

	final PieceType type = PieceType.PAWN;
	
	public Pawn(Color color) {
		super(color);
	}
	
	public PieceType getType() {
		return this.type;
	}

	@Override
	public void isAttackPossible(Coordinate from, Coordinate to) throws InvalidMoveException {
		if (this.color.equals(Color.WHITE)) {
			if (!isWhiteAttackPossible(from, to))
				throw new InvalidMoveException("Invalid pawn attack");
		} else {
			if (!isBlackAttackPossible(from, to))
				throw new InvalidMoveException("Invalid pawn attack");
		}
	}

	@Override
	public void isCapturePossible(Coordinate from, Coordinate to) throws InvalidMoveException {
		if (this.color.equals(Color.WHITE)) {
			if (!isWhiteCapturePossible(from, to))
				throw new InvalidMoveException("Invalid pawn capture");
		} else {
			if (!isBlackCapturePossible(from, to))
				throw new InvalidMoveException("Invalid pawn capture");
		}

	}

	private boolean isBlackCapturePossible(Coordinate from, Coordinate to) {
		if (Math.abs(from.getX() - to.getX()) == 1 && from.getY() - to.getY() == 1)
			return true;
		else
			return false;
	}

	private boolean isWhiteCapturePossible(Coordinate from, Coordinate to) {
		if (Math.abs(from.getX() - to.getX()) == 1 && from.getY() - to.getY() == -1)
			return true;
		else
			return false;
	}

	private boolean isBlackAttackPossible(Coordinate from, Coordinate to) {
		if (from.getY() == 6 && to.getY() == 4 && Math.abs(from.getX() - to.getX()) == 0)
			return true;
		if (from.getX() - to.getX() == 0 && from.getY() - to.getY() == 1)
			return true;
		else
			return false;
	}

	private boolean isWhiteAttackPossible(Coordinate from, Coordinate to) {
		if (from.getY() == 1 && to.getY() == 3 && Math.abs(from.getX() - to.getX()) == 0)
			return true;
		if (from.getX() - to.getX() == 0 && from.getY() - to.getY() == -1)
			return true;
		else
			return false;
	}
}
