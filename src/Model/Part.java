package Model;

/**
 *
 * //@author Ethan
 */
public abstract class Part {
    
    public abstract void setName(String name);
    
    public abstract String getName();
    
    public abstract void setPrice(double price);
    
    public abstract double getPrice();
    
    public abstract void setInStock(int inStock);
    
    public abstract int getInStock();
    
    public abstract void setMin(int min);
    
    public abstract int getMin();
    
    public abstract void setMax(int max);
    
    public abstract int getMax();
    
    public abstract void setPartID(int partID);
    
    public abstract int getPartID();
    
}