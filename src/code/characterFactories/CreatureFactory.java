package code.characterFactories;

import code.creatures.*;
// This is a simple faactory for the creature class and subclasses. Since there are sub components that need gathered and created for a creature,
// we opted for simple factory in this case.
public class CreatureFactory {
    public Creature createCreature(String creature){
        Creature c = null;

        if(creature.equals("Blinker")){
            c = new Blinker();
        } else if(creature.equals("Orbiter")){
            c = new Orbiter();
        } else if(creature.equals("Seeker")){
            c = new Seeker();
        }
        return c;
    }
}