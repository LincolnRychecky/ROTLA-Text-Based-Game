import code.Board;
import code.Room;
import code.adventurers.Adventurer;
import code.adventurers.combatBehaviour.CombatBehaviour;
import code.adventurers.combatBehaviour.Expert;
import code.characterFactories.AdventurerFactory;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BoardTest {

    private Board board;

    @Before
    public void setup() {
        board = new Board();
        board.generateBoard();
    }

    @Test
    public void testRollDiceOutcomeLowerBound(){
        int diceScore = Board.rollDice();
        Assert.assertTrue(diceScore>0);
    }

    @Test
    public void testRollDiceOutcomeUpperBound(){
        int diceScore = Board.rollDice();
        Assert.assertTrue(diceScore<=12);
    }

    @Test
    public void testRoomsCreated(){
        Assert.assertTrue(Board.rooms.size()>0);
    }

    @Test
    public void testAddCelebrationsForCombatBehaviour(){
        CombatBehaviour combatBehaviour = new Expert();
        combatBehaviour = board.addCelebrationsForCombatBehaviour(combatBehaviour);
        AdventurerFactory factory = new AdventurerFactory();
        Adventurer adventurer = factory.createAdventurer("Brawler", "Tester");
        Assert.assertFalse(combatBehaviour.celebrationMessages.isEmpty());
    }

    @Test
    public void testAllTreasuresDistributed(){
        int treasuresDistributed = 0;
        for(Room room : Board.rooms){
            treasuresDistributed+=room.treasures.size();
        }
        Assert.assertEquals(24, treasuresDistributed);
    }

    @Test
    public void testNoTreasuresDistributedInStartingRoom(){
        Assert.assertEquals(0, Board.rooms.get(0).treasures.size());
    }

    @After
    public void tearDown(){
        Board.rooms.clear();
    }

}
