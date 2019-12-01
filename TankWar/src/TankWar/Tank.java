package TankWar;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * 
 * @author ������
 * ѧ�ţ�031702411
 * �������̹���ӵ���
 * 
 */

public class Tank {
	//̹���ƶ��ٶ�
	public static final int XSPEED=5;
	public static final int YSPEED=5;
	//̹�˵�����״̬
	private boolean live=true;
	private int life=2;//̹�˵�����ֵ,��̹ͨ��Ϊ2��������̹��Ϊ1������̹��Ϊ3

	TankClient tc;
	
	private boolean good;//�жϵ���̹�ˣ�trueΪ�ҷ�̹�ˣ�falseΪ�з�̹��
	
	//̹��λ��
	private int x,y;
	private int oldX,oldY;//̹����һ����λ��
	
	private static Random r=new Random();
	
	private boolean bL=false,bU=false,bR=false,bD=false;//���̰������
	
	private Direction dir=Direction.STOP;//̹��״̬��ʼΪSTOP
	private Direction ptDir;//��Ͳ�ķ���
	
	private int step=r.nextInt(12)+3;//�洢3-15��һ�������
	
	private static Toolkit tk=Toolkit.getDefaultToolkit();
	private static Image[] tankImages=null;
	private static Map<String,Image> imgs=new HashMap<String,Image>();
	
	static {
		tankImages=new Image[] {
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankL.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankR.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankD.gif"))
		};
		
		imgs.put("L", tankImages[1]);
		imgs.put("R", tankImages[2]);
		imgs.put("U", tankImages[0]);
		imgs.put("D", tankImages[3]);
	}
	
	public Tank(int x,int y,boolean good) {
		this.x=x;
		this.y=y;
		this.good=good;
		if(good)ptDir=Direction.U;
		else ptDir=Direction.D;
		this.oldX=x;
		this.oldY=y;
	}
	
	public Tank(int x,int y,boolean good,Direction dir,TankClient tc) {
		this(x,y,good);
		this.tc=tc;
		this.dir=dir;
	}
	/**
	 * ��������ǻ���̹��
	 * @param g  ���ڵĻ���
	 */
	public void draw(Graphics g) {
		if(!live) {
			if(!good) {
				tc.tanks.remove(this);
			}
			return;
		}
		 
		 switch(ptDir){
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
	 * ���������̹�˵��ƶ�
	 */
	void move() {
		this.oldX=x;
		this.oldY=y;
		
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
			case STOP:
				break;
		}
		
		if(this.dir!=Direction.STOP) {
			this.ptDir=this.dir;
		}
		/*
		 * ̹�˵��ﴰ�ڱ�Եʱͣ��ԭ��
		 */
		if(x<0)x=0;
		if(y<30)y=30;
		if(x+tankImages[0].getWidth(null) > TankClient.GAME_WIDTH)
			x=TankClient.GAME_WIDTH-tankImages[0].getWidth(null);
		if(y+tankImages[0].getHeight(null)>TankClient.GAME_HEIGHT)
			y=TankClient.GAME_HEIGHT-tankImages[0].getHeight(null);
		/*
		 * �õз�̹���ھ���step�ε��ػ�������ı䷽��
		 */
		if(!good) {
			Direction[] dirs=Direction.values();
			if(step==0){
				step=r.nextInt(12)+3;
				int rn=r.nextInt(dirs.length);
				dir=dirs[rn];
			}
			step--;
			if(r.nextInt(40)>38)this.fire();//�õз�̹����������ӵ�
		}
		
	}
	
	public void setY(int y) {
		this.y = y;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	private void stay() {
		this.x=oldX;
		this.y=oldY;
	}
	/**
	 * 
	 * ���������ͨ�����¼��̸ı�̹�˷���
	 */
	public void keyPressed(KeyEvent e) {
		int key=e.getKeyCode();
		if(key==KeyEvent.VK_RIGHT) {
			bR=true;
		}
		else if(key==KeyEvent.VK_LEFT) {
			bL=true;
		}
		else if(key==KeyEvent.VK_UP) {
			bU=true;
		}
		else if(key==KeyEvent.VK_DOWN) {
			bD=true;
		}
		else if(key==KeyEvent.VK_F2) {
			if(!this.live) {
				this.live=true;
				this.life=5;
				this.x=300;
				this.y=800;
			}
		}
		
		locateDirection();
	}
	
	void locateDirection() {
		if(bL&&!bR&&!bU&&!bD) dir=Direction.L;
		else if(!bL&&bR&&!bU&&!bD) dir=Direction.R;
		else if(!bL&&!bR&&bU&&!bD) dir=Direction.U;
		else if(!bL&&!bR&&!bU&&bD) dir=Direction.D;
		else if(!bL&&!bR&&!bU&&!bD) dir=Direction.STOP;
	}
/**
 * ��������ǵ�̧����̲��ٸı�̹�˷���
 * �Լ�̧��ctrl ��ʱ�����ӵ�
 * @param e
 */
	public void keyReleased(KeyEvent e) {
		int key=e.getKeyCode();
		if(key==KeyEvent.VK_RIGHT) {
			bR=false;
		}
		else if(key==KeyEvent.VK_LEFT) {
			bL=false;
		}
		else if(key==KeyEvent.VK_UP) {
			bU=false;
		}
		else if(key==KeyEvent.VK_DOWN) {
			bD=false;
		}
		else if(key==KeyEvent.VK_CONTROL) {
			fire();
		}
		locateDirection();
	}


	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	/**
	 * ���������̹�˷����ӵ�
	 * @return  ���ط�������ӵ�
	 */
	public Missile fire(){
		if(!live)return null;
		int x=this.x+tankImages[0].getWidth(null)/2-Missile.WIDTH/2;
		int y=this.y+tankImages[0].getHeight(null)/2-Missile.HEIGHT/2;
		Missile m=new Missile(x,y,good,ptDir,this.tc);
		tc.missiles.add(m);
		return m;
	}
	
	public Rectangle getRect() {
		return new Rectangle(x,y,tankImages[0].getWidth(null),tankImages[0].getHeight(null));
	}
	
	public boolean isGood() {
		return good;
	}
	/**
	 * ��������������ж�̹���Ƿ�ײ����ǽ��
	 * @param w   ײ����ǽ��
	 * @return    �Ƿ�ײ����ǽ�壬���Ƿ���true�����򷵻�false
	 */
	public boolean collidesWithWall(Wall w) {
		if(this.live&&this.getRect().intersects(w.getRect())) {
			this.stay();
			return true;
		}
		return false;
	}
	/**
	 * ��������������ж�̹���Ƿ�ײ����̹��
	 * @param w   ײ����̹��
	 * @return    �Ƿ�ײ����̹�ˣ����Ƿ���true�����򷵻�false
	 */
	public boolean collideaWithTank(java.util.List<Tank> tanks) {
		for(int i=0;i<tanks.size();i++) {
			Tank t=tanks.get(i);
			if(this!=t) {
				if(this.live&&t.isLive()&&this.getRect().intersects(t.getRect())) {
					this.stay();
					t.stay();
					return true;
				}
			}
		}
		return false;
	}
	

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
	/**
	 * ����������ж��Ƿ�Ե����������ĵ��ߣ����Ե�����ֵ��������
	 * @param b  ��������ֵ�ĵ���
	 * @return   �Ƿ�Ե����ߣ����Ƿ���true�����򷵻�false
	 */
	public boolean eat(Blood b) {
		if(this.live&&b.isLive()&&this.getRect().intersects(b.getRect())) {
			this.life+=1;
			b.setLive(false);
			return true;
		}
		return false;
	}
	
}
