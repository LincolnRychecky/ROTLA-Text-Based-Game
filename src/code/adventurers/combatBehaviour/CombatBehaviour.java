package code.adventurers.combatBehaviour;

import code.Room;
import code.adventurers.Adventurer;
import code.creatures.Creature;

import java.util.ArrayList;

public abstract class CombatBehaviour {

    private String characterName = "";

    public String getCharacterName(){
        return characterName;
    }

    public void setCharacterName(String name){
        characterName = name;
    }

    public ArrayList<String> celebrationMessages = new ArrayList<>();

    public StringBuilder getCelebration(){
        return new StringBuilder();
    }

    public void celebrate(){}

    public abstract int fight(Adventurer adventurer, Creature creature, Room room, int fightAdvantage);
}
