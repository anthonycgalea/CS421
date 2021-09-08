package com.company;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.xml.soap.Text;

public class Main extends Application {

    //Create global variables for text fields to allow for easy access
    TextField fNameInput;
    TextField mInitialInput;
    TextField lNameInput;
    TextField yearInput;
    TextField makeInput;
    TextField modelInput;
    TextField vinInput;
    TextField baseCostInput;
    TextField rebateInput;
    RadioButton noneRadio;
    RadioButton simpleRadio;
    RadioButton compRadio;
    CheckBox gapCoverage;
    CheckBox disasterCoverage;
    CheckBox wheelCoverage;
    TextField dealerDiscountInput;
    final int windowWidth = 900;
    final int windowHeight = 600;

    public static void main(String[] args) {
	    // Launch JavaFX Code
        launch(args);
    }

    private void setGridPaneSpacing(GridPane pane) {
        pane.setVgap(10);
        pane.setHgap(10);
        ColumnConstraints colConst = new ColumnConstraints();
        colConst.setPrefWidth(125);
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.getColumnConstraints().addAll(colConst, colConst, colConst, colConst, colConst, colConst);
    }

    private GridPane createBuyerGUI() {
        GridPane buyerGridPane = new GridPane(); //For buyer information
        fNameInput = new TextField();
        mInitialInput = new TextField();
        lNameInput = new TextField();
        buyerGridPane.add(new Label("Buyer Information"), 3, 0);
        buyerGridPane.add(new Label("First Name"), 0, 1);
        buyerGridPane.add(fNameInput, 1, 1);
        buyerGridPane.add(new Label("Middle Initial"), 2, 1);
        buyerGridPane.add(mInitialInput, 3, 1);
        buyerGridPane.add(new Label("Last Name"), 4, 1);
        buyerGridPane.add(lNameInput, 5, 1);
        buyerGridPane.setPadding(new Insets(10, 10, 10, 10));
        setGridPaneSpacing(buyerGridPane);
        return buyerGridPane;
    }

    private GridPane createVehicleGUI() {
        GridPane vehicleGridPane = new GridPane(); //For vehicle information
        yearInput = new TextField();
        makeInput = new TextField();
        modelInput = new TextField();
        vinInput = new TextField();
        vehicleGridPane.add(new Label("Vehicle Information"), 3, 0);
        vehicleGridPane.add(new Label("Year"), 0, 1);
        vehicleGridPane.add(yearInput, 1, 1);
        vehicleGridPane.add(new Label("Make"), 2, 1);
        vehicleGridPane.add(makeInput, 3, 1);
        vehicleGridPane.add(new Label("Model"), 4, 1);
        vehicleGridPane.add(modelInput, 5, 1);
        vehicleGridPane.add(new Label("VIN"), 2, 2);
        vehicleGridPane.add(vinInput, 3, 2);
        setGridPaneSpacing(vehicleGridPane);
        return vehicleGridPane;
    }

    private GridPane createPricingGUI() {
        GridPane pricingGridPane = new GridPane(); //For base pricing information
        baseCostInput = new TextField();
        rebateInput = new TextField();
        pricingGridPane.add(new Label("Pricing"), 3, 0);
        pricingGridPane.add(new Label("Base Cost"), 1, 1);
        pricingGridPane.add(baseCostInput, 2, 1);
        pricingGridPane.add(new Label("Vehicle Rebate"), 3, 1);
        pricingGridPane.add(rebateInput, 4, 1);
        pricingGridPane.setPadding(new Insets(10, 10, 10, 10));
        setGridPaneSpacing(pricingGridPane);
        return pricingGridPane;
    }

    private GridPane createWarrantyRadios() {
        GridPane warrantyGridPane = new GridPane(); //For selecting a base warranty options
        noneRadio = new RadioButton("None");
        simpleRadio = new RadioButton("Simple");
        compRadio = new RadioButton("Comprehensive");
        warrantyGridPane.add(new Label("Warranty Selection"), 3, 0);
        warrantyGridPane.add(noneRadio, 2, 1);
        warrantyGridPane.add(simpleRadio, 3, 1);
        warrantyGridPane.add(compRadio, 4, 1);
        setGridPaneSpacing(warrantyGridPane);
        return warrantyGridPane;
    }
    private GridPane createExtraWarrantySelects() {
        GridPane extraWarranty = new GridPane();
        gapCoverage = new CheckBox("Gap");
        disasterCoverage = new CheckBox("Disaster");
        wheelCoverage = new CheckBox("Wheel Damage");
        extraWarranty.add(new Label("Extra Warranties"), 3, 0);
        extraWarranty.add(gapCoverage,2,1);
        extraWarranty.add(disasterCoverage,3,1);
        extraWarranty.add(wheelCoverage,4,1);
        setGridPaneSpacing(extraWarranty);
        return extraWarranty;
    }

    private GridPane createDealerDiscount() {
        GridPane dealerPane = new GridPane();
        dealerDiscountInput = new TextField();
        dealerPane.add(new Label("Dealer Discount"), 3, 1);
        dealerPane.add(dealerDiscountInput,4,1);
        setGridPaneSpacing(dealerPane);
        return dealerPane;
    }

    private GridPane generateReceiptButton() {
        GridPane receiptBtnPane = new GridPane();
        Button receiptBtn = new Button("Generate Receipt");
        receiptBtn.setPrefWidth(400);
        receiptBtn.setPrefHeight(100);
        receiptBtnPane.add(receiptBtn, 1, 0);
        setGridPaneSpacing(receiptBtnPane);
        ColumnConstraints colConst1 = new ColumnConstraints(); //For column 1, overriding the column widths from the built in function
        colConst1.setPrefWidth(250);
        ColumnConstraints colConstBtn = new ColumnConstraints(); //For the column with the button, overriding the column widths from the built in function
        colConstBtn.setPrefWidth(400);
        receiptBtnPane.getColumnConstraints().clear();
        receiptBtnPane.getColumnConstraints().addAll(colConst1, colConstBtn);
        return receiptBtnPane;
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Car Dealership Information");

        //Create root vbox for housing all GridPanes
        VBox root=new VBox();
        root.setSpacing(10);
        root.getChildren().addAll(createBuyerGUI(), createVehicleGUI(), createPricingGUI(), createWarrantyRadios(), createExtraWarrantySelects(),createDealerDiscount(), generateReceiptButton());
        primaryStage.setScene(new Scene(root, windowWidth, windowHeight));
        primaryStage.show();
    }
}
