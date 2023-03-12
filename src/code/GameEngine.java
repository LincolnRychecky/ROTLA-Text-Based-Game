package code;

import code.adventurers.Adventurer;
import code.command.AdventurerMoveCommand;
import code.creatures.Creature;
import code.observer.Logger;
import code.observer.Tracker;

import java.util.ArrayList;

public class GameEngine {
    private final Board board;
    private final BoardRenderer boardRenderer;

    // In this case, GameEngine, Board and Board Renderer show good Cohesion. These are grouped together such that the
    // context of implementation is closely related. Board Render displays contents of the Board and GameEngine uses the
    // board to simulate the game.

    GameEngine(){
        board = new Board();
        board.generateBoard();
        boardRenderer = new BoardRenderer();
    }

    public void playGame(){
        int round = 0;
        Tracker t = Tracker.getInstance();
        t.init(board.getAllAdventurers(), board.getAllCreatures(), board.getAllCreatureLocations(),0);
        while (checkGameStatus()){
            round++;
            Logger l = Logger.getInstance();
            l.init(board.getAllAdventurers(), board.getAllCreatures(), round);
            for(Room room: Board.rooms){
                ArrayList<Adventurer> adventurers = new ArrayList<>();
                adventurers.addAll(room.adventurers);
                for(Adventurer a: adventurers){
                    if(!a.movedThisTurn){
                        AdventurerMoveCommand amc = new AdventurerMoveCommand(a, room);
                        amc.execute();
                        // a.move(room);
                    }
                }
                // Here, all creatures have different ways of movement. However, these details don't affect the working of the GameEngine. This is
                // a good example of Abstraction.
                ArrayList<Creature> creatures = new ArrayList<>();
                creatures.addAll(room.creatures);
                for(Creature c: creatures){
                    if(!c.movedThisTurn){
                        c.move(room);
                    }
                }
            }

            // close out l to prevent continued logging by this particular object
            l.close();
            //reset all characters to "have not moved" state in preparation for next turn
            for(Adventurer a: board.getAllAdventurers()){
                a.movedThisTurn = false;
            }
            for(Creature c: board.getAllCreatures()){
                c.movedThisTurn = false;
            }
            boardRenderer.render(this.board, round);
            // display the summary of the round/turn and set the turn in the tracker
            t.setTurn(round);
            t.display();

        }
        onGameEnded();
    }

    public boolean checkGameStatus(){
        ArrayList<Adventurer> adventurers = new ArrayList<>();
        ArrayList<Creature> creatures = new ArrayList<>();
        for(Room room : Board.rooms){
            adventurers.addAll(room.adventurers);
            creatures.addAll(room.creatures);
        }
        return board.checkGameStatus(adventurers, creatures);
    }

    public void onGameEnded(){
        String reason = "";
        ArrayList<Adventurer> adventurers = new ArrayList<>();
        ArrayList<Creature> creatures = new ArrayList<>();
        for(Room room : Board.rooms){
            adventurers.addAll(room.adventurers);
            creatures.addAll(room.creatures);
        }
        if(adventurers.size()==0){
            reason = "all Adventurers eliminated";
        } else if(creatures.size() == 0){
            reason = "all Creatures eliminated";
        } else{
            reason = "all treasure found";
        }
        System.out.println("GAME ENDED :"+reason);
    }



}
