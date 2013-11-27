/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ibsichile.easyjs.test;

import org.easy.easyjs.annotation.JsonElement;

/**
 *
 * @author mm29950
 */
public class Cars {
    
    @JsonElement(type=JsonElement.TEXT)
    private String id;
    @JsonElement(type=JsonElement.TEXT)
    private String brand;
    @JsonElement(type=JsonElement.TEXT)
    private String color;

    public Cars() {
    }

    public Cars(String id, String brand, String color) {
        this.id = id;
        this.brand = brand;
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    
}
