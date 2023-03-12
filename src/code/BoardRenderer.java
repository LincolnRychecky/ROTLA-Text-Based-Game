package code;

import code.adventurers.Adventurer;
import code.creatures.Creature;

public class BoardRenderer {
    public void render(Board board, int round){
        System.out.println("RotLA Turn "+round+":\n");
        Board.rooms.get(0).display();
        System.out.println();
        System.out.println();
        for(int i = 1; i < Board.rooms.size(); i+=3){
            Board.rooms.get(i).display();
            System.out.print("            ");
            Board.rooms.get(i+1).display();
            System.out.print("            ");
            Board.rooms.get(i+2).display();
            System.out.println();
            System.out.println();
        }
        System.out.println();
        System.out.println();

        int numOrbiters = 0;
        int numSeekers = 0;
        int numBlinkers = 0;

        for(Room r : Board.rooms){
            if(r.adventurers.size() > 0){
                for(Adventurer a: r.adventurers){
                    System.out.println(a.getClass().getSimpleName() + "     -     " + a.treasuresFound() + " Treasure(s)     -    " + a.remainingHealth() + " Health");
                }
            }
            if(r.creatures.size() > 0){

                for(Creature c: r.creatures){
                    if(c.getClass().getSimpleName().equals("Seeker")){
                        numSeekers += 1;
                    } else if(c.getClass().getSimpleName().equals("Orbiter")){
                        numOrbiters += 1;
                    } else{
                        numBlinkers += 1;
                    }
                }
            }
        }
        System.out.println();
        System.out.println("Orbiters     -     " + numOrbiters + " Remaining");
        System.out.println("Seekers     -     " + numSeekers + " Remaining");
        System.out.println("Blinkers     -     " + numBlinkers + " Remaining");
        System.out.println();
    }

    public void displayEndConditions(){

    }

}
