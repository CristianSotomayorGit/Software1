package Software1.Controller;

import Software1.Model.Inventory;
import Software1.Model.Part;
import Software1.Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Cristian Sotomayor
 */

/**
 * ModifyProductViewController Software1.Controller class for modifyproduct.fxml
 */

public class ModifyProductViewController implements Initializable {

    /**
     * List that contains all associated parts
     */
    private final ObservableList<Part> associatedPartList = FXCollections.observableArrayList();
    /**
     * Stage object
     */
    private Stage stage;

    /**
     * Object to set scene
     */
    private Object scene;

    /**
     * TableView object to display parts and associated parts
     */
    @FXML
    private TableView<Part> partTableView, associatedPartTableView;

    /**
     * TableColumn objects to display part and product ids
     */
    @FXML
    private TableColumn<Part, Integer> partIdTableViewColumn, associatedPartIdTableViewColumn;

    /**
     * TableColumn objects to display part and product names
     */
    @FXML
    private TableColumn<Part, String> partNameTableViewColumn, associatedPartNameTableViewColumn;

    /**
     * TableColumn objects to display part and product stock
     */
    @FXML
    private TableColumn<Part, Integer> partStockTableViewColumn, associatedPartStockTableViewColumn;

    /**
     * TableColumn objects to display part and product prices
     */
    @FXML
    private TableColumn<Part, Double> partPriceTableViewColumn, associatedPartPriceTableViewColumn;

    /**
     * TextField objects for user to input product attributes
     */
    @FXML
    private TextField productIdTextField, productNameTextField, productStockTextField, productPriceTextField, productMaximumTextField, productMinimumTextField, searchTextField;

    /**
     * Product object
     */
    @FXML
    private Product selectedProduct;

    /**
     * Variable of type int
     */
    private int productID;

    /**
     * Method to display product selected
     *
     * @param selectedProduct the product to set
     */
    public void setProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
        productID = Inventory.getAllProducts().indexOf(selectedProduct);
        productIdTextField.setText(Integer.toString(selectedProduct.getId()));
        productNameTextField.setText(selectedProduct.getName());
        productStockTextField.setText(Integer.toString(selectedProduct.getStock()));
        productPriceTextField.setText(Double.toString(selectedProduct.getPrice()));
        productMaximumTextField.setText(Integer.toString(selectedProduct.getMaximum()));
        productMinimumTextField.setText(Integer.toString(selectedProduct.getMinimum()));
        associatedPartList.addAll(selectedProduct.getAllAssociatedParts());
    }

    /**
     * Method to trigger associated part search after user inputs search criteria and presses search button
     */
    @FXML
    public void search() {
        String term = searchTextField.getText();
        ObservableList retrievedPart = Inventory.lookupPartByName(term);
        if (retrievedPart.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("No Match Found");
            alert.setHeaderText("Cannot find a part name with: " + term);
            alert.showAndWait();
        } else {
            partTableView.setItems(retrievedPart);
        }
    }

    /**
     * Method to cancel product modification
     *
     * @param event ActionEvent
     * @throws IOException Exception
     */
    @FXML
    public void cancel(ActionEvent event) throws IOException {
        if (MainViewController.confirm("Cancel", "You are about to cancel. Are you sure?")) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/Software1/View/mainform.fxml"));
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }


    /**
     * Method to save modified product
     *
     * @param event ActionEvent
     * @throws IOException Exception
     */
    @FXML
    void save(ActionEvent event) throws IOException {
        int productInventory = Integer.parseInt(productStockTextField.getText());
        int productMinimum = Integer.parseInt(productMinimumTextField.getText());
        int productMaximum = Integer.parseInt(productMaximumTextField.getText());
        if (MainViewController.confirm("Attention!", "You are saving this part. Are you sure?"))
            if (productMaximum < productMinimum) {
                MainViewController.inform("Wrong Input", "Wrong Input in min and max field", "Renter Min and Max value.");
            } else if (productInventory < productMinimum || productInventory > productMaximum) {
                MainViewController.inform("Wrong Input", "Wrong Input in inventory field", "Stock must not be greater tha maximum or less than minimum");
            } else {
                Product modProduct = selectedProduct;
                selectedProduct.setId(Integer.parseInt(productIdTextField.getText()));
                selectedProduct.setName(productNameTextField.getText());
                selectedProduct.setStock(Integer.parseInt(productStockTextField.getText()));
                selectedProduct.setPrice(Double.parseDouble(productPriceTextField.getText()));
                selectedProduct.setMaximum(Integer.parseInt(productMaximumTextField.getText()));
                selectedProduct.setMinimum(Integer.parseInt(productMinimumTextField.getText()));
                selectedProduct.getAllAssociatedParts().clear();
                selectedProduct.addAssociatedPart(associatedPartList);
                Inventory.updateProduct(productID, selectedProduct);

                //Back to Software1.Software1.Main.Main Screen
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/Software1/View/mainform.fxml"));
                stage.setTitle("Inventory Management System");
                stage.setScene(new Scene((Parent) scene));
                stage.show();
            }
    }

    /**
     * Method to add part to associated parts
     */
    @FXML
    void add() {
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();

        if (selectedPart != null) {
            associatedPartList.add(selectedPart);
            addToAssociatedPartTable();
        } else {
            MainViewController.inform("Pick", "Pick part", "Pick a part in order to add to the Product");
        }
    }

    /**
     * Method to remove part from associated parts
     */
    @FXML
    void remove() {
        Part selectedPart = associatedPartTableView.getSelectionModel().getSelectedItem();

        if (selectedPart != null) {
            MainViewController.confirm("Deleting Parts", "Are you sure you want to delete " + selectedPart.getName() + " from the Product?");
            associatedPartList.remove(selectedPart);
            addToAssociatedPartTable();
        } else {
            MainViewController.inform("No Part Picked", "No Part Picked", "Please pick a part to be removed");
        }
    }


    /**
     * Method to display new part in tableview
     */
    public void addToPartTableView() {
        partTableView.setItems(Inventory.getAllParts());
    }


    /**
     * Method to display new associated part in tableview
     */
    private void addToAssociatedPartTable() {
        associatedPartTableView.setItems(associatedPartList);
    }


    /**
     * Method to trigger actions to be performed at initialization
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Columns and Table for un-associated parts.
        partIdTableViewColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameTableViewColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceTableViewColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        partStockTableViewColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partTableView.setItems(Inventory.getAllParts());

        //Columns and Table for associated parts
        associatedPartIdTableViewColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameTableViewColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartPriceTableViewColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedPartStockTableViewColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartTableView.setItems(associatedPartList);

        addToPartTableView();
        addToAssociatedPartTable();
    }
}
