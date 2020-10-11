/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 *
 * @author Zaw L Than
 */
public class Inventory {
    
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    public static void addPart(Part part) {
        allParts.add(part);
    }    
    
    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    public static Part lookupPart(int partId){
        for(Part part : allParts)
            if(part.getId() == partId)
                return part;               
        return null;        
    }

    public static Product lookupProduct(int productId){
        for(Product product : allProducts)
            if(product.getId() == productId)
                return product;                           
        return null; 
    }

    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> result = FXCollections.observableArrayList();
        for(Part part : allParts)
            if(part.getName().contains(partName))
                result.add(part);
        return result;
    }
 
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> result = FXCollections.observableArrayList();
        for(Product product : allProducts)
            if(product.getName().contains(productName))
                result.add(product);
        return result;
    }
  
    public static void updatePart(int index, Part selectedPart){
        selectedPart.setId(index + 1);
        Inventory.getAllParts().set(index, selectedPart);        
    }

    public static void updatProduct(int index, Product newProduct){
        newProduct.setId(index + 1);
        Inventory.getAllProducts().set(index, newProduct);
    }
 
    public static boolean deletePart(Part selectedPart){
        for(Part part : allParts)
            if(part.getId() == selectedPart.getId())
                return allParts.remove(part);
        return false;
    }

    public static boolean deleteProduct(Product selectedProduct){
        for(Product product : allProducts)
            if(product.getId() == selectedProduct.getId())
                return allProducts.remove(product);
        return false; 
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    
    public static ObservableList<Product> getAllProducts() {
            return allProducts;
    }   
}
