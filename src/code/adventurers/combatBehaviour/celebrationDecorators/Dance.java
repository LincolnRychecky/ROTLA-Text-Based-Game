package code.adventurers.combatBehaviour.celebrationDecorators;

import code.adventurers.combatBehaviour.CombatBehaviour;

import static code.RotLAConstants.DanceMessage;

public class Dance extends CelebrationDecorator {
    public Dance(CombatBehaviour combatBehaviour) {
        super(combatBehaviour);
        celebrationMessages.add(DanceMessage);
    }
}
