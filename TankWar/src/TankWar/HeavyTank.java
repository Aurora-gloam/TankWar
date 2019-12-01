package TankWar;

public class HeavyTank extends EnemyTank {

	public HeavyTank(int x, int y, boolean good, Direction dir, TankClient tc) {
		super(x, y, good, dir, tc);
		this.setLife(3);
		this.setSpeed(2,2);
	}
}
