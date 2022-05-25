package com.sxt;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;

public abstract class Tank extends GameObject {
	
	private String upImg;
	private String downImg;
	private String rightImg;
	private String leftImg;
	
	public int width = 40;
	public int height = 50;
	private int speed = 3;
	private Direction direction = Direction.UP;
	
	public Tank(String img, int x, int y, String upImg,  String downImg, String leftImg, String rightImg, 
			GamePanel gamePanel) {
		super(img, x, y, gamePanel);
		this.upImg = upImg;
		this.leftImg = leftImg;
		this.downImg = downImg;
		this.rightImg = rightImg;
	}
	public void leftward() {
		x -= speed;
		setImg(leftImg);
		direction = Direction.LEFT;
	}
	public void upward() {
		y -= speed;
		setImg(upImg);
		direction = Direction.RIGHT;
	}
	public void rightward() {
		x += speed;
		setImg(rightImg);
		direction = Direction.UP;
	}
	public void downward() {
		x += speed;
		setImg(downImg);
		direction = Direction.DOWN;
	}
	public void setImg(String img) {
		this.img = Toolkit.getDefaultToolkit().getImage(img);
	}
	@Override
	public abstract void paintSelf(Graphics g);
	@Override
	public abstract Rectangle getRec();
}
