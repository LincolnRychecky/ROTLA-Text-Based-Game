package code;

import code.adventurers.Adventurer;
import code.creatures.Creature;
import code.treasures.Treasure;

import java.util.ArrayList;
import java.util.Random;

public class Room {
    public int[] location;

    public ArrayList<Creature> creatures = new ArrayList<>();
    public ArrayList<Adventurer> adventurers = new ArrayList<>();
    public ArrayList<Treasure> treasures = new ArrayList<>();
    public ArrayList<Room> connectedRooms = new ArrayList<>();

    Room(int[] location){
        this.location = location;
    }

    public Room selectRandomConnectedRoom() {
        Random random = new Random();
        return connectedRooms.get(random.nextInt(connectedRooms.size()));
    }

    public Treasure selectRandomTreasure(){
        if(treasures.size()==1){
            return treasures.get(0);
        } else {
            Random random = new Random();
            int treasureSelected = random.nextInt(1, treasures.size());
            return treasures.get(treasureSelected);
        }
    }

    public void display(){
        String adventurers, creatures;
        adventurers = creatures = "";
        for(Creature c: this.creatures){
            creatures += c.getClass().getSimpleName().charAt(0);
        }
        for (Adventurer a: this.adventurers){
            adventurers += a.getClass().getSimpleName().charAt(0);
        }
        System.out.print(location[0] + "-" + location[1] + "-" + location[2] + ": " + adventurers + ":" + creatures);
    }

    public String getCordinateString(){
        return location[0] + "-" + location[1] + "-" + location[2];
    }

}