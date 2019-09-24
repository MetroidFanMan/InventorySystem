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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Add Product Controller class
 *
 * @author Ethan
 */

public class AddProductScreenController implements Initializable {

    @FXML
    private Button addProductSearchButton;

    @FXML
    private TextField addProductSearchField;

    @FXML
    private TableView<Part> addProductTable1;
    
    @FXML
    private TableColumn<Part, Integer> addProductT1C1;

    @FXML
    private TableColumn<Part, String> addProductT1C2;

    @FXML
    private TableColumn<Part, Integer> addProductT1C3;

    @FXML
    private TableColumn<Part, Double> addProductT1C4;

    @FXML
    private TableView<Part> addProductTable2;

    @FXML
    private TableColumn<Part, Integer> addProductT2C1;

    @FXML
    private TableColumn<Part, String> addProductT2C2;

    @FXML
    private TableColumn<Part, Integer> addProductT2C3;

    @FXML
    private TableColumn<Part, Double> addProductT2C4;

    @FXML
    private Button addProductCancelButton;

    @FXML
    private Button addProductDeleteButton;

    @FXML
    private Button addProductSaveButton;

    @FXML
    private Button addProductAddButton;

    @FXML
    private TextField addProductNameField;

    @FXML
    private TextField addProductInvField;

    @FXML
    private TextField addProductCostField;

    @FXML
    private TextField addProductMaxField;

    @FXML
    private TextField addProductMinField;
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        addProductT1C1.setCellValueFactory(new PropertyValueFactory<>("partID"));
        addProductT1C2.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductT1C3.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        addProductT1C4.setCellValueFactory(new PropertyValueFactory<>("price"));
        addProductTable1.setItems(allParts);
        
        addProductT2C1.setCellValueFactory(new PropertyValueFactory<>("partID"));
        addProductT2C2.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductT2C3.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        addProductT2C4.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
            
    @FXML
    void addProductAddButtonAction(ActionEvent event) {
        Part part = addProductTable1.getSelectionModel().getSelectedItem();
        addProductTable2.getItems().add(part);
    }

    @FXML
    void addProductCancelButtonAction(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("All unsaved data will be lost!");
        alert.setContentText("Are you sure you want to leave?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Stage stage = (Stage) addProductCancelButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            alert.close();
        }
    }

    @FXML
    void addProductDeleteButtonAction(ActionEvent event) {
        Part part = addProductTable2.getSelectionModel().getSelectedItem();
        addProductTable2.getItems().remove(part);
    }

    @FXML
    void addProductSaveButtonAction(ActionEvent event) throws IOException {
        
        boolean correct = false;

        try {
            
            Product product = new Product();
            String name = addProductNameField.getText();
            int inv;
            double price = Integer.parseInt(addProductCostField.getText());
            int max;
            int min;
            
            if (addProductInvField.getText().equals("")){
                inv = 0;
            }
            else {
                inv = Integer.parseInt(addProductInvField.getText());                
            }
            
            if (addProductMaxField.getText().equals("")){
                max = 0;
            }
            else {
                max = Integer.parseInt(addProductMaxField.getText());                
            }
            
            if (addProductMinField.getText().equals("")){
                min = 0;
            }
            else {
                min = Integer.parseInt(addProductMinField.getText());
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
            else if (addProductTable2.getItems().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No parts added!");
                alert.setContentText("A product must have atleast one part!");
                alert.showAndWait();
            }
            else if (sumOfParts(addProductTable2.getItems()) > price){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("YOU'RE LOSING MONEY!!!");
                    alert.setContentText("Parts can not exceed the price of a product");
                    alert.showAndWait();
                }
            else {

                product.setProductID(productID);
                product.setName(name);
                product.setInStock(inv);                
                product.setPrice(price);
                product.setMax(max);
                product.setMin(min);                
                product.addAssociatedPart(addProductTable2.getItems());                
                Inventory.products.addAll(product);

                productID++;
                
                correct = true;

            }
        }
        catch (NumberFormatException e){            
            if (addProductCostField.getText().equals("")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Empty fields detected");
                alert.setContentText("Required Fields: \n\t Product Name \n\t Product Inventory (default 0) \n\t Product Price");
                alert.showAndWait();
            }
        }
        
        if (correct){
            Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Stage stage = (Stage) addProductSaveButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
            
    }

    @FXML
    void addProductSearchButtonAction(ActionEvent event) {
        String searchField = addProductSearchField.getText();
        if(searchField.equals("")){
            addProductT1C1.setCellValueFactory(new PropertyValueFactory<>("partID"));
            addProductT1C2.setCellValueFactory(new PropertyValueFactory<>("name"));
            addProductT1C3.setCellValueFactory(new PropertyValueFactory<>("inStock"));
            addProductT1C4.setCellValueFactory(new PropertyValueFactory<>("price"));
            addProductTable1.setItems(allParts);
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
                        addProductTable1.setItems(foundPart);
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
                        addProductTable1.setItems(foundPart);
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
    
    private double sumOfParts(ObservableList<Part> parts){
        double sum = 0;
        for (Part part : parts){
            sum += part.getPrice();
        }
        return sum;
    }
    
}
