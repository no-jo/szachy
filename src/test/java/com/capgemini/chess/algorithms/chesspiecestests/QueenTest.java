package com.capgemini.chess.algorithms.chesspiecestests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.capgemini.chess.algorithms.chesspieces.Piece;
import com.capgemini.chess.algorithms.chesspieces.Queen;
import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

public class QueenTest {
	
	Piece queen =  new Queen(Color.BLACK);

	@Test
	public void shouldQueenMoveSuccessfully() throws InvalidMoveException {
		// given
		Coordinate from = new Coordinate(5, 0);
		Coordinate to = new Coordinate(7, 2);

		// when
		queen.isAttackPossible(from, to);
		queen.isCapturePossible(from, to);

		// then
		assertTrue(true);
	}

	@Test(expected = InvalidMoveException.class)
	public void shouldQueenNotAttack() throws InvalidMoveException {
		// given
		Coordinate from = new Coordinate(5, 0);
		Coordinate to = new Coordinate(4, 2);

		// when
		queen.isAttackPossible(from, to);
		queen.isCapturePossible(from, to);

		// then expect exception
	}
	
	@Test(expected = InvalidMoveException.class)
	public void shouldQueenNotMove() throws InvalidMoveException {
		// given
		Coordinate from = new Coordinate(5, 0);
		Coordinate to = new Coordinate(6, 3);

		// when
		queen.isAttackPossible(from, to);
		queen.isCapturePossible(from, to);

		// then expect exception
	}

}
