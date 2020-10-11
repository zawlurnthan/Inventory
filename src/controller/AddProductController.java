/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;
import model.Product;

/**
 * FXML Controller class
 *
 * @author Zaw L Than
 */
public class AddProductController implements Initializable {
    
    ObservableList<Part> tempCon = FXCollections.observableArrayList();
    Stage stage;
    Parent scene;

    @FXML
    private TextField idTxt;
    @FXML
    private TextField nameTxt;
    @FXML
    private TextField invTxt;
    @FXML
    private TextField priceTxt;
    @FXML
    private TextField maxTxt;
    @FXML
    private TextField minTxt;
    @FXML
    private TextField searchBar;
    @FXML
    private TableView<Part> allPartTableView;    
    @FXML
    private TableColumn<Part, Integer> allPartIdCol;
    @FXML
    private TableColumn<Part, String> allPartNameCol;
    @FXML
    private TableColumn<Part, Integer> allPartInvLevelCol;
    @FXML
    private TableColumn<Part, Double> allPartPricePerUnitCol;
    @FXML
    private TableView<Part> associatePartTableView;
    @FXML
    private TableColumn<Part, Integer> associatePartIdCol;
    @FXML
    private TableColumn<Part, String> associatePartNameCol;
    @FXML
    private TableColumn<Part, String> associatePartInvLevelCol;
    @FXML
    private TableColumn<Part, Double> associatePartPricePerUnitCol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        allPartTableView.setItems(Inventory.getAllParts());        
        
        allPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        associatePartTableView.setItems(tempCon);        
        
        associatePartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatePartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatePartInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatePartPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }    

    @FXML
    private void onActionSearch(ActionEvent event) {
        String input = searchBar.getText();
        int id = 0;
        ObservableList<Part> result = Inventory.lookupPart(input);        
  
        try{
            if(result.size() == 0)
            id = Integer.parseInt(input); 
            result.add(Inventory.lookupPart(id));  
        }
        catch(NumberFormatException e){}
        
        allPartTableView.setItems(result);
        searchBar.setText("");
    }

    @FXML
    private void onActionAdd(ActionEvent event) {
        tempCon.add(allPartTableView.getSelectionModel().getSelectedItem());
        associatePartTableView.setItems(tempCon);
    }

    @FXML
    private void onActionDelete(ActionEvent event) {
        tempCon.remove(associatePartTableView.getSelectionModel().getSelectedItem());
        associatePartTableView.setItems(tempCon);          
    }

    @FXML
    private void onActionSave(ActionEvent event) throws IOException {
        try{
            String name = nameTxt.getText();
            int stock = Integer.parseInt(invTxt.getText());
            double price = Double.parseDouble(priceTxt.getText());
            int min = Integer.parseInt(minTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());

            Product product = new Product(name, price, stock, min, max);
            product.addAssociatedPart(tempCon);

            // checking if parts are added to product
            if(tempCon.isEmpty()){
                --Product.counter;
                
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText(" Product must always have at least one part!");
                alert.showAndWait();                 
            }else{                
                
                //checking if product price is less than cost of parts
                double totalPrice = 0;
                for (Part part : product.getAllAssociatedParts())
                    totalPrice += part.getPrice();
                
                if(totalPrice > product.getPrice()){
                    --Product.counter;
                    
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("Price of product cannot be less than the cost of the parts!");
                    alert.showAndWait();   
                }else{
                    Inventory.addProduct(product);  

                    stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }  
            }                        
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
    
}
