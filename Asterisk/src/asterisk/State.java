/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asterisk;

import java.util.List;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author i0910465
 */
public class State {
    
    private static int nextId = 1;
    private static SortedMap<Integer, State> stateDirectory = new TreeMap<>();
    private int id;  // Bigger than 1. 0 is default
    private List<Box> productionList;
    private StorageHouse storageHouse;
    
    public static void printProductionList(List<Box> productionList){
        int j = 1;
        System.out.print("\n\tProduction list: [\n");
        for (Box b : productionList){
            System.out.printf("\t( ID: " + b.getId() + ", Departure: " + b.getDepartureDate() + " ), ");
            j++;
            if (j >= 3){
                j = 0;
                System.out.print("\n");
            }
        }
        System.out.print("\t]\n\n");
    }
    
    private static State stateAlreadyFound(StorageHouse storageHouse) {
        for (State state : stateDirectory.values()) {
            if (storageHouse.equals(state.getStorageHouse()))
                return state;
        }
        return null;
    }
    
    public static void addState(State state) {
        stateDirectory.put(state.getId(), state);
    }
    
    public static State getState(int stateId) {
        return stateDirectory.get(stateId);
    }

    public State(List<Box> productionList, StorageHouse storageHouse) {
        State s = stateAlreadyFound(storageHouse);
        if (s != null) {
            this.id = s.getId();
        } else {
            this.id = nextId;
            nextId += 1;
        }
        this.productionList = productionList;
        this.storageHouse = storageHouse;
    }
    
    public State() {
        this.id = nextId;
        nextId += 1;
        this.productionList = null;
        this.storageHouse = null;
    }
    
    private State(List<Box> productionList, StorageHouse storageHouse, int id) {
        this.id = id;
        this.productionList = productionList;
        this.storageHouse = storageHouse;
    }

    public int getId() {
        return id;
    }

    public List<Box> getProductionList() {
        return productionList;
    }

    public void setProductionList(List<Box> productionList) {
        this.productionList = productionList;
    }

    public StorageHouse getStorageHouse() {
        return storageHouse;
    }

    public void setStorageHouse(StorageHouse storageHouse) {
        this.storageHouse = storageHouse;
    }
    
    public State clone(){        
        return new State(this.cloneProductionList(),
                         this.cloneStorageHouse(),
                         this.id);
    }
    
    public StorageHouse cloneStorageHouse(){
        return this.storageHouse.clone();
    }
    
    public List<Box> cloneProductionList(){
        List<Box> newProductionList = new ArrayList<>();
        newProductionList.addAll(this.productionList);
        return newProductionList;
    }
    
    public void printSelf() {
        System.out.println("State ID: " + id + "\n");
        printProductionList(productionList);
        StorageHouse.print(storageHouse);
    }
    
}
