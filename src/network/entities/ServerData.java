package network.entities;

import java.io.Serializable;

public class ServerData implements Serializable {
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
	public boolean isPaused;

	public ServerData(String data) {
		try {
			String[] values = data.split(",");
			ballX = Integer.parseInt(values[0].trim());
			ballY = Integer.parseInt(values[1].trim());
			player1X = Integer.parseInt(values[2].trim());
			player1Y = Integer.parseInt(values[3].trim());
			player2X = Integer.parseInt(values[4].trim());
			player2Y = Integer.parseInt(values[5].trim());
			p1IsJumping = Boolean.valueOf(values[6].trim());
			p1IsPunching = Boolean.valueOf(values[7].trim());
			p2IsJumping = Boolean.valueOf(values[8].trim());
			p2IsPunching = Boolean.valueOf(values[9].trim());
			p1Powerup = values[10];
			p1PowerupX = Integer.parseInt(values[11].trim());
			p1PowerupY = Integer.parseInt(values[12].trim());
			p2Powerup = values[13];
			p2PowerupX = Integer.parseInt(values[14].trim());
			p2PowerupY = Integer.parseInt(values[15].trim());
			powUpTopMsg = values[16];
			powUpBotMsg = values[17];
			p1Hearts = Integer.parseInt(values[18].trim());
			p2Hearts = Integer.parseInt(values[19].trim());
			p1IsAlive = Boolean.valueOf(values[20].trim());
			p2IsAlive = Boolean.valueOf(values[21].trim());
			p1IsInvincible = Boolean.valueOf(values[22].trim());
			p2IsInvincible = Boolean.valueOf(values[23].trim());
			countdown = Integer.parseInt(values[24].trim());
			round = Integer.parseInt(values[25].trim());
			isPaused = Boolean.valueOf(values[26].trim());
		} catch (Exception e) {
		}
	}

	public ServerData(int ballX, int ballY, int player1X, int player1Y, int player2X, int player2Y, boolean p1IsJumping,
			boolean p1IsPunching, boolean p2IsJumping, boolean p2IsPunching, String p1Powerup, int p1PowerupX,
			int p1PowerupY, String p2Powerup, int p2PowerupX, int p2PowerupY, String powUpTopMsg, String powUpBotMsg,
			int p1Hearts, int p2Hearts, boolean p1IsAlive, boolean p2IsAlive, boolean p1IsInvincible,
			boolean p2IsInvincible, int countdown, int round, boolean isPaused) {
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
		this.powUpTopMsg = powUpTopMsg;
		this.powUpBotMsg = powUpBotMsg;
		this.p1Hearts = p1Hearts;
		this.p2Hearts = p2Hearts;
		this.p1IsAlive = p1IsAlive;
		this.p2IsAlive = p2IsAlive;
		this.p1IsInvincible = p1IsInvincible;
		this.p2IsInvincible = p2IsInvincible;
		this.countdown = countdown;
		this.round = round;
		this.isPaused = isPaused;
	}

	public String getCommaSeparatedStringData() {
		return ballX + "," + ballY + "," + player1X + "," + player1Y + "," + player2X + "," + player2Y + ","
				+ p1IsJumping + "," + p1IsPunching + "," + p2IsJumping + "," + p2IsPunching + "," + p1Powerup + ","
				+ p1PowerupX + "," + p1PowerupY + "," + p2Powerup + "," + p2PowerupX + "," + p2PowerupY + ","
				+ powUpTopMsg + "," + powUpBotMsg + "," + p1Hearts + "," + p2Hearts + "," + p1IsAlive + "," + p2IsAlive
				+ "," + p1IsInvincible + "," + p2IsInvincible + "," + countdown + "," + round + "," + isPaused;
	}
}
