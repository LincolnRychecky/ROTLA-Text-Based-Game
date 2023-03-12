package code.command;
import code.Room;
import code.adventurers.Adventurer;
// concrete adventurer command class for moving
public class AdventurerMoveCommand implements Command {
    Adventurer adventurer;
    Room room;

    public AdventurerMoveCommand(Adventurer adventurer, Room room){
        this.adventurer = adventurer;
        this.room = room;
    }

    public void execute(){
        adventurer.move(room);
    }
}