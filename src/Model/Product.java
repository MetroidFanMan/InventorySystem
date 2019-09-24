package Model;

import static Model.Inventory.allParts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Ethan
 */
public class Product {
    
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int productID, inStock, min, max;
    private String name;
    private double price;
    
    public Product (){
        
        this.associatedParts = associatedParts;
        this.productID = productID;
        this.inStock = inStock;
        this.max = max;
        this.min = min;
        this.price = price;
        this.name = name;
        
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    public void setPrice(double price){
        this.price = price;
    }
    
    public double getPrice(){
        return price;
    }
    
    public void setInStock(int inStock){
        this.inStock = inStock;
    }
    
    public int getInStock(){
        return inStock;
    }
    
    public void setMin(int min){
        this.min = min;
    }
    
    public int getMin(){
        return min;
    }
    
    public void setMax(int max){
        this.max = max;
    }
    
    public int getMax(){
        return max;
    }
    
    public void addAssociatedPart(ObservableList<Part> p){
        associatedParts = p;
    }
    
    public ObservableList<Part> getAssociatedPart(){
        return associatedParts;
    }    
    
    public boolean removeAssociatedPart (int p){
        
        for (Part part : associatedParts){
            
            int id = part.getPartID();
            if (id == p) {
                associatedParts.remove(id - 1);
                return true;
            }
        }        
        return false;
    }
    
    public Part lookupAssociatedPart (int p){
        Part foundPart = null;
        for (Part part : associatedParts){
            if (part.getPartID() == p){
                foundPart = part;
            }
        }
        return foundPart;
    }
    
    public void setProductID (int productID){
        this.productID = productID;
    }
    
    public int getProductID (){
        return productID;
    }
    
}