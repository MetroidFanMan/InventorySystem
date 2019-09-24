package Model;

/**
 *
 * @author Ethan
 */
public class OutsourcedPart extends Part {
    
    private int partID, inStock, min, max;
    private String name, companyName;
    private double price;
    
    public OutsourcedPart (){
        
        this.partID = partID;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.min = min;
        this.max = max;
        this.companyName = companyName;
        
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
    
    public void setPartID(int partID){
        this.partID = partID;
    }
    
    public int getPartID(){
        return partID;
    }
    
    public String getCompanyName(){
        return companyName;
    }
    
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }
    
    
}