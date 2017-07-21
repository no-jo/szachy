package com.capgemini.chess.algorithms.movements;

import com.capgemini.chess.algorithms.chesspieces.Piece;
import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

/**
 * Chess move definition.
 * 
 * @author Michal Bejm
 *
 */
public abstract class Move {

	Coordinate from;
	Coordinate to;
	Piece movedPiece;
	MoveType type;

	public static Move createNewMove(Coordinate from, Coordinate to, Board board) throws InvalidMoveException {

		isFromDifferentThanTo(from, to);

		Move result = null;
		Piece pieceFrom = board.getPieceAt(from);
		Piece pieceTo = board.getPieceAt(to);

		if (pieceTo == null) {
			result = new Attack(from, to, pieceFrom);
		} else if (pieceTo.getColor() != pieceFrom.getColor()) {
			result = new Capture(from, to, pieceFrom);
		} else if (pieceTo.getColor() == pieceFrom.getColor()) {
			throw new InvalidMoveException("Cannot capture own piece");
		} else {
			throw new InvalidMoveException("Cannot classify move");
		}

		if (pieceFrom.getType() == PieceType.KING && from.getX() == 4 && (Math.abs(from.getX() - to.getX()) == 2)) {
			if (from.getX() > to.getX()) {
				result = new QueensideCastling(from, to, pieceFrom);
			} else {
				result = new KingsideCastling(from, to, pieceFrom);
			}
	
		} else {
			result = new Attack(from, to, pieceFrom);
		}

		if (pieceFrom.getType() == PieceType.PAWN) {
			if (!board.getMoveHistory().isEmpty()) {
				Move lastMove = board.getMoveHistory().get(board.getMoveHistory().size() - 1);

				if (from.getX() != to.getX() && lastMove.getMovedPiece().getColor() != pieceFrom.getColor()
						&& lastMove.getMovedPiece().getType() == PieceType.PAWN
						&& Math.abs(lastMove.getFrom().getY() - lastMove.getTo().getY()) == 2
						&& Math.abs(lastMove.getTo().getX() - from.getX()) == 1) {
					result = new EnPassant(from, to, pieceFrom);
				}
			}
		}
		return result;
	};

	static private void isFromDifferentThanTo(Coordinate from, Coordinate to) throws InvalidMoveException {
		if (from.equals(to))
			throw new InvalidMoveException("Invalid move:cannot stay in place");
	}

	public abstract void perform();

	public Move(Coordinate from, Coordinate to, Piece piece) {
		this.from = from;
		this.to = to;
		this.movedPiece = piece;
	};

	public Coordinate getFrom() {
		return from;
	}

	public void setFrom(Coordinate from) {
		this.from = from;
	}

	public Coordinate getTo() {
		return to;
	}

	public void setTo(Coordinate to) {
		this.to = to;
	}

	public Piece getMovedPiece() {
		return movedPiece;
	}

	public void setMovedPiece(Piece movedPiece) {
		this.movedPiece = movedPiece;
	}

	public MoveType getType() {
		return type;
	}

	public void setType(MoveType type) {
		this.type = type;
	}

}
