package TankWar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
/**
 * 
 * @author 杨文燕
 * 学号：031702411
 * 这个类是增长生命值的道具类
 * 
 */
public class Blood {
	int x,y,w,h;//道具的位置与宽度、高度
	TankClient tc;
	private int life=100;//
	private boolean live=true;
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public Blood() {
		this.h=30;
		this.w=20;
		this.x=450;
		this.y=200;
	}
	
	public void draw(Graphics g) {
		if(!this.live)return;
		Color c=g.getColor();
		g.setColor(Color.MAGENTA);
		g.fillRect(x, y, w, h);
		g.setColor(c);
		
		this.life-=1;
		if(this.life<=0) {
			this.live=false;
		}
	}
	
	public Rectangle getRect() {
		return new Rectangle(x,y,w,h);
		
	}
}
