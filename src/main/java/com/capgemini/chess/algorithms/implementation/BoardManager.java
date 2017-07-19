package com.capgemini.chess.algorithms.implementation;

import java.util.Arrays;
import java.util.List;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.BoardState;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.enums.Piece;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;
import com.capgemini.chess.algorithms.implementation.exceptions.KingInCheckException;

/**
 * Class for managing of basic operations on the Chess Board.
 *
 * @author Michal Bejm
 *
 */
public class BoardManager {

	private Board board = new Board();

	public BoardManager() {
		initBoard();
	}

	public BoardManager(List<Move> moves) {
		initBoard();
		for (Move move : moves) {
			addMove(move);
		}
	}

	public BoardManager(Board board) {
		this.board = board;
	}

	/**
	 * Getter for generated board
	 *
	 * @return board
	 */
	public Board getBoard() {
		return this.board;
	}

	/**
	 * Performs move of the chess piece on the chess board from one field to
	 * another.
	 *
	 * @param from
	 *            coordinates of 'from' field
	 * @param to
	 *            coordinates of 'to' field
	 * @return move object which includes moved piece and move type
	 * @throws InvalidMoveException
	 *             in case move is not valid
	 */
	public Move performMove(Coordinate from, Coordinate to) throws InvalidMoveException {

		Move move = validateMove(from, to);

		addMove(move);

		return move;
	}

	/**
	 * Calculates state of the chess board.
	 *
	 * @return state of the chess board
	 */
	public BoardState updateBoardState() {

		Color nextMoveColor = calculateNextMoveColor();

		boolean isKingInCheck = isKingInCheck(nextMoveColor);
		boolean isAnyMoveValid = isAnyMoveValid(nextMoveColor);

		BoardState boardState;
		if (isKingInCheck) {
			if (isAnyMoveValid) {
				boardState = BoardState.CHECK;
			} else {
				boardState = BoardState.CHECK_MATE;
			}
		} else {
			if (isAnyMoveValid) {
				boardState = BoardState.REGULAR;
			} else {
				boardState = BoardState.STALE_MATE;
			}
		}
		this.board.setState(boardState);
		return boardState;
	}

	/**
	 * Checks threefold repetition rule (one of the conditions to end the chess
	 * game with a draw).
	 *
	 * @return true if current state repeated at list two times, false otherwise
	 */
	public boolean checkThreefoldRepetitionRule() {

		// there is no need to check moves that where before last capture/en
		// passant/castling
		int lastNonAttackMoveIndex = findLastNonAttackMoveIndex();
		List<Move> omittedMoves = this.board.getMoveHistory().subList(0, lastNonAttackMoveIndex);
		BoardManager simulatedBoardManager = new BoardManager(omittedMoves);

		int counter = 0;
		for (int i = lastNonAttackMoveIndex; i < this.board.getMoveHistory().size(); i++) {
			Move moveToAdd = this.board.getMoveHistory().get(i);
			simulatedBoardManager.addMove(moveToAdd);
			boolean areBoardsEqual = Arrays.deepEquals(this.board.getPieces(),
					simulatedBoardManager.getBoard().getPieces());
			if (areBoardsEqual) {
				counter++;
			}
		}

		return counter >= 2;
	}

	/**
	 * Checks 50-move rule (one of the conditions to end the chess game with a
	 * draw).
	 *
	 * @return true if no pawn was moved or not capture was performed during
	 *         last 50 moves, false otherwise
	 */
	public boolean checkFiftyMoveRule() {

		// for this purpose a "move" consists of a player completing his turn
		// followed by his opponent completing his turn
		if (this.board.getMoveHistory().size() < 100) {
			return false;
		}

		for (int i = this.board.getMoveHistory().size() - 1; i >= this.board.getMoveHistory().size() - 100; i--) {
			Move currentMove = this.board.getMoveHistory().get(i);
			PieceType currentPieceType = currentMove.getMovedPiece().getType();
			if (currentMove.getType() != MoveType.ATTACK || currentPieceType == PieceType.PAWN) {
				return false;
			}
		}

		return true;
	}

	// PRIVATE

	private void initBoard() {

		this.board.setPieceAt(Piece.BLACK_ROOK, new Coordinate(0, 7));
		this.board.setPieceAt(Piece.BLACK_KNIGHT, new Coordinate(1, 7));
		this.board.setPieceAt(Piece.BLACK_BISHOP, new Coordinate(2, 7));
		this.board.setPieceAt(Piece.BLACK_QUEEN, new Coordinate(3, 7));
		this.board.setPieceAt(Piece.BLACK_KING, new Coordinate(4, 7));
		this.board.setPieceAt(Piece.BLACK_BISHOP, new Coordinate(5, 7));
		this.board.setPieceAt(Piece.BLACK_KNIGHT, new Coordinate(6, 7));
		this.board.setPieceAt(Piece.BLACK_ROOK, new Coordinate(7, 7));

		for (int x = 0; x < Board.SIZE; x++) {
			this.board.setPieceAt(Piece.BLACK_PAWN, new Coordinate(x, 6));
		}

		this.board.setPieceAt(Piece.WHITE_ROOK, new Coordinate(0, 0));
		this.board.setPieceAt(Piece.WHITE_KNIGHT, new Coordinate(1, 0));
		this.board.setPieceAt(Piece.WHITE_BISHOP, new Coordinate(2, 0));
		this.board.setPieceAt(Piece.WHITE_QUEEN, new Coordinate(3, 0));
		this.board.setPieceAt(Piece.WHITE_KING, new Coordinate(4, 0));
		this.board.setPieceAt(Piece.WHITE_BISHOP, new Coordinate(5, 0));
		this.board.setPieceAt(Piece.WHITE_KNIGHT, new Coordinate(6, 0));
		this.board.setPieceAt(Piece.WHITE_ROOK, new Coordinate(7, 0));

		for (int x = 0; x < Board.SIZE; x++) {
			this.board.setPieceAt(Piece.WHITE_PAWN, new Coordinate(x, 1));
		}
	}

	private void addMove(Move move) {

		addRegularMove(move);

		if (move.getType() == MoveType.CASTLING) {
			addCastling(move);
		} else if (move.getType() == MoveType.EN_PASSANT) {
			addEnPassant(move);
		}

		this.board.getMoveHistory().add(move);
	}

	private void addRegularMove(Move move) {
		Piece movedPiece = this.board.getPieceAt(move.getFrom());
		this.board.setPieceAt(null, move.getFrom());
		this.board.setPieceAt(movedPiece, move.getTo());

		performPromotion(move, movedPiece);
	}

	private void performPromotion(Move move, Piece movedPiece) {
		if (movedPiece == Piece.WHITE_PAWN && move.getTo().getY() == (Board.SIZE - 1)) {
			this.board.setPieceAt(Piece.WHITE_QUEEN, move.getTo());
		}
		if (movedPiece == Piece.BLACK_PAWN && move.getTo().getY() == 0) {
			this.board.setPieceAt(Piece.BLACK_QUEEN, move.getTo());
		}
	}

	private void addCastling(Move move) {
		// if czy krol ruszyl sie w lewo czy w prawo
		if (move.getFrom().getX() > move.getTo().getX()) {
			// krol w lewo, roszada dluga
			Piece rook = this.board.getPieceAt(new Coordinate(0, move.getFrom().getY()));
			this.board.setPieceAt(null, new Coordinate(0, move.getFrom().getY()));
			this.board.setPieceAt(rook, new Coordinate(move.getTo().getX() + 1, move.getTo().getY()));
		} else {
			// krol w prawo, roszada krotka
			Piece rook = this.board.getPieceAt(new Coordinate(Board.SIZE - 1, move.getFrom().getY()));
			this.board.setPieceAt(null, new Coordinate(Board.SIZE - 1, move.getFrom().getY()));
			this.board.setPieceAt(rook, new Coordinate(move.getTo().getX() - 1, move.getTo().getY()));
		}
	}

	private void addEnPassant(Move move) {
		Move lastMove = this.board.getMoveHistory().get(this.board.getMoveHistory().size() - 1);
		this.board.setPieceAt(null, lastMove.getTo());
	}

	private Move validateMove(Coordinate from, Coordinate to) throws InvalidMoveException, KingInCheckException {

		areCoordinatesInBounds(from, to);

		Piece piece = board.getPieceAt(from);
		isFromPieceValid(piece);

		Move newMove = new Move();
		newMove.setMovedPiece(piece);
		newMove.setFrom(from);
		newMove.setTo(to);
		newMove.setType(determineMoveType(from, to, piece));

		if (newMove.getType() == MoveType.CASTLING) {
			verifyCastlingConditions(piece);
		} else {
			piece.isMovePossible(newMove);
		}

		if (isAnyPieceBlocking(newMove))
			throw new InvalidMoveException();

		board.setPieceAt(piece, to);
		board.setPieceAt(null, from);
		if (isKingInCheck(piece.getColor())) { // sprawdzamy czy nasz krol PO
												// ruchu nie bedzie w szachu
			board.setPieceAt(null, to);
			board.setPieceAt(piece, from);
			throw new KingInCheckException();
		} else {
			board.setPieceAt(null, to);
			board.setPieceAt(piece, from);
		}

		return newMove;
	}

	private boolean isAnyPieceBlocking(Move move) {

		Coordinate to = move.getTo();
		Coordinate from = move.getFrom();
		int deltaX = Math.abs(from.getX() - to.getX());
		int deltaY = Math.abs(from.getY() - to.getY());

		if (deltaY == 0) {
			int iter_start = from.getX() < to.getX() ? from.getX() : to.getX();
			for (int i = iter_start + 1; i < iter_start + deltaX; i++) {
				if (board.getPieceAt(new Coordinate(i, from.getY())) != null)
					return true;
			}
		} else if (deltaX == 0) {
			int iter_start = from.getY() < to.getY() ? from.getY() : to.getY();
			for (int i = iter_start + 1; i < iter_start + deltaY; i++) {
				if (board.getPieceAt(new Coordinate(from.getX(), i)) != null)
					return true;
			}
		} else if (deltaX == deltaY){
			int iter_start = from.getY() < to.getY() ? from.getY() : to.getY();
			int start = from.getX() < to.getX() ? from.getX() : to.getX();
			for (int i = iter_start + 1, k = start + 1; i < iter_start + deltaY; i++, k++) {
				if (board.getPieceAt(new Coordinate(k, i)) != null)
					return true;
			}
		}
		return false;
	}

	private void verifyCastlingConditions(Piece piece) throws InvalidMoveException {
		if (board.getState() == BoardState.CHECK)
			throw new InvalidMoveException("King under check cannot castle");
		boolean hasKingMoved = false;
		boolean hasRookMoved = false;
		// TODO sprawdzenie czy zadne pole przez ktore przechodzi krol
		// nie jest szachowane
		for (Move move : this.board.getMoveHistory()) {
			if (piece.getColor() == Color.WHITE && move.getMovedPiece().getType() == piece.getType()
					&& move.getMovedPiece().getColor() == piece.getColor()
					&& move.getFrom().equals(new Coordinate(4, 0))) {
				hasKingMoved = true;
			}
			if (piece.getColor() == Color.BLACK && move.getMovedPiece().getType() == piece.getType()
					&& move.getMovedPiece().getColor() == piece.getColor()
					&& move.getFrom().equals(new Coordinate(4, 7))) {
				hasKingMoved = true;
			}
			if (piece.getColor() == Color.WHITE && move.getMovedPiece().getType() == PieceType.ROOK
					&& move.getMovedPiece().getColor() == piece.getColor()
					&& move.getFrom().getX() > move.getTo().getX() ? move.getFrom().equals(new Coordinate(0, 0))
							: move.getFrom().equals(new Coordinate(7, 0))) {
				hasRookMoved = true;
			}
			if (piece.getColor() == Color.BLACK && move.getMovedPiece().getType() == PieceType.ROOK
					&& move.getMovedPiece().getColor() == piece.getColor()
					&& move.getFrom().getX() > move.getTo().getX() ? move.getFrom().equals(new Coordinate(0, 7))
							: move.getFrom().equals(new Coordinate(7, 7))) {
				hasRookMoved = true;
			}
		}

		if (hasKingMoved || hasRookMoved) {
			throw new InvalidMoveException("Rook or king have already moved. Castling not allowed");
		}
	}

	private MoveType determineMoveType(Coordinate from, Coordinate to, Piece piece) throws InvalidMoveException {
		MoveType result;

		if ((piece == Piece.WHITE_KING || piece == Piece.BLACK_KING) && (Math.abs(from.getX() - to.getX()) > 1)) {
			result = MoveType.CASTLING;
		} else if (board.getPieceAt(to) == null) {
			result = (MoveType.ATTACK);
		} else if (board.getPieceAt(to).getColor() != piece.getColor()) {
			result = (MoveType.CAPTURE);
		} else if (board.getPieceAt(to).getColor() == piece.getColor()) {
			throw new InvalidMoveException("Cannot capture own piece");
		} else {
			throw new InvalidMoveException("Cannot classify move");
		}

		if (piece == Piece.WHITE_PAWN || piece == Piece.BLACK_PAWN) {
			if (!this.board.getMoveHistory().isEmpty()) {
				Move lastMove = this.board.getMoveHistory().get(this.board.getMoveHistory().size() - 1);
				if (from.getX() != to.getX() && lastMove.getMovedPiece().getColor() != piece.getColor()
						&& lastMove.getMovedPiece().getType() == PieceType.PAWN
						&& Math.abs(lastMove.getFrom().getY() - lastMove.getTo().getY()) == 2
						&& Math.abs(lastMove.getTo().getX() - from.getX()) == 1) {
					result = (MoveType.EN_PASSANT);
				}
			}
		}
		return result;
	}

	private void isFromPieceValid(Piece piece) throws InvalidMoveException {
		if (piece == null) {
			throw new InvalidMoveException("No piece to move in this position");
		} else if (calculateNextMoveColor() != piece.getColor()) {
			throw new InvalidMoveException("The same color cannot move twice");
		}
	}

	private void areCoordinatesInBounds(Coordinate from, Coordinate to) throws InvalidMoveException {
		if (to.getX() > Board.SIZE - 1 || to.getX() < 0 || to.getY() > Board.SIZE - 1 || to.getY() < 0) {
			throw new InvalidMoveException("Move out of bounds");
		}
		if (from.getX() > Board.SIZE - 1 || from.getX() < 0 || from.getY() > Board.SIZE - 1 || from.getY() < 0) {
			throw new InvalidMoveException("Move out of bounds");
		}
	}

	private boolean isKingInCheck(Color kingColor) {

		Coordinate current_king_position = findCurrentKingPosition(kingColor);
		boolean result = false;
		// Piece[][] pieces = this.board.getPieces();

		if (current_king_position == null) {
			return false;
		}

		Move move = new Move();
		move.setTo(current_king_position);
		move.setType(MoveType.CAPTURE);

		for (int x = 0; x < Board.SIZE; x++) {
			for (int y = 0; y < Board.SIZE; y++) {
				Piece piece = board.getPieceAt(new Coordinate(x, y));
				if (piece == null)
					continue;
				else if (piece.getColor() == kingColor) {
					continue;
				} else {
					move.setFrom(new Coordinate(x, y));
					try {
						piece.isMovePossible(move);
						isAnyPieceBlocking(move);
					} catch (InvalidMoveException e) {
						continue;
					}
					result = true;
					break;
				}
			}
		}
		return result;
	}

	private Coordinate findCurrentKingPosition(Color kingColor) {
		Coordinate current_king_position = null;
		for (int x = 0; x < Board.SIZE; x++) {
			for (int y = 0; y < Board.SIZE; y++) {
				Piece piece = board.getPieceAt(new Coordinate(x, y));
				if (piece == null)
					continue;
				else if (piece.getColor() == kingColor && piece.getType() == PieceType.KING) {
					current_king_position = new Coordinate(x, y);
				}
			}

		}
		return current_king_position;
	}

	private boolean isAnyMoveValid(Color nextMoveColor) {

		// TODO please add implementation here

		return false;
	}

	private Color calculateNextMoveColor() {
		if (this.board.getMoveHistory().size() % 2 == 0) {
			return Color.WHITE;
		} else {
			return Color.BLACK;
		}
	}

	private int findLastNonAttackMoveIndex() {
		int counter = 0;
		int lastNonAttackMoveIndex = 0;

		for (Move move : this.board.getMoveHistory()) {
			if (move.getType() != MoveType.ATTACK) {
				lastNonAttackMoveIndex = counter;
			}
			counter++;
		}

		return lastNonAttackMoveIndex;
	}

}
