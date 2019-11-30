package TankWar;

import java.io.IOException;
import java.util.Properties;
/**
 * ������������ļ�������
 * @author ������
 * ѧ�ţ�031702411
 *
 */
public class PropertyMgr {
	static Properties props=new Properties();
	
	static {
		try {
			props.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config/tank.properties"));
		} catch (IOException e1) {
		e1.printStackTrace();
		}
	}
	/**
	* ���������ļ���key��Ӧ��ֵ
	 */
	public static String getProperty(String key) {
		return props.getProperty(key);
	}
}
