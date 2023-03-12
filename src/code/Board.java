package code;

import code.adventurers.*;
import code.adventurers.combatBehaviour.*;
import code.adventurers.combatBehaviour.celebrationDecorators.*;
import code.characterFactories.AdventurerFactory;
import code.characterFactories.CreatureFactory;
import code.creatures.Creature;
import code.treasures.*;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static code.RotLAConstants.TotalTreasures;

public class Board {
    public static ArrayList<Room> rooms = new ArrayList<>();

    public static int totalTreasureFound = 0;

    public static int rollDice(){
        // Get value of sum of two dice rolls
        Random random1 = new Random();
        Random random2 = new Random();
        return random1.nextInt(1,7) + random2.nextInt(1,7);
    }

    boolean checkGameStatus(ArrayList<Adventurer> adventurers, ArrayList<Creature> creatures){
        // verify the end conditions of the game
        return adventurers.size() != 0 && creatures.size() != 0 && totalTreasureFound<TotalTreasures;
    }

    public void generateBoard(){
        generateRooms();
        distributeTreasures();
        generateCharacters();

    }

    private void distributeTreasures() {
        ArrayList<Treasure> treasures = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            treasures.add(new Gem());
            treasures.add(new Armor());
            treasures.add(new Portal());
            treasures.add(new Potion());
            treasures.add(new Sword());
            treasures.add(new Trap());
        }
        Random random = new Random();
        while(!treasures.isEmpty()){

            int position = random.nextInt(0,rooms.size());
            if(position!=0) {
                rooms.get(position).treasures.add(treasures.get(0));
                treasures.remove(0);
            }
        }
    }

    private static void generateRooms() {
        // first, populate array list with all rooms
        rooms.add(new Room(new int[] {0,1,1}));
        for(int floor = 1; floor < 5; floor++){
            for(int x = 0; x < 3; x++){
                for(int y = 0; y < 3; y++){
                    rooms.add(new Room(new int[] {floor,x,y}));
                }
            }
        }

        // now, set each rooms connected rooms attribute to its neighboring rooms
        for (int i = 0; i < rooms.size(); i++){
            ArrayList<Room> connected = new ArrayList<>();

            // special case for starting point. If the starting point, can only move up into room 1
            if(rooms.get(i).location[0] == 0){
                connected.add(rooms.get(5));
            } else{
                 // first perform checks for possible up and down neighbors (only center moves have up or down neighbors available)
                if(rooms.get(i).location[1] == 1 && rooms.get(i).location[2] == 1){
                    // special case. 1, 1, 1 is attached to the starting room
                    if (rooms.get(i).location[0] == 1 && rooms.get(i).location[1] == 1 && rooms.get(i).location[2] == 1){
                        connected.add(rooms.get(0));
                    }
                    if (rooms.get(i).location[0] == 1){
                        connected.add(rooms.get(i+9));
                    } else if (rooms.get(i).location[0] == 4){
                        connected.add(rooms.get(i-9));
                    } else {
                        connected.add(rooms.get(i+9));
                        connected.add(rooms.get(i-9));
                    }
                }

                // next perform checks for east and west neighbors
                if(rooms.get(i).location[2] == 2){
                    connected.add(rooms.get(i-1));
                } else if(rooms.get(i).location[2] == 0){
                    connected.add(rooms.get(i+1));
                } else{
                    connected.add(rooms.get(i-1));
                    connected.add(rooms.get(i+1));
                }

                // finally, perform checks for north and south neighbors
                if(rooms.get(i).location[1] == 2){
                    connected.add(rooms.get(i-3));
                } else if(rooms.get(i).location[1] == 0){
                    connected.add(rooms.get(i+3));
                } else{
                    connected.add(rooms.get(i-3));
                    connected.add(rooms.get(i+3));
                }
            }

            rooms.get(i).connectedRooms = connected;
        }
    }

    public void generateCharacters(){

        Scanner input = new Scanner(System.in);
        System.out.println("What character would you like to play as? Brawler, Runner, Sneaker, or Thief?: ");
        String inputValue = input.nextLine();
        while(!inputValue.equals("Brawler") && !inputValue.equals("Runner") && !inputValue.equals("Sneaker") && !inputValue.equals("Thief")){
            System.out.println("Choice must be Brawler, Runner, Sneaker, or Thief: ");
            inputValue = input.nextLine();
        }
        System.out.println("What would you like to name your character?: ");
        String inputName = input.nextLine();
        //input.close();
        // add Adventurer
        Room startingRoom = rooms.get(0);
        AdventurerFactory adventurerFactory = new AdventurerFactory();
        startingRoom.adventurers.add(adventurerFactory.createAdventurer(inputValue, inputName));

        ArrayList<Creature> creatures = new ArrayList<>();
        // add four creatures of each type
        CreatureFactory creatureFactory = new CreatureFactory();
        for (int i = 0; i < 4; i++) {
            creatures.add(creatureFactory.createCreature("Seeker"));
            creatures.add(creatureFactory.createCreature("Orbiter"));
            creatures.add(creatureFactory.createCreature("Blinker"));
        }

        for(Creature creature : creatures){
            // get starting position of each creature based on their special conditions
            int[] location = creature.determineStartingLocation();
            rooms.forEach(room -> {
                if (Arrays.equals(room.location, location)){
                    room.creatures.add(creature);
                }
            });
        }
    }

    public CombatBehaviour addCelebrationsForCombatBehaviour(CombatBehaviour combatBehaviour) {

        CombatBehaviour behaviourWithCelebration = combatBehaviour;

        Random random = new Random(System.currentTimeMillis());
        int times = random.nextInt(0, 3);
        for( int i=0; i<times; i++ ){
            behaviourWithCelebration = new Dance(behaviourWithCelebration);
        }

        random.setSeed(System.currentTimeMillis());
        times = random.nextInt(0, 3);
        for( int i=0; i<times; i++ ){
            behaviourWithCelebration = new Jump(behaviourWithCelebration);
        }

        random.setSeed(System.currentTimeMillis());
        times = random.nextInt(0, 3);
        for( int i=0; i<times; i++ ){
            behaviourWithCelebration = new Shout(behaviourWithCelebration);
        }

        random.setSeed(System.currentTimeMillis());
        times = random.nextInt(0, 3);
        for( int i=0; i<times; i++ ){
            behaviourWithCelebration = new Spin(behaviourWithCelebration);
        }

        return behaviourWithCelebration;
    }

    public ArrayList<Adventurer> getAllAdventurers(){
        ArrayList<Adventurer> adventurers = new ArrayList<>();
        for(Room r : rooms){
            if(r.adventurers.size() > 0){
                for(Adventurer a: r.adventurers){
                    adventurers.add(a);
                }
            }
        }
        return adventurers;
    }

    public ArrayList<Creature> getAllCreatures(){
        ArrayList<Creature> creatures = new ArrayList<>();
        for(Room r : rooms){
            if(r.creatures.size() > 0){
                for(Creature c: r.creatures){
                    creatures.add(c);
                }
            }
        }
        return creatures;
    }

    public ArrayList<ArrayList<String>> getAllCreatureLocations(){
        ArrayList<ArrayList<String>> creatureLocations = new ArrayList<>();
        for(Room r : rooms){
            if(r.creatures.size() > 0){
                for(Creature c: r.creatures){
                    creatureLocations.add(new ArrayList<String>(Arrays.asList(c.getClass().getSimpleName(), r.getCordinateString())));
                }
            }
        }
        return creatureLocations;
    }

}