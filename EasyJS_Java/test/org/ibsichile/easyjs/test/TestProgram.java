/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ibsichile.easyjs.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.easy.easyjs.EasyJS;

/**
 *
 * @author mm29950
 */
public class TestProgram {
    public static void main(String[] args)
    {
        Person p = new Person();
        p.setDateOfBirth("08/25/1987");
        p.setFirstName("Marcelo");
        p.setHeight(1.68);
        p.setLastName("Magana");
        p.setRace("Latino");
        p.setRut("1-9");
        p.setWeight(78.9);
        List<String> colors = new ArrayList<String>();
        colors.add("Rojo");
        colors.add("Verde");
        colors.add("Amarillo");
        colors.add("Azul");
        colors.add("Gris");
        p.setFavouriteColorsList(colors);
        HashMap<String, Cars> carsList = new HashMap<String, Cars>();
        Cars c = new Cars();
        c.setBrand("Suzuki");
        c.setColor("Black");
        c.setId("dhfr-21");
        carsList.put(c.getId(), c);
        p.setCarsList(carsList);
        EasyJS easy = new EasyJS();
        String json = easy.toJson(p);
        System.out.println("Resultado: \n"+ json);
    }
}
