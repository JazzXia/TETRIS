package com.tarena.tetris;

import java.awt.image.BufferedImage;

public class Cell {
	private int row;
	private int col;// Column ÁÐ
	private BufferedImage image; // ÌùÍ¼
	
	public Cell(int row, int col, 
			BufferedImage image) {
		super();
		this.row = row;
		this.col = col;
		this.image = image;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public String toString(){
		return row+","+col;
	}
	
	public void moveRight(){
		col++;
	}
	public void moveLeft(){
		col--;
	}
	public void softDrop(){
		row++;
	}
}











