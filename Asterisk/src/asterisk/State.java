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
    
    private List<Box> productionList;
    private StorageHouse storageHouse;
    private boolean valid;

    public State(){
        this.productionList = new ArrayList<>();
        this.storageHouse = new StorageHouse();
        this.valid = true;
    }
    
    public State(List<Box> productionList, StorageHouse storageHouse) {
        this.productionList = productionList;
        this.storageHouse = storageHouse;
        this.valid = true;
    }
    
    /**
     * @return the productionList
     */
    public List<Box> getProductionList() {
        return productionList;
    }

    /**
     * @param productionList the productionList to set
     */
    public void setProductionList(List<Box> productionList) {
        this.productionList = productionList;
    }

    /**
     * @return the storageHouse
     */
    public StorageHouse getStorageHouse() {
        return storageHouse;
    }

    /**
     * @param storageHouse the storageHouse to set
     */
    public void setStorageHouse(StorageHouse storageHouse) {
        this.storageHouse = storageHouse;
    }

    /**
     * @return the isValid
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * @param isValid the isValid to set
     */
    public void setValid(boolean isValid) {
        this.valid = isValid;
    }
    
    

}
