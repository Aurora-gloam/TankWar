package TankWar;

import java.awt.Graphics;
import java.awt.Rectangle;
/**
 * �������ǽ����
 * @author ������
 * ѧ�ţ�031702411
 *
 */
public class Wall {
	int x,y,w,h;//ǽ���λ���Լ���ȡ��߶�
	TankClient tc;
	
	public Wall(int x,int y,int w,int h,TankClient tc) {
		this.h=h;
		this.w=w;
		this.x=x;
		this.y=y;
		this.tc=tc;
	}
	/**
	 * ��������ǻ���ǽ��
	 * @param g  ���ڵĻ���
	 */
	public void draw(Graphics g) {
		g.fillRect(x, y, w, h);
	}
	
	public Rectangle getRect() {
		return new Rectangle(x,y,w,h);
		
	}
}
