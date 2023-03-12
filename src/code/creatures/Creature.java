package code.creatures;

import code.Board;
import code.Room;
import code.adventurers.Adventurer;
import code.observer.Observer;
import code.observer.Subject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Creature implements Subject{
    private List<Observer> observers = new ArrayList<>();
    private HashMap<String, String> lastAction;
    public boolean movedThisTurn = false;

    public abstract void move(Room room);

    public void fight(Room room) {

        ArrayList<Adventurer> adventurersInRoom = new ArrayList<>();
        adventurersInRoom.addAll(room.adventurers);
        for(Adventurer adventurer : adventurersInRoom){
            int adventurerScore = Board.rollDice();
            int creatureScore = Board.rollDice();
            String outcome = "lost";
            if(adventurerScore<creatureScore){
                adventurer.health-=1;
                if(adventurer.health<=0){
                    room.adventurers.remove(adventurer);
                }
                outcome = "won";
            } else{
                room.creatures.remove(this);
                adventurer.celebrate();
            }
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("detail", adventurer.getClass().getSimpleName());
            data.put("character", this.getClass().getSimpleName());
            data.put("result", outcome);
            data.put("room", room.getCordinateString());
            data.put("action type", "fought");
            setLastAction(data);
            if(!room.creatures.contains(this)){
                break;
            }
        }
    }

    public abstract int[] determineStartingLocation();

    public void registerObserver(Observer O){
        observers.add(O);
    }

    public void removeObserver(Observer O){
        observers.remove(O);
    }

    public void notifyObservers(){
        for (Observer obs: observers){
            obs.update(lastAction);
        }
    }

    public void actionTaken(){
        notifyObservers();
    }

    public void setLastAction(HashMap<String, String> lastAction){
        this.lastAction = lastAction;
        actionTaken();
    }
}
