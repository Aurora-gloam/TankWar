package TankWar;

import java.awt.Graphics;
import java.awt.Rectangle;
/**
 * 这个类是墙体类
 * @author 杨文燕
 * 学号：031702411
 *
 */
public class Wall {
	int x,y,w,h;//墙体的位置以及宽度、高度
	TankClient tc;
	
	public Wall(int x,int y,int w,int h,TankClient tc) {
		this.h=h;
		this.w=w;
		this.x=x;
		this.y=y;
		this.tc=tc;
	}
	/**
	 * 这个方法是画出墙体
	 * @param g  窗口的画笔
	 */
	public void draw(Graphics g) {
		g.fillRect(x, y, w, h);
	}
	
	public Rectangle getRect() {
		return new Rectangle(x,y,w,h);
		
	}
}
