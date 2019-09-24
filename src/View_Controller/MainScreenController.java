package View_Controller;

import static Model.Inventory.allParts;
import static Model.Inventory.products;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
/**
 * FXML Main Controller class
 *
 * @author Ethan
 */

public class MainScreenController implements Initializable {

    @FXML
    private Button partsSearchButton;

    @FXML
    private Button partsModifyButton;

    @FXML
    private Button partsDeleteButton;

    @FXML
    private Button partsAddButton;

    @FXML
    private TextField partsSearchField;
    
    @FXML
    private TableView<Part> partsMainTable;
    
    @FXML
    private TableColumn<Part, Integer> addPartColumn1;

    @FXML
    private TableColumn<Part, String> addPartColumn2;

    @FXML
    private TableColumn<Part, Integer> addPartColumn3;

    @FXML
    private TableColumn<Part, Double> addPartColumn4;

    @FXML
    private TableView<Product> productsMainTable;
    
    @FXML
    private TableColumn<Product, Integer> addProductColumn1;

    @FXML
    private TableColumn<Product, String> addProductColumn2;

    @FXML
    private TableColumn<Product, Integer> addProductColumn3;

    @FXML
    private TableColumn<Product, Double> addProductColumn4;

    @FXML
    private Button productsDeleteButton;

    @FXML
    private Button productsModifyButton;

    @FXML
    private Button productsAddButton;

    @FXML
    private Button productsSearchButton;

    @FXML
    private TextField productsSearchField;

    @FXML
    private Button mainExitButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        addPartColumn1.setCellValueFactory(new PropertyValueFactory<>("partID"));
        addPartColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));
        addPartColumn3.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        addPartColumn4.setCellValueFactory(new PropertyValueFactory<>("price"));
        partsMainTable.setItems(allParts);
        
        addProductColumn1.setCellValueFactory(new PropertyValueFactory<>("productID"));
        addProductColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductColumn3.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        addProductColumn4.setCellValueFactory(new PropertyValueFactory<>("price"));
        productsMainTable.setItems(products);
        
    }

    @FXML
    void mainExitButtonAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void partsAddButtonAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddPartScreen.fxml"));
        Stage stage = (Stage) partsAddButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void partsDeleteButtonAction(ActionEvent event) {
        try {
            Part removedPart = partsMainTable.getSelectionModel().getSelectedItem();
            
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Deleting Part Number " + removedPart.getPartID());
            alert.setContentText("Are you sure you want to delete " + removedPart.getName());
            
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK){
                allParts.remove(removedPart);
                partsMainTable.getSelectionModel().clearSelection();
            }
            else {
                alert.close();
            }            
        }
        catch (NullPointerException e){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("No part selected");
            alert.setContentText("You must select a part first!");
            alert.showAndWait();
        }
        
        
        
    }

    @FXML
    void partsModifyButtonAction(ActionEvent event) throws IOException, NullPointerException {
        
        if (partsMainTable.getSelectionModel().getSelectedIndex() != -1){
            Parent root;
            Stage stage;
            stage = (Stage) partsModifyButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyPartScreen.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            ModifyPartScreenController controller = loader.getController();
            Part part = partsMainTable.getSelectionModel().getSelectedItem();
            controller.setPart(part);
            stage.show();
        }
        else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("No part selected");
            alert.setContentText("You must select a part first!");
            alert.showAndWait();
        }        
    }

    @FXML
    void partsSearchButtonAction(ActionEvent event) {
        String searchField = partsSearchField.getText();
        if(searchField.equals("")){
            addPartColumn1.setCellValueFactory(new PropertyValueFactory<>("partID"));
            addPartColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));
            addPartColumn3.setCellValueFactory(new PropertyValueFactory<>("inStock"));
            addPartColumn4.setCellValueFactory(new PropertyValueFactory<>("price"));
            partsMainTable.setItems(allParts);
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
                        partsMainTable.setItems(foundPart);
                    }
                }
                if (found == false){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
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
                        partsMainTable.setItems(foundPart);
                    }
                }
                 if (found == false){
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Uh oh!");
                    alert.setContentText("It seems the part you were looking for was lost in the void!");
                    alert.showAndWait();
                }
            }
        }
    }

    @FXML
    void productsAddButtonAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddProductScreen.fxml"));
        Stage stage = (Stage) productsAddButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void productsDeleteButtonAction(ActionEvent event) {
        try {

            Product removedProduct = productsMainTable.getSelectionModel().getSelectedItem();
            
            if (removedProduct.getAssociatedPart().isEmpty()){
            
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Deleting Product Number " + removedProduct.getProductID());
                alert.setContentText("Are you sure you want to delete " + removedProduct.getName());

                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK){
                    products.remove(removedProduct);
                    productsMainTable.getSelectionModel().clearSelection();
                }
                else {
                    alert.close();
                }
                
            }
            else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Associated Parts Found");
                alert.setContentText("All parts must be removed from this product before deletion");
                alert.showAndWait();
            }
            
        }
        catch (NullPointerException e){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("No product selected");
            alert.setContentText("You must select a product first!");
            alert.showAndWait();
        }
    }

    @FXML
    void productsModifyButtonAction(ActionEvent event) throws IOException {
        if (productsMainTable.getSelectionModel().getSelectedIndex() != -1){
            Parent root;
            Stage stage;
            stage = (Stage) productsModifyButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyProductScreen.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            ModifyProductScreenController controller = loader.getController();
            Product product = productsMainTable.getSelectionModel().getSelectedItem();
            controller.setProduct(product);
            stage.show();
        }
        else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("No product selected");
            alert.setContentText("You must select a product first!");
            alert.showAndWait();
        }
    }

    @FXML
    void productsSearchButtonAction(ActionEvent event) {
        String searchField = productsSearchField.getText();
        if(searchField.equals("")){
            addProductColumn1.setCellValueFactory(new PropertyValueFactory<>("productID"));
            addProductColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));
            addProductColumn3.setCellValueFactory(new PropertyValueFactory<>("inStock"));
            addProductColumn4.setCellValueFactory(new PropertyValueFactory<>("price"));
            productsMainTable.setItems(products);
        }
        else {
            boolean found = false;
            try {
                int Id = Integer.parseInt(searchField);
                for (Product p : products){
                    if(p.getProductID() == Id){
                        found = true;

                        ObservableList<Product> foundPart = FXCollections.observableArrayList();
                        foundPart.addAll(p);
                        productsMainTable.setItems(foundPart);
                    }
                }
                if (found == false){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Uh oh!");
                    alert.setContentText("It seems the part you were looking for was lost in the void!");
                    alert.showAndWait();
                }
            }
            catch(NumberFormatException e){
                String name = searchField;
                for (Product p : products){
                    if(p.getName().equalsIgnoreCase(name)){
                        found = true;
                        
                        ObservableList<Product> foundPart = FXCollections.observableArrayList();
                        foundPart.addAll(p);
                        productsMainTable.setItems(foundPart);
                    }
                }
                 if (found == false){
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Uh oh!");
                    alert.setContentText("It seems the part you were looking for was lost in the void!");
                    alert.showAndWait();
                }
            }
        }
    }

}
