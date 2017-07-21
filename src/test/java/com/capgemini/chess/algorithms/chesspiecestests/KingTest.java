package com.capgemini.chess.algorithms.chesspiecestests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.capgemini.chess.algorithms.chesspieces.King;
import com.capgemini.chess.algorithms.chesspieces.Piece;
import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

/**
 * @author JOANNANO Class to test class King (implementation of king movements).
 */
public class KingTest {

	Piece king = new King(Color.WHITE);

	@Test(expected = InvalidMoveException.class)
	public void shouldKingDeclineMoveByTwo() throws InvalidMoveException {
		// given
		Coordinate from = new Coordinate(4, 0);
		Coordinate to = new Coordinate(4, 2);

		// when
		king.isAttackPossible(from, to);

		// then exception
	}

	@Test
	public void shouldKingMoveCorrectlyMoveByOne() throws InvalidMoveException {
		// given
		Coordinate from = new Coordinate(4, 0);
		Coordinate to = new Coordinate(4, 1);

		// when
		king.isAttackPossible(from, to);
		king.isCapturePossible(from, to);

		// then
		assertTrue(true);
	}

	@Test (expected = InvalidMoveException.class)
	public void shouldKingNotCaptureInMoveMoreThanOne() throws InvalidMoveException {
		// given
		Coordinate from = new Coordinate(4, 0);
		Coordinate to = new Coordinate(6, 1);

		// when
		king.isCapturePossible(from, to);

		// then exception
	}

}
