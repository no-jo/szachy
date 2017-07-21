package com.capgemini.chess.algorithms.chesspiecestests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.capgemini.chess.algorithms.chesspieces.Pawn;
import com.capgemini.chess.algorithms.chesspieces.Piece;
import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

public class PawnTest {
	
	Piece blackPawn = new Pawn(Color.BLACK);
	Piece whitePawn = new Pawn(Color.WHITE);

	@Test
	public void shouldPawnAttackCorrectly() throws InvalidMoveException {
		// given
		Coordinate from = new Coordinate(4, 6);
		Coordinate to = new Coordinate(4, 4);

		// when
		blackPawn.isAttackPossible(from, to);

		// then
		assertTrue(true);
	}
	
	@Test(expected = InvalidMoveException.class)
	public void shouldPawnNotAttackBackwards() throws InvalidMoveException {
		// given
		Coordinate from = new Coordinate(1, 2);
		Coordinate to = new Coordinate(1, 1);

		// when
		whitePawn.isAttackPossible(from, to);
		//then exception
	}
	
	@Test(expected = InvalidMoveException.class)
	public void shouldPawnNotAttackPerpendicular() throws InvalidMoveException {
		// given
		Coordinate from = new Coordinate(1, 2);
		Coordinate to = new Coordinate(0, 3);

		// when
		blackPawn.isAttackPossible(from, to);
		
		//then exception
	}

	@Test(expected = InvalidMoveException.class)
	public void shouldPawnNotMoveTooFar() throws InvalidMoveException {
		// given
		Coordinate from = new Coordinate(1, 2);
		Coordinate to = new Coordinate(1, 4);

		// when
		whitePawn.isAttackPossible(from, to);
		
		//then exception
	}

	@Test(expected = InvalidMoveException.class)
	public void shouldPawnNotCaptureAhead() throws InvalidMoveException {
		// given
		Coordinate from = new Coordinate(1, 2);
		Coordinate to = new Coordinate(1, 3);

		// when
		whitePawn.isCapturePossible(from, to);
		
		//then exception
	}

	@Test(expected = InvalidMoveException.class)
	public void shouldPawnNotCaptureBackwards() throws InvalidMoveException {
		// given
		Coordinate from = new Coordinate(5, 5);
		Coordinate to = new Coordinate(4, 4);

		// when
		whitePawn.isCapturePossible(from, to);
		
		//then exception
	}

}
