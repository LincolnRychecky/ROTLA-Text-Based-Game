package code.creatures;

import code.Room;
import code.adventurers.Adventurer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;

public class Seeker extends Creature {

    @Override
    public void move(Room room) {
        movedThisTurn = true;
        Room currentRoom = room;
        String previousRoom = currentRoom.getCordinateString();

        // if there are no adventurers in the present room, look for adventurers in adjacent rooms and move
        if(currentRoom.adventurers.size() == 0){
            HashMap<Adventurer, Room> adventurersRoomMap = new LinkedHashMap<>();
            room.connectedRooms.forEach(connectedRoom -> {
                connectedRoom.adventurers.forEach(adventurer -> {
                    adventurersRoomMap.put(adventurer, connectedRoom);
                });
            });
            // If there are no adventurers in nearby rooms, don't move.
            if (!adventurersRoomMap.isEmpty()) {
                ArrayList<Adventurer> adventurers = new ArrayList<>();
                adventurers.addAll(adventurersRoomMap.keySet());
                Random random = new Random();
                currentRoom = adventurersRoomMap.get(adventurers.get(random.nextInt(adventurers.size())));
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

        // fight all adventures in the present room
        fight(currentRoom);
    }

    @Override
    public int[] determineStartingLocation() {
        // Starts anywhere in the four levels
        Random random = new Random();
        return new int[]{random.nextInt(1,5), random.nextInt(0,3), random.nextInt(0,3)} ;
    }
}
