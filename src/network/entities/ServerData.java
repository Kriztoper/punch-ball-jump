package network.entities;

import java.io.Serializable;

public class ServerData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int[] ball;
	private int[] player1;
	private int[] player2;
	private int[] powerups_top;
	private int[] powerups_bot;
	private String[] powerup_type;
	private int[] players_hearts;
	private int key_pressed;
	
	public ServerData(int[] ball, int[] player1, int[] player2, int[] powerups_top, int[] powerups_bot,
			String[] powerup_type, int[] players_hearts, int key_pressed) {
		this.ball = ball;
		this.player1 = player1;
		this.player2 = player2;
		this.powerups_top = powerups_top;
		this.powerups_bot = powerups_bot;
		this.powerup_type = powerup_type;
		this.players_hearts = players_hearts;
		this.key_pressed = key_pressed;
	}

	public int[] getBall() {
		return ball;
	}

	public int[] getPlayer1() {
		return player1;
	}

	public int[] getPlayer2() {
		return player2;
	}

	public int[] getPowerups_top() {
		return powerups_top;
	}

	public int[] getPowerups_bot() {
		return powerups_bot;
	}

	public String[] getPowerup_type() {
		return powerup_type;
	}

	public int[] getPlayers_hearts() {
		return players_hearts;
	}

	public int getKey_pressed() {
		return key_pressed;
	}
}
