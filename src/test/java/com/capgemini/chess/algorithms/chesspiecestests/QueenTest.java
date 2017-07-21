package com.capgemini.chess.algorithms.chesspiecestests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.capgemini.chess.algorithms.chesspieces.King;
import com.capgemini.chess.algorithms.chesspieces.Pawn;
import com.capgemini.chess.algorithms.chesspieces.Queen;
import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.BoardManager;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

public class QueenTest {

	@Test
	public void shouldQueenCaptureSuccessfully() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.setPieceAt(new Queen(Color.WHITE), new Coordinate(5, 0));
		board.setPieceAt(new Pawn(Color.BLACK), new Coordinate(7, 2));
		board.setPieceAt(new King(Color.WHITE), new Coordinate(0, 0));
		BoardManager boardManager = new BoardManager(board);

		// when
		Move move = boardManager.performMove(new Coordinate(5, 0), new Coordinate(7, 2));

		// then
		assertEquals(MoveType.CAPTURE, move.getType());
		assertEquals(new Queen(Color.WHITE), move.getMovedPiece());
	}

	@Test(expected = InvalidMoveException.class)
	public void shouldQueenNotAttack() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.setPieceAt(new Queen(Color.WHITE), new Coordinate(5, 0));
		BoardManager boardManager = new BoardManager(board);

		// when
		boardManager.performMove(new Coordinate(5, 0), new Coordinate(4, 2));

		// then expect exception
	}

}
