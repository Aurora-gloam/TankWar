package TankWar;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;
/**
 * �������ǽ����
 * @author ������
 * ѧ�ţ�031702411
 *
 */
public class Wall {
	int x,y,w,h;//ǽ���λ���Լ���ȡ��߶�
	TankClient tc;
	String type=null;

	private boolean live=true;

	private static Toolkit tk=Toolkit.getDefaultToolkit();
	private static Image[] wallImages=null;
	private static Map<String,Image> imgs=new HashMap<String,Image>();
	
	static {
		wallImages=new Image[] {
				tk.getImage(Tank.class.getClassLoader().getResource("images/grass.png")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/steels.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/walls.gif"))
		};
		
		imgs.put("grass", wallImages[0]);
		imgs.put("steel", wallImages[1]);
		imgs.put("wall", wallImages[2]);

	}
	
	public Wall(int x,int y,String type) {
		this.x=x;
		this.y=y;
		this.type=type;
	}
	/**
	 * ��������ǻ���ǽ��
	 * @param g  ���ڵĻ���
	 */
	public void draw(Graphics g) {
		if(!this.live)return;
		 switch(type){
			case "grass":
				g.drawImage(imgs.get("grass"),x,y,null);
				break;
			case "steel":
				g.drawImage(imgs.get("steel"),x,y,null);
				break;
			case "wall":
				g.drawImage(imgs.get("wall"),x,y,null);
				break;
		 }
	}
	
	public boolean isLive() {
		return live;
	}
	public void setLive(boolean live) {
		this.live=live;
	}
	
	public String getType() {
		return type;
	}
	
	public Rectangle getRect() {
		return new Rectangle(x,y,wallImages[0].getWidth(null),wallImages[0].getHeight(null));
		
	}
}
