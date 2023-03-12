package code.command;

import code.adventurers.Adventurer;
// concrete adventurer command class for celebration
public class AdventurerCelebrateCommand implements Command {
    Adventurer adventurer;

    public AdventurerCelebrateCommand(Adventurer adventurer){
        this.adventurer = adventurer;
    }

    public void execute(){
        adventurer.celebrate();
    }
}