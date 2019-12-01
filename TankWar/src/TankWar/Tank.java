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

public interface Tank {
	/**
	 * ��������ǻ���̹��
	 * @param g  ���ڵĻ���
	 */
	public void draw(Graphics g);
	/**
	 * ���������̹�˵��ƶ�
	 */
	public void move();
	public void stay() ;
	public void setY(int y);

	public void setX(int x);
	
	
	void locateDirection();

	public boolean isLive();
	public void setLive(boolean live);

	/**
	 * ���������̹�˷����ӵ�
	 * @return  ���ط�������ӵ�
	 */
	public Missile fire();
	
	public Rectangle getRect();
	public boolean isGood() ;
	/**
	 * ��������������ж�̹���Ƿ�ײ����ǽ��
	 * @param w   ײ����ǽ��
	 * @return    �Ƿ�ײ����ǽ�壬���Ƿ���true�����򷵻�false
	 */
	public boolean collideaWithWall(java.util.List<Wall> walls);
	/**
	 * ��������������ж�̹���Ƿ�ײ����̹��
	 * @param w   ײ����̹��
	 * @return    �Ƿ�ײ����̹�ˣ����Ƿ���true�����򷵻�false
	 */
	public boolean collideaWithTank(java.util.List<Tank> tanks) ;
	
	public int getLife() ;

	public void setLife(int life) ;
	/**
	 * ����������ж��Ƿ�Ե����������ĵ��ߣ����Ե�����ֵ��������
	 * @param b  ��������ֵ�ĵ���
	 * @return   �Ƿ�Ե����ߣ����Ƿ���true�����򷵻�false
	 */
	public boolean eat(Blood b) ;
	/**
	 * ��������ǵ�̧����̲��ٸı�̹�˷���
	 * �Լ�̧��ո� ��ʱ�����ӵ�
	 * @param e
	 */
	public void keyReleased(KeyEvent e);
	/**
	 * 
	 * ���������ͨ�����¼��̸ı�̹�˷���
	 */
	public void keyPressed(KeyEvent e);
	
}
