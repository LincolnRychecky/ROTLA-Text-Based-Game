package code.adventurers.searchBehaviour;

import code.Board;
import code.Room;
import code.treasures.Trap;
import code.treasures.Treasure;

import java.util.Random;

public class Careful implements SearchBehaviour {
    @Override
    public Treasure search(Room room) {
        if(room.treasures.size()==0){
            return null;
        } else {
            int diceScore = Board.rollDice();
            if (diceScore >= 4) {
                Treasure treasure = room.selectRandomTreasure();
                if(treasure.getClass() == Trap.class){
                    Random random = new Random();
                    int chanceToEvadeTrap = random.nextInt(0,2);
                    if(chanceToEvadeTrap==1){
                        return null;
                    }
                }
                return treasure;
            } else{
                return null;
            }
        }
    }
}
