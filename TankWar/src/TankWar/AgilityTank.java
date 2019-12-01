package TankWar;

public class AgilityTank extends EnemyTank {
	
	public AgilityTank(int x, int y, boolean good, Direction dir, TankClient tc) {
		super(x, y, good, dir, tc);
		this.setLife(1);
	}
}
