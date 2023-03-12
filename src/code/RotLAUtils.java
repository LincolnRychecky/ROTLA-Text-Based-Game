package code;

import code.adventurers.Adventurer;
import code.creatures.Creature;
import java.util.Random;

public class RotLAUtils {
    public static int fightCharacter(Adventurer adventurer, Creature creature, Room room, int adventurerFightAdvantage, String combatType) {
        int adventurerScore = Board.rollDice() + adventurerFightAdvantage;
        int creatureScore = Board.rollDice();
        if(adventurerScore<creatureScore){
            Random random = new Random(System.currentTimeMillis());
            boolean damageAvoided = random.nextInt(0,2) == 1;
            if (!combatType.equals("Stealth") || (combatType.equals("Stealth") && !damageAvoided)){
                // health lost for each loss
                adventurer.health-=1;
                if(adventurer.health<=0){
                    room.adventurers.remove(adventurer);
                    // Here, the identity of the object Adventurer is utilised to simulate the existence of the adventurer in the game.
                    // A movement of adventurer from one room to another in run time is apparent from the identity of the object.
                    // Once an adventurer dies, the object itself ceases to exist in memory.
                }
                combatType.equals("Stealth");
            }
            return 0;
        } else{
            // creature eliminated for each win
            room.creatures.remove(creature);
            adventurer.celebrate();
            return 0;
        }
    }
}
