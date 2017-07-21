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

/**
 * @author JOANNANO
 * Class testing class knight (implementation of knight movements).
 */
public class KnightTest {

	@Test
	public void shouldKnightMakeMoveCorrectly() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.getMoveHistory().add(createDummyMove(board));
		board.setPieceAt(new Knight(Color.BLACK), new Coordinate(3, 4));
		board.setPieceAt(new Rook(Color.WHITE), new Coordinate(2, 6));
		board.setPieceAt(new King(Color.BLACK), new Coordinate(7, 7));

		// when
		BoardManager boardManager = new BoardManager(board);
		Move move = boardManager.performMove(new Coordinate(3, 4), new Coordinate(2, 6));

		// then
		assertEquals(MoveType.CAPTURE, move.getType());
		assertEquals(new Knight(Color.BLACK), move.getMovedPiece());
		assertEquals(boardManager.getBoard().getPieceAt(new Coordinate(2, 6)), new Knight(Color.BLACK));
	}
	
	@Test(expected = InvalidMoveException.class)
	public void shouldKnightDeclineMove() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.setPieceAt(new Knight(Color.WHITE), new Coordinate(1, 1));
		BoardManager boardManager = new BoardManager(board);

		// when
		boardManager.performMove(new Coordinate(1, 1), new Coordinate(3, 3));

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
