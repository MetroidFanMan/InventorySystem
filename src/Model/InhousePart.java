package Model;

/**
 *
 * @author Ethan
 */
public class InhousePart extends Part{
    
    private int partID, machineID, inStock, min, max;
    private String name;
    private double price;
    
    public InhousePart() {
        
        this.partID = partID;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.max = max;
        this.min = min;
        this.machineID = machineID;
    
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
    
    public int getMachineID(){
        return machineID;
    }
    
    public void setMachineID(int machineID){
        this.machineID = machineID;
    }
    
}