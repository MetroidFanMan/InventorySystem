package View_Controller;

import Model.InhousePart;
import Model.Inventory;
import static Model.Inventory.partID;
import Model.OutsourcedPart;
import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Add Part Controller class
 *
 * @author Ethan
 */

public class AddPartScreenController {

    @FXML
    private RadioButton addPartInHouseRB;

    @FXML
    private RadioButton addPartOutsourcedRB;

    @FXML
    private Label addPartWildCardLabel;

    @FXML
    private TextField addPartNameField;

    @FXML
    private TextField addPartInvField;

    @FXML
    private TextField addPartCostField;

    @FXML
    private TextField addPartMaxField;

    @FXML
    private TextField addPartMinField;

    @FXML
    private TextField addPartWildCardField;

    @FXML
    private Button addPartCancelButton;

    @FXML
    private Button addPartSaveButton;
    
    @FXML
    void radioButtonSelection(){
        if (addPartInHouseRB.isSelected()){
            addPartWildCardLabel.setText("Machine ID");
            addPartWildCardField.setPromptText("Machine ID");
        }
        else {
            addPartWildCardLabel.setText("Company Name");
            addPartWildCardField.setPromptText("Company Name");
        }
    }

    @FXML
    void addPartCancelButtonAction(ActionEvent event) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("All unsaved data will be lost!");
        alert.setContentText("Are you sure you want to leave?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Stage stage = (Stage) addPartCancelButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            alert.close();
        }
    }

    @FXML
    void addPartSaveButtonAction(ActionEvent event) throws IOException {
        
        boolean correct = false;
        
        try{
            
            String name = addPartNameField.getText();
            int inv;
            double price = Double.parseDouble(addPartCostField.getText());
            int max;
            int min;
        
            if (addPartInvField.getText().equals("")){
                inv = 0;
            }
            else {
                inv = Integer.parseInt(addPartInvField.getText());
            }
            
            if (addPartMaxField.getText().equals("")){
                max = 0;
            }
            else {
                max = Integer.parseInt(addPartMaxField.getText());
            }
            
            if (addPartMinField.getText().equals("")){
                min = 0;
            }
            else {
                min = Integer.parseInt(addPartMinField.getText());
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
            else {        
                if (addPartInHouseRB.isSelected()) {

                    InhousePart part = new InhousePart();
                    part.setPartID(partID);
                    part.setName(name);
                    part.setInStock(inv);
                    part.setPrice(price);
                    part.setMax(max);
                    part.setMin(min);
                    part.setMachineID(Integer.parseInt(addPartWildCardField.getText()));
                    Inventory.allParts.add(part);
                    
                    correct = true;

                }
                else if (addPartOutsourcedRB.isSelected()) {

                    OutsourcedPart part = new OutsourcedPart();
                    part.setPartID(partID);
                    part.setName(name);
                    part.setInStock(inv);
                    part.setPrice(price);
                    part.setMax(max);
                    part.setMin(min);
                    part.setCompanyName(addPartWildCardField.getText());
                    Inventory.allParts.add(part);
                    
                    correct = true;

                }

                partID++;
            }
        }
        catch (NumberFormatException e){
            
            if (addPartCostField.getText().equals("")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Empty fields detected");
                alert.setContentText("Required Fields: \n\t Product Name \n\t Product Inventory (default 0) \n\t Product Price");
                alert.showAndWait();
            }            
            else if (addPartWildCardField.getText().equals("")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Omnipotent Part Detected");
                alert.setContentText("These things scare me, so please give the machines ID that crafted the part!");
                alert.showAndWait();
            }
        }
        
        if (correct){
            Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Stage stage = (Stage) addPartSaveButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
}