package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Ethan
 */
public class Inventory {
    
    public static ObservableList<Product> products = FXCollections.observableArrayList();
    public static ObservableList<Part> allParts = FXCollections.observableArrayList();
    public static int partID = 1;
    public static int productID = 1;
    
    public void addProduct(Product p){
        products.add(p);
    }
    
    public boolean removeProduct(int i){
        if (products.get(i) != null){
            products.remove(i);
            return true;
        } else {
            return false;
        }
    }
        
    public Product lookupProduct(int i){
        Product product = products.get(i - 1);
        return product;
    }
   
    public void updateProduct(int i, Product product){
        Product tempProduct = products.get(i - 1);
        products.remove(tempProduct);
        products.add(product);
    }
    
    public void addPart(Part p){
        allParts.add(p);
    }
    
    public boolean deletePart(Part p){
        boolean partDeleted = false;
        for (Part part : allParts){
            if (part == p) {
                allParts.remove(part);
                partDeleted = true;
            }
        }
        return partDeleted;
    }
    
    public Part lookupPart(int i){
        Part part = allParts.get(i - 1);
        return part;
    }
    
    public void updatepart(int i, Part part){
        Part tempPart = allParts.get(i - 1);
        allParts.remove(tempPart);
        allParts.add(part);
    }
    
}