package code.adventurers;

import code.Board;
import code.adventurers.combatBehaviour.CombatBehaviour;
import code.adventurers.searchBehaviour.SearchBehaviour;
import code.command.AdventurerCelebrateCommand;
import code.command.AdventurerFightCommand;
import code.command.AdventurerSearchCommand;
import code.creatures.Creature;
import code.observer.Observer;
import code.observer.Subject;
import code.Room;
import code.treasures.Trap;
import code.treasures.Treasure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import static code.RotLAConstants.MAX_LIVES_ADVENTURER;


public abstract class Adventurer implements Subject{
    protected List<Treasure> treasures = new ArrayList<>();
    public int health = MAX_LIVES_ADVENTURER;
    protected int fightAdvantage = 0;
    private List<Observer> observers = new ArrayList<>();
    private HashMap<String, String>  lastAction;
    public boolean movedThisTurn = false;
    protected String name;

    protected CombatBehaviour combatBehaviour;
    protected SearchBehaviour searchBehaviour;
    protected Room currRoom;

    // Here, all the details of movement, fighting, etc. are Encapsulated in the Adventurer class such that the GameEngine and GameRenderer classes don't
    // know the implementation details of an Adventurer but are still able to function.

    public abstract void prepare(); 

    public void move(Room room){
        movedThisTurn = true;

        // if no creatures, player can move, search, or celebrate
        if(room.creatures.size() == 0){
            Scanner input = new Scanner(System.in);
            System.out.println("No creatures present in room. Would you like to move, search, or celebrate?: ");
            String inputValue = input.nextLine();
            while(!inputValue.equals("move") && !inputValue.equals("search") && !inputValue.equals("celebrate")){
                System.out.println("Choice must be move, search, or celebrate: ");
                inputValue = input.nextLine();
            }
            // input.close();
            
            if(inputValue.equals("move")){
                Room currentRoom = room.selectRandomConnectedRoom();
                room.adventurers.remove(this);
                currentRoom.adventurers.add(this);
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("detail", currentRoom.getCordinateString());
                data.put("character", this.getClass().getSimpleName());
                data.put("action type", "moved to");
                setLastAction(data);

                if(currentRoom.creatures.size()>0) {
                    ArrayList<Creature> creatures = new ArrayList<>();
                    creatures.addAll(currentRoom.creatures);
                    for (Creature creature : creatures) {
                        // sneaker only has 50% probability of being spotted by creature and having to fight
                        AdventurerFightCommand asc = new AdventurerFightCommand(this, room, creature);
                        asc.execute();
                        // fight(currentRoom, creature);
                        if(!currentRoom.adventurers.contains(this)){
                            return;
                        }
                    }
                }
            } else if(inputValue.equals("search")){
                AdventurerSearchCommand asc = new AdventurerSearchCommand(this, room);
                asc.execute();
                // checkForTreasure(room);
            } else{
                AdventurerCelebrateCommand asc = new AdventurerCelebrateCommand(this);
                asc.execute();
                // celebrate();
            }

        } else{ // if creatures, player can fight or move
            Scanner input = new Scanner(System.in);
            System.out.println("Creatures present in room. Would you like to move, or fight?: ");
            String inputValue = input.nextLine();
            while(!inputValue.equals("move") && !inputValue.equals("fight")){
                System.out.println("Choice must be move, or fight: ");
                inputValue = input.nextLine();
            }
            
            if(inputValue.equals("move")){
                Room currentRoom = room.selectRandomConnectedRoom();
                room.adventurers.remove(this);
                currentRoom.adventurers.add(this);
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("detail", currentRoom.getCordinateString());
                data.put("character", this.getClass().getSimpleName());
                data.put("action type", "moved to");
                setLastAction(data);

                if(currentRoom.creatures.size()>0) {
                    ArrayList<Creature> creatures = new ArrayList<>();
                    creatures.addAll(currentRoom.creatures);
                    for (Creature creature : creatures) {
                        // sneaker only has 50% probability of being spotted by creature and having to fight
                        AdventurerFightCommand asc = new AdventurerFightCommand(this, room, creature);
                        asc.execute();
                        if(!currentRoom.adventurers.contains(this)){
                            return;
                        }
                    }
                }
            }else{
                if(room.creatures.size()>0) {
                    ArrayList<Creature> creatures = new ArrayList<>();
                    creatures.addAll(room.creatures);
                    for (Creature creature : creatures) {
                        // sneaker only has 50% probability of being spotted by creature and having to fight
                        AdventurerFightCommand asc = new AdventurerFightCommand(this, room, creature);
                        asc.execute();
                        if(!room.adventurers.contains(this)){
                            return;
                        }
                    }
                }
            }
        }
    }

    public void fight(Room room, Creature creature) {
        int result = combatBehaviour.fight(this, creature, room, fightAdvantage);
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("detail", creature.getClass().getSimpleName());
        data.put("character", this.getClass().getSimpleName());
        data.put("result", Integer.toString(result));
        data.put("room", room.getCordinateString());
        data.put("action type", "fought");
        setLastAction(data);
    }

    public void checkForTreasure(Room room){
        Treasure treasure = searchBehaviour.search(room);
        if(treasure!=null) {
            room.treasures.remove(treasure);

            // Don't add treasure if the adventurer already has a treasure of same type. Exception is Trap
            if (treasure.getClass() != Trap.class) {
                for (Treasure t : treasures) {
                    if (t.getClass() == treasure.getClass()) {
                        return;
                    }
                }
            }
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("detail", treasure.getClass().getSimpleName());
            data.put("character", this.getClass().getSimpleName());
            data.put("action type", "found");
            data.put("health", String.valueOf(treasure.healthBoost));
            setLastAction(data);
            health += treasure.healthBoost;
            fightAdvantage += treasure.combatAdvantage;
            treasures.add(treasure);
            Board.totalTreasureFound += 1;
        }
    }

    public int treasuresFound(){
        return treasures.size();
    }

    public int remainingHealth(){
        return health;
    }

    public void celebrate(){
        combatBehaviour.celebrate();
    }

    public void registerObserver(Observer O){
        observers.add(O);
    }

    public void removeObserver(Observer O){
        observers.remove(O);
    }

    public void notifyObservers(){
        for (Observer obs: observers){
            obs.update(lastAction);
        }
    }

    public void actionTaken(){
        notifyObservers();
    }

    public void setLastAction(HashMap<String, String> lastAction){
        this.lastAction = lastAction;
        actionTaken();
    }

    public String getNameSetCombatBehaviour(){
        return combatBehaviour.getCharacterName();
    }

    public String getCombatBehaviourName(){
        return combatBehaviour.getClass().getSimpleName();
    }

}
