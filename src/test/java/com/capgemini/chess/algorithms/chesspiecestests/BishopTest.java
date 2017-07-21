package com.capgemini.chess.algorithms.chesspiecestests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.capgemini.chess.algorithms.chesspieces.Bishop;
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
 * Class testing class Bishop (implementation of bishop movements).
 */
public class BishopTest {
	
	@Test
	public void testPerformMoveBishopAttack() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.setPieceAt(new Bishop(Color.WHITE), new Coordinate(0, 6));
		board.setPieceAt(new King(Color.WHITE), new Coordinate(0, 0));
		BoardManager boardManager = new BoardManager(board);

		// when
		Move move = boardManager.performMove(new Coordinate(0, 6), new Coordinate(6, 0));

		// then
		assertEquals(MoveType.ATTACK, move.getType());
		assertEquals(new Bishop(Color.WHITE), move.getMovedPiece());
	}

	@Test(expected = InvalidMoveException.class)
	public void testPerformMoveInvalidBishopDestination() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.setPieceAt(new Bishop(Color.WHITE), new Coordinate(1, 1));
		BoardManager boardManager = new BoardManager(board);

		// when
		boardManager.performMove(new Coordinate(1, 1), new Coordinate(1, 2));
		
		//then exception

	}
}
