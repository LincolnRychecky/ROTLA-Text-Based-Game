package code.adventurers.searchBehaviour;

import code.Board;
import code.Room;
import code.treasures.Treasure;

public class Quick implements SearchBehaviour {
    @Override
    public Treasure search(Room room) {
        if (room.treasures.size() != 0) {
            int diceScore = Board.rollDice();
            if (diceScore >= 6) {
                return room.selectRandomTreasure();
            }
        }
        return null;
    }
}
