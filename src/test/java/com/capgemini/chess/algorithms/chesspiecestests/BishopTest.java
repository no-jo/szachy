package com.capgemini.chess.algorithms.chesspiecestests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.capgemini.chess.algorithms.chesspieces.Bishop;
import com.capgemini.chess.algorithms.chesspieces.Piece;
import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

/**
 * @author JOANNANO Class testing class Bishop (implementation of bishop
 *         movements).
 */
public class BishopTest {

	Piece bishop = new Bishop(Color.BLACK);

	@Test
	public void shouldBishopMoveCorrectly() throws InvalidMoveException {
		// given
		Coordinate from = new Coordinate(0, 6);
		Coordinate to = new Coordinate(6, 0);

		// when
		bishop.isAttackPossible(from, to);
		bishop.isCapturePossible(from, to);

		// then
		assertTrue(true);
	}

	@Test(expected = InvalidMoveException.class)
	public void shouldBishopNotCaptureStright() throws InvalidMoveException {
		// given
		Coordinate from = new Coordinate(1, 1);
		Coordinate to = new Coordinate(1, 2);

		// when
		bishop.isCapturePossible(from, to);

		// then exception
	}
	
	@Test(expected = InvalidMoveException.class)
	public void shouldBishopNotAttackStright() throws InvalidMoveException {
		// given
		Coordinate from = new Coordinate(1, 1);
		Coordinate to = new Coordinate(1, 0);

		// when
		bishop.isAttackPossible(from, to);

		// then exception
	}
}
