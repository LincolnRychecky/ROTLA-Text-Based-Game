package code.characterFactories;
import code.adventurers.combatBehaviour.CombatBehaviour;
import code.adventurers.searchBehaviour.SearchBehaviour;
import code.adventurers.combatBehaviour.Untrained;
import code.adventurers.searchBehaviour.Quick;
// element factory to gather components for runner subclass
public class RunnerElementFactory implements AdventurerElementFactory{
    public CombatBehaviour createCombatBehaviour(){
        return addCelebrationsForCombatBehaviour(new Untrained());
    }

    public SearchBehaviour createSearchBehaviour(){
        return new Quick();
    }
}