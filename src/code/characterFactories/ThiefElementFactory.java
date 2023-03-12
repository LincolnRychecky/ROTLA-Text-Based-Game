package code.characterFactories;
import code.adventurers.combatBehaviour.CombatBehaviour;
import code.adventurers.searchBehaviour.SearchBehaviour;
import code.adventurers.combatBehaviour.Trained;
import code.adventurers.searchBehaviour.Careful;
// element factory to gather components for thief subclass
public class ThiefElementFactory implements AdventurerElementFactory{
    public CombatBehaviour createCombatBehaviour(){
        return addCelebrationsForCombatBehaviour(new Trained());
    }

    public SearchBehaviour createSearchBehaviour(){
        return new Careful();
    }
}