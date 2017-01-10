package manager;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

import event.BulletCollisionEvent;
import event.CollisionEvent;
import event.Event;
import event.PlayerInputEvent;
import event.ShootEvent;
import listener.BulletCollisionHandler;
import listener.CollisionHandler;
import listener.EventHandler;
import listener.PlayerInputHandler;
import listener.ShootHandler;
import model.GameCharacter;
import model.Enemy;

public class EventManager {
	// Hold all the things that need to be notified of events
	HashMap<String, LinkedList<EventHandler>> handlers;
	PriorityQueue<Event> events;
	String characterId;

	public static final String COLLISION = "Collision";
	public static final String PLAYERINPUT = "PlayerInput";
	public static final String BULLETCOLLISION = "BulletCollision";
	public static final String SHOOT = "Shoot";
	public static final String FALL = "Fall";

	// Initialize the hashmap for all events
	public EventManager() {
		handlers = new HashMap<String, LinkedList<EventHandler>>();
		events = new PriorityQueue<Event>();
	}
	
	public PriorityQueue<Event> getEvents() {
		return events;
	}

	public GameCharacter getCharacter(String guid) {
		LinkedList<EventHandler> list = handlers.get(PLAYERINPUT);
		for (int i = 0; i < list.size(); i++) {
			if (((GameCharacter) list.get(i)).getGuid().equals(characterId)) {
				return (GameCharacter) list.get(i);
			}
		}
		return null;
	}
	
	public void removeDead() {
		for (int i = 0; i < handlers.get(BULLETCOLLISION).size(); i++) {
			if (!((Enemy) handlers.get(BULLETCOLLISION).get(i)).alive)
					handlers.get(BULLETCOLLISION).remove(i);
		}
	}

	// Register methods
	public void registerForCollision(CollisionHandler handler) {
		if (!handlers.containsKey(COLLISION))
			handlers.put(COLLISION, new LinkedList<EventHandler>());
		handlers.get(COLLISION).add(handler);
	}
	
	public void registerForPlayerInput(PlayerInputHandler handler) {
		if (handler instanceof GameCharacter)
			characterId = ((GameCharacter) handler).getGuid();
		if (!handlers.containsKey(PLAYERINPUT))
			handlers.put(PLAYERINPUT, new LinkedList<EventHandler>());
		handlers.get(PLAYERINPUT).add(handler);
	}
	
	public void registerForShoot(ShootHandler handler) {
		if (!handlers.containsKey(SHOOT))
			handlers.put(SHOOT, new LinkedList<EventHandler>());
		handlers.get(SHOOT).add(handler);
	}
	
	public void registerForBulletCollision(BulletCollisionHandler handler) {
		if (!handlers.containsKey(BULLETCOLLISION))
			handlers.put(BULLETCOLLISION, new LinkedList<EventHandler>());
		handlers.get(BULLETCOLLISION).add(handler);
	}

	// Add an event to the queue
	public void addEvent(Event e) {
		events.add(e);
	}

	// Notify handlers
	public void handleEvent(long currentTime, int priority) {
		if (!events.isEmpty()) {
			Event e = events.peek();
			LinkedList<EventHandler> list;
			if (priority == 2 && e.getPriority() == 2) {
				list = handlers.get(PLAYERINPUT);
				for (EventHandler handler: list) {
					if (((PlayerInputEvent) e).getKey() == 'l') {
						ScriptManager.loadScript("scripts/playerLeft.js");
					}
					else {
						ScriptManager.loadScript("scripts/playerRight.js");
					}
					ScriptManager.bindArgument("game_object", handler);
					ScriptManager.executeScript();
				}
				events.remove();
			}
			else if (priority == 3 && e.getPriority() == 3) {
				list = handlers.get(COLLISION);
				for (EventHandler handler: list) {
					((CollisionHandler) handler).onEvent((CollisionEvent) e);
				}
				events.remove();
			}
			else if (priority == 4 && e.getPriority() == 4) {
				list = handlers.get(BULLETCOLLISION);
				for (EventHandler handler: list) {
					((BulletCollisionHandler) handler).onEvent((BulletCollisionEvent) e);
				}
				events.remove();
			}
			else if (priority == 5 && e.getPriority() == 5) {
				list = handlers.get(SHOOT);
				for (EventHandler handler: list) {
					((ShootHandler) handler).onEvent((ShootEvent) e);
				}
				events.remove();
			}
		}
	}
}
