package Software1.Controller;


import Software1.Model.InHousePart;
import Software1.Model.Inventory;
import Software1.Model.OutSourcedPart;
import Software1.Model.Part;
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

/**
 * @author Cristian Sotomayor
 */

/**
 * ModifyPartViewController Software1.Controller class for modifypart.fxml
 */

public class ModifyPartViewController implements Initializable {

    /**
     * Part object
     */
    public Part selectedPart;

    /**
     * Stage object
     */
    private Stage stage;

    /**
     * Object to set scene
     */
    private Object scene;

    /**
     * RadioButton object for user to choose to input a part belonging to class OutSourcedPart
     */
    @FXML
    private RadioButton outsourcedPartRadioButton, inHousePartRadioButton;
    /**
     * Label object to change label when user chooses between inHouse or outsourced parts.
     */
    @FXML
    private Label inHousePartOrOutsourcedPartTextFieldLabel;
    /**
     * TextField objects for user to input part attributes
     */
    @FXML
    private TextField idTextField, NameTextField, stockTextField, priceTextField, maximumTextField, minimumTextField, companyNameOrMachineIdTextField;

    /**
     * Variable of type int
     */
    private int partId;

    /**
     * Method to display part selected
     *
     * @param selectedPart the part to set
     */
    public void setPart(Part selectedPart) {
        this.selectedPart = selectedPart;
        partId = Inventory.getAllParts().indexOf(selectedPart);
        idTextField.setText(Integer.toString(selectedPart.getId()));
        NameTextField.setText(selectedPart.getName());
        stockTextField.setText(Integer.toString(selectedPart.getStock()));
        priceTextField.setText(Double.toString(selectedPart.getPrice()));
        maximumTextField.setText(Integer.toString(selectedPart.getMaximum()));
        minimumTextField.setText(Integer.toString(selectedPart.getMinimum()));
        if (selectedPart instanceof InHousePart) {
            InHousePart inHouse = (InHousePart) selectedPart;
            inHousePartRadioButton.setSelected(true);
            this.inHousePartOrOutsourcedPartTextFieldLabel.setText("Machine ID");
            companyNameOrMachineIdTextField.setText(Integer.toString(inHouse.getMachineId()));
        } else {
            OutSourcedPart outsourced = (OutSourcedPart) selectedPart;
            outsourcedPartRadioButton.setSelected(true);
            this.inHousePartOrOutsourcedPartTextFieldLabel.setText("Company Name");
            companyNameOrMachineIdTextField.setText(outsourced.getCompanyName());
        }
    }

    /**
     * Method to change label when user chooses between inHouse or outsourced parts.
     */
    public void changeLabelName() {
        if (!outsourcedPartRadioButton.isSelected())
            this.inHousePartOrOutsourcedPartTextFieldLabel.setText("Machine ID");
        else this.inHousePartOrOutsourcedPartTextFieldLabel.setText("Company Name");
    }

    /**
     * Method to save new part
     *
     * @param event ActionEvent
     * @throws IOException Exception
     */
    @FXML
    void save(ActionEvent event) throws IOException {
        int partStock = Integer.parseInt(stockTextField.getText());
        int partMinimum = Integer.parseInt(minimumTextField.getText());
        int partMaximum = Integer.parseInt(maximumTextField.getText());
        if (MainViewController.confirm("Attention!", "You are saving this part. Are you sure?"))
            if (partMaximum < partMinimum) {
                MainViewController.inform("Wrong Input", "Wrong input in max/min text field", "Enter max and min values again.");
            } else if (partStock < partMinimum || partStock > partMaximum) {
                MainViewController.inform("Wrong Input", "Wrong input in stock text field", "Stock must not be greater than max or lower than min.");
            } else {
                int id = Integer.parseInt(idTextField.getText());
                String name = NameTextField.getText();
                double price = Double.parseDouble(priceTextField.getText());
                int stock = Integer.parseInt(stockTextField.getText());
                int minimum = Integer.parseInt(minimumTextField.getText());
                int maximum = Integer.parseInt(maximumTextField.getText());
                if (inHousePartRadioButton.isSelected()) {
                    try {
                        int machineID = Integer.parseInt(companyNameOrMachineIdTextField.getText());
                        InHousePart temp = new InHousePart(id, name, price, stock, minimum, maximum, machineID);
                        Inventory.updatePart(partId, temp);
                        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("/Software1/View/mainform.fxml"));
                        stage.setTitle("Inventory Management System");
                        stage.setScene(new Scene((Parent) scene));
                        stage.show();
                    } catch (NumberFormatException e) {
                        MainViewController.inform("Input Error", "Check Machine ID ", "Machine ID can only contain numbers 0-9");
                    }
                } else {
                    String companyName = companyNameOrMachineIdTextField.getText();
                    OutSourcedPart temp = new OutSourcedPart(id, name, price, stock, minimum, maximum, companyName);
                    Inventory.updatePart(partId, temp);
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("mainform.fxml"));
                    stage.setTitle("Inventory Management System");
                    stage.setScene(new Scene((Parent) scene));
                    stage.show();
                }
            }
    }

    /**
     * Method to cancel new part addition
     *
     * @param event ActionEvent
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
