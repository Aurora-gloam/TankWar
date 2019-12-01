package TankWar;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 
 * @author ������
 * ѧ�ţ�031702411
 * ��������ӵ���
 * 
 */
public class Missile {
	public static final int XSPEED=10;//�ӵ���X���ƶ�����
	public static final int YSPEED=10;//�ӵ���X���ƶ�����
	
	public static final int WIDTH=10;//�ӵ����
	public static final int HEIGHT=10;//�ӵ��߶�
	
	int x,y;//�ӵ�λ��
	Direction dir;//�ӵ�����
	
	private boolean good;//�ӵ���̹�˵�ͳһ����
	
	private boolean live=true;//�ӵ�������
	
	TankClient tc;//TankClient������
	
	private static Toolkit tk=Toolkit.getDefaultToolkit();
	private static Image[] missileImages=null;
	private static Map<String,Image> imgs=new HashMap<String,Image>();
	
	static {
		missileImages=new Image[] {
				tk.getImage(Tank.class.getClassLoader().getResource("images/MissileU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/MissileL.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/MissileR.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/MissileD.gif"))
		};
		
		imgs.put("L", missileImages[1]);
		imgs.put("R", missileImages[2]);
		imgs.put("U", missileImages[0]);
		imgs.put("D", missileImages[3]);
	}
	
	public boolean isLive() {//��ȡlive
		return live;
	}
	public void setLive(boolean live) {
		this.live = live;
	}
	public boolean isGood() {
		return good;
	}

	public Missile(int x, int y, Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	public Missile(int x, int y,boolean good, Direction dir,TankClient tc) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.tc=tc;
		this.good=good;
	}
	/**
	 * ��������ǻ����ӵ�
	 * @param g  ���ڵĻ���
	 */
	public void draw(Graphics g) {
		if(!live) {
			tc.missiles.remove(this);
			return;
		}
		
		switch(dir){
		case L:
			g.drawImage(imgs.get("L"),x,y,null);
			break;
		case R:
			g.drawImage(imgs.get("R"),x,y,null);
			break;
		case U:
			g.drawImage(imgs.get("U"),x,y,null);
			break;
		case D:
			g.drawImage(imgs.get("D"),x,y,null);
			break;
		case STOP:
			break;
	}
		
		move();
	}
	/**
	 * ����������ӵ����ƶ�
	 */
	void move() {
		switch(dir){
			case L:
				x-=XSPEED;
				break;
			case R:
				x+=XSPEED;
				break;
			case U:
				y-=YSPEED;
				break;
			case D:
				y+=YSPEED;
				break;
		}
		if(x<0||y<0||x>TankClient.GAME_WIDTH||y>TankClient.GAME_HEIGHT) {//���ӵ��ɳ����ڣ��ӵ���ʧ�����ڻ������ӵ�
			live=false;
		}
	}
	
	public Rectangle getRect() {
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}
	/**
	 * ��������Ǵ��̹�ˣ������ӵ���̹����ʧ
	 * @param t   �����̹��
	 * @return    �Ƿ���У����Ƿ���true������false
	 */
	public boolean hitTank(Tank t) {
		if(this.live&&this.getRect().intersects(t.getRect())&&t.isLive()&&this.good!=t.isGood()) {
				t.setLife(t.getLife()-1);
				if(t.getLife()<=0)
					t.setLive(false);
				else {
					if(t.isGood()) {
						t.setX(400);
						t.setY(600);
					}
				}
	
			this.live=false;
			Explode e=new Explode(x,y,tc);
			tc.explodes.add(e);
			return true;
		}
		return false;
	}
	
	public boolean hitTank(List<Tank> tanks) {
		for(int i=0;i < tanks.size();i++)
		{
			if(hitTank(tanks.get(i))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * ���������ײ��ǽ�壬ײ��ǽ�����ӵ���ʧ
	 * @param w   ײ������ǽ��
	 * @return    �Ƿ�ײ��ǽ�壬���Ƿ���true�����򷵻�false
	 */
	public boolean hitWall(Wall w) {
		if(this.live&&this.getRect().intersects(w.getRect())) {
			this.live=false;
			return true;
		}
		return false;
	}
	/**
	 * ����������ж�ײ���ӵ���������ӵ���ײ�������ӵ�����ʧ
	 * @param w   ײ������ǽ��
	 * @return    �Ƿ�ײ��ǽ�壬���Ƿ���true�����򷵻�false
	 */
	public boolean hitMissile(Missile m) {
		if(this.live&&this.getRect().intersects(m.getRect())&&this.good!=m.isGood()&&this!=m&&m.isLive()) {
			this.live=false;
			m.setLive(false);
			return true;
		}
		return false;
	}
	public boolean hitMissile(List<Missile> missiles) {
		for(int i=0;i < missiles.size();i++)
		{
			if(hitMissile(missiles.get(i))) {
				return true;
			}
		}
		return false;
	}
}
