package code.adventurers;

import code.characterFactories.AdventurerElementFactory;

public class Brawler extends Adventurer {
    private AdventurerElementFactory elementFactory;

    public Brawler(AdventurerElementFactory elementFactory, String name){
        this.elementFactory = elementFactory;
        this.health = 12;
        this.name = name;
    }

    public void prepare(){
        combatBehaviour = elementFactory.createCombatBehaviour();
        combatBehaviour.setCharacterName(this.getClass().getSimpleName());
        searchBehaviour = elementFactory.createSearchBehaviour();
    }
}
