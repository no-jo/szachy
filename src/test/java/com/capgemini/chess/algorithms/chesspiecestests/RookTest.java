package com.capgemini.chess.algorithms.chesspiecestests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.capgemini.chess.algorithms.chesspieces.King;
import com.capgemini.chess.algorithms.chesspieces.Knight;
import com.capgemini.chess.algorithms.chesspieces.Rook;
import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.BoardManager;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

public class RookTest {

	@Test
	public void testPerformMoveRookCapture() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.getMoveHistory().add(createDummyMove(board));
		board.setPieceAt(new Rook(Color.BLACK), new Coordinate(1, 4));
		board.setPieceAt(new Knight(Color.WHITE), new Coordinate(5, 4));
		board.setPieceAt(new King(Color.BLACK), new Coordinate(7, 7));
		BoardManager boardManager = new BoardManager(board);

		// when
		Move move = boardManager.performMove(new Coordinate(1, 4), new Coordinate(5, 4));

		// then
		assertEquals(MoveType.CAPTURE, move.getType());
		assertEquals(new Rook(Color.BLACK), move.getMovedPiece());
	}

	@Test
	public void testPerformMoveRookAttack() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.getMoveHistory().add(createDummyMove(board));
		board.setPieceAt(new Rook(Color.BLACK), new Coordinate(1, 4));
		board.setPieceAt(new King(Color.BLACK), new Coordinate(7, 7));
		BoardManager boardManager = new BoardManager(board);
		Coordinate to = new Coordinate(5, 4);

		// when
		Move move = boardManager.performMove(new Coordinate(1, 4), to);

		// then
		assertEquals(new Rook(Color.BLACK), boardManager.getBoard().getPieceAt(to));
		assertEquals(MoveType.ATTACK, move.getType());
	}
	
	@Test (expected = InvalidMoveException.class)
	public void shouldRookNotCaptureLikeBishop() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.getMoveHistory().add(createDummyMove(board));
		board.setPieceAt(new Rook(Color.BLACK), new Coordinate(1, 4));
		board.setPieceAt(new Knight(Color.WHITE), new Coordinate(3, 5));
		board.setPieceAt(new King(Color.BLACK), new Coordinate(7, 7));
		BoardManager boardManager = new BoardManager(board);

		// when
		boardManager.performMove(new Coordinate(1, 4), new Coordinate(3, 5));

		// then exception
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
