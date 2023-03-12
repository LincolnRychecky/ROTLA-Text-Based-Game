package code.adventurers;

import code.characterFactories.AdventurerElementFactory;
import code.command.AdventurerCelebrateCommand;
import code.command.AdventurerFightCommand;
import code.command.AdventurerSearchCommand;
import code.creatures.Creature;
import code.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Runner extends Adventurer {
    private AdventurerElementFactory elementFactory;

    public Runner(AdventurerElementFactory elementFactory, String name){
        this.elementFactory = elementFactory;
        this.health = 10;
        this.name = name;
    }

    public void prepare(){
        combatBehaviour = elementFactory.createCombatBehaviour();
        combatBehaviour.setCharacterName(this.getClass().getSimpleName());
        searchBehaviour = elementFactory.createSearchBehaviour();
    }

    // Inheritance : Runner inherits from Adventurer and has the same fighting behaviour as is for all adventurers. However, the movement behaviour changes.
    @Override
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
            } else if(inputValue.equals("search")){
                AdventurerSearchCommand asc = new AdventurerSearchCommand(this, room);
                asc.execute();
            } else{
                AdventurerCelebrateCommand asc = new AdventurerCelebrateCommand(this);
                asc.execute();
            }

        } else{ // if creatures, player can fight or move
            Scanner input = new Scanner(System.in);
            System.out.println("Creatures present in room. Would you like to move, or fight?: ");
            String inputValue = input.nextLine();
            while(!inputValue.equals("move") && !inputValue.equals("fight")){
                System.out.println("Choice must be move, or fight: ");
                inputValue = input.nextLine();
            }
            //input.close();
            
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
}