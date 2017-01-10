package networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import event.BulletCollisionEvent;
import event.CollisionEvent;
import event.PlayerInputEvent;
import event.ShootEvent;
import manager.EventManager;
import model.Bullet;
import model.Enemy;
import model.GameCharacter;
import processing.core.PApplet;
import time.BaseTimeline;
import time.GenTimeline;

public class Client extends PApplet {
	int width = 600;
	int height = 600;
	int inc = 50;
	int enemyStart = 50;
	int playerY = 500;
	int round = 0;
	int coolDown = 0;

	BaseTimeline base;
	GenTimeline gameTimeline;

	EventManager manager;
	GameCharacter character;
	ArrayList<Enemy> enemies;

	Socket socket;
	ObjectOutputStream out;
	ObjectInputStream in;
	HashMap<String, GameCharacter> characters;

	public static final int SERVER_PORT = 7779;
	public static final String HOME = "127.0.0.1";

	public static void main(String[] args) {
		PApplet.main("networking.Client");
	}

	public void settings() {
		size(width, height);
	}

	public void setup() {
		// Set up timelines
		base = new BaseTimeline();
		gameTimeline = new GenTimeline(2, base);

		// Create events manager
		manager = new EventManager();

		// Set up networking
		try {
			socket = new Socket(HOME, SERVER_PORT);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.out.println("Port already in use.");
		}

		// Make all game objects
		character = new GameCharacter(this, 50, playerY, 50, 50, true);
		characters = new HashMap<String, GameCharacter>();
		enemies = new ArrayList<Enemy>();

		int start = 50;
		for (int i = 0; i < 5; i++) {
			enemies.add(new Enemy(this, start, 50, 30, 30, true));
			start += 50;
		}

		start = 50;
		for (int i = 0; i < 5; i++) {
			enemies.add(new Enemy(this, start, 100, 30, 30, true));
			start += 50;
		}

		// Register for events
		manager.registerForCollision(character);
		manager.registerForPlayerInput(character);
		manager.registerForShoot(character);
		manager.registerForPlayerInput(character);

		for (Enemy enemy: enemies)
			manager.registerForBulletCollision(enemy);
	}

	@SuppressWarnings("unchecked")
	public void draw() {
		// Update player
		character = manager.getCharacter(character.getGuid());
		// Check for restart
		if (character.restart || enemies.isEmpty()) {
			character.restart = false;
			character.respawn();
			character.getPlayerBullets().clear();
			enemies.clear();
			manager.getEvents().clear();
			
			int start = 50;
			for (int i = 0; i < 5; i++) {
				enemies.add(new Enemy(this, start, 50, 30, 30, true));
				start += 50;
			}

			start = 50;
			for (int i = 0; i < 5; i++) {
				enemies.add(new Enemy(this, start, 100, 30, 30, true));
				start += 50;
			}
			
			for (Enemy enemy: enemies)
				manager.registerForBulletCollision(enemy);
		}

		// Increment time
		base.incTime();

		if (coolDown > 0)
			coolDown--;
		if (coolDown == 0)
			character.canShoot = true;

		// Move enemies
		for (Enemy enemy: enemies) {
			if (round % 20 == 0) {
				enemy.move();
				// Shift down
				if (enemy.getX() - enemy.getStartX() >= 300) {
					enemy.setX(enemy.getStartX());
					enemy.setY(enemy.getY() + 50);
				}
			}
			if (enemy.getR().intersects(character.getR())) {
				manager.addEvent(new CollisionEvent(3, base.getTime()));
				return;
			}
		}

		// Check with bullets for enemy collision
		for (Enemy enemy: enemies) {
			for (Bullet bullet: character.getPlayerBullets()) {
				if (enemy.getR().intersects(bullet.getR())) {
					bullet.setUsed(true);
					manager.addEvent(new BulletCollisionEvent(4, gameTimeline.getTime(), enemy));
					//enemy.alive = false;
				}
			}
		}
		
		for (int j = 0; j < character.getPlayerBullets().size(); j++) {
			// Move the bullet
			character.getPlayerBullets().get(j).move();
			// Remove off screen bullets and used bullets
			if (character.getPlayerBullets().get(j).getY() < 0 || character.getPlayerBullets().get(j).isUsed()) {
				character.getPlayerBullets().remove(j);
			}
		}

		// Check if enemies are dead
		for (int i = 0; i < enemies.size(); i++) {
			if (!enemies.get(i).alive) {
				enemies.remove(i);
			}
		}
		manager.removeDead();

		// Handle events
		manager.handleEvent(gameTimeline.getTime(), 2);
		manager.handleEvent(gameTimeline.getTime(), 3);
		manager.handleEvent(gameTimeline.getTime(), 4);
		manager.handleEvent(gameTimeline.getTime(), 5);

		// Actually display stuff
		background(0);
		character.display();
		for (Enemy enemy: enemies) {
			enemy.display();
		}
		for (Bullet bullet: character.getPlayerBullets()) {
			bullet.display();
		}

		round++;

		try {
			// Send stuff to server
			out.writeObject(character);
			out.reset();

			// Get input from server!
			characters = (HashMap<String, GameCharacter>) in.readObject();
			for (String key: characters.keySet()) {
				if (character.getGuid().equals(characters.get(key).getGuid())) {
					character = characters.get(key);
				}
				characters.get(key).parent = this;
			}

			for (String key: characters.keySet()) {
				characters.get(key).display();
			}

		} catch (IOException e) {
			e.printStackTrace();
			//System.out.println("Port already in use.");
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found.");
			e.printStackTrace();
		}
	}

	public void keyPressed() {
		// Check if the key is the space bar 
		if (key == ' ') {
			if (character.canShoot && coolDown == 0) {
				manager.addEvent(new ShootEvent(5, gameTimeline.getTime()));
				character.canShoot = false;
			}
			coolDown = 2;
		}
		if (key == CODED) {
			// Check if the key is the right arrow
			if (keyCode == RIGHT) {
				manager.addEvent(new PlayerInputEvent(2, gameTimeline.getTime(), 'r'));
			}
			// Check if the key is the left arrow
			if (keyCode == LEFT) {
				manager.addEvent(new PlayerInputEvent(2, gameTimeline.getTime(), 'l'));
			}
		}
	}

	public void keyReleased() {
		if (key == ' ') {
			character.canShoot = true;
		}
	}
}
