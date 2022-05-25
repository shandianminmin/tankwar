package com.sxt;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class PlayerOne extends Tank {
	
	private boolean up= false;
    private boolean left = false;
    private boolean right= false;
    private boolean down = false;

	public PlayerOne(String img, int x, int y, String upImg, String downImg, String leftImg, String rightImg, GamePanel gamePanel) {
		super(img, x, y, upImg, downImg, leftImg, rightImg, gamePanel);
		// TODO 自动生成的构造函数存根
	}
	public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_A:
                left = true;
                break;
            case KeyEvent.VK_S:
                down = true;
                break;
            case KeyEvent.VK_D:
                right = true;
                break;
            case KeyEvent.VK_W:
                up = true;
                break;
            default:
                break;
        }
    }

    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_A:
                left = false;
                break;
            case KeyEvent.VK_S:
                down = false;
                break;
            case KeyEvent.VK_D:
                right = false;
                break;
            case KeyEvent.VK_W:
                up = false;
                break;
            default:
                break;
        }
    }
    
    public void move() {
    	if(left){
    		leftward();
    	}
    	else if(right) {
    		rightward();
    	}
    	else if(up) {
    		upward();
    	}
    	else if(down) {
    		downward();
    	}
	}

	@Override
	public void paintSelf(Graphics g) {
		// TODO 自动生成的方法存根
		g.drawImage(img, x, y, null);
		move();
		
	}

	@Override
	public Rectangle getRec() {
		// TODO 自动生成的方法存根
		return new Rectangle(x, y, width, height);
	}

}