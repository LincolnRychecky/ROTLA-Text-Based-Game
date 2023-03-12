package code.characterFactories;
import code.adventurers.combatBehaviour.CombatBehaviour;
import code.adventurers.searchBehaviour.SearchBehaviour;
import code.adventurers.combatBehaviour.celebrationDecorators.*;
import java.util.Random;

public interface AdventurerElementFactory{
    public CombatBehaviour createCombatBehaviour();
    public SearchBehaviour createSearchBehaviour();
    default CombatBehaviour addCelebrationsForCombatBehaviour(CombatBehaviour combatBehaviour) {

        CombatBehaviour behaviourWithCelebration = combatBehaviour;

        Random random = new Random(System.currentTimeMillis());
        int times = random.nextInt(0, 3);
        for( int i=0; i<times; i++ ){
            behaviourWithCelebration = new Dance(behaviourWithCelebration);
        }

        random.setSeed(System.currentTimeMillis());
        times = random.nextInt(0, 3);
        for( int i=0; i<times; i++ ){
            behaviourWithCelebration = new Jump(behaviourWithCelebration);
        }

        random.setSeed(System.currentTimeMillis());
        times = random.nextInt(0, 3);
        for( int i=0; i<times; i++ ){
            behaviourWithCelebration = new Shout(behaviourWithCelebration);
        }

        random.setSeed(System.currentTimeMillis());
        times = random.nextInt(0, 3);
        for( int i=0; i<times; i++ ){
            behaviourWithCelebration = new Spin(behaviourWithCelebration);
        }

        return behaviourWithCelebration;
    }
}