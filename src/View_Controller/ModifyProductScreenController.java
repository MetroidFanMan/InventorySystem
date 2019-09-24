package View_Controller;

import Model.Inventory;
import static Model.Inventory.allParts;
import static Model.Inventory.productID;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Modify Product Controller class
 *
 * @author Ethan
 */

public class ModifyProductScreenController implements Initializable{

    @FXML
    private Button modifyProductSearchButton;

    @FXML
    private TextField modifyProductSearchField;

    @FXML
    private TableView<Part> modifyProductTable1;

    @FXML
    private TableColumn<Part, Integer> modifyProductT1C1;

    @FXML
    private TableColumn<Part, String> modifyProductT1C2;

    @FXML
    private TableColumn<Part, Integer> modifyProductT1C3;

    @FXML
    private TableColumn<Part, Double> modifyProductT1C4;

    @FXML
    private TableView<Part> modifyProductTable2;

    @FXML
    private TableColumn<Part, Integer> modifyProductT2C1;

    @FXML
    private TableColumn<Part, String> modifyProductT2C2;

    @FXML
    private TableColumn<Part, Integer> modifyProductT2C3;

    @FXML
    private TableColumn<Part, Double> modifyProductT2C4;

    @FXML
    private Button modifyProductSaveButton;

    @FXML
    private Button modifyProductCancelButton;

    @FXML
    private Button modifyProductDeleteButton;

    @FXML
    private Button modifyProductAddButton;

    @FXML
    private Label modifyProductIdLabel;

    @FXML
    private TextField modifyProductNameField;

    @FXML
    private TextField modifyProductInvField;

    @FXML
    private TextField modifyProductCostField;

    @FXML
    private TextField modifyProductMaxField;

    @FXML
    private TextField modifyProductMinField;
    
    Product product;    
    
    private ObservableList<Part> tempList = FXCollections.observableArrayList();
    
    boolean associatedPartSum(double price, ObservableList<Part> parts){
        return false;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        
        modifyProductT1C1.setCellValueFactory(new PropertyValueFactory<>("partID"));
        modifyProductT1C2.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductT1C3.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        modifyProductT1C4.setCellValueFactory(new PropertyValueFactory<>("price"));
        modifyProductTable1.setItems(allParts);
        
        modifyProductT2C1.setCellValueFactory(new PropertyValueFactory<>("partID"));
        modifyProductT2C2.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductT2C3.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        modifyProductT2C4.setCellValueFactory(new PropertyValueFactory<>("price"));
        
    }

    @FXML
    void modifyProductAddButtonAction(ActionEvent event) {
        Part part = modifyProductTable1.getSelectionModel().getSelectedItem();
        modifyProductTable2.getItems().add(part);
    }

    @FXML
    void modifyProductCancelButtonAction(ActionEvent event) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("All unsaved data will be lost!");
        alert.setContentText("Are you sure you want to leave?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Stage stage = (Stage) modifyProductCancelButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            alert.close();
        }
    }

    //Confirmation was not added for this button
    /*Hitting the delete button does not remove the part from the product if
    the save button is not selected*/
    @FXML
    void modifyProductDeleteButtonAction(ActionEvent event) {
        Part part = modifyProductTable2.getSelectionModel().getSelectedItem();
        modifyProductTable2.getItems().remove(part);
    }

    @FXML
    void modifyProductSaveButtonAction(ActionEvent event) throws IOException {
        
        boolean correct = false;
        
        try {
            
            Product product2 = new Product();
            String name = modifyProductNameField.getText();
            int inv;
            double price = Double.parseDouble(modifyProductCostField.getText());
            int max;
            int min;
            
            if (modifyProductInvField.getText().equals("")){
                inv = 0;
            }
            else {
                inv = Integer.parseInt(modifyProductInvField.getText());                
            }
            
            if (modifyProductMaxField.getText().equals("")){
                max = 0;
            }
            else {
                max = Integer.parseInt(modifyProductMaxField.getText());                
            }
            
            if (modifyProductMinField.getText().equals("")){
                min = 0;
            }
            else {
                min = Integer.parseInt(modifyProductMinField.getText());
            }

            if (max < min){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Minimum inventory is greater than Maximum");
                alert.setContentText("Please set the minimum value lower than the maximum!");
                alert.showAndWait();            
            }
            else if (max < inv || min > inv){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Inventory amount is incorrect");
                alert.setContentText("Inventory needs to be between Max and Min values!");
                alert.showAndWait();
            }
            else if (name.equals("")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Empty fields detected");
                alert.setContentText("Required Fields: \n\t Product Name \n\t Product Inventory (default 0) \n\t Product Price");
                alert.showAndWait();
            }
            else if (modifyProductTable2.getItems().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No parts added!");
                alert.setContentText("A product must have atleast one part!");
                alert.showAndWait();
            }
            else if (sumOfParts(modifyProductTable2.getItems()) > price){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("YOU'RE LOSING MONEY!!!");
                    alert.setContentText("Parts can not exceed the price of a product");
                    alert.showAndWait();
                }
            else {

                product2.setProductID(Integer.parseInt(modifyProductIdLabel.getText()));
                product2.setName(name);
                product2.setInStock(inv);
                product2.setPrice(price);
                product2.setMax(max);
                product2.setMin(min);                    
                product2.addAssociatedPart(modifyProductTable2.getItems());
                Inventory.products.removeAll(product);
                Inventory.products.addAll(product2);
                
                correct = true;
            }
        }
        catch (NumberFormatException e){            
            if (modifyProductCostField.getText().equals("")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Empty fields detected");
                alert.setContentText("Required Fields: \n\t Product Name \n\t Product Inventory (default 0) \n\t Product Price");
                alert.showAndWait();
            }
        }
        
        if (correct){
            Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Stage stage = (Stage) modifyProductSaveButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void modifyProductSearchButtonAction(ActionEvent event) {
        String searchField = modifyProductSearchField.getText();
        if(searchField.equals("")){
            modifyProductT1C1.setCellValueFactory(new PropertyValueFactory<>("partID"));
            modifyProductT1C2.setCellValueFactory(new PropertyValueFactory<>("name"));
            modifyProductT1C3.setCellValueFactory(new PropertyValueFactory<>("inStock"));
            modifyProductT1C4.setCellValueFactory(new PropertyValueFactory<>("price"));
            modifyProductTable1.setItems(allParts);
        }
        else {
            boolean found = false;
            try {
                int Id = Integer.parseInt(searchField);
                for (Part p : allParts){
                    if(p.getPartID() == Id){
                        found = true;

                        ObservableList<Part> foundPart = FXCollections.observableArrayList();
                        foundPart.addAll(p);
                        modifyProductTable1.setItems(foundPart);
                    }
                }
                if (found == false){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("FYI");
                    alert.setHeaderText("Uh oh!");
                    alert.setContentText("It seems the part you were looking for was lost in the void!");
                    alert.showAndWait();
                }
            }
            catch(NumberFormatException e){
                String name = searchField;
                for (Part p : allParts){
                    if(p.getName().equalsIgnoreCase(name)){
                        found = true;
                        
                        ObservableList<Part> foundPart = FXCollections.observableArrayList();
                        foundPart.addAll(p);
                        modifyProductTable1.setItems(foundPart);
                    }
                }
                 if (found == false){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("FYI");
                    alert.setHeaderText("Uh oh!");
                    alert.setContentText("It seems the part you were looking for was lost in the void!");
                    alert.showAndWait();
                }
            }
        }
    }
    
    @FXML
    public void setProduct(Product product){
        this.product = product;
        modifyProductIdLabel.setText(Integer.toString(product.getProductID()));
        modifyProductNameField.setText(product.getName());
        modifyProductInvField.setText(Integer.toString(product.getInStock()));
        modifyProductCostField.setText(Double.toString(product.getPrice()));
        modifyProductMaxField.setText(Integer.toString(product.getMax()));
        modifyProductMinField.setText(Integer.toString(product.getMin()));
        tempList.addAll(product.getAssociatedPart());
        modifyProductTable2.setItems(tempList);        
                
    }
    
    private double sumOfParts(ObservableList<Part> parts){
        double sum = 0;
        for (Part part : parts){
            sum += part.getPrice();
        }
        return sum;
    }
}