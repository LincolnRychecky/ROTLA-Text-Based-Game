package code.adventurers.combatBehaviour.celebrationDecorators;

import code.adventurers.combatBehaviour.CombatBehaviour;

import static code.RotLAConstants.SpinMessage;

public class Spin extends CelebrationDecorator {
    public Spin(CombatBehaviour combatBehaviour) {
        super(combatBehaviour);
        celebrationMessages.add(SpinMessage);
    }
}
