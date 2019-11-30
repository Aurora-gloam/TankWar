package TankWar;

import java.io.IOException;
import java.util.Properties;
/**
 * 这个类是配置文件管理类
 * @author 杨文燕
 * 学号：031702411
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
	* 返回配置文件中key对应的值
	 */
	public static String getProperty(String key) {
		return props.getProperty(key);
	}
}
