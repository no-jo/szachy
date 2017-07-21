package com.capgemini.chess.algorithms.movements;

import com.capgemini.chess.algorithms.chesspieces.Piece;
import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.enums.CastlingType;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

public class KingsideCastling extends Move {

	public KingsideCastling(Coordinate from, Coordinate to, Piece piece) throws InvalidMoveException {
		super(from, to, piece);
		verifyCastlingConditions();
	}

	@Override
	public void perform() {
	
	}
	
	private void verifyCastlingConditions() throws InvalidMoveException {
	}
		
//		if (isKingInCheck(this.getMovedPiece().getColor()))
//			throw new InvalidMoveException("King under check cannot castle");
		//TODO how to pass is king in check method between board  / move
		piecesDidNotMoveBefore();
		noPiecesInBetween();
		kingDoesNotPassAttackedField();

	}
	
	private void kingDoesNotPassAttackedField(Move newMove, CastlingType castlingType) throws InvalidMoveException {
		int x, y;
		if (castlingType == CastlingType.QUEENSIDE) {
			x = 3;
		} else {
			x = 5;
		}
		if (newMove.getMovedPiece().getColor() == Color.WHITE) {
			y = 0;
		} else {
			y = 7;
		}
		if (isFieldAttackedByOpponent(newMove.getMovedPiece().getColor(), new Coordinate(x, y))) {
			throw new InvalidMoveException("King cannot castle throw attacked field");
		}
	}

	private void noPiecesInBetween(Move newMove, CastlingType castlingType) throws InvalidMoveException {
		Coordinate to;
		if (castlingType == CastlingType.QUEENSIDE) {
			to = new Coordinate(0, newMove.getTo().getY());
		} else
			to = new Coordinate(7, newMove.getTo().getY());
		isAnyPieceBlocking(newMove.getFrom(), to);
	}

	private void piecesDidNotMoveBefore(Move newMove, CastlingType castlingType) throws InvalidMoveException {
		boolean hasKingMoved = false;
		boolean hasRookMoved = false;

		for (Move move : this.board.getMoveHistory()) {
			if (move.getMovedPiece().getType() == newMove.getMovedPiece().getType()
					&& move.getMovedPiece().getColor() == newMove.getMovedPiece().getColor()) {
				hasKingMoved = true;
			}

			if (castlingType == CastlingType.QUEENSIDE
					&& move.getMovedPiece().getColor() == newMove.getMovedPiece().getColor()
					&& move.getMovedPiece().getType() == PieceType.ROOK && move.getFrom().getX() == 0) {
				hasRookMoved = true;
			}

			if (castlingType == CastlingType.KINGSIDE
					&& move.getMovedPiece().getColor() == newMove.getMovedPiece().getColor()
					&& move.getMovedPiece().getType() == PieceType.ROOK && move.getFrom().getX() == 7) {
				hasRookMoved = true;
			}
		}

		if (hasKingMoved || hasRookMoved) {
			throw new InvalidMoveException("Rook or king have already moved. Castling not allowed");
		}
	}


}
