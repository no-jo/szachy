package com.capgemini.chess.algorithms.data.enums;

/**
 * Chess this color
 * 
 * @author Michal Bejm
 *
 */
public enum Color {
	WHITE, 
	BLACK;


public Color getOpponent() {
	if (this == Color.WHITE) return Color.BLACK;
	else return Color.WHITE;
}

}