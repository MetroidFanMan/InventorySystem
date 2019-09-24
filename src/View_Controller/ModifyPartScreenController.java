package View_Controller;

import Model.InhousePart;
import static Model.Inventory.allParts;
import Model.OutsourcedPart;
import Model.Part;
import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Modify Part Controller class
 *
 * @author Ethan
 */

public class ModifyPartScreenController {

    @FXML
    private RadioButton modifyPartInHouseRB;

    @FXML
    private RadioButton modifyPartOutsourcedRB;

    @FXML
    private Label modifyPartIdLabel;

    @FXML
    private Label modifyPartWildCardLabel;

    @FXML
    private TextField modifyPartNameField;

    @FXML
    private TextField modifyPartInvField;

    @FXML
    private TextField modifyPartCostField;

    @FXML
    private TextField modifyPartMaxField;

    @FXML
    private TextField modifyPartMinField;

    @FXML
    private TextField modifyPartWildCardField;

    @FXML
    private Button modifyPartCancelButton;

    @FXML
    private Button modifyPartSaveButton;
    
    Part part;

    @FXML
    void modifyPartCancelButtonAction(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("All unsaved data will be lost!");
        alert.setContentText("Are you sure you want to leave?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Stage stage = (Stage) modifyPartCancelButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            alert.close();
        }
    }

    @FXML
    void modifyPartInHouseRBAction(ActionEvent event) {
        modifyPartWildCardLabel.setText("Machine ID");
        modifyPartWildCardField.setPromptText("Machine ID");
    }

    @FXML
    void modifyPartOutsourcedRBAction(ActionEvent event) {
        modifyPartWildCardLabel.setText("Company Name");
        modifyPartWildCardField.setPromptText("Company Name");
    }

    @FXML
    void modifyPartSaveButtonAction(ActionEvent event) throws IOException {
        
        boolean correct = false;
        
        try{
            
            String name = modifyPartNameField.getText();
            int inv;
            double price = Double.parseDouble(modifyPartCostField.getText());
            int max;
            int min;
        
            if (modifyPartInvField.getText().equals("")){
                inv = 0;
            }
            else {
                inv = Integer.parseInt(modifyPartInvField.getText());
            }
            
            if (modifyPartMaxField.getText().equals("")){
                max = 0;
            }
            else {
                max = Integer.parseInt(modifyPartMaxField.getText());
            }
            
            if (modifyPartMinField.getText().equals("")){
                min = 0;
            }
            else {
                min = Integer.parseInt(modifyPartMinField.getText());
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
                if (modifyPartInHouseRB.isSelected()) {           
                    InhousePart updatedPart = new InhousePart();
                    updatedPart.setPartID(Integer.parseInt(modifyPartIdLabel.getText()));
                    updatedPart.setName(name);
                    updatedPart.setPrice(price);
                    updatedPart.setInStock(inv);
                    updatedPart.setMax(max);
                    updatedPart.setMin(min);
                    updatedPart.setMachineID(Integer.parseInt(modifyPartWildCardField.getText()));
                    allParts.removeAll(part);
                    allParts.addAll(updatedPart);
                    
                    correct = true;

                }
                else if (modifyPartOutsourcedRB.isSelected()) {
                    OutsourcedPart updatedPart = new OutsourcedPart();
                    updatedPart.setPartID(Integer.parseInt(modifyPartIdLabel.getText()));
                    updatedPart.setName(name);
                    updatedPart.setPrice(price);
                    updatedPart.setInStock(inv);
                    updatedPart.setMax(max);
                    updatedPart.setMin(min);
                    updatedPart.setCompanyName(modifyPartWildCardField.getText());
                    allParts.removeAll(part);
                    allParts.addAll(updatedPart);
                    
                    correct = true;

                }
            }
        }
        catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty fields detected");
            alert.setContentText("Required Fields: \n\t Product Name \n\t Product Inventory (default 0) \n\t Product Price");
            alert.showAndWait();
        }
        
        if (correct){
            Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Stage stage = (Stage) modifyPartSaveButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    
    @FXML
    public void setPart(Part part){
        this.part = part;
        
        if (part.getClass().equals(InhousePart.class)){
            InhousePart inhouse = (InhousePart)part;
            modifyPartIdLabel.setText(Integer.toString(inhouse.getPartID()));
            modifyPartNameField.setText(inhouse.getName());
            modifyPartInvField.setText(Integer.toString(inhouse.getInStock()));
            modifyPartCostField.setText(Double.toString(inhouse.getPrice()));
            modifyPartMaxField.setText(Integer.toString(inhouse.getMax()));
            modifyPartMinField.setText(Integer.toString(inhouse.getMin()));
            modifyPartWildCardField.setText(Integer.toString(inhouse.getMachineID()));
            modifyPartInHouseRB.fire();
            
        }
        else if (part.getClass().equals(OutsourcedPart.class)){
            OutsourcedPart outsourced = (OutsourcedPart)part;
            modifyPartIdLabel.setText(Integer.toString(outsourced.getPartID()));
            modifyPartNameField.setText(outsourced.getName());
            modifyPartInvField.setText(Integer.toString(outsourced.getInStock()));
            modifyPartCostField.setText(Double.toString(outsourced.getPrice()));
            modifyPartMaxField.setText(Integer.toString(outsourced.getMax()));
            modifyPartMinField.setText(Integer.toString(outsourced.getMin()));
            modifyPartWildCardField.setText(outsourced.getCompanyName());
            modifyPartOutsourcedRB.fire();
        }
    }

}
