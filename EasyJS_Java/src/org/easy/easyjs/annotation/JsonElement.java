package org.easy.easyjs.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 *
 * @author Marcelo Magana Silva
 */
@Retention(RUNTIME) @Target({FIELD, METHOD})
public @interface JsonElement {
    public static final String TEXT = "txt";
    public static final String LABEL = "lbl";
    public static final String SELECT = "select";
    public static final String CHECKBOX = "checkbox";
    public static final String RADIO = "radio";
    /**
     * Name of the JSON element.
     * <p> If the value is "##default", then element name is derived from the
     * JavaBean property name. </p>
     */
    String name() default "##default";
    
    /**
     * Type of the JSON element.
     * <p> this value is mandatory and shall be setted from Enum @link(FieldTypes)
     * </p>
     */
    String type();
    
    /**
     * Selected Index for a <code> <select> </code> element.
     * <p> If the value is leave by default and the type property has not equals to SELECT, this field will not be
     * considered, otherwise it will be used as the selected element in a <code> <select> </code> element. </p>
     */
    
    int selected() default 0;
}
