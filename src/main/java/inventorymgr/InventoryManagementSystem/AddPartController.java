package inventorymgr.InventoryManagementSystem;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 Controller class for the Add Part Screen of the Inventory Management System.
 *
 * FUTURE ENHANCEMENT: Check existing list to prevent duplicate parts from being created
 */
public class AddPartController {

    //FX:ID Declarations

    /** If radio is enabled, new part will be inHouse (enabled by default)
     */
    @FXML
    private RadioButton inhouseRadio;

    /** name input field for part
     */
    @FXML
    private TextField nameField;

    /** Current inventory input field for part
     */
    @FXML
    private TextField invField;

    /** Price input field for part
     */
    @FXML
    private TextField priceField;

    /** Maximum inventory input field for part
     */
    @FXML
    private TextField maxField;

    /** Minimum inventory input field for part
     */
    @FXML
    private TextField minField;

    /** Holds the input data for switchField (Machine ID or Company Name depending on part type).
     */
    @FXML
    private TextField switchField; //Hold Value of Machine ID or Company Name based on Radio Switch

    /** Text describing the data for switchField (Machine ID or Company Name depending on part type).
     */
    @FXML
    private Text switchFieldText; //Should "switch" based on In-House or Outsourced

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

    /** Switches UI to match Outsourced Part Properties
     */
    @FXML
    private void onOutsourcedRadioClick()
    {
        switchFieldText.setText("Company Name");
    }

    /** Switches UI to match InHouse Part Properties
     */
    @FXML
    private void onInHouseRadioClick()
    {
        switchFieldText.setText("Machine ID");
    }

    /** Starts the process for saving the information as a part.
     * <p>After checking all fields with validateInput(), method will determine if a inHouse or Outsourced part
     * needs to be created. Unless an input field was invalid, the method will transition back to the Main Page.</p>
     *
     * @exception IOException required to use changeScene().
     * @see AddPartController#validateInput()
     * @see AddPartController#createInHousePart()
     * @see AddPartController#createOutsourcedPart()
     * @see Main#changeScene(String)
     */
    @FXML
    private void onSaveButtonClick() throws IOException {

        if(!validateInput()) //If input is invalid, return.
            return;

        if(inhouseRadio.isSelected())
        {
            createInHousePart();
        }
        else
        {
            createOutsourcedPart();
        }


        //Transition Back to Main Scene
        Main m = new Main();
        m.changeScene("inventory_mainpage.fxml");
    }

    /** Creates a new InHouse part and adds it to the allParts list.
     *
     * <p>Assumes input has been validated at runtime. Generates ID Number based on the LAST part added to the list
     * (Last Part's ID Number + 1). Creates a new InHouse part from text fields and adds it to the list.</p>
     *
     * @see AddPartController#validateInput()
     */
    private void createInHousePart()
    {
        int idNum = 0;

        //Get Next ID Num
        if(Inventory.getAllParts() == null || Inventory.getAllParts().size() < 1) //If list does not exist, set ID to 1.
        {
            idNum = 1;
        }
        else
        {
            Part temp = Inventory.getAllParts().get(Inventory.getAllParts().size() - 1);
            idNum = temp.getId() + 1;
        }

        Inventory.addPart(new InHouse(
                idNum,
                nameField.getText(),
                Double.valueOf(priceField.getText()),
                Integer.valueOf(invField.getText()),
                Integer.valueOf(minField.getText()),
                Integer.valueOf(maxField.getText()),
                Integer.valueOf(switchField.getText())
        ));
    }

    /** Creates a new Outsourced part and adds it to the allParts list.
     *
     * <p>Assumes input has been validated at runtime. Generates ID Number based on the LAST part added to the list
     * (Last Part's ID Number + 1). Creates a new Outsourced part from text fields and adds it to the list.</p>
     *
     * @see AddPartController#validateInput()
     */
    private void createOutsourcedPart()
    {
        int idNum = 0;

        //Get Next ID Num
        if(Inventory.getAllParts() == null) //If list does not exist, set ID to 1.
        {
            idNum = 1;
        }
        else
        {
            Part temp = Inventory.getAllParts().get(Inventory.getAllParts().size() - 1);
            idNum = temp.getId() + 1;
        }

        Inventory.addPart(new Outsourced(
                idNum,
                nameField.getText(),
                Double.valueOf(priceField.getText()),
                Integer.valueOf(invField.getText()),
                Integer.valueOf(minField.getText()),
                Integer.valueOf(maxField.getText()),
                switchField.getText()
        ));
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

        //Check Machine ID (switchField)
        if(inhouseRadio.isSelected()) //Should only fire if inHouse is Selected
        {
            try
            {
                Integer.parseInt(switchField.getText());
            } catch (Exception e)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Data in Machine ID Field. Please enter a numerical value.");
                alert.showAndWait();
                return false;
            }
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

}
