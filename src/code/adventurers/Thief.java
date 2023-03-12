package code.adventurers;

import code.characterFactories.AdventurerElementFactory;

public class Thief extends Adventurer {
    private AdventurerElementFactory elementFactory;

    public Thief(AdventurerElementFactory elementFactory, String name){
        this.elementFactory = elementFactory;
        this.health = 8;
        this.name = name;
    }

    public void prepare(){
        combatBehaviour = elementFactory.createCombatBehaviour();
        combatBehaviour.setCharacterName(this.getClass().getSimpleName());
        searchBehaviour = elementFactory.createSearchBehaviour();
    }
}