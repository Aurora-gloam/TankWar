package TankWar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
/**
 * 
 * @author ������
 * ѧ�ţ�031702411
 * ���������������ֵ�ĵ�����
 * 
 */
public class Blood {
	int x,y,w,h;//���ߵ�λ�����ȡ��߶�
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
