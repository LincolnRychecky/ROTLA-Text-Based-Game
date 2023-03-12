package code.observer;

import code.adventurers.Adventurer;
import code.creatures.Creature;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
// logger makes use of the singleton pattern and is eagerly instantiated.
public class Logger implements Observer {

    private static Logger uniqueInstance = new Logger();
    private HashMap<String, String> message;
    private Integer turn;
    private List<Creature> creatures = new ArrayList<>();
    private List<Adventurer> adventurers = new ArrayList<>();

    // we could potentially add privaate attributes that maintain lists of creature and 
    // adventurer objects being observed, but not necessary at this moment.

    public Logger(){ }

    public static Logger getInstance() {
        return uniqueInstance;
    }

    public void init(ArrayList<Adventurer> adventurers, ArrayList<Creature> creatures, Integer turn){
        this.creatures = creatures;
        this.adventurers = adventurers;
        // for each adventurer and creature register the observer.
        adventurers.forEach(adventurer -> adventurer.registerObserver(this));
        creatures.forEach(creature -> creature.registerObserver(this));
        this.turn = turn;
    }

    public void update(HashMap<String, String> message){
        this.message = message;
        log();
    }

    public void log(){
        // code from: https://www.w3schools.com/java/java_files_create.asp
        try {
            String logDirectoryPath = "logs";
            File logDirectory = new File(logDirectoryPath);
            if(!logDirectory.exists()){
                logDirectory.mkdirs();
            }
            String logPathForCurrentTurn = String.format(logDirectoryPath+"/Java.txtLogger-%s.txt",turn);
            File logFileForCurrentTurn = new File(logPathForCurrentTurn);
            logFileForCurrentTurn.createNewFile();
            FileWriter fWriter = new FileWriter(logPathForCurrentTurn, true);
            fWriter.write(message.get("character") + " " + message.get("action type") + " " + message.get("detail") + System.getProperty( "line.separator" ));
            fWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void close(){
        // for each adventurer and creature unregister the observer in order to close it out
        adventurers.forEach(adventurer -> adventurer.removeObserver(this));
        creatures.forEach(creature -> creature.removeObserver(this));
    }

}