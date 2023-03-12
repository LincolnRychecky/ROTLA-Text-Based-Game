package code.adventurers.combatBehaviour.celebrationDecorators;

import code.Room;
import code.adventurers.Adventurer;
import code.adventurers.combatBehaviour.CombatBehaviour;
import code.creatures.Creature;

public abstract class CelebrationDecorator extends CombatBehaviour {

    protected final CombatBehaviour combatBehaviour;

    public CelebrationDecorator(CombatBehaviour combatBehaviour){
        this.combatBehaviour = combatBehaviour;
        this.celebrationMessages = combatBehaviour.celebrationMessages;
        this.setCharacterName(combatBehaviour.getCharacterName());
    }
    @Override
    public void celebrate() {
        StringBuilder builder = getCelebration();
        if (builder.isEmpty()) {
            return;
        }
        System.out.println(builder);
    }

    public StringBuilder getCelebration() {
        StringBuilder builder = new StringBuilder();
        builder.append(getCharacterName()).append(" celebrates: ");
        if(celebrationMessages.size()==0){
            return builder;
        }
        builder.append(celebrationMessages.get(0));
        for (int i = 1; i < celebrationMessages.size(); i++) {
            builder.append(", ").append(celebrationMessages.get(i));
        }
        builder.append('.');
        return builder;
    }

    @Override
    public int fight(Adventurer adventurer, Creature creature, Room room, int fightAdvantage){
        return combatBehaviour.fight(adventurer, creature, room, fightAdvantage);
    }
}
