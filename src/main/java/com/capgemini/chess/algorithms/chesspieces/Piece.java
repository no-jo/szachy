package com.capgemini.chess.algorithms.chesspieces;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

/**
 * @author JOANNANO Abstract class describing general properties of chess
 *         pieces. Each child class has to implement specific movement dynamic.
 */
public abstract class Piece {

	final Color color;
	PieceType type = null;
	
	/**
	 * Sole constructor to ensure each piece has its type and color.
	 * 
	 * @param type
	 * @param color
	 */
	Piece(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	public PieceType getType() {
		return this.type;
	}

	/**
	 * Method checking if given attack move is consistent with figure's way of
	 * movement.
	 * 
	 * @param newMove
	 * @throws InvalidMoveException
	 */
	public abstract void isAttackPossible(Coordinate from, Coordinate to) throws InvalidMoveException;

	/**
	 * Method checking if given capture move is consistent with figure's way of
	 * movement.
	 * 
	 * @param newMove
	 * @throws InvalidMoveException
	 */
	public abstract void isCapturePossible(Coordinate from, Coordinate to) throws InvalidMoveException;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Piece other = (Piece) obj;
		if (color != other.color) {
			return false;
		}
		if (type != other.type) {
			return false;
		}
		return true;
	}

}
