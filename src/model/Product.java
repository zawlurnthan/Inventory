/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Zaw L Than
 */
public class Product {
    
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    public static int counter = 0;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(String name, double price, int stock, int min, int max) {
        this.id = ++counter;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    } 
    
    public void setId(int id){
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public void addAssociatedPart(ObservableList<Part> list){
        this.associatedParts.addAll(list);
    }
  
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        for(Part part : associatedParts)
            if(part.getId() == selectedAssociatedPart.getId())
                return associatedParts.remove(part);
        return false;
    }
    
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}
