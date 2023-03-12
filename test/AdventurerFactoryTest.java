import code.adventurers.Adventurer;
import code.characterFactories.AdventurerFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AdventurerFactoryTest {
    Adventurer brawler;
    Adventurer runner;
    Adventurer thief;
    Adventurer sneaker;

    @Before
    public void setup(){
        AdventurerFactory factory = new AdventurerFactory();
        brawler = factory.createAdventurer("Brawler", "B1");
        runner = factory.createAdventurer("Runner", "R1");
        thief = factory.createAdventurer("Thief", "T1");
        sneaker = factory.createAdventurer("Sneaker", "S1");
    }

    @Test
    public void testBrawlerCombatBehavior(){
        Assert.assertEquals(brawler.getCombatBehaviourName(), "Expert");
    }

    @Test
    public void testBrawlerName(){
        Assert.assertEquals(brawler.getNameSetCombatBehaviour(), "Brawler");
    }

    @Test
    public void testRunnerCombatBehavior(){
        Assert.assertEquals(runner.getCombatBehaviourName(), "Untrained");
    }

    @Test
    public void testThiefCombatBehavior(){
        Assert.assertEquals(thief.getCombatBehaviourName(), "Trained");
    }

    @Test
    public void testSneakerCombatBehavior(){
        Assert.assertEquals(sneaker.getCombatBehaviourName(), "Stealth");
    }

}
