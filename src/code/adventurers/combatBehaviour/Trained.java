package code.adventurers.combatBehaviour;

import code.Room;
import code.RotLAUtils;
import code.adventurers.Adventurer;
import code.creatures.Creature;

import java.util.Random;

public class Trained extends CombatBehaviour {
    @Override
    public int fight(Adventurer adventurer, Creature creature, Room room, int fightAdvantage) {
        Random random = new Random(System.currentTimeMillis());
        int trainedFighterAdvantage = random.nextInt(1,3);
        return RotLAUtils.fightCharacter(adventurer, creature, room, fightAdvantage+trainedFighterAdvantage, "Trained");
    }
}
