/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ibsichile.easyjs.test;

import java.util.HashMap;
import java.util.List;
import org.easy.easyjs.annotation.JsonElement;

/**
 *
 * @author mm29950
 */
public class Person {
    @JsonElement(type=JsonElement.HTML_TEXT)
    private String rut;
    @JsonElement(type=JsonElement.HTML_TEXT, checked = "true", linkValue = "asdad")
    private String firstName;
    @JsonElement(type=JsonElement.HTML_TEXT, id="fecha_nacimiento")
    private String dateOfBirth;
    @JsonElement(type=JsonElement.HTML_TEXT)
    private String lastName;
    @JsonElement(type=JsonElement.HTML_TEXT)
    private String race;
    @JsonElement(type=JsonElement.HTML_TEXT)
    private double height;
    @JsonElement(type=JsonElement.HTML_TEXT)
    private double weight;
    @JsonElement(type=JsonElement.HTML_SELECT, selected = "1")
    private List<String> favouriteColorsList;
    @JsonElement(type=JsonElement.HTML_SELECT, selected = "1")
    private HashMap<String, Cars> carsList = new HashMap<String, Cars>();

    public Person() {
    }

    public Person(String rut, String firstName, String dateOfBirth, String lastName, String race, double height, double weight) {
        this.rut = rut;
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
        this.lastName = lastName;
        this.race = race;
        this.height = height;
        this.weight = weight;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    public List<String> getFavouriteColorsList() {
        return favouriteColorsList;
    }

    public void setFavouriteColorsList(List<String> favouriteColorsList) {
        this.favouriteColorsList = favouriteColorsList;
    }

    public HashMap<String, Cars> getCarsList() {
        return carsList;
    }

    public void setCarsList(HashMap<String, Cars> carsList) {
        this.carsList = carsList;
    }
    
    
    
}
