package code.creatures;

import code.Board;
import code.Room;

import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Random;

public class Orbiter extends Creature {

    private final boolean clockwise;

    public Orbiter() {
        //Set the direction of Orbiter
        Random random = new Random();
        int toss = random.nextInt(0, 2);
        clockwise = toss == 1;
    }

    @Override
    public void move(Room room) {
        movedThisTurn = true;
        Room currentRoom = room;
        String previousRoom = currentRoom.getCordinateString();

        // if there are no adventurers in the present room, move to the next room
        if (currentRoom.adventurers.size() == 0) {
            int[] newLocation;
            int x = currentRoom.location[1];
            int y = currentRoom.location[2];
            int z = currentRoom.location[0];
            if (clockwise) {
                if (x == 0 && y != 2) {
                    newLocation = new int[]{z, x, y + 1}; // Move right
                } else if (y == 2 && x != 2) {
                    newLocation = new int[]{z, x + 1, y}; // move down
                } else if (x == 2 && y != 0) {
                    newLocation = new int[]{z, x, y - 1}; // move left
                } else {
                    newLocation = new int[]{z, x - 1, y}; // move up
                }
            } else {
                if (x == 0 && y != 0) {
                    newLocation = new int[]{z, x, y - 1}; // move left
                } else if (x!=2 && y == 0) {
                    newLocation = new int[]{z, x + 1, y}; // move up
                } else if (x == 2 && y != 2) {
                    newLocation = new int[]{z, x, y + 1}; // move right
                } else {
                    newLocation = new int[]{z, x - 1, y}; //move down
                }
            }
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
        int x = 1, y = 1;
        while (x == 1 && y == 1) {
            x = random.nextInt(0, 3);
            y = random.nextInt(0, 3);
        }
        return new int[]{random.nextInt(1, 5), x, y};
    }
}
