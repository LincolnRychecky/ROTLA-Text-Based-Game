package code.characterFactories;

import code.adventurers.combatBehaviour.CombatBehaviour;
import code.adventurers.searchBehaviour.SearchBehaviour;
import code.adventurers.combatBehaviour.Expert;
import code.adventurers.searchBehaviour.Careless;
// element factory to gather components for brawler subclass
public class BrawlerElementFactory implements AdventurerElementFactory{
    public CombatBehaviour createCombatBehaviour(){
        return addCelebrationsForCombatBehaviour(new Expert());
    }

    public SearchBehaviour createSearchBehaviour(){
        return new Careless();
    }
}