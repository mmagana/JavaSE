package org.easy.easyjs.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.easy.easyjs.annotation.validator.JsonElementConstraintValidator;

/**
 *
 * @author Marcelo Magana Silva
 */
@Constraint(validatedBy=JsonElementConstraintValidator.class)
@Retention(RUNTIME) @Target({FIELD, METHOD})
public @interface JsonElement {
    public static final String HTML_TEXT = "txt";
    public static final String HTML_TEXT_AREA = "txtarea";
    public static final String HTML_LABEL = "lbl";
    public static final String HTML_SELECT = "select";
    public static final String HTML_CHECKBOX = "checkbox";
    public static final String HTML_RADIO = "radio";
    public static final String HTML_SPAN = "span";
    public static final String HTML_SUBMIT = "submit";
    public static final String HTML_LINK = "link";
    public static final String HTML_BUTTON = "button";
    public static final String HTML_LEGEND = "legend";
    public static final String HTML_IMAGE = "img";
    public static final String HTML_H1 = "h1";
    public static final String HTML_H2 = "h2";
    public static final String HTML_H3 = "h3";
    public static final String HTML_H4 = "h4";
    public static final String HTML_UNORDERED_LIST = "ul";
    public static final String HTML_ORDERED_LIST = "ol";
    public static final String HTML_HIDDEN = "hidden";
    
    String message() default "Invelid HTML Element";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
    /**
     * Name of the JSON element, used as ID in an HTML Element.
     * <p> If the value is "##default", then element name is derived from the
     * JavaBean property name. </p>
     */
    @NotNull
    String id() default "##default";
    
    /**
     * Type of the JSON element.
     * <p> this value is mandatory and shall be setted from Constant values from this Annotation
     * </p>
     */
    @NotNull ()
    @Size(min=2, max=8)
    @Pattern(regexp = "[a-z]")
    String type();
    
    /**
     * Selected Index for a <select>  element.
     * <p> If the value is leave by default and the type property has not equals to SELECT, this field will not be
     * considered, otherwise it will be used as the selected element in a  <select> element. </p>
     */
    @NotNull
    @Pattern(regexp = "[0-9]")
    String selected() default "0";
    
    
    /**
     * Name used as name in an HTML Radio Element.
     */
    @NotNull
    String name() default "";
    
    /**
     * Value used as text in an HTML Radio Element.
     */
    @NotNull
    String text() default "";
    
    /**
     * Value used as height in an HTML Img Element.
     */
    @NotNull
    String height() default "";
    
    /**
     * Value used as width in an HTML Img Element.
     */
    @NotNull
    String width() default "";
    
    /**
     * Used as value in an HTML link Element.
     */
    @NotNull
    String linkValue() default "";
    
    /**
     * Used as checked value in both HTML radio and checkbox Element.
     */
    @NotNull
    @Pattern(regexp = "[a-z]")
    String checked() default "";
}
