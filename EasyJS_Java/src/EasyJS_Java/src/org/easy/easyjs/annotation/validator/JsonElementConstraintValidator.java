/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easy.easyjs.annotation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.easy.easyjs.annotation.JsonElement;

/**
 *
 * @author mm29950
 */
public class JsonElementConstraintValidator implements ConstraintValidator<JsonElement, String> {

    private String htmlElement;
    private JsonElement elem;

    @Override
    public void initialize(JsonElement constraintAnnotation) {
        htmlElement = constraintAnnotation.type();
        elem = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean validation = false;
        context.disableDefaultConstraintViolation();
        if (value.equals(htmlElement)) {
            if (htmlElement.equals(JsonElement.HTML_TEXT) || htmlElement.equals(JsonElement.HTML_BUTTON)
                    || htmlElement.equals(JsonElement.HTML_CHECKBOX) || htmlElement.equals(JsonElement.HTML_H1)
                    || htmlElement.equals(JsonElement.HTML_H2) || htmlElement.equals(JsonElement.HTML_H3)
                    || htmlElement.equals(JsonElement.HTML_H4) || htmlElement.equals(JsonElement.HTML_HIDDEN)
                    || htmlElement.equals(JsonElement.HTML_LABEL) || htmlElement.equals(JsonElement.HTML_LEGEND)
                    || htmlElement.equals(JsonElement.HTML_ORDERED_LIST) || htmlElement.equals(JsonElement.HTML_SPAN)
                    || htmlElement.equals(JsonElement.HTML_SUBMIT) || htmlElement.equals(JsonElement.HTML_TEXT)
                    || htmlElement.equals(JsonElement.HTML_TEXT_AREA) || htmlElement.equals(JsonElement.HTML_UNORDERED_LIST)) {
                if (!elem.checked().isEmpty() || !elem.height().isEmpty() || !elem.linkValue().isEmpty() || !elem.name().isEmpty() || !elem.selected().isEmpty() || !elem.text().isEmpty() || !elem.width().isEmpty()) {
                    String error = "The element defined cannot be configured with the selected elements";
                    context.buildConstraintViolationWithTemplate(error)
                            .addConstraintViolation();
                    validation = false;
                } else {
                    validation = true;
                }
            } else if (htmlElement.equals(JsonElement.HTML_LINK)) {
                if (elem.linkValue().isEmpty()) {
                    String error = "The element must have an URL value to use as href";
                    context.buildConstraintViolationWithTemplate(error)
                            .addConstraintViolation();
                    validation = false;
                } else {
                    validation = true;
                }
            } else if (htmlElement.equals(JsonElement.HTML_IMAGE)) {
                if (elem.width().isEmpty() || elem.height().isEmpty()) {
                    String error = "The element must have a correct size format";
                    context.buildConstraintViolationWithTemplate(error)
                            .addConstraintViolation();
                    validation = false;
                } else {
                    validation = true;
                }
            } else if (htmlElement.equals(JsonElement.HTML_RADIO)) {
                if (elem.name().isEmpty() || elem.text().isEmpty() || elem.checked().isEmpty()) {
                    String error = "The element must have a correct format, you shall define the name, text and checked attributes";
                    context.buildConstraintViolationWithTemplate(error)
                            .addConstraintViolation();
                    validation = false;
                } else {
                    validation = true;
                }
            }
        } else {
            String error = "The element defined cannot be configured with the selected elements";
            context.buildConstraintViolationWithTemplate(error)
                    .addConstraintViolation();
            validation = false;
        }
        return validation;
    }
}
