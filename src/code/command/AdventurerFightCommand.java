package code.command;

import code.Room;
import code.adventurers.Adventurer;
import code.creatures.Creature;
// concrete adventurer command class for fighting
public class AdventurerFightCommand implements Command {
    Adventurer adventurer;
    Room room;
    Creature creature;

    public AdventurerFightCommand(Adventurer adventurer, Room room, Creature creature){
        this.adventurer = adventurer;
        this.room = room;
        this.creature = creature;
    }

    public void execute(){
        adventurer.fight(room, creature);
    }
}