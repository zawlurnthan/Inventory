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
import model.Part;

/**
 * FXML Controller class
 *
 * @author Zaw L Than
 */
public class ModifyPartController implements Initializable {
    
    int index;
    Stage stage;
    Parent scene;

    @FXML
    private RadioButton rdBtnInHouse;
    @FXML
    private ToggleGroup TgGroup;
    @FXML
    private RadioButton rdBtnOutsourced;
    @FXML
    private TextField mdfpIdTxt;
    @FXML
    private TextField mdfpNameTxt;
    @FXML
    private TextField mdfpInvTxt;
    @FXML
    private TextField mdfpPCTxt;
    @FXML
    private TextField mdfpMaxTxt;
    @FXML
    private TextField mdfpMinTxt;
    @FXML
    private Label switchLb;
    @FXML
    private TextField mIDOrCoNameTxt;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

     @FXML
    private void onRadioCheck() {
        String label = " ";
        
        if(rdBtnOutsourced.isSelected())
            label = "Company Name"; 
        else
            label = "Machine ID"; 
        
        switchLb.setText(label);
    }

    @FXML
    private void onActionSave(ActionEvent event) throws IOException {
        try{  
            String name = mdfpNameTxt.getText();
            int stock = Integer.parseInt(mdfpInvTxt.getText());
            double price = Double.parseDouble(mdfpPCTxt.getText());
            int min = Integer.parseInt(mdfpMinTxt.getText());
            int max = Integer.parseInt(mdfpMaxTxt.getText());
            
            int machineId;
            String companyName = "";
            
            if(rdBtnInHouse.isSelected()){
                machineId = Integer.parseInt(mIDOrCoNameTxt.getText());
                InHouse part = new InHouse(name, price, stock, min, max, machineId);
                Inventory.updatePart(index, part);                
            }else if(rdBtnOutsourced.isSelected()){
                companyName = mIDOrCoNameTxt.getText();
                Outsourced part = new Outsourced(name, price, stock, min, max, companyName);
                Inventory.updatePart(index, part);
            }
            --Part.counter; 
            
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
    private void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    public void sendPart(Part part){
        mdfpIdTxt.setText(String.valueOf(part.getId()));
        mdfpNameTxt.setText(part.getName());
        mdfpInvTxt.setText(String.valueOf(part.getStock()));
        mdfpPCTxt.setText(String.valueOf(part.getPrice()));
        mdfpMinTxt.setText(String.valueOf(part.getMin()));
        mdfpMaxTxt.setText(String.valueOf(part.getMax()));

        if(part instanceof InHouse){
            mIDOrCoNameTxt.setText(String.valueOf(((InHouse)part).getMachineId()));   
            rdBtnInHouse.setSelected(true);
        }else if(part instanceof Outsourced){
            mIDOrCoNameTxt.setText(((Outsourced)part).getCompanyName()); 
            rdBtnOutsourced.setSelected(true);
        }  
        ModifyPartController.this.onRadioCheck();   
        index = Inventory.getAllParts().indexOf(part);
    }   
}
