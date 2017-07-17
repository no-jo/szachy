package com.capgemini.chess.algorithms.chesspieces;

import java.util.List;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.data.generated.ChessBoard;

public abstract class ChessPiece {
	
	abstract Color getColor();
	
	abstract PieceType getType();
	
	abstract List<Coordinate> getValidFutureAttackPositions(Coordinate from, ChessBoard board);
	
	abstract List<Coordinate> getValidFutureCapturePositions(Coordinate from, ChessBoard board);		
	
}
