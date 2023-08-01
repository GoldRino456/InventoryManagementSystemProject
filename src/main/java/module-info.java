module inventorymgr.InventoryManagementSystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens inventorymgr.InventoryManagementSystem to javafx.fxml;
    exports inventorymgr.InventoryManagementSystem;
}