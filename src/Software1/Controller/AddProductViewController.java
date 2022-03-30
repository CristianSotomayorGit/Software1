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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Cristian Sotomayor
 */

/**
 * AddProductViewController Software1.Controller class for addproduct.fxml
 */

public class AddProductViewController implements Initializable {

    /**
     * List that contains all associated parts
     */
    private final ObservableList<Part> associatedPartList = FXCollections.observableArrayList();

    /**
     * Stage object
     */
    private Stage stage;

    /**
     * Object for setting scenes
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
    private TextField productNameTextField, productStockTextField, productPriceTextField, productMaximumTextField, productMinimumTextField, searchTextField;

    /**
     * Method to trigger associated part search after user inputs search criteria and presses search button
     */
    @FXML
    public void search() {
        String term = searchTextField.getText();
        ObservableList retrievedPart = Inventory.lookupPartByName(term);
        if (retrievedPart.isEmpty()) {
            MainViewController.confirm("No Match Found", "Cannot find a part name with: " + term);
        } else {
            partTableView.setItems(retrievedPart);
        }
    }

    /**
     * Method to add associated part button
     */
    @FXML
    void add() {
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();

        if (selectedPart != null) {
            associatedPartList.add(selectedPart);
            addToPartTableView();
            addToAssociatedPartTable();
        } else {
            MainViewController.inform("Select a part", "Select a part", "Select a part to add to the Product");
        }
    }

    /**
     * Method to remove associated part
     */
    @FXML
    void remove() {
        Part selectedPart = associatedPartTableView.getSelectionModel().getSelectedItem();

        if (selectedPart != null) {
            MainViewController.confirm("Attention!", "You are about to delete" + selectedPart.getName() + " from the Product. Are you sure?");
            associatedPartList.remove(selectedPart);
            addToPartTableView();
            addToAssociatedPartTable();
        } else {
            MainViewController.inform("No Selection", "No Selection", "Please choose something to remove");
        }
    }

    /**
     * Method to cancel new product addition
     *
     * @param event The event to take
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
     * Method to save new product
     *
     * @param event The event to take
     * @throws IOException Exception
     */
    @FXML
    void save(ActionEvent event) throws IOException {

        if (associatedPartList.isEmpty()) {
            MainViewController.inform("Input Error", "Please add one ore more parts", "A product must have one or more parts associated with it.");
            return;
        }

        if (productNameTextField.getText().isEmpty() || productStockTextField.getText().isEmpty() || productPriceTextField.getText().isEmpty() || productMaximumTextField.getText().isEmpty() || productMinimumTextField.getText().isEmpty()) {
            MainViewController.inform("Wrong Input", "Must not have empty text fields", "Renter Values.");
            return;
        }

        Integer stock = Integer.parseInt(this.productStockTextField.getText());
        Integer minimum = Integer.parseInt(this.productMinimumTextField.getText());
        Integer maximum = Integer.parseInt(this.productMaximumTextField.getText());

        if (maximum < minimum) {
            MainViewController.inform("Wrong Input", "Wrong input min and max field", "Renter max and min values.");
            return;
        }

        if (stock < minimum || stock > maximum) {
            MainViewController.inform("Wrong Input", "Wrong input in stock text field", "Stock must not be greater tha maximum or less than minimum");
            return;
        }


        if (MainViewController.confirm("Attention!", "You are saving this part. Are you sure?")) {
            Product product = new Product();
            product.setId(getNewId());
            product.setName(this.productNameTextField.getText());
            product.setPrice(Double.parseDouble(this.productPriceTextField.getText()));
            product.setStock(Integer.parseInt(this.productStockTextField.getText()));
            product.setMinimum(Integer.parseInt(this.productMinimumTextField.getText()));
            product.setMaximum(Integer.parseInt(this.productMaximumTextField.getText()));
            product.addAssociatedPart(associatedPartList);
            Inventory.addProduct(product);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/Software1/View/mainform.fxml"));
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }

    }

    /**
     * Method to retrieve new id
     *
     * @return newId The id to return
     */
    private int getNewId() {
        int newId = 1;
        for (int i = 0; i < Inventory.getAllProducts().size(); i++) {
            if (Inventory.getAllProducts().get(i).getId() == newId) {
                newId++;
            }
        }
        return newId;
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

        ObservableList<Part> originalPartList = Inventory.getAllParts();

        //Columns and Table for un-associated parts.
        partIdTableViewColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameTableViewColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceTableViewColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        partStockTableViewColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        partTableView.setItems(originalPartList);

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