/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ConcurrentModificationException;
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
import model.Inventory;
import model.Part;
import model.Product;

/**
 * FXML Controller class
 *
 * @author Zaw L Than
 */
public class MainScreenController implements Initializable {
    
    Stage stage;
    Parent scene;

    @FXML
    private TextField partSearchTxt;
    @FXML
    private TextField productSearchTxt;
    @FXML
    private TableView<Part> partTableView;
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInvLevelCol;
    @FXML
    private TableColumn<Part, Double> partPricePerUnitCol;
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TableColumn<Product, Integer> productIdCol;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, Integer> productInvLevelCol;
    @FXML
    private TableColumn<Product, Double> productPricePerUnitCol;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        partTableView.setItems(Inventory.getAllParts());        
        
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        productTableView.setItems(Inventory.getAllProducts());
        
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));        
    }    

    @FXML
    private void onActionPartSearch(ActionEvent event) {
        String input = partSearchTxt.getText();
        int id = 0;
        ObservableList<Part> result = Inventory.lookupPart(input);

        try{
            if(result.size() == 0)
            id = Integer.parseInt(input); 
            result.add(Inventory.lookupPart(id));
        }
        catch(NumberFormatException e){}
        
        partTableView.setItems(result);
        partSearchTxt.setText("");
    }

    @FXML
    private void onActionPartAdd(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private void onActionPartModify(ActionEvent event) throws IOException {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
            loader.load();

            ModifyPartController MPController = loader.getController();
            MPController.sendPart(partTableView.getSelectionModel().getSelectedItem());

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please select an item from the list to modify!");
            alert.showAndWait();
        }
    }

    

    @FXML
    private void onActionPartDelete(ActionEvent event) throws IOException {
        
        try{    
            Inventory.deletePart(partTableView.getSelectionModel().getSelectedItem());
        }
        catch(ConcurrentModificationException e){
        }catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Select one of the list to delete!");
            alert.showAndWait();
        }
    }

    @FXML
    private void onActionProductSearch(ActionEvent event) {
        String input = productSearchTxt.getText();
        int id = 0;
        ObservableList<Product> result = Inventory.lookupProduct(input);
        
        try{
            if(result.size() == 0)
            id = Integer.parseInt(input); 
            result.add(Inventory.lookupProduct(id));                      
        }
        catch(NumberFormatException e){}
        
        productTableView.setItems(result);
        productSearchTxt.setText("");
    }

    @FXML
    private void onActionProductAdd(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private void onActionProductModify(ActionEvent event) throws IOException {
         try{

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
            loader.load();

            ModifyProductController MdPController = loader.getController();
            MdPController.sendProduct(productTableView.getSelectionModel().getSelectedItem());

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please select one from the list to modify!");
            alert.showAndWait();
        }
    }

    @FXML
    private void onActionProductDelete(ActionEvent event) throws IOException {
        try{
            Inventory.deleteProduct(productTableView.getSelectionModel().getSelectedItem());

        }catch(ConcurrentModificationException e){
        }catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Select one of the list to delete!");
            alert.showAndWait();
        }
    }

    @FXML
    private void onActionExit(ActionEvent event) {
        System.exit(0);
    }    
}
