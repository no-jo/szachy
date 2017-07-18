package com.capgemini.chess.algorithms.chesspieces;

import java.util.List;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.data.generated.ChessBoard;

public class Rook extends ChessPiece {

	private Color color;
	private PieceType type = PieceType.ROOK;
	
	public Rook(Color c) {
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

	@Override
	public List<Coordinate> getValidFutureAttackPositions(Coordinate from, ChessBoard board) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Coordinate> getValidFutureCapturePositions(Coordinate from, ChessBoard board) {
		// TODO Auto-generated method stub
		return null;
	}

}
