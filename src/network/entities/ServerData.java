package network.entities;

import java.io.Serializable;

public class ServerData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int ballX, ballY;
	private int player1X, player1Y;
	private int player2X, player2Y;
	private int p1IsJumping, p1IsPunching;
	private int p2IsJumping, p2IsPunching;
	private String p1Powerup, p2Powerup;
	private int p1PowerupX, p1PowerupY;
	private int p2PowerupX, p2PowerupY;
	private String powUpTopMsg, powUpBotMsg;
	private int p1Hearts, p2Hearts;
	private boolean p1IsAlive, p2IsAlive;
	private boolean p1IsInvincible, p2IsInvincible;
	int countdown, round;
	
	public ServerData() {
		
	}

	public int getBallX() {
		return ballX;
	}

	public int getBallY() {
		return ballY;
	}

	public int getPlayer1X() {
		return player1X;
	}

	public int getPlayer1Y() {
		return player1Y;
	}

	public int getPlayer2X() {
		return player2X;
	}

	public int getPlayer2Y() {
		return player2Y;
	}

	public int getP1IsJumping() {
		return p1IsJumping;
	}

	public int getP1IsPunching() {
		return p1IsPunching;
	}

	public int getP2IsJumping() {
		return p2IsJumping;
	}

	public int getP2IsPunching() {
		return p2IsPunching;
	}

	public String getP1Powerup() {
		return p1Powerup;
	}

	public String getP2Powerup() {
		return p2Powerup;
	}

	public int getP1PowerupX() {
		return p1PowerupX;
	}

	public int getP1PowerupY() {
		return p1PowerupY;
	}

	public int getP2PowerupX() {
		return p2PowerupX;
	}

	public int getP2PowerupY() {
		return p2PowerupY;
	}

	public String getPowUpTopMsg() {
		return powUpTopMsg;
	}



	public String getPowUpBotMsg() {
		return powUpBotMsg;
	}

	public int getP1Hearts() {
		return p1Hearts;
	}

	public int getP2Hearts() {
		return p2Hearts;
	}

	public boolean isP1IsAlive() {
		return p1IsAlive;
	}

	public boolean isP2IsAlive() {
		return p2IsAlive;
	}

	public boolean isP1IsInvincible() {
		return p1IsInvincible;
	}

	public boolean isP2IsInvincible() {
		return p2IsInvincible;
	}

	public int getCountdown() {
		return countdown;
	}

	public int getRound() {
		return round;
	}

	public void setBallX(int ballX) {
		this.ballX = ballX;
	}

	public void setBallY(int ballY) {
		this.ballY = ballY;
	}

	public void setPlayer1X(int player1x) {
		player1X = player1x;
	}

	public void setPlayer1Y(int player1y) {
		player1Y = player1y;
	}

	public void setPlayer2X(int player2x) {
		player2X = player2x;
	}

	public void setPlayer2Y(int player2y) {
		player2Y = player2y;
	}

	public void setP1IsJumping(int p1IsJumping) {
		this.p1IsJumping = p1IsJumping;
	}

	public void setP1IsPunching(int p1IsPunching) {
		this.p1IsPunching = p1IsPunching;
	}

	public void setP2IsJumping(int p2IsJumping) {
		this.p2IsJumping = p2IsJumping;
	}

	public void setP2IsPunching(int p2IsPunching) {
		this.p2IsPunching = p2IsPunching;
	}

	public void setP1Powerup(String p1Powerup) {
		this.p1Powerup = p1Powerup;
	}

	public void setP2Powerup(String p2Powerup) {
		this.p2Powerup = p2Powerup;
	}

	public void setP1PowerupX(int p1PowerupX) {
		this.p1PowerupX = p1PowerupX;
	}

	public void setP1PowerupY(int p1PowerupY) {
		this.p1PowerupY = p1PowerupY;
	}

	public void setP2PowerupX(int p2PowerupX) {
		this.p2PowerupX = p2PowerupX;
	}

	public void setP2PowerupY(int p2PowerupY) {
		this.p2PowerupY = p2PowerupY;
	}

	public void setPowUpTopMsg(String powUpTopMsg) {
		this.powUpTopMsg = powUpTopMsg;
	}

	public void setPowUpBotMsg(String powUpBotMsg) {
		this.powUpBotMsg = powUpBotMsg;
	}

	public void setP1Hearts(int p1Hearts) {
		this.p1Hearts = p1Hearts;
	}

	public void setP2Hearts(int p2Hearts) {
		this.p2Hearts = p2Hearts;
	}

	public void setP1IsAlive(boolean p1IsAlive) {
		this.p1IsAlive = p1IsAlive;
	}

	public void setP2IsAlive(boolean p2IsAlive) {
		this.p2IsAlive = p2IsAlive;
	}

	public void setP1IsInvincible(boolean p1IsInvincible) {
		this.p1IsInvincible = p1IsInvincible;
	}

	public void setP2IsInvincible(boolean p2IsInvincible) {
		this.p2IsInvincible = p2IsInvincible;
	}

	public void setCountdown(int countdown) {
		this.countdown = countdown;
	}

	public void setRound(int round) {
		this.round = round;
	}

}
