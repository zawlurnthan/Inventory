/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

/**
 * FXML Controller class
 *
 * @author Zaw L Than
 */
public class AddPartController implements Initializable {
    
    Stage stage;
    Parent scene;

    @FXML
    private RadioButton rdBtnInHouse;
    @FXML
    private ToggleGroup TgGroup;
    @FXML
    private RadioButton rdBtnOutsourced;
    @FXML
    private TextField adpIdTxt;
    @FXML
    private TextField adpNameTxt;
    @FXML
    private TextField adpInvTxt;
    @FXML
    private TextField adpPCTxt;
    @FXML
    private TextField adpMaxTxt;
    @FXML
    private TextField adpMinTxt;
    @FXML
    private TextField adpMaIDTxt;
    @FXML
    private Label switchLb;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   

    @FXML
    private void onActionSavePart(ActionEvent event) throws IOException {
        
        try{
            String name = adpNameTxt.getText();
            int stock = Integer.parseInt(adpInvTxt.getText());
            double price = Double.parseDouble(adpPCTxt.getText());
            int min = Integer.parseInt(adpMinTxt.getText());
            int max = Integer.parseInt(adpMaxTxt.getText());
            int machineId;
            String companyName = "";
            
            if(rdBtnInHouse.isSelected()){
                machineId = Integer.parseInt(adpMaIDTxt.getText());
                Inventory.addPart(new InHouse(name, price, stock, min, max, machineId));
                
            }else if(rdBtnOutsourced.isSelected()){
                companyName = adpMaIDTxt.getText();
                Inventory.addPart(new Outsourced(name, price, stock, min, max, companyName));
            }  
            
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
            
            
            }catch(NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Please enter valid value in each boxs!");
                alert.showAndWait();                
            }
    }

    @FXML
    private void onActionCancelPart(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();            
    }

    @FXML
    private void onRadioCheck() {
        String label = "";
        
        if(rdBtnOutsourced.isSelected())
            label = "Company Name"; 
        else
            label = "Mechine ID"; 
        
        switchLb.setText(label);
    }    
}
