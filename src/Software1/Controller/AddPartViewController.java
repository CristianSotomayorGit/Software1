package Software1.Controller;


import Software1.Model.InHousePart;
import Software1.Model.Inventory;
import Software1.Model.OutSourcedPart;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Software1.Model.Inventory.getAllParts;


/**
 * @author Cristian Sotomayor
 * AddPartViewController Software1.Controller class for addpart.fxml
 */
public class AddPartViewController implements Initializable {

    /**
     * Stage object
     */
    private Stage stage;

    /**
     * Object for setting scenes
     */
    private Object scene;

    /**
     * RadioButton object for user to choose to input a part belonging to class OutSourcedPart
     */
    @FXML
    private RadioButton outsourcedPartRadioButton;
    /**
     * Label object to change label when user chooses between inHouse or outsourced parts.
     */
    @FXML
    private Label inHousePartOrOutsourcedPartTextFieldLabel;

    /**
     * TextField objects for user to input part attributes
     */
    @FXML
    private TextField nameTextField, stockTextField, priceTextField, maximumTextField, minimumTextField, companyNameOrMachineIDTextField;

    /**
     * Method to retrieve new id
     *
     * @return newId The id to return
     */
    public static int getNewId() {
        int newId = 1;
        for (int i = 0; i < getAllParts().size(); i++) {
            newId++;
        }
        return newId;
    }

    /**
     * Method to change label when user chooses between inHouse or outsourced parts.
     */
    public void changeLabelName() {
        if (outsourcedPartRadioButton.isSelected())
            this.inHousePartOrOutsourcedPartTextFieldLabel.setText("Company Name");
        else this.inHousePartOrOutsourcedPartTextFieldLabel.setText("Machine ID");
    }

    /**
     * Method to save new part
     *
     * @param event The event to take.
     */
    @FXML
    public void save(ActionEvent event) {
        try {
            int partStock = Integer.parseInt(stockTextField.getText());
            int partMinimum = Integer.parseInt(minimumTextField.getText());
            int partMaximum = Integer.parseInt(maximumTextField.getText());
            if (MainViewController.confirm("Attention!", "You are saving this part. Are you sure?"))
                if (partMaximum < partMinimum) {
                    MainViewController.inform("Wrong Input", "Wrong input in max/min text field", "Enter max and min values again.");
                } else if (partStock < partMinimum || partStock > partMaximum) {
                    MainViewController.inform("Wrong Input", "Wrong input in stock text field", "Stock must not be greater than max or lower than min.");
                } else {
                    int newID = getNewId();
                    String name = nameTextField.getText();
                    double price = Double.parseDouble(priceTextField.getText());
                    if (outsourcedPartRadioButton.isSelected()) {
                        String companyName = companyNameOrMachineIDTextField.getText();
                        OutSourcedPart temp = new OutSourcedPart(newID, name, price, partStock, partMinimum, partMaximum, companyName);
                        Inventory.addPart(temp);
                    } else {
                        int machineID = Integer.parseInt(companyNameOrMachineIDTextField.getText());
                        InHousePart temp = new InHousePart(newID, name, price, partStock, partMinimum, partMaximum, machineID);
                        Inventory.addPart(temp);
                    }
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/Software1/View/mainform.fxml"));
                    stage.setTitle("Inventory Management System");
                    stage.setScene(new Scene((Parent) scene));
                    stage.show();
                }
        } catch (Exception e) {
            MainViewController.inform("Wrong Input", "Wrong Input while adding part", "Renter Values");
        }
    }

    /**
     * Method to cancel new part addition
     *
     * @param event The event to take
     * @throws IOException Exception
     */
    @FXML
    public void cancel(ActionEvent event) throws IOException {
        if (MainViewController.confirm("Cancel?", "Are you sure?")) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/Software1/View/mainform.fxml"));
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene((Parent) scene));
            stage.show();

        }
    }

    /**
     * Method to trigger actions to be performed at initialization
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
