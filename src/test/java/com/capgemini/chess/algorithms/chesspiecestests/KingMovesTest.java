package com.capgemini.chess.algorithms.chesspiecestests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.capgemini.chess.algorithms.chesspieces.King;
import com.capgemini.chess.algorithms.chesspieces.Rook;
import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.generated.ChessBoard;

public class KingMovesTest {

	private King king;
	private ChessBoard board;
	
	@Before
	public void setUp() throws Exception {
		king = new King(Color.WHITE);
		board = new ChessBoard();
	}

	@Test
	public void shouldReturnCorrectAttackPositionsFromtheMiddleOfTheBoard() {
		//given
		List<Coordinate> expectedAttackPosistions = new ArrayList<Coordinate>();
		expectedAttackPosistions.add(new Coordinate(4, 4));
		expectedAttackPosistions.add(new Coordinate(4, 5));
		expectedAttackPosistions.add(new Coordinate(4, 6));
		expectedAttackPosistions.add(new Coordinate(5, 4));
		expectedAttackPosistions.add(new Coordinate(5, 6));
		expectedAttackPosistions.add(new Coordinate(6, 4));
		expectedAttackPosistions.add(new Coordinate(6, 5));
		expectedAttackPosistions.add(new Coordinate(6, 6));
		
		Coordinate from = new Coordinate(5, 5);
		board.setPieceAt(king, from);
		
		//when
		List<Coordinate> retrievedAttackPositions = king.getValidFutureAttackPositions(from, board);
		
		//then
		//assertThat(retrievedAttackPositions, is(expectedAttackPosistions));
		assertTrue(expectedAttackPosistions.equals(retrievedAttackPositions));
	}
	
	@Test
	public void shouldReturnCorrectAttackPositionsWhenStandingNextToOwnPiece() {
		//given
		List<Coordinate> expectedAttackPosistions = new ArrayList<Coordinate>();
		//expectedAttackPosistions.add(new Coordinate(0, 1)); ROOK's position
		expectedAttackPosistions.add(new Coordinate(0, 2));
		expectedAttackPosistions.add(new Coordinate(0, 3));
		expectedAttackPosistions.add(new Coordinate(1, 1));
		expectedAttackPosistions.add(new Coordinate(1, 3));
		expectedAttackPosistions.add(new Coordinate(2, 1));
		expectedAttackPosistions.add(new Coordinate(2, 2));
		expectedAttackPosistions.add(new Coordinate(2, 3));
		
		Coordinate from = new Coordinate(1, 2);
		Rook rook = new Rook(Color.WHITE);
		board.setPieceAt(king, from);
		board.setPieceAt(rook, new Coordinate(0,1));
		
		//when
		List<Coordinate> retrievedAttackPositions = king.getValidFutureAttackPositions(from, board);
		
		//then
		//assertThat(retrievedAttackPositions, is(expectedAttackPosistions));
		assertTrue(expectedAttackPosistions.equals(retrievedAttackPositions));
	}

}
