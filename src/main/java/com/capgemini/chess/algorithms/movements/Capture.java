package com.capgemini.chess.algorithms.movements;

import com.capgemini.chess.algorithms.chesspieces.Piece;
import com.capgemini.chess.algorithms.data.Coordinate;

public class Capture extends Move {

	public Capture(Coordinate from, Coordinate to, Piece piece) {
		super(from, to, piece);
	}

	@Override
	public void perform() {
		// TODO Auto-generated method stub
		
	}

}
