package com.capgemini.chess.algorithms.chesspiecestests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.capgemini.chess.algorithms.chesspieces.Piece;
import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.BoardManager;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

public class KingTest {

	@Before
	public void setUp() throws Exception {
		Board board = new Board();
		BoardManager boardManager = new BoardManager(board);
	}

	@Test
	public void shouldKingMoveByOneField() {
		fail("First test");
	}

}
