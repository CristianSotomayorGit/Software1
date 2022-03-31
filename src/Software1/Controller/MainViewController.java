package Software1.Controller;


import Software1.Model.*;
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

import javax.swing.text.View;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author Cristian Sotomayor
 * MainViewController Software1.Controller class for mainform.fxml
 */

public class MainViewController implements Initializable {

    /**
     * TableView object to display parts in mainform.fxml
     */
    @FXML
    private TableView<Part> partTableView;

    /**
     * TableView object to display products in mainform.fxml
     */
    @FXML
    private TableView<Product> productTableView;

    /**
     * TableColumn objects to display part and product ids in mainform.fxml
     */
    @FXML
    private TableColumn<Part, Integer> partIdTVColumn, productIdTVColumn;
    /**
     * TableColumn objects to display part and product names in mainform.fxml
     */
    @FXML
    private TableColumn<Part, String> partNameTVColumn, productNameTVColumn;
    /**
     * TableColumn objects to display part and product stock in mainform.fxml
     */
    @FXML
    private TableColumn<Part, Integer> partStockTVColumn, productStockTVColumn;
    /**
     * TableColumn objects to display part and product prices in mainform.fxml
     */
    @FXML
    private TableColumn<Part, Double> partPriceTVColumn, productPriceTVColumn;
    /**
     * TextField object for user to input part search criteria
     */
    @FXML
    private TextField partSearchTextField;
    /**
     * TextField object for user to input product search criteria
     */
    @FXML
    private TextField productSearchTextField;

    /**
     * Parent Object
     */
    private Parent scene;

    /**
     * Method to create dialog box to inform user of an event
     * @param title String
     * @param header String
     * @param content String
     */
    @FXML
    static void inform(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Method to create dialog box for user to confirm an event
     * @param title String
     * @param content String
     * @return button press
     */
    @FXML
    static boolean confirm(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText("Confirm");
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    /**
     * Method to open addpart.fxml
     * @param event ActionEvent
     * @throws IOException Exception
     */
    @FXML
    public void addPart(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Software1/View/addpart.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method to open modifypart.fxml
     * @param event ActionEvent
     *
     */
    @FXML
    public void modifyPart(ActionEvent event) {
        try {
            Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
            if (selectedPart == null) {
                inform("No Selection", "There is no selected part to modify.", "Please select a part before attempting to modify.");
            } else {
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Software1/View/modifypart.fxml"));
                scene = loader.load();
                ModifyPartViewController controller = loader.getController();
                controller.setPart(selectedPart);
                stage.setTitle("Modify Part");
                stage.setScene(new Scene(scene));
                stage.show();
            }

        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    /**
     * Method to trigger part deletion after user presses delete and confirms event
     */
    @FXML
    public void deletePart() {
        if (partTableView.getSelectionModel().isEmpty()) {
            inform("Attention!", "No Part Selected", "No Part Selected");
            return;
        }
        if (confirm("Attention!", "You are about to delete this part. Are you sure?")) {
            int selectedPart = partTableView.getSelectionModel().getSelectedIndex();
            partTableView.getItems().remove(selectedPart);
        }
    }

    /**
     * Method to trigger part search after user inputs search criteria and presses search button
     */
    @FXML
    public void searchPart() {
        String term = partSearchTextField.getText();
        ObservableList foundPart = Inventory.lookupPartByName(term);
        if (foundPart.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("No Match Found");
            alert.setHeaderText("Cannot find a part name with: " + term);
            alert.showAndWait();
        } else if (term.isEmpty()){
            partTableView.setItems(Inventory.getAllParts());
            partIdTVColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
            partNameTVColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            partStockTVColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
            partPriceTVColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        } else {
            partTableView.setItems(foundPart);
        }
    }

    /**
     * Method to open addproduct.fxml
     * @param event ActionEvent
     * @throws IOException Exception
     */
    @FXML
    public void addProduct(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Software1/View/addproduct.fxml"));
        stage.setTitle("Add Product");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Method to open modifyproduct.fxml
     * @param event ActionEvent
     */
    @FXML
    public void modifyProduct(ActionEvent event) {
        try {
            Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
            if (selectedProduct == null) {
                inform("No Selection", "There is no selected part to modify.", "Please select a part before attempting to modify.");
            } else {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Software1/View/modifyproduct.fxml"));
            scene = loader.load();
            ModifyProductViewController controller = loader.getController();
            controller.setProduct(selectedProduct);
            stage.setTitle("Modify Product");
            stage.setScene(new Scene(scene));
            stage.show();}

        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    /**
     * Method to trigger product deletion after user presses delete and confirms event
     */
    @FXML
    public void deleteProduct() {
        TableView.TableViewSelectionModel<Product> selectedProduct = productTableView.getSelectionModel();


        if (selectedProduct.isEmpty()) {
            inform("Attention!", "No Product Selected", "Please choose product from the above list");
        }
        int id = productTableView.getSelectionModel().getSelectedItem().getId();
        if (Inventory.lookupProductById(id).getAllAssociatedParts().isEmpty()) {
            if (confirm("Attention!", "You are about to delete this product. Are you sure?")) {
                productTableView.getItems().remove(selectedProduct.getSelectedIndex());
            }
        }
        else {
            inform("Cannot Delete", "Cannot delete product.", "Cannot delete product because of associated parts.");
        }
    }


    /**
     * Method to trigger product search after user inputs search criteria and presses search button
     */
    @FXML
    public void searchProduct() {

        System.out.print("search product");
        String term = productSearchTextField.getText();
        ObservableList foundProducts = Inventory.lookupProductByName(term);
        if (foundProducts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("No Match Found");
            alert.setHeaderText("Cannot find a product name with: " + term);
            alert.showAndWait();
        } else if (term.isEmpty()){
            productTableView.setItems((Inventory.getAllProducts()));
            productIdTVColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
            productNameTVColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            productStockTVColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
            productPriceTVColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        } else {
            productTableView.setItems(foundProducts);

        }
    }

    /**
     * Method to close application
     */
    @FXML
    public void exit() {


        confirm("Exit", "You are about to exit. Are you sure?");
        {
            System.exit(0);
        }
    }

    /**
     * Method to trigger actions to be performed at initialization
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        partTableView.setItems(Inventory.getAllParts());
        partIdTVColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        partNameTVColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStockTVColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceTVColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTableView.setItems((Inventory.getAllProducts()));
        productIdTVColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        productNameTVColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productStockTVColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceTVColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


    }

}