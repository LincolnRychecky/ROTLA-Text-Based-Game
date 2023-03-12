package code.characterFactories;
import code.adventurers.combatBehaviour.CombatBehaviour;
import code.adventurers.searchBehaviour.SearchBehaviour;
import code.adventurers.combatBehaviour.Stealth;
import code.adventurers.searchBehaviour.Quick;
// element factory to gather components for sneaker subclass
public class SneakerElementFactory implements AdventurerElementFactory{
    public CombatBehaviour createCombatBehaviour(){
        return addCelebrationsForCombatBehaviour(new Stealth());
    }

    public SearchBehaviour createSearchBehaviour(){
        return new Quick();
    }
}