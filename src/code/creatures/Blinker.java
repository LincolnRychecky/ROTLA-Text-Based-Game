package code.creatures;

import code.Board;
import code.Room;

import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Random;

public class Blinker extends Creature {

    // Here, the method move demonstrates effective polymorphism. Based on the rules of the game,
    // the movement behaviour is different for each type of creature. However, when each creature is
    // accessed by GameEngine, same method move() is used to start movement of a creature.
    @Override
    public void move(Room room) {
        movedThisTurn = true;
        Room currentRoom = room;
        String previousRoom = currentRoom.getCordinateString();

        if (currentRoom.adventurers.size() == 0) {

            Random random = new Random();

            // Blinker moves randomly across the floors and among the rooms on the floor

            int[] newLocation = new int[]{random.nextInt(1,5), random.nextInt(0,3), random.nextInt(0,3)};

            List<Room> nextRoom = Board.rooms.stream().filter(eachRoom -> Arrays.equals(eachRoom.location, newLocation)).toList();
            if(nextRoom.size()>0){
                currentRoom = nextRoom.get(0);
                room.creatures.remove(this);
                currentRoom.creatures.add(this);
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("detail", currentRoom.getCordinateString());
                data.put("previous room", previousRoom);
                data.put("character", this.getClass().getSimpleName());
                data.put("action type", "moved to");
                setLastAction(data);
            }
        }

        fight(currentRoom);
    }

    @Override
    public int[] determineStartingLocation() {
        Random random = new Random();
        // Blinker starts on 4th floor
        return new int[]{4, random.nextInt(0,3), random.nextInt(0,3)};
    }
}
