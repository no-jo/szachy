package com.capgemini.chess.algorithms.chesspiecestests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.capgemini.chess.algorithms.chesspieces.King;
import com.capgemini.chess.algorithms.chesspieces.Pawn;
import com.capgemini.chess.algorithms.chesspieces.Rook;
import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.BoardManager;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

public class PawnTest {

	@Test
	public void shouldPawnAttackCorrectly() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.getMoveHistory().add(createDummyMove(board));
		board.setPieceAt(new Pawn(Color.BLACK), new Coordinate(4, 6));
		board.setPieceAt(new King(Color.BLACK), new Coordinate(7, 7));
		BoardManager boardManager = new BoardManager(board);

		// when
		Move move = boardManager.performMove(new Coordinate(4, 6), new Coordinate(4, 4));

		// then
		assertEquals(MoveType.ATTACK, move.getType());
		assertEquals(new Pawn(Color.BLACK), move.getMovedPiece());
	}
	
	@Test(expected = InvalidMoveException.class)
	public void shouldPawnNotAttackBackwards() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.setPieceAt(new Pawn(Color.WHITE), new Coordinate(1, 2));
		BoardManager boardManager = new BoardManager(board);

		// when
		boardManager.performMove(new Coordinate(1, 2), new Coordinate(1, 1));
		
		//then exception
	}
	
	@Test(expected = InvalidMoveException.class)
	public void shouldPawnNotAttackPerpendicular() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.setPieceAt(new Pawn(Color.WHITE), new Coordinate(1, 2));
		BoardManager boardManager = new BoardManager(board);

		// when
		boardManager.performMove(new Coordinate(1, 2), new Coordinate(0, 3));
		
		//then exception
	}

	@Test(expected = InvalidMoveException.class)
	public void shouldPawnNotMoveTooFar() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.setPieceAt(new Pawn(Color.WHITE), new Coordinate(1, 2));
		BoardManager boardManager = new BoardManager(board);

		// when
		boardManager.performMove(new Coordinate(1, 2), new Coordinate(1, 4));
		
		//then exception
	}

	@Test(expected = InvalidMoveException.class)
	public void shouldPawnNotCaptureAhead() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.setPieceAt(new Pawn(Color.WHITE), new Coordinate(1, 2));
		board.setPieceAt(new Pawn(Color.BLACK), new Coordinate(1, 3));
		BoardManager boardManager = new BoardManager(board);

		// when
		boardManager.performMove(new Coordinate(1, 2), new Coordinate(1, 3));
		
		//then exception
	}

	@Test(expected = InvalidMoveException.class)
	public void shouldPawnNotCaptureBackwards() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.setPieceAt(new Pawn(Color.WHITE), new Coordinate(2, 4));
		board.setPieceAt(new Pawn(Color.BLACK), new Coordinate(1, 3));
		BoardManager boardManager = new BoardManager(board);

		// when
		boardManager.performMove(new Coordinate(1, 2), new Coordinate(1, 3));
		
		//then exception
	}
	
	private Move createDummyMove(Board board) {

		Move move = new Move();

		if (board.getMoveHistory().size() % 2 == 0) {
			board.setPieceAt(new Rook(Color.WHITE), new Coordinate(0, 0));
			move.setMovedPiece(new Rook(Color.WHITE));
		} else {
			board.setPieceAt(new Rook(Color.BLACK), new Coordinate(0, 0));
			move.setMovedPiece(new Rook(Color.BLACK));
		}
		move.setFrom(new Coordinate(0, 0));
		move.setTo(new Coordinate(0, 0));
		move.setType(MoveType.ATTACK);
		board.setPieceAt(null, new Coordinate(0, 0));
		return move;
	}

}
