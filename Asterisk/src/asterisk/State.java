/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asterisk;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author i0910465
 */
public class State{
    
    private static int nextId = 1;
    private int id;  // Bigger than 1. 0 is default
    private List<Box> productionList;
    private StorageHouse storageHouse;

    public State(List<Box> productionList, StorageHouse storageHouse) {
        this.id = nextId;
        nextId += 1;
        this.productionList = productionList;
        this.storageHouse = storageHouse;
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
        List<Box> newProductionList = new ArrayList<>();
        newProductionList.addAll(this.productionList);
        StorageHouse newStorageHouse = this.storageHouse.clone();
        
        return new State(newProductionList,
                         newStorageHouse,
                         this.id);
    }
    
}
