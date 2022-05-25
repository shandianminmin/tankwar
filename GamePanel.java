package com.sxt;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class GamePanel extends JFrame{
	Image offScreemImage = null;
	
	int width = 800;
	int height = 610;
	Image select = Toolkit.getDefaultToolkit().getImage("images/selecttank.gif");
	int y = 150;
	int state = 0;
	int a = 1;
	PlayerOne playerOne = new PlayerOne("images/player1/p1tankU.gif",125, 510,
			"images/player1/p1tankU.gif","images/player1/p1tankD.gif",
			"images/player1/p1tankL.gif","images/player1/p1tankR.gif", this);
	public void launch() {
		System.out.println(88);
		setTitle("坦克大作战");
		setSize(width,height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(3);
		setResizable(false);
		setVisible(true);
		this.addKeyListener(new GamePanel.KeyMonitor());
		while(true) {
			repaint();
			try {
				Thread.sleep(25);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	public void paint(Graphics g) {
		if(offScreemImage == null) {
		    offScreemImage = this.createImage(width, height);
		}
		Graphics gImage = offScreemImage.getGraphics();
		gImage.setColor(Color.gray);
		gImage.fillRect(0, 0, width, height);
		gImage.setColor(Color.blue);
		gImage.setFont(new Font("仿宋", Font.BOLD, 50));
		if(state == 0) {
		
		gImage.drawString("选择游戏模式", 220, 100);
		gImage.drawString("单人模式", 220, 200);
		gImage.drawString("双人模式", 220, 300);
		gImage.drawImage(select, 160, y, null);
		} else if(state == 1 || state == 2) {
			gImage.drawString("游戏开始", 220, 100);
			if(state == 1) {
				gImage.drawString("单人模式", 220, 200);
			}else {
				gImage.drawString("双人模式", 220, 200);
			}
			playerOne.paintSelf(gImage);
		}
		g.drawImage(offScreemImage,0,0,null);
	}
	
	private class KeyMonitor extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			switch(key) {
			case KeyEvent.VK_1:
				a = 1;
				y = 150;
				break;
			case KeyEvent.VK_2:
				a = 2;
				y = 250;
				break;
			case KeyEvent.VK_ENTER:
				state = a;
				break;
			default:
				playerOne.keyPressed(e);
			}
			System.out.print(e.getKeyChar());
		}
		
		public void keyReleased(KeyEvent e) {
			playerOne.keyReleased(e);
			
		}
	}
	
	public static void main(String[] args) {
		GamePanel gp = new GamePanel();
		gp.launch();
	}

}
