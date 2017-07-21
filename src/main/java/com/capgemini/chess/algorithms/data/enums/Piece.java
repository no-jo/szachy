package com.capgemini.chess.algorithms.data.enums;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

/**
 * Chess this definition
 * 
 * @author Michal Bejm
 *
 */
public enum Piece {
	
	WHITE_KING(PieceType.KING, Color.WHITE),
    WHITE_QUEEN(PieceType.QUEEN, Color.WHITE),
    WHITE_BISHOP(PieceType.BISHOP, Color.WHITE),
    WHITE_KNIGHT(PieceType.KNIGHT, Color.WHITE),
    WHITE_ROOK(PieceType.ROOK, Color.WHITE),
    WHITE_PAWN(PieceType.PAWN, Color.WHITE),
    BLACK_KING(PieceType.KING, Color.BLACK),
    BLACK_QUEEN(PieceType.QUEEN, Color.BLACK),
    BLACK_BISHOP(PieceType.BISHOP, Color.BLACK),
    BLACK_KNIGHT(PieceType.KNIGHT, Color.BLACK),
    BLACK_ROOK(PieceType.ROOK, Color.BLACK),
    BLACK_PAWN(PieceType.PAWN, Color.BLACK);

    private final PieceType type;
    private final Color color;

    Piece(PieceType type, Color color) {
        this.type = type;
        this.color = color;
    }

	public PieceType getType() {
		return type;
	}

	public Color getColor() {
		return color;
	}
	
	/**
	 * Method checking if given move is consistent with figure's way of movement.
	 * @param newMove
	 * @throws InvalidMoveException
	 */
	public void isMovePossible(Move newMove) throws InvalidMoveException {
		
		Coordinate from = newMove.getFrom();
		Coordinate to = newMove.getTo();
	
		if ((this == Piece.WHITE_KING || this == Piece.BLACK_KING)
				&& newMove.getType() != MoveType.CASTLING) {
			{
				if (!canKingMove(from, to))
					throw new InvalidMoveException("King tried to move too far");
			}
		} else if (this == Piece.WHITE_QUEEN || this == Piece.BLACK_QUEEN)

		{
			if (!canQueenMove(from, to))
				throw new InvalidMoveException("Invalid queen move");
		} else if (this == Piece.WHITE_BISHOP || this == Piece.BLACK_BISHOP)

		{
			if (!canBishopMove(from, to)) {
				throw new InvalidMoveException("Wrong bishop move");
			}
		} else if (this == Piece.WHITE_KNIGHT || this == Piece.BLACK_KNIGHT)

		{
			if (!canKnightMove(from, to))
				throw new InvalidMoveException("Invalid Knight move");
		} else if (this == Piece.WHITE_ROOK || this == Piece.BLACK_ROOK)

		{
			if (!canRookMove(from, to))
				throw new InvalidMoveException("Wrong rook move");
			
		} else if ((this == Piece.WHITE_PAWN || this == Piece.BLACK_PAWN)
				&& newMove.getType() != MoveType.EN_PASSANT) {
			if (newMove.getType() == MoveType.CAPTURE) {
				if (!canPawnThisColorCapture(this.getColor(), from, to))
					throw new InvalidMoveException("Invalid capture by pawn");
			} else if (newMove.getType() == MoveType.ATTACK) {
				if (!canPawnThisColorAttack(this.getColor(), from, to))
					throw new InvalidMoveException("Invalid attack by pawn");
			}
		}

	}
	
	private boolean canKingMove(Coordinate from, Coordinate to) {
		if (Math.abs(from.getX() - to.getX()) <= 1 && Math.abs(from.getY() - to.getY()) <= 1)
			return true;
		else
			return false;
	}

	private boolean canPawnThisColorAttack(Color color, Coordinate from, Coordinate to) {

		if (color == Color.WHITE && from.getY() == 1 && to.getY() == 3 && Math.abs(from.getX() - to.getX()) == 0)
			return true;
		if (color == Color.BLACK && from.getY() == 6 && to.getY() == 4 && Math.abs(from.getX() - to.getX()) == 0)
			return true;
		if (color == Color.WHITE && from.getX() - to.getX() == 0 && from.getY() - to.getY() == -1)
			return true;
		if (color == Color.BLACK && from.getX() - to.getX() == 0 && from.getY() - to.getY() == 1)
			return true;
		else
			return false;
	}

	private boolean canPawnThisColorCapture(Color color, Coordinate from, Coordinate to) {
		if (color == Color.WHITE && Math.abs(from.getX() - to.getX()) == 1 && from.getY() - to.getY() == -1)
			return true;
		if (color == Color.BLACK && Math.abs(from.getX() - to.getX()) == 1 && from.getY() - to.getY() == 1)
			return true;
		else
			return false;
	}

	private boolean canRookMove(Coordinate from, Coordinate to) {
		if (Math.abs(from.getX() - to.getX()) >= 1 && Math.abs(from.getY() - to.getY()) == 0)
			return true;
		if (Math.abs(from.getX() - to.getX()) == 0 && Math.abs(from.getY() - to.getY()) >= 1)
			return true;
		else
			return false;
	}

	private boolean canKnightMove(Coordinate from, Coordinate to) {
		if (Math.abs(from.getX() - to.getX()) == 2 && Math.abs(from.getY() - to.getY()) == 1)
			return true;
		if (Math.abs(from.getX() - to.getX()) == 1 && Math.abs(from.getY() - to.getY()) == 2)
			return true;
		else
			return false;
	}

	private boolean canBishopMove(Coordinate from, Coordinate to) {
		if (Math.abs(from.getX() - to.getX()) == Math.abs(from.getY() - to.getY()))
			return true;
		else
			return false;
	}

	private boolean canQueenMove(Coordinate from, Coordinate to) {
		if (Math.abs(from.getX() - to.getX()) >= 1 && Math.abs(from.getY() - to.getY()) == 0)
			return true;
		if (Math.abs(from.getX() - to.getX()) == 0 && Math.abs(from.getY() - to.getY()) >= 1)
			return true;
		if (Math.abs(from.getX() - to.getX()) == Math.abs(from.getY() - to.getY()))
			return true;
		else
			return false;
	}	
	
}

