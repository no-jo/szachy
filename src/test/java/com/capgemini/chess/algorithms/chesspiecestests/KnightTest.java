package com.capgemini.chess.algorithms.chesspiecestests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.capgemini.chess.algorithms.chesspieces.Knight;
import com.capgemini.chess.algorithms.chesspieces.Piece;
import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

/**
 * @author JOANNANO
 * Class testing class knight (implementation of knight movements).
 */
public class KnightTest {
	
	Piece knight = new Knight(Color.WHITE);

	@Test
	public void shouldKnightMakeMoveCorrectly() throws InvalidMoveException {
		// given
		Coordinate from = new Coordinate(3, 4);
		Coordinate to = new Coordinate(2, 6);

		// when
		knight.isAttackPossible(from, to);
		knight.isCapturePossible(from, to);

		// then
		assertTrue(true);
	}
	
	@Test(expected = InvalidMoveException.class)
	public void shouldKnightNotCaptureDiagonally() throws InvalidMoveException {
		// given
		Coordinate from = new Coordinate(1, 1);
		Coordinate to = new Coordinate(3, 3);

		// when
		knight.isCapturePossible(from, to);

		// then
		assertTrue(true);
	}
	
	@Test(expected = InvalidMoveException.class)
	public void shouldKnighNotAttackDiagonally() throws InvalidMoveException {
		// given
		Coordinate from = new Coordinate(1, 1);
		Coordinate to = new Coordinate(3, 3);

		// when
		knight.isAttackPossible(from, to);

		// then
		assertTrue(true);
	}
}
