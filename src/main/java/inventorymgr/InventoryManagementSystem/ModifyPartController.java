package inventorymgr.InventoryManagementSystem;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;


/**
 Controller class for the Modify Part Screen of the Inventory Management System.
 *
 */
public class ModifyPartController {

    /** Index of selected item from Mainpage
     */
    private static int index;

    //FX:ID Declarations

    /** If radio is enabled, part is saved as Outsourced
     */
    @FXML
    private RadioButton outsourcedRadio;

    /** If radio is enabled, part is saved as InHouse
     */
    @FXML
    private RadioButton inhouseRadio;

    /** ID field for part
     */
    @FXML
    private TextField idField;

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

    /** Used by Mainpage Controller to Set the index of the part being modified.
     *
     * @param num int - Index of the part to Modify
     *
     * LOGICAL ERROR: Part selection on Mainpage would not carry over to Modify page (although it wouldn't crash).
     * The index static property as well as this method resolved that issue.
     */
    public static void setModifyPartIndex(int num) //Used to set index of Selected Object
    {
        index = num;
    }

    /** Populates the text fields with the Part at the index in the list.
     *
     * <p>Along with populating the fields, the InHouse or Outsourced radio is also
     * enabled here as well. </p>
     */
    @FXML
    public void initialize()
    {
        Part temp = Inventory.getAllParts().get(index);

        if(Inventory.getAllParts().get(index) instanceof InHouse)
        {
            inhouseRadio.fire();
            switchField.setText("" + ((InHouse) Inventory.getAllParts().get(index)).getMachineId());
        }
        else
        {
            outsourcedRadio.fire();
            switchField.setText(((Outsourced) Inventory.getAllParts().get(index)).getCompanyName());
        }

        idField.setPromptText("" + temp.getId());
        nameField.setText(temp.getName());
        invField.setText("" + temp.getStock());
        priceField.setText("" + temp.getPrice());
        maxField.setText("" + temp.getMax());
        minField.setText("" + temp.getMin());
    }

    /** Starts the process for saving the information as a part.
     * <p>After checking all fields with validateInput(), method will determine if either the inHouse or Outsourced radio
     * is selected. From there, the method will either update (part was originally of that type) or create a new part
     * (the part was originally of another type). Unless an input field was invalid, the method will transition
     * back to the Main Page.</p>
     *
     * @exception IOException required to use changeScene().
     * @see ModifyPartController#validateInput()
     * @see ModifyPartController#createInHousePart()
     * @see ModifyPartController#createOutsourcedPart()
     * @see Main#changeScene(String)
     */
    @FXML
    private void onSaveButtonClick() throws IOException
    {

        Part temp = Inventory.getAllParts().get(index);

        if(!validateInput()) //If input is invalid, return.
            return;

        if(inhouseRadio.isSelected())
        {
            if(temp instanceof InHouse)//Object is ALREADY an InHouse Part
            {
                temp.setName(nameField.getText());
                temp.setPrice(Double.valueOf(priceField.getText()));
                temp.setStock(Integer.valueOf(invField.getText()));
                temp.setMin(Integer.valueOf(minField.getText()));
                temp.setMax(Integer.valueOf(maxField.getText()));
                ((InHouse) temp).setMachineId(Integer.valueOf(switchField.getText()));

            }
            else //Object is NOT an InHouse Part
            {
                createInHousePart();
            }


        }
        else
        {
            if(temp instanceof Outsourced)//Object is ALREADY an Outsourced Part
            {
                temp.setName(nameField.getText());
                temp.setPrice(Double.valueOf(priceField.getText()));
                temp.setStock(Integer.valueOf(invField.getText()));
                temp.setMin(Integer.valueOf(minField.getText()));
                temp.setMax(Integer.valueOf(maxField.getText()));
                ((Outsourced) temp).setCompanyName(switchField.getText());

            }
            else //Object is NOT an Outsourced Part
            {
                createOutsourcedPart();
            }
        }


        //Transition Back to Main Scene
        Main m = new Main();
        m.changeScene("inventory_mainpage.fxml");
    }

    /** Creates a new InHouse part and adds it to the allParts list.
     *
     * <p>Assumes input has been validated at runtime. Creates a new InHouse part from text fields and replaces the part
     * at the index in the list.</p>
     *
     * @see ModifyPartController#validateInput()
     */
    private void createInHousePart()
    {
        InHouse temp = new InHouse(
                Inventory.getAllParts().get(index).getId(),
                nameField.getText(),
                Double.valueOf(priceField.getText()),
                Integer.valueOf(invField.getText()),
                Integer.valueOf(minField.getText()),
                Integer.valueOf(maxField.getText()),
                Integer.valueOf(switchField.getText()));

        Inventory.updatePart(index, temp);
    }

    /** Creates a new Outsourced part and adds it to the allParts list.
     *
     * <p>Assumes input has been validated at runtime. Creates a new Outsourced part from text fields and and replaces the part
     * at the index in the list.</p>
     *
     * @see ModifyPartController#validateInput()
     */
    private void createOutsourcedPart()
    {
        Outsourced temp = new Outsourced(
                Inventory.getAllParts().get(index).getId(),
                nameField.getText(),
                Double.valueOf(priceField.getText()),
                Integer.valueOf(invField.getText()),
                Integer.valueOf(minField.getText()),
                Integer.valueOf(maxField.getText()),
                switchField.getText());

        Inventory.updatePart(index, temp);
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
