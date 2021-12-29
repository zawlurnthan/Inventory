/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventoryapplication;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;
import model.Product;

/**
 *
 * @author Zaw L Than
 */
public class InventoryApplication extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        
        Scene scene = new Scene(root);
    
        stage.setScene(scene);
        stage.setTitle("Inventory Management System");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        InHouse part1 = new InHouse("battery", 108.99, 35, 20, 100, 335);
        InHouse part2 = new InHouse("Screen", 270.99, 80, 20, 100, 335);
        Outsourced part3 = new Outsourced("Processor", 315.99, 33, 20, 100, "Samsung");
        InHouse part4 = new InHouse("Mother Board", 210, 113, 500, 100, 335);
        Outsourced part5 = new Outsourced("Hard Drive", 170, 188, 500, 100, "Sata");
        InHouse part6 = new InHouse("SSD", 200, 109, 500, 100, 335);
        
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addPart(part5);
        Inventory.addPart(part6);
        
        Product product1 = new Product("iPhone", 1099.99, 35, 20, 100);
        Product product2 = new Product("MacBook Pro", 1099.99, 80, 20, 100);
        Product product3 = new Product("Mac Mini", 1099.99, 33, 20, 100);
        
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);
        
        launch(args);
    }
   
}
