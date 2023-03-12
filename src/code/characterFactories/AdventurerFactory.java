package code.characterFactories;

import code.adventurers.*;
// this is the adventurer factory which is a bllend between abstract and simple factory. Since we only need a single type of factory, we are jusst making
// use of the idea of the sub elements factory to gather components which are then assembled into the whole of the object.
public class AdventurerFactory {
    public Adventurer createAdventurer(String adventurer, String name){
        AdventurerElementFactory brawlerFactory = new BrawlerElementFactory();
        AdventurerElementFactory runnerFactory = new RunnerElementFactory();
        AdventurerElementFactory sneakerFactory = new SneakerElementFactory();
        AdventurerElementFactory thiefFactory = new ThiefElementFactory();

        Adventurer a = null;

        if(adventurer.equals("Brawler")){
            a = new Brawler(brawlerFactory, name);
        } else if(adventurer.equals("Runner")){
            a = new Runner(runnerFactory, name);
        } else if(adventurer.equals("Sneaker")){
            a = new Sneaker(sneakerFactory, name);
        } else if(adventurer.equals("Thief")){
            a = new Thief(thiefFactory, name);
        }
        a.prepare();
        return a;
    }
}