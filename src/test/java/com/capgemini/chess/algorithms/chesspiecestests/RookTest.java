package com.capgemini.chess.algorithms.chesspiecestests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.capgemini.chess.algorithms.chesspieces.Piece;
import com.capgemini.chess.algorithms.chesspieces.Rook;
import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

public class RookTest {
	
	Piece rook = new Rook(Color.BLACK);

	@Test
	public void shouldRookMoveStright() throws InvalidMoveException {
		// given
		Coordinate from = new Coordinate(1, 4);
		Coordinate to = new Coordinate(5, 4);

		// when
		rook.isAttackPossible(from, to);
		rook.isCapturePossible(from, to);

		// then
		assertTrue(true);
	}
	
	@Test (expected = InvalidMoveException.class)
	public void shouldRookNotCaptureDiagonally() throws InvalidMoveException {
		// given
		Coordinate from = new Coordinate(1, 4);
		Coordinate to = new Coordinate(3, 6);

		// when
		rook.isCapturePossible(from, to);

		// then exception
	}
	
	@Test (expected = InvalidMoveException.class)
	public void shouldRookNotAttackDiagonally() throws InvalidMoveException {
		// given
		Coordinate from = new Coordinate(3, 6);
		Coordinate to = new Coordinate(1, 4);

		// when
		rook.isAttackPossible(from, to);

		// then exception
	}
}
