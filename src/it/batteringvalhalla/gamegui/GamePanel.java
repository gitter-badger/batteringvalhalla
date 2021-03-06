package it.batteringvalhalla.gamegui;

import it.batteringvalhalla.gamecore.GameManager;
import it.batteringvalhalla.gamecore.GameWorld;
import it.batteringvalhalla.gamecore.input.InputHandler;
import it.batteringvalhalla.gamecore.object.actor.Direction;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1568479702061945112L;
	GameFrame frame;
	GameWorld world;
	GameManager manager;
	InputHandler inputkey;

	public GamePanel(GameFrame frame) {
		super();
		this.frame = frame;
		this.setOpaque(true);
		this.setPreferredSize(new Dimension(1024, 768));
		this.setFocusable(true);
		this.setFocusTraversalKeysEnabled(true);
		this.setVisible(true);
		inputkey = new InputHandler();
		addKeyListener(inputkey);
		init();

	}

	private void init() {
		manager = new GameManager(this);
		world = manager.getWorld();
		manager.init();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		world.paint(g);
		paintUI(g);
	}

	public GameManager getManager() {
		return manager;
	}

	public void getInput() {
		List<Boolean> keys = inputkey.getKeys();
		Boolean moving = new Boolean(false);
		if (world.getState() == 1) {
			if (keys.get(0)) {
				moving = true;
				world.getPlayer().setDirection(Direction.nord);
			}
			if (keys.get(1)) {
				moving = true;
				world.getPlayer().setDirection(Direction.sud);
			}
			if (keys.get(2)) {
				moving = true;
				world.getPlayer().setDirection(Direction.est);
			}
			if (keys.get(3)) {
				moving = true;
				world.getPlayer().setDirection(Direction.ovest);
			}
		}
		if (keys.get(4))
			if (world.getState() == 1) {
				world.setState(2);
			} else {
				world.setState(1);
			}
		if (!moving)
			world.getPlayer().setDirection(Direction.stop);
	}

	public void resetInput() {
		inputkey.resetKeys();
	}

	private void paintUI(Graphics g) {
		g.setColor(Color.BLACK);
		g.setFont(new Font("Serif", Font.ITALIC, 30));
		g.drawString("Live:" + world.getPlayer().getLive(), 30, 70);

		g.setColor(Color.BLACK);
		g.setFont(new Font("Serif", Font.BOLD, 30));
		g.drawString("Match:" + world.getMatch().toString(), 150, 70);
	}

	public Integer paintRestartPrompt() {
		switch (JOptionPane
				.showConfirmDialog(null, "You survived to " + world.getMatch()
						+ " matchs. " + "Retry ?", "Game Over",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)) {
		case JOptionPane.YES_OPTION:
			return 1;
		case JOptionPane.NO_OPTION:
			return 0;
		default:
			break;
		}

		return -1;

	}
}
