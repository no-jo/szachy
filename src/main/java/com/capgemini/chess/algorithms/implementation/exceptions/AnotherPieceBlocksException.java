package com.capgemini.chess.algorithms.implementation.exceptions;

public class AnotherPieceBlocksException extends InvalidMoveException {

	private static final long serialVersionUID = -7109029342454067452L;
	
	public AnotherPieceBlocksException() {
		super("The piece cannot leap over another");
	}
}
