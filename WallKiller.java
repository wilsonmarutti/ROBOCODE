package schoolbots;
import robocode.*;
import java.awt.Color;

public class WallKiller extends Robot {

	boolean peek;
	double moveAmount;

	public void run() {
	setColors(Color.blue,Color.yellow,Color.green);
	
	moveAmount = Math.max(getBattleFieldWidth(), getBattleFieldHeight());
	peek = false;
	
	turnLeft(getHeading() %90);
	ahead(moveAmount);
	peek = true;
	turnGunRight(0);
	turnLeft(90);

		while(true) {
			peek = true;
			ahead(moveAmount);
			peek = false;
			turnLeft(90);		
		}
	}
	
	public void onHitRobot(HitRobotEvent e) {
		if(e.getBearing() > -90 && e.getBearing() < 90) {
			fire(6);
			ahead(moveAmount);
		}
	}


	public void onScannedRobot(ScannedRobotEvent e) {	
		fire(6);
		if (peek) {
			scan();
			turnRadarRight(anguloRelativo(e.getBearing()+getHeading()-getRadarHeading())); //para mirar o radar no adversário.
			turnGunRight(anguloRelativo(e.getBearing()+getHeading()-getGunHeading()));  //para mirar o canhão no adversário.
			turnRight(anguloRelativo(e.getBearing())); //para virar seu robô em direção do adversário
		}
	}
	
	public void onHitByBullet(HitByBulletEvent e) {
		turnLeft(90);
		if(peek){
			fire(10);
		}
	}
	
	public void onHitWall(HitWallEvent e) {
		double x = getX();
		double y = getY();
		double m = getBattleFieldWidth();
		double n = getBattleFieldHeight();
		if((x==m)||(x==0)){
			ahead(100);
		
		}
		if((y==n)||(y==0)){
			back(40);
		}
	}
	public double anguloRelativo(double ANG) {
		if (ANG> -180 && ANG<= 180) {
			return ANG;
		}
		double REL = ANG;
		while (REL<= -180) {
			REL += 360;
		}
		while (ANG> 180) {
			REL -= 360;
		}
		return REL;
	}	
	
	public void mira(double Adv) {
		double A=getHeading()+Adv-getGunHeading();
		if (!(A > -180 && A <= 180)) {
			while (A <= -180) {
				A += 360;
			}
			while (A > 180) {
				A -= 360;
			}
		}
		turnGunRight(A);
	}
}
