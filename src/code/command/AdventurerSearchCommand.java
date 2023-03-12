package code.command;

import code.adventurers.Adventurer;
import code.Room;
// concrete adventurer command class for searching
public class AdventurerSearchCommand implements Command {
    Adventurer adventurer;
    Room room;

    public AdventurerSearchCommand(Adventurer adventurer, Room room){
        this.adventurer = adventurer;
        this.room = room;
    }

    public void execute(){
        adventurer.checkForTreasure(room);
    }
}