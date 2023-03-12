package code.adventurers.combatBehaviour;

import code.Room;
import code.RotLAUtils;
import code.adventurers.Adventurer;
import code.creatures.Creature;

public class Untrained extends CombatBehaviour {
    @Override
    public int fight(Adventurer adventurer, Creature creature, Room room, int fightAdvantage) {
        return RotLAUtils.fightCharacter(adventurer, creature, room, fightAdvantage, "Untrained");
    }
}
