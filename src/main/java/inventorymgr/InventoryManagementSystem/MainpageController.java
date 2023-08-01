package inventorymgr.InventoryManagementSystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Controller class for the Main Page of the Inventory Management System.
 *
 * FUTURE ENHANCEMENT: Saving/Loading Parts from a file. Interface would need to be adjusted to account for a load/save button
 * but would greatly enhance the usability of the program.
 */
public class MainpageController {

    /**
     * Initializes a new Inventory object which is referenced frequently throughout the class.
     */
    Inventory inventory = new Inventory();

    //FX:ID Declarations

    /**
     * Displays Parts stored in Inventory
     */
    @FXML
    private TableView partsTable;

    /**
     * ID Column in Parts Table
     */
    @FXML
    private TableColumn partsTable_id;

    /**
     * Name Column in Parts Table
     */
    @FXML
    private TableColumn partsTable_name;

    /**
     * Inv (Stock) Column in Parts Table
     */
    @FXML
    private TableColumn partsTable_inv;

    /**
     * Price Column in Parts Table
     */
    @FXML
    private TableColumn partsTable_price;

    /**
     * Used to initiate a search of the Parts List
     */
    @FXML
    private TextField partsSearchBar;

    /**
     * Displays Products stored in Inventory
     */
    @FXML
    private TableView productsTable;

    /**
     * ID Column in Products Table
     */
    @FXML
    private TableColumn productsTable_id;

    /**
     * Name Column in Products Table
     */
    @FXML
    private TableColumn productsTable_name;

    /**
     * Inv (Stock) Column in Products Table
     */
    @FXML
    private TableColumn productsTable_inv;

    /**
     * Price Column in Products Table
     */
    @FXML
    private TableColumn productsTable_price;

    /**
     * Used to initiate a search of the Products List
     */
    @FXML
    private TextField productsSearchBar;

    //Controller Functions

    /** Prompts User to Confirm before Exiting the Program.
     *
     */
    @FXML
    private void onExitButtonClick() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you would like to quit?");
        alert.showAndWait();

        if(alert.getResult().getButtonData().isDefaultButton())
        {
            Main m = new Main();
            m.closeProgram();
        }
    }

    /** Transitions Scene to the Add Part Window when part_addButton is clicked.
     * @exception IOException required by changeScene method.
     * @see Main#changeScene(String)
     */
    @FXML
    private void onPartAddButtonClick() throws IOException {
       Main m = new Main();
       m.changeScene("inventory_addpart.fxml");
    }

    /** Transitions Scene to the Modify Part Window when parts_modifyButton is clicked.
     * <p> Only progresses to the next screen if a part is selected.
     *     Method will display an Alert if nothing is selected from Part List table.
     * </p>
     *
     * LOGICAL ERROR: Scene would transition to next scene even when no part was selected if button was clicked. Added if/else statement to prevent this as well as
     * an alert to inform user to select a part.
     *
     * @exception IOException required by changeScene method.
     * @see Main#changeScene(String)
     */
    @FXML
    private void onPartModifyButtonClick() throws IOException {
        if(partsTable.getSelectionModel().getSelectedItem() != null)
        {
            ModifyPartController.setModifyPartIndex(partsTable.getSelectionModel().getSelectedIndex());
            Main m = new Main();
            m.changeScene("inventory_modifypart.fxml");

        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No Part Selected to Modify!");
            alert.showAndWait();
        }
    }

    /** Removes a selected part from Parts list when parts_deleteButton is clicked.
     * <p> User will be prompted to confirm part deletion before it is actually removed.
     *     Method will display an Alert if nothing is selected from Part List table.
     * </p>
     */
    @FXML
    private void onPartDeleteButtonClick() {
        if(partsTable.getSelectionModel().getSelectedItem() != null)
        {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you would like to delete the selected part?\nThis cannot be undone.");
            alert.showAndWait();

            if(alert.getResult().getButtonData().isDefaultButton())
            {
                Inventory.deletePart(Inventory.getAllParts().get(partsTable.getSelectionModel().getSelectedIndex()));
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No part selected to delete!");
            alert.showAndWait();
        }
    }

    /** Returns a boolean value if any part is found to be associated with a selected product.
     * <p> Used by the onProductDeleteButtonClick() method to check if a product can be safely removed or not.
     * </p>
     * @return boolean value. True if match is found, false if no match is found.
     */
    private boolean checkForPartsInProduct() //If true, part was found in a Product
    {
        if(Inventory.getAllProducts().get(productsTable.getSelectionModel().getSelectedIndex()).getAllAssociatedParts() == null) //If null/has not been initialized
        {
            return false;
        }

        //If the selected Product contains ANY associated Parts
        if(Inventory.getAllProducts().get(productsTable.getSelectionModel().getSelectedIndex()).getAllAssociatedParts().size() > 0) //If 0/has been initialized
        {
            return true;
        }

        return false; //No match found. Product can be removed.
    }

    /** Transitions Scene to the Add Product Window when product_addButton is clicked.
     * @exception IOException required by changeScene method.
     * @see Main#changeScene(String)
     */
    @FXML
    private void onProductAddButtonClick() throws IOException {
        Main m = new Main();
        m.changeScene("inventory_addproduct.fxml");
    }

    /** Transitions Scene to the Modify Product Window when product_modifyButton is clicked.
     * <p> Only progresses to the next screen if a product is selected.
     *     Method will display an Alert if nothing is selected from Product List table.
     * </p>
     *
     * LOGICAL ERROR: Scene would transition even when no product was selected if button was clicked. Added if/else statement to prevent this as well as
     * an alert to inform user to select product.
     *
     * @exception IOException required by changeScene method.
     * @see Main#changeScene(String)
     */
    @FXML
    private void onProductModifyButtonClick() throws IOException {
        if(productsTable.getSelectionModel().getSelectedItem() != null)
        {
            ModifyProductController.setModifyProductIndex(productsTable.getSelectionModel().getSelectedIndex());
            Main m = new Main();
            m.changeScene("inventory_modifyproduct.fxml");
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No Product Selected to Modify!");
            alert.showAndWait();
        }
    }

    /** Removes a selected product from Product list when product_deleteButton is clicked.
     * <p> User will be prompted to confirm product deletion before it is actually removed.
     *     Method will display an Alert if nothing is selected from Product List table.
     * </p>
     */
    @FXML
    private void onProductDeleteButtonClick() {
        if(productsTable.getSelectionModel().getSelectedItem() != null)
        {
            if(checkForPartsInProduct()) //If true, a part was found in a product. Cannot remove.
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Selected Product currently has associated Parts.\nParts must be removed from product before it can be deleted.");
                alert.showAndWait();
                return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you would like to delete the selected product?\nThis cannot be undone.");
            alert.showAndWait();

            if(alert.getResult().getButtonData().isDefaultButton())
            {
                Inventory.deleteProduct(Inventory.getAllProducts().get(productsTable.getSelectionModel().getSelectedIndex()));
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No Product Selected to Delete!");
            alert.showAndWait();
        }
    }

    /** Initializes the tables when the scene is changed to Main page.
     * <p> Refreshes the parts and products tables by calling their respective initialization methods.
     * </p>
     */
    @FXML
    public void initialize()
    {
        initPartsTable();
        initProductsTable();
    }

    /** Initializes the parts table.
     * <p> Loads all Part objects from Inventory and displays them in the table.
     * </p>
     */
    private void initPartsTable() {
        partsTable_id.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partsTable_name.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partsTable_inv.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partsTable_price.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        partsTable.setItems(inventory.getAllParts());
    }

    /** Initializes the products table.
     * <p> Loads all Product objects from Inventory and displays them in the table.
     * </p>
     */
    private void initProductsTable() {
        productsTable_id.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        productsTable_name.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productsTable_inv.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        productsTable_price.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        productsTable.setItems(inventory.getAllProducts());
    }

    /** Initiates a part search based on either ID number or Part Name.
     * <p> Search is only initiated if there are parts currently listed in the Inventory. Search Bar input is checked as an
     * int first, otherwise it is interpreted as a String. If search bar is blank, initPartsTable() is called to refresh table.
     * </p>
     *
     * RUNTIME ERROR: Fixed by using Try/Catch block when attempting to parse int for ID search. Otherwise, when it attempted to parse int program would crash.
     *
     * @see MainpageController#initPartsTable()
     */
    @FXML
    private void partsSearchStarted()
    {
        //Check if field is empty or if list is null
        if(partsSearchBar.getText().isBlank() || Inventory.getAllParts() == null)
        {
            if(Inventory.getAllParts() != null)
            {
                initPartsTable();
            }

            return;
        }

        try //See if it passes as a Number
        {
            int temp = Integer.parseInt(partsSearchBar.getText());

            ObservableList<Part> tempList = null;

            if(Inventory.lookupPart(temp) != null)
            {
                tempList = FXCollections.observableList(new ArrayList<Part>());
                tempList.add(0, Inventory.lookupPart(temp));
            }

            partsTable.setItems(tempList);

            if(tempList == null)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No matching parts found!");
                alert.showAndWait();
            }
        }
        catch(Exception e) //If not, gotta be a String
        {
            partsTable.setItems(inventory.lookupPart(partsSearchBar.getText()));

            if(inventory.lookupPart(partsSearchBar.getText()).size() < 1)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No matching parts found!");
                alert.showAndWait();
            }
        }
    }

    /** Initiates a product search based on either ID number or Product Name.
     * <p> Search is only initiated if there are products currently listed in the Inventory. Search Bar input is checked as an
     * int first, otherwise it is interpreted as a String. If search bar is blank, initProductsTable() is called to refresh table.
     * Alerts the user if no products are found during a ID or String search.
     * </p>
     *
     * RUNTIME ERROR: Fixed by using Try/Catch block when attempting to parse int for ID search. Otherwise, when it attempted to parse int program would crash.
     *
     * @see MainpageController#initProductsTable()
     */
    @FXML
    private void productsSearchStarted()
    {
        //Check if field is empty or if list is null
        if(productsSearchBar.getText().isBlank() || Inventory.getAllProducts() == null)
        {
            if(Inventory.getAllProducts() != null)
            {
                initProductsTable();
            }

            return;
        }

        try //See if it passes as a Number
        {
            int temp = Integer.parseInt(productsSearchBar.getText());

            ObservableList<Product> tempList = null;

            if(Inventory.lookupProduct(temp) != null)
            {
                tempList = FXCollections.observableList(new ArrayList<Product>());
                tempList.add(0, Inventory.lookupProduct(temp));
            }

            productsTable.setItems(tempList);

            if(tempList == null)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No matching products found!");
                alert.showAndWait();
            }
        }
        catch(Exception e) //If not, gotta be a String
        {
            productsTable.setItems(inventory.lookupProduct(productsSearchBar.getText()));

            if(inventory.lookupProduct(productsSearchBar.getText()).size() < 1)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No matching products found!");
                alert.showAndWait();
            }
        }
    }

}