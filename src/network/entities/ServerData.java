package network.entities;

import java.io.Serializable;

public class ServerData implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public int ballX;
	public int ballY;
	public int player1X;
	public int player1Y;
	public int player2X;
	public int player2Y;
	public boolean p1IsJumping;
	public boolean p1IsPunching;
	public boolean p2IsJumping;
	public boolean p2IsPunching;
	public String p1Powerup;
	public int p1PowerupX;
	public int p1PowerupY;
	public String p2Powerup;
	public int p2PowerupX;
	public int p2PowerupY;
	public String powUpTopMsg;
	public String powUpBotMsg;
	public int p1Hearts;
	public int p2Hearts;
	public boolean p1IsAlive;
	public boolean p2IsAlive;
	public boolean p1IsInvincible;
	public boolean p2IsInvincible;
	public int countdown;
	public int round;

	public ServerData(int ballX, int ballY, int player1X, int player1Y, int player2X, int player2Y, boolean p1IsJumping,
			boolean p1IsPunching, boolean p2IsJumping, boolean p2IsPunching, String p1Powerup, int p1PowerupX,
			int p1PowerupY, String p2Powerup, int p2PowerupX, int p2PowerupY, String powUpTopMsg, String powUpBotMsg,
			int p1Hearts, int p2Hearts, boolean p1IsAlive, boolean p2IsAlive, boolean p1IsInvincible,
			boolean p2IsInvincible, int countdown, int round) {
		this.ballX = ballX;
		this.ballY = ballY;
		this.player1X = player1X;
		this.player1Y = player1Y;
		this.player2X = player2X;
		this.player2Y = player2Y;
		this.p1IsJumping = p1IsJumping;
		this.p1IsPunching = p1IsPunching;
		this.p2IsJumping = p2IsJumping;
		this.p2IsPunching = p2IsPunching;
		this.p1Powerup = p1Powerup;
		this.p1PowerupX = p1PowerupX;
		this.p1PowerupY = p1PowerupY;
		this.p2Powerup = p2Powerup;
		this.p2PowerupX = p2PowerupX;
		this.p2PowerupY = p2PowerupY;
		this.powUpTopMsg = powUpBotMsg;
		this.powUpBotMsg = powUpBotMsg;
		this.p1Hearts = p1Hearts;
		this.p2Hearts = p2Hearts;
		this.p1IsAlive = p1IsAlive;
		this.p2IsAlive = p2IsAlive;
		this.p1IsInvincible = p1IsInvincible;
		this.p2IsInvincible = p2IsInvincible;
		this.countdown = countdown;
		this.round = round;
	}
}
