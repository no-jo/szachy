package com.capgemini.chess.algorithms.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.capgemini.chess.algorithms.chesspieces.Bishop;
import com.capgemini.chess.algorithms.chesspieces.King;
import com.capgemini.chess.algorithms.chesspieces.Knight;
import com.capgemini.chess.algorithms.chesspieces.Pawn;
import com.capgemini.chess.algorithms.chesspieces.Queen;
import com.capgemini.chess.algorithms.chesspieces.Rook;
import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.enums.BoardState;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;
import com.capgemini.chess.algorithms.implementation.exceptions.KingInCheckException;
import com.capgemini.chess.algorithms.movements.Move;

/**
 * Test class for testing {@link BoardManager}
 * 
 * @author Michal Bejm
 *
 */
public class BoardManagerTest {

	@Test
	public void testGenerateBoardInitialPosition() {
		// given
		List<Move> moves = new ArrayList<>();

		// when
		BoardManager boardManager = new BoardManager(moves);

		// then
		for (int x = 0; x < Board.SIZE; x++) {
			for (int y = 0; y < Board.SIZE; y++) {
				if (y > 1 && y < 6) {
					assertNull(boardManager.getBoard().getPieceAt(new Coordinate(x, y)));
				} else {
					assertNotNull(boardManager.getBoard().getPieceAt(new Coordinate(x, y)));
				}
			}
		}
		assertEquals(new Pawn(Color.WHITE), boardManager.getBoard().getPieceAt(new Coordinate(5, 1)));
		assertEquals(new King(Color.WHITE), boardManager.getBoard().getPieceAt(new Coordinate(4, 0)));
		assertEquals(new Bishop(Color.WHITE), boardManager.getBoard().getPieceAt(new Coordinate(5, 0)));
		assertEquals(new Rook(Color.BLACK), boardManager.getBoard().getPieceAt(new Coordinate(0, 7)));
		assertEquals(new Knight(Color.BLACK), boardManager.getBoard().getPieceAt(new Coordinate(1, 7)));
		assertEquals(new Queen(Color.BLACK), boardManager.getBoard().getPieceAt(new Coordinate(3, 7)));
		assertEquals(32, calculateNumberOfPieces(boardManager.getBoard()));
	}

	@Test
	public void testGenerateBoardAttack() {
		// given
		List<Move> moves = new ArrayList<>();
		Move move = new Move();
		move.setFrom(new Coordinate(5, 1));
		move.setTo(new Coordinate(5, 3));
		move.setType(MoveType.ATTACK);
		moves.add(move);

		// when
		BoardManager boardManager = new BoardManager(moves);

		// then
		assertNull(boardManager.getBoard().getPieceAt(new Coordinate(5, 1)));
		assertNotNull(boardManager.getBoard().getPieceAt(new Coordinate(5, 3)));
		assertEquals(32, calculateNumberOfPieces(boardManager.getBoard()));
	}

	@Test
	public void testGenerateBoardCapture() {
		// given
		List<Move> moves = new ArrayList<>();
		Move move = new Move();
		move.setFrom(new Coordinate(0, 0));
		move.setTo(new Coordinate(0, 6));
		move.setType(MoveType.CAPTURE);
		moves.add(move);

		// when
		BoardManager boardManager = new BoardManager(moves);

		// then
		assertNull(boardManager.getBoard().getPieceAt(new Coordinate(0, 0)));
		assertNotNull(boardManager.getBoard().getPieceAt(new Coordinate(0, 6)));
		assertEquals(31, calculateNumberOfPieces(boardManager.getBoard()));
	}

	@Test
	public void testGenerateBoardCastling() {
		// given
		List<Move> moves = new ArrayList<>();
		Move move = new Move();
		move.setFrom(new Coordinate(4, 0));
		move.setTo(new Coordinate(2, 0));
		move.setType(MoveType.CASTLING);
		moves.add(move);

		// when
		BoardManager boardManager = new BoardManager(moves);

		// then
		assertNull(boardManager.getBoard().getPieceAt(new Coordinate(4, 0)));
		assertNotNull(boardManager.getBoard().getPieceAt(new Coordinate(2, 0)));
		assertEquals(new King(Color.WHITE), boardManager.getBoard().getPieceAt(new Coordinate(2, 0)));
		assertEquals(new Rook(Color.WHITE), boardManager.getBoard().getPieceAt(new Coordinate(3, 0)));
	}

	@Test
	public void testGenerateBoardEnPassant() {
		// given
		List<Move> moves = new ArrayList<>();
		Move move1 = new Move();
		move1.setFrom(new Coordinate(1, 1));
		move1.setTo(new Coordinate(1, 4));
		move1.setType(MoveType.ATTACK);
		moves.add(move1);
		Move move2 = new Move();
		move2.setFrom(new Coordinate(2, 6));
		move2.setTo(new Coordinate(2, 4));
		move2.setType(MoveType.ATTACK);
		moves.add(move2);
		Move move3 = new Move();
		move3.setFrom(new Coordinate(1, 4));
		move3.setTo(new Coordinate(2, 5));
		move3.setType(MoveType.EN_PASSANT);
		moves.add(move3);

		// when
		BoardManager boardManager = new BoardManager(moves);

		// then
		assertNull(boardManager.getBoard().getPieceAt(new Coordinate(2, 4)));
		assertNull(boardManager.getBoard().getPieceAt(new Coordinate(1, 4)));
		assertNotNull(boardManager.getBoard().getPieceAt(new Coordinate(2, 5)));
		assertEquals(new Pawn(Color.WHITE), boardManager.getBoard().getPieceAt(new Coordinate(2, 5)));
		assertEquals(31, calculateNumberOfPieces(boardManager.getBoard()));
	}

	@Test
	public void testGenerateBoardPromotion() {
		// given
		List<Move> moves = new ArrayList<>();
		Move move = new Move();
		move.setFrom(new Coordinate(1, 6));
		move.setTo(new Coordinate(1, 0));
		move.setType(MoveType.CAPTURE);
		moves.add(move);

		// when
		BoardManager boardManager = new BoardManager(moves);

		// then
		assertEquals(new Queen(Color.BLACK), boardManager.getBoard().getPieceAt(new Coordinate(1, 0)));
	}



	@Test
	public void testPerformMoveCastling() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.setPieceAt(new King(Color.WHITE), new Coordinate(4, 0));
		board.setPieceAt(new Rook(Color.WHITE), new Coordinate(7, 0));

		// when
		BoardManager boardManager = new BoardManager(board);
		Move move = boardManager.performMove(new Coordinate(4, 0), new Coordinate(6, 0));

		// then
		assertEquals(MoveType.CASTLING, move.getType());
		assertEquals(new King(Color.WHITE), move.getMovedPiece());
	}

	@Test
	public void testPerformMoveEnPassant() throws InvalidMoveException {
		// given
		Board board = new Board();
		BoardManager boardManager = new BoardManager(board);

		board.getMoveHistory().add(createDummyMove(board));
		board.setPieceAt(new Pawn(Color.WHITE), new Coordinate(1, 4));
		board.setPieceAt(new Pawn(Color.BLACK), new Coordinate(2, 6));
		board.setPieceAt(new King(Color.BLACK), new Coordinate(7, 7));
		board.setPieceAt(new King(Color.WHITE), new Coordinate(0, 0));
		boardManager.performMove(new Coordinate(2, 6), new Coordinate(2, 4));

		// when
		Move move = boardManager.performMove(new Coordinate(1, 4), new Coordinate(2, 5));

		// then
		assertEquals(MoveType.EN_PASSANT, move.getType());
		assertEquals(new Pawn(Color.WHITE), move.getMovedPiece());
	}

	@Test(expected = InvalidMoveException.class)
	public void testPerformMoveInvalidIndexOutOfBound() throws InvalidMoveException {
		// given
		BoardManager boardManager = new BoardManager();

		// when
		boardManager.performMove(new Coordinate(8, 6), new Coordinate(7, 6));

	}

	@Test(expected = InvalidMoveException.class)
	public void testPerformMoveInvalidMoveOrder() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.setPieceAt(new King(Color.BLACK), new Coordinate(0, 7));
		BoardManager boardManager = new BoardManager(board);

		// when
		boardManager.performMove(new Coordinate(0, 7), new Coordinate(1, 6));
	}

	@Test(expected = InvalidMoveException.class)
	public void testPerformMoveInvalidEmptySpot() throws InvalidMoveException {
		// given
		Board board = new Board();
		BoardManager boardManager = new BoardManager(board);

		// when
		boardManager.performMove(new Coordinate(0, 7), new Coordinate(1, 6));

	}

	@Test(expected = InvalidMoveException.class)
	public void testPerformMoveInvalidSameSpot() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.setPieceAt(new King(Color.WHITE), new Coordinate(0, 0));
		BoardManager boardManager = new BoardManager(board);

		// when
		boardManager.performMove(new Coordinate(0, 0), new Coordinate(0, 0));

	}

	@Test(expected = InvalidMoveException.class)
	public void testPerformMoveInvalidQueenLeapsOver() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.setPieceAt(new Queen(Color.WHITE), new Coordinate(1, 1));
		board.setPieceAt(new Pawn(Color.BLACK), new Coordinate(4, 4));
		BoardManager boardManager = new BoardManager(board);

		// when

		boardManager.performMove(new Coordinate(1, 1), new Coordinate(6, 6));

	}

	@Test(expected = InvalidMoveException.class)
	public void testPerformMoveInvalidRookLeapsOver() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.setPieceAt(new Rook(Color.WHITE), new Coordinate(3, 0));
		board.setPieceAt(new Pawn(Color.WHITE), new Coordinate(3, 2));
		BoardManager boardManager = new BoardManager(board);

		// when
		boardManager.performMove(new Coordinate(3, 0), new Coordinate(3, 7));
	}

	@Test(expected = InvalidMoveException.class)
	public void testPerformMoveInvalidOwnPieceCapture() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.setPieceAt(new Knight(Color.WHITE), new Coordinate(5, 6));
		board.setPieceAt(new Pawn(Color.WHITE), new Coordinate(3, 5));
		BoardManager boardManager = new BoardManager(board);

		// when
		boardManager.performMove(new Coordinate(5, 6), new Coordinate(3, 5));
	}

	@Test(expected = InvalidMoveException.class)
	public void testPerformMoveInvalidCastlingKingMoved() throws InvalidMoveException {
		// given
		Board board = new Board();
		BoardManager boardManager = new BoardManager(board);

		board.setPieceAt(new King(Color.WHITE), new Coordinate(4, 0));
		board.setPieceAt(new Rook(Color.WHITE), new Coordinate(7, 0));
		boardManager.performMove(new Coordinate(4, 0), new Coordinate(3, 0));
		board.getMoveHistory().add(createDummyMove(board));
		boardManager.performMove(new Coordinate(3, 0), new Coordinate(4, 0));
		board.getMoveHistory().add(createDummyMove(board));

		// when
		boardManager.performMove(new Coordinate(4, 0), new Coordinate(6, 0));
	}

	@Test(expected = InvalidMoveException.class)
	public void testPerformMoveInvalidCastlingRookMoved() throws InvalidMoveException {
		// given
		Board board = new Board();
		BoardManager boardManager = new BoardManager(board);

		board.setPieceAt(new King(Color.WHITE), new Coordinate(4, 0));
		board.setPieceAt(new Rook(Color.WHITE), new Coordinate(7, 0));
		boardManager.performMove(new Coordinate(7, 0), new Coordinate(7, 5));
		board.getMoveHistory().add(createDummyMove(board));
		boardManager.performMove(new Coordinate(7, 5), new Coordinate(7, 0));
		board.getMoveHistory().add(createDummyMove(board));

		// when
		boardManager.performMove(new Coordinate(4, 0), new Coordinate(6, 0));
	}

	@Test(expected = InvalidMoveException.class)
	public void testPerformMoveInvalidShortCastlingWithPiecesBetween() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.setPieceAt(new King(Color.WHITE), new Coordinate(4, 0));
		board.setPieceAt(new Rook(Color.WHITE), new Coordinate(7, 0));
		board.setPieceAt(new Bishop(Color.WHITE), new Coordinate(5, 0));
		BoardManager boardManager = new BoardManager(board);

		// when
		boardManager.performMove(new Coordinate(4, 0), new Coordinate(6, 0));
	}

	@Test(expected = InvalidMoveException.class)
	public void testPerformMoveInvalidLongCastlingWithPiecesBetween() throws InvalidMoveException {
		// given
		Board board = new Board();
		// createDummyMove(board);
		board.setPieceAt(new King(Color.WHITE), new Coordinate(4, 0));
		board.setPieceAt(new Rook(Color.WHITE), new Coordinate(0, 0));
		board.setPieceAt(new Bishop(Color.WHITE), new Coordinate(1, 0));
		BoardManager boardManager = new BoardManager(board);

		// when
		boardManager.performMove(new Coordinate(4, 0), new Coordinate(2, 0));
	}

	@Test(expected = InvalidMoveException.class)
	public void testPerformMoveInvalidCastlingKingUnderCheck() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.setPieceAt(new King(Color.WHITE), new Coordinate(4, 0));
		board.setPieceAt(new Rook(Color.WHITE), new Coordinate(7, 0));
		board.setPieceAt(new Rook(Color.BLACK), new Coordinate(5, 7));
		BoardManager boardManager = new BoardManager(board);

		// when
		boardManager.performMove(new Coordinate(4, 0), new Coordinate(6, 0));
	}

	@Test (expected = KingInCheckException.class)
	public void testPerformMoveInvalidKingWouldBeChecked() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.setPieceAt(new King(Color.WHITE), new Coordinate(4, 0));
		board.setPieceAt(new Bishop(Color.WHITE), new Coordinate(4, 5));
		board.setPieceAt(new Rook(Color.BLACK), new Coordinate(4, 7));
		BoardManager boardManager = new BoardManager(board);

		// when
		boardManager.performMove(new Coordinate(4, 5), new Coordinate(7, 2));
	}

	@Test 
	public void shouldKingBeDefendedFromCheck() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.setPieceAt(new King(Color.WHITE), new Coordinate(4, 0));
		board.setPieceAt(new Bishop(Color.WHITE), new Coordinate(4, 5));
		board.setPieceAt(new Rook(Color.BLACK), new Coordinate(4, 7));
		board.setPieceAt(new Pawn(Color.WHITE), new Coordinate(4, 1));
		BoardManager boardManager = new BoardManager(board);

		// when
		boardManager.performMove(new Coordinate(4, 5), new Coordinate(7, 2));
	}

	@Test
	public void testUpdateBoardStateRegular() throws InvalidMoveException {
		// given
		BoardManager boardManager = new BoardManager();

		// when
		BoardState boardState = boardManager.updateBoardState();

		// then
		assertEquals(BoardState.REGULAR, boardState);
	}

	@Test
	public void testUpdateBoardStateCheck() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.getMoveHistory().add(createDummyMove(board));
		board.setPieceAt(new Bishop(Color.WHITE), new Coordinate(1, 3));
		board.setPieceAt(new King(Color.BLACK), new Coordinate(4, 0));

		// when
		BoardManager boardManager = new BoardManager(board);
		BoardState boardState = boardManager.updateBoardState();

		// then
		assertEquals(BoardState.CHECK, boardState);
	}

	@Test
	public void testUpdateBoardStateCheckMate() throws InvalidMoveException {
		// given
		Board board = new Board();
		board.getMoveHistory().add(createDummyMove(board));
		board.setPieceAt(new Rook(Color.WHITE), new Coordinate(0, 1));
		board.setPieceAt(new Rook(Color.WHITE), new Coordinate(1, 0));
		board.setPieceAt(new King(Color.BLACK), new Coordinate(4, 0));

		// when
		BoardManager boardManager = new BoardManager(board);
		BoardState boardState = boardManager.updateBoardState();

		// then
		assertEquals(BoardState.CHECK_MATE, boardState);
	}

	@Test
	public void testUpdateBoardStateStaleMate() throws InvalidMoveException {
		// given
		// probably error in test ->
		Board board = new Board();
		board.getMoveHistory().add(createDummyMove(board));
		board.setPieceAt(new King(Color.BLACK), new Coordinate(7, 0));
		board.setPieceAt(new Queen(Color.WHITE), new Coordinate(5, 1));
		board.setPieceAt(new King(Color.WHITE), new Coordinate(6, 2));

		// when
		BoardManager boardManager = new BoardManager(board);
		BoardState boardState = boardManager.updateBoardState();

		// then
		assertEquals(BoardState.STALE_MATE, boardState);
	}

	@Test
	public void testCheckThreefoldRepetitionRuleSuccessful() {
		// given
		List<Move> moves = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			Move move1 = new Move();
			move1.setFrom(new Coordinate(5, 1));
			move1.setTo(new Coordinate(5, 3));
			move1.setType(MoveType.ATTACK);
			moves.add(move1);

			Move move2 = new Move();
			move2.setFrom(new Coordinate(5, 6));
			move2.setTo(new Coordinate(5, 4));
			move2.setType(MoveType.ATTACK);
			moves.add(move2);

			Move move3 = new Move();
			move3.setFrom(new Coordinate(5, 3));
			move3.setTo(new Coordinate(5, 1));
			move3.setType(MoveType.ATTACK);
			moves.add(move3);

			Move move4 = new Move();
			move4.setFrom(new Coordinate(5, 4));
			move4.setTo(new Coordinate(5, 6));
			move4.setType(MoveType.ATTACK);
			moves.add(move4);
		}
		BoardManager boardManager = new BoardManager(moves);

		// when
		boolean isThreefoldRepetition = boardManager.checkThreefoldRepetitionRule();

		// then
		assertTrue(isThreefoldRepetition);
	}

	@Test
	public void testCheckThreefoldRepetitionRuleUnsuccessful() {
		// given
		List<Move> moves = new ArrayList<>();
		Move move1 = new Move();
		move1.setFrom(new Coordinate(5, 1));
		move1.setTo(new Coordinate(5, 3));
		move1.setType(MoveType.ATTACK);
		moves.add(move1);

		Move move2 = new Move();
		move2.setFrom(new Coordinate(5, 6));
		move2.setTo(new Coordinate(5, 4));
		move2.setType(MoveType.ATTACK);
		moves.add(move2);

		Move move3 = new Move();
		move3.setFrom(new Coordinate(5, 3));
		move3.setTo(new Coordinate(5, 1));
		move3.setType(MoveType.ATTACK);
		moves.add(move3);

		Move move4 = new Move();
		move4.setFrom(new Coordinate(5, 4));
		move4.setTo(new Coordinate(5, 6));
		move4.setType(MoveType.ATTACK);
		moves.add(move4);
		BoardManager boardManager = new BoardManager(moves);

		// when
		boolean isThreefoldRepetition = boardManager.checkThreefoldRepetitionRule();

		// then
		assertFalse(isThreefoldRepetition);
	}

	@Test
	public void testCheckFiftyMoveRuleSuccessful() {
		// given
		Board board = new Board();
		BoardManager boardManager = new BoardManager(board);
		for (int i = 0; i < 100; i++) {
			board.getMoveHistory().add(createDummyMove(board));
		}

		// when
		boolean areFiftyMoves = boardManager.checkFiftyMoveRule();

		// then
		assertTrue(areFiftyMoves);
	}

	@Test
	public void testCheckFiftyMoveRuleUnsuccessfulNotEnoughMoves() {
		// given
		Board board = new Board();
		BoardManager boardManager = new BoardManager(board);
		for (int i = 0; i < 99; i++) {
			board.getMoveHistory().add(createDummyMove(board));
		}

		// when
		boolean areFiftyMoves = boardManager.checkFiftyMoveRule();

		// then
		assertFalse(areFiftyMoves);
	}

	@Test
	public void testCheckFiftyMoveRuleUnsuccessfulPawnMoved() {
		// given
		BoardManager boardManager = new BoardManager(new Board());

		Move move = new Move();
		boardManager.getBoard().setPieceAt(new Pawn(Color.WHITE), new Coordinate(0, 0));
		move.setMovedPiece(new Pawn(Color.WHITE));
		move.setFrom(new Coordinate(0, 0));
		move.setTo(new Coordinate(0, 0));
		move.setType(MoveType.ATTACK);
		boardManager.getBoard().setPieceAt(null, new Coordinate(0, 0));
		boardManager.getBoard().getMoveHistory().add(move);

		for (int i = 0; i < 99; i++) {
			boardManager.getBoard().getMoveHistory().add(createDummyMove(boardManager.getBoard()));
		}

		// when
		boolean areFiftyMoves = boardManager.checkFiftyMoveRule();

		// then
		assertFalse(areFiftyMoves);
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

	private int calculateNumberOfPieces(Board board) {
		int counter = 0;
		for (int x = 0; x < Board.SIZE; x++) {
			for (int y = 0; y < Board.SIZE; y++) {
				if (board.getPieceAt(new Coordinate(x, y)) != null) {
					counter++;
				}
			}
		}
		return counter;
	}
}
