package code.adventurers;

import code.characterFactories.AdventurerElementFactory;

public class Sneaker extends Adventurer {
    private AdventurerElementFactory elementFactory;

    public Sneaker(AdventurerElementFactory elementFactory, String name){
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