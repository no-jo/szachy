package com.capgemini.chess.algorithms.chesspieces;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.data.generated.ChessBoard;

/**
 * @author JOANNANO
 * This class includes implementation of movement specific for the king.
 */
/**
 * @author JOANNANO
 *
 */

/**
 * @author JOANNANO
 *
 */
/**
 * @author JOANNANO
 *
 */
public final class King extends ChessPiece {

	final private Color color;
	final private PieceType type = PieceType.KING;

	public King(Color c) {
		color = c;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public PieceType getType() {
		return type;
	}

	/**
	 * @param A
	 *            position on board and current board state.
	 * @return list of Coordinates where the king can move next. This includes
	 *         out of bounds coordinates as that move is validated earlier.
	 */
	@Override
	public List<Coordinate> getValidFutureAttackPositions(Coordinate from, ChessBoard board) {
		return getValidFutureAttackOrCapturePositions(from, board);
	}

	@Override
	public List<Coordinate> getValidFutureCapturePositions(Coordinate from, ChessBoard board) {
		return getValidFutureAttackOrCapturePositions(from, board);
	}

	private List<Coordinate> getValidFutureAttackOrCapturePositions(Coordinate from, ChessBoard board) {
		List<Coordinate> attackPositions = new ArrayList<Coordinate>();
		for (int x = from.getX() - 1; x <= from.getX() + 1; x++) {
			for (int y = from.getY() - 1; y <= from.getY() + 1; y++) {
				Coordinate current_tile = new Coordinate(x, y);
				if (!current_tile.equals(from) && ( board.getPieceAt(current_tile) == null
						|| board.getPieceAt(current_tile).getColor() != this.getColor() ))
					attackPositions.add(current_tile);
			}
		}
		return attackPositions;
	}
}
