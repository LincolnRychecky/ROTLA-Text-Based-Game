package code.observer;

import code.adventurers.Adventurer;
import code.creatures.Creature;

import java.util.ArrayList;
import java.util.HashMap;
// logger makes use of the singleton pattern and is lazily instantiated.
public class Tracker implements Observer {

    private static Tracker uniqueInstance;
    private HashMap<String, String>  message;
    private Integer turn;
    private HashMap<String, HashMap<String, String>> adventurerData = new HashMap<String, HashMap<String, String>>();
    private ArrayList<ArrayList<String>> creatureData = new ArrayList<>();
    // we could potentially add private attributes that maintain lists of creature and 
    // adventurer objects being observed, but not necessary at this moment.

    private Tracker(){ }

    public static Tracker getInstance() {
        if (uniqueInstance == null){
            uniqueInstance = new Tracker();
        }
        return uniqueInstance;
    }

    public void init(ArrayList<Adventurer> adventurers, ArrayList<Creature> creatures, ArrayList<ArrayList<String>> creatureLocations, Integer turn){
        // for each adventurer and creature register the observer.
        adventurers.forEach(adventurer -> adventurer.registerObserver(this));
        creatures.forEach(creature -> creature.registerObserver(this));

        for(Adventurer a: adventurers){
            adventurerData.put(a.getClass().getSimpleName(), new HashMap<String, String>());
            adventurerData.get(a.getClass().getSimpleName()).put("room", "0-1-1");
            adventurerData.get(a.getClass().getSimpleName()).put("health", String.valueOf(a.health));
            adventurerData.get(a.getClass().getSimpleName()).put("treasure", "");
        }

        creatureData = creatureLocations;

        this.turn = turn;
    }

    public void update(HashMap<String, String> message){
        this.message = message;
        log();
    }

    public void log(){
        if(this.message.get("action type").equals("moved to")){
            // if adventurer, update the adventurer data hashmap. Otherwise, we are updating the creature data arraylist
            switch (this.message.get("character")) {
                case "Brawler":
                case "Runner":
                case "Sneaker":
                case "Thief":
                    adventurerData.get(this.message.get("character")).put("room", this.message.get("detail"));
                    break;
                case "Blinker":
                case "Orbiter":
                case "Seeker":
                    updateCreatureLocation(this.message.get("character"), this.message.get("previous room"), this.message.get("detail"));
                    break;
            }
        } else if(this.message.get("action type").equals("fought")){
            // if adventurer, update the adventurer data hashmap. Otherwise, we are updating the creature data arraylist
            switch (this.message.get("character")) {
                case "Brawler":
                case "Runner":
                case "Sneaker":
                case "Thief":
                    // if the adventurer did not win(0) or avoid the fight (0), the health needs deducted (added since the result is negative in the message)
                    if(Integer.parseInt(this.message.get("result")) != 0){
                        adventurerData.get(this.message.get("character")).put("health", String.valueOf(Integer.parseInt(adventurerData.get(this.message.get("character")).get("health")) + Integer.parseInt(this.message.get("result"))));
                    } else{
                        removeCreature(this.message.get("detail"), this.message.get("room"));
                    }
                    break;
                case "Blinker":
                case "Orbiter":
                case "Seeker":
                    // if creature lost, remove it from creature data arraylist. Otherwise, deduct 1 health from adventurer it fought (if more damage can be dealt this will need revisited)
                    if(this.message.get("result").equals("lost")){
                        removeCreature(this.message.get("character"), this.message.get("room"));
                    } else{
                        adventurerData.get(this.message.get("detail")).put("health", String.valueOf(Integer.parseInt(adventurerData.get(this.message.get("detail")).get("health")) - 1));
                    }
                    break;
                
            }
        } else{
            // default case is that an adventurer found treasure. Update the treasue data in the adventurer data hashmap
            String treasureString = "";
            if(adventurerData.get(this.message.get("character")).get("treasure").equals(null)){
                treasureString = this.message.get("detail") + ", ";
            } else{
                treasureString = adventurerData.get(this.message.get("character")).get("treasure") + ", " + this.message.get("detail");
            }
            adventurerData.get(this.message.get("character")).put("treasure", treasureString);
            adventurerData.get(this.message.get("character")).put("health", String.valueOf(Integer.parseInt(adventurerData.get(this.message.get("character")).get("health")) + Integer.parseInt(this.message.get("health"))));
        }
    }

    private int getActiveAdventurers(){
        int numActive = 0;
        for(String key: adventurerData.keySet()){
            if(!adventurerData.get(key).get("health").equals("0")){
                numActive ++;
            }
        }
        return numActive;
    }

    private int getActiveCreatures(){
        return creatureData.size();
    }

    public void setTurn(int turn){
        this.turn = turn;
    }

    public void updateCreatureLocation(String character, String previousRoom, String newRoom){
        for(ArrayList<String> l : creatureData){
            if(l.get(0).equals(character) && l.get(1).equals(previousRoom)){
                // System.out.println("entered here");
                l.set(1, newRoom);
                break;
            }
        }
    }

    public void removeCreature(String character, String room){
        // loop through the creature data arraylist and identify the index of the creature with values character and room. Once it is identified, remove the element from the aarray list.
        for(int i = 0; i < creatureData.size(); i++){
            if(creatureData.get(i).get(0).equals(character) && creatureData.get(i).get(1).equals(room)){
                creatureData.remove(i);
                break;
            }
        }
    }

    public void display(){
        System.out.println("Tracker: Turn " + turn);
        System.out.println();
        System.out.println("Total Active Adventurers: " + getActiveAdventurers());  
        for(String key: adventurerData.keySet()){
            System.out.println("Adventurer: " + key + "   Room: " + adventurerData.get(key).get("room") + "   Health: " + adventurerData.get(key).get("health") + "   Treasure: " + adventurerData.get(key).get("treasure"));
        }
        System.out.println();
        System.out.println();
        System.out.println("Total Active Creatures: " + getActiveCreatures());  
        for(ArrayList<String> l : creatureData){
            System.out.println("Creature: " + l.get(0) + "   Room: " + l.get(1));
        }
        System.out.println();
    }
}