package com.capgemini.chess.algorithms.chesspiecestests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.capgemini.chess.algorithms.chesspieces.King;
import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.BoardManager;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

/**
 * @author JOANNANO
 * Class testing class King (implementation of king movements).
 */
public class KingTest {
	
	Board board = new Board();
	BoardManager boardManager = null;

	@Before
	public void setUp() throws Exception {

	}

	@Test(expected = InvalidMoveException.class)
	public void shouldKingDeclineMoveByTwo() throws InvalidMoveException {
		// given
		board.setPieceAt(new King(Color.WHITE), new Coordinate(4, 0));
		boardManager = new BoardManager(board);

		// when
		boardManager.performMove(new Coordinate(4, 0), new Coordinate(4, 2));
		
		//then exception
	}
	
	@Test
	public void shouldKingAttackCorrectlyMoveByOne() throws InvalidMoveException {
		// given
		board.setPieceAt(new King(Color.WHITE), new Coordinate(4, 0));
		boardManager = new BoardManager(board);

		// when
		Move move = boardManager.performMove(new Coordinate(4, 0), new Coordinate(4, 1));

		// then
		assertEquals(MoveType.ATTACK, move.getType());
		assertEquals(new King(Color.WHITE), move.getMovedPiece());
	}
	
	@Test
	public void shouldKingCaptureCorrectlyMoveByOne() throws InvalidMoveException {
		// given
		board.setPieceAt(new King(Color.WHITE), new Coordinate(4, 0));
		board.setPieceAt(new King(Color.BLACK), new Coordinate(5, 1));
		boardManager = new BoardManager(board);

		// when
		Move move = boardManager.performMove(new Coordinate(4, 0), new Coordinate(5, 1));

		// then
		assertEquals(MoveType.CAPTURE, move.getType());
		assertEquals(new King(Color.WHITE), move.getMovedPiece());
	}

}
