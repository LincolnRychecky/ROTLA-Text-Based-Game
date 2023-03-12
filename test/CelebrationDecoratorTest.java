import code.adventurers.Adventurer;
import code.adventurers.combatBehaviour.CombatBehaviour;
import code.adventurers.combatBehaviour.Expert;
import code.adventurers.combatBehaviour.celebrationDecorators.CelebrationDecorator;
import code.adventurers.combatBehaviour.celebrationDecorators.Dance;
import code.characterFactories.AdventurerFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CelebrationDecoratorTest {
    CombatBehaviour combatBehaviour;
    CelebrationDecorator celebrationDecorator;
    Adventurer adventurer;
    int noOfDecorators;

    @Before
    public void setup(){
        combatBehaviour = new Expert();
        noOfDecorators = 5;
        for (int i = 0; i< noOfDecorators; i++){
            combatBehaviour = new Dance(combatBehaviour);
        }
        AdventurerFactory factory = new AdventurerFactory();
        adventurer = factory.createAdventurer("Brawler", "Tester");
    }

    @Test
    public void testCelebrateMessagesAreNotEmpty(){
        Assert.assertFalse(combatBehaviour.celebrationMessages.isEmpty());
    }

    @Test
    public void testCharacterNameIsSet(){
        Assert.assertFalse(adventurer.getNameSetCombatBehaviour().isEmpty());
    }

    @Test
    public void testCelebrationOutputIsNotEmpty(){
        Assert.assertFalse(combatBehaviour.getCelebration().isEmpty());
    }

    @Test
    public void testAllDecoratorsAddedShowCelebrations(){
        Assert.assertEquals(combatBehaviour.celebrationMessages.size(), noOfDecorators);
    }

}
