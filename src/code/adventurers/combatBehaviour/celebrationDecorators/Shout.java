package code.adventurers.combatBehaviour.celebrationDecorators;

import code.adventurers.combatBehaviour.CombatBehaviour;

import static code.RotLAConstants.ShoutMessage;

public class Shout extends CelebrationDecorator {

    public Shout(CombatBehaviour combatBehaviour) {
        super(combatBehaviour);
        celebrationMessages.add(ShoutMessage);
    }
}
