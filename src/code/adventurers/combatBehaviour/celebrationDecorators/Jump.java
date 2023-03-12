package code.adventurers.combatBehaviour.celebrationDecorators;

import code.adventurers.combatBehaviour.CombatBehaviour;

import static code.RotLAConstants.JumpMessage;

public class Jump extends CelebrationDecorator {
    public Jump(CombatBehaviour combatBehaviour) {
        super(combatBehaviour);
        celebrationMessages.add(JumpMessage);
    }
}
