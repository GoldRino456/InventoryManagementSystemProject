package inventorymgr.InventoryManagementSystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;

/**
 Controller class for the Modify Product Screen of the Inventory Management System.
 *
 * FEATURE ENHANCEMENT:
 */
public class ModifyProductController {

    //Variable Declarations

    /** Temporary Product that is stored in the list if saved
     *
     */
    private Product temp;

    /** Index of selected item from Mainpage
     */
    private static int index;

    //FX:ID Declarations

    /** id input field for product
     */
    @FXML
    private TextField idField;

    /** name input field for product
     */
    @FXML
    private TextField nameField;

    /** Current inventory input field for product
     */
    @FXML
    private TextField invField;

    /** Price input field for product
     */
    @FXML
    private TextField priceField;

    /** Maximum inventory input field for product
     */
    @FXML
    private TextField maxField;

    /** Minimum inventory input field for product
     */
    @FXML
    private TextField minField;

    /** Table of Parts included in Product
     */
    @FXML
    private TableView selectedPartsTable;

    /** ID Column of selectedPartsTable
     */
    @FXML
    private TableColumn selectedPartsTable_id;

    /** Name Column of selectedPartsTable
     */
    @FXML
    private TableColumn selectedPartsTable_name;

    /** Inv Column of selectedPartsTable
     */
    @FXML
    private TableColumn selectedPartsTable_inv;

    /** Price Column of selectedPartsTable
     */
    @FXML
    private TableColumn selectedPartsTable_price;

    /** Table of All Available Parts
     */
    @FXML
    private TableView partsTable;

    /** ID Column of PartsTable
     */
    @FXML
    private TableColumn partsTable_id;

    /** Name Column of PartsTable
     */
    @FXML
    private TableColumn partsTable_name;

    /** Inv Column of PartsTable
     */
    @FXML
    private TableColumn partsTable_inv;

    /** Price Column of PartsTable
     */
    @FXML
    private TableColumn partsTable_price;

    /** Search Field for PartsTable
     */
    @FXML
    private TextField partsSearchBar;

    //Controller Functions

    /** Transitions Scene to the Main Page, does not save data.
     * @exception IOException required by changeScene method.
     * @see Main#changeScene(String)
     */
    @FXML
    private void onCancelButtonClick() throws IOException {
        Main m = new Main();
        m.changeScene("inventory_mainpage.fxml");
    }

    /** Starts the process for saving the information as a product.
     * <p>After checking all fields with validateInput(), method will determine if Product has any parts associated with it.
     * Data gets saved into the temp product then added to the products list.</p>
     *
     * @exception IOException required to use changeScene().
     * @see ModifyProductController#validateInput()
     * @see Main#changeScene(String)
     */
    @FXML
    private void onSaveButtonClick() throws IOException
    {
        if(!validateInput()) //If input is invalid, return.
            return;


        //Save Product + Update Inventory List
        saveDataToProduct();
        Inventory.updateProduct(index, temp);

        //Transition Back to Main Scene
        Main m = new Main();
        m.changeScene("inventory_mainpage.fxml");
    }

    /** Sets the text field data into the temp product
     *
     */
    private void saveDataToProduct()
    {
        temp.setName(nameField.getText());
        temp.setPrice(Double.valueOf(priceField.getText()));
        temp.setStock(Integer.valueOf(invField.getText()));
        temp.setMin(Integer.valueOf(minField.getText()));
        temp.setMax(Integer.valueOf(maxField.getText()));
    }

    /** Called when the page loads to initialize all fields and variables.
     */
    @FXML
    public void initialize()
    {
        initPartsTable();
        initTempProduct();
        initProductInfo();
        initSelectedPartsTable();
    }

    /** Refreshes the Parts table and loads all available parts
     *
     */
    @FXML
    private void initPartsTable() {
        partsTable_id.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partsTable_name.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partsTable_inv.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partsTable_price.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        partsTable.setItems(Inventory.getAllParts());
    }

    /** Refreshes the selectedParts Table and loads all associated parts.
     */
    @FXML
    private void initSelectedPartsTable() {
        selectedPartsTable_id.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        selectedPartsTable_name.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        selectedPartsTable_inv.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        selectedPartsTable_price.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        selectedPartsTable.setItems(temp.getAllAssociatedParts());
    }

    /** Initiates a search of the parts list.
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
            partsTable.setItems(Inventory.lookupPart(partsSearchBar.getText()));

            if(Inventory.lookupPart(partsSearchBar.getText()).size() < 1)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No matching parts found!");
                alert.showAndWait();
            }
        }
    }

    /** Checks the input of all fields and displays an alert for any issues found.
     *
     * <p>Runs a check for all numerical fields first, then checks if Min-Max range is valid (and inventory falls between them).</p>
     *
     * @return boolean - True if all input fields validate, else returns false.
     */
    private boolean validateInput()
    {
        //Are all numerical fields ACTUALLY numerical values?
        if(!checkNumericalFields())
            return false;

        //Is Min/Max a valid range and does Inventory fall within it?
        if(!checkMinMaxRange())
            return false;

        //All fields are valid, input is OK.
        return true;
    }

    /** Checks if input in numerical text fields can actually be parsed as numerical data.
     *
     * <p>Runs a check on each numerical field by trying to parse a value from a specified text field. If there are any issues,
     * input must be invalid and an error will be displayed alerting the user that data in a specific field is invalid.</p>
     *
     * @return boolean - True if all input fields validate, else returns false.
     */
    private boolean checkNumericalFields()
    {
        //Check Price
        try
        {
            Double.parseDouble(priceField.getText());
        } catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Data in Price Field. Please enter a decimal value.");
            alert.showAndWait();
            return false;
        }

        //Check Inventory
        try
        {
            Integer.parseInt(invField.getText());
        } catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Data in Inventory Field. Please enter a numerical value.");
            alert.showAndWait();
            return false;
        }

        //Check Max
        try
        {
            Integer.parseInt(maxField.getText());
        } catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Data in Max Field. Please enter a numerical value.");
            alert.showAndWait();
            return false;
        }

        //Check Min
        try
        {
            Integer.parseInt(minField.getText());
        } catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Data in Minimum Field. Please enter a numerical value.");
            alert.showAndWait();
            return false;
        }

        //All fields have valid input
        return true;
    }

    /** Checks if min-max range is valid, if so also checks if the inventory count falls within that range.
     *
     * <p>Checks that the Max field is larger than the Min field and that inventory is less than Max, but larger than Min. If any issues,
     * an error is displayed informing the user of the issue.</p>
     *
     * @return boolean - True if all input fields validate, else returns false.
     */
    private boolean checkMinMaxRange()
    {
        if(Integer.parseInt(maxField.getText()) > Integer.parseInt(minField.getText())) //Max Field is Greater than Min Field
        {

            if(Integer.parseInt(invField.getText()) < Integer.parseInt(maxField.getText()) //Inv is less than Max Field
                    && Integer.parseInt(invField.getText()) > Integer.parseInt(minField.getText())) //Inv is greater than Min Field
            {
                return true;
            }

            Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory Count is not within Min-Max Range.\nPlease enter a number between Minimum and Maximum values.");
            alert.showAndWait();
            return false;

        }

        Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Minimum-Maximum range.\nMin value must be less than Max value.");
        alert.showAndWait();
        return false;
    }

    /** Initializes temp product to be used in the rest of the class using selected part's index.
     */
    private void initTempProduct()
    {
        temp = Inventory.getAllProducts().get(index);
    }

    /** Initializes text fields with all information from the selected part.
     */
    private void initProductInfo()
    {
        idField.setText("" + temp.getId());
        nameField.setText(temp.getName());
        invField.setText("" + temp.getStock());
        priceField.setText("" + temp.getPrice());
        maxField.setText("" + temp.getMax());
        minField.setText("" + temp.getMin());
    }

    /** Adds the selected part into the Product's part list
     */
    @FXML
    private void addPartToProduct()
    {
        if(partsTable.getSelectionModel().getSelectedItem() != null)
        {
            temp.addAssociatedPart(Inventory.lookupPart(partsTable.getSelectionModel().getSelectedIndex()+1));
            initSelectedPartsTable();

        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No Part Selected to add!");
            alert.showAndWait();
        }
    }

    /** Removes the selected part from the Product's part list
     */
    @FXML
    private void removePartFromProduct()
    {
        if(selectedPartsTable.getSelectionModel().getSelectedItem() != null)
        {
            temp.deleteAssociatedPart(temp.getAllAssociatedParts().get(selectedPartsTable.getSelectionModel().getSelectedIndex()));
            initSelectedPartsTable();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No Part Selected to remove!");
            alert.showAndWait();
        }
    }

    /** Used by Mainpage Controller to Set the index of the product being modified.
     *
     * @param idx - Index of the product to Modify
     */
    public static void setModifyProductIndex(int idx)
    {
        index = idx;
    }
}
