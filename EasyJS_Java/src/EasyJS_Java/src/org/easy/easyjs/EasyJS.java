/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easy.easyjs;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.easy.easyjs.annotation.JsonElement;
import org.easy.easyjs.exception.EasyJSParsingFieldsException;
import org.easy.easyjs.util.Utilities;

/**
 *
 * @author mm29950
 */
public class EasyJS {

    private final Collection<Integer> modifiers = new HashSet<Integer>();
    private final EasyMemoryStack<Object> ancestor = new EasyMemoryStack<Object>();

    public EasyJS() {
        modifiers.add(Modifier.TRANSIENT);
        modifiers.add(Modifier.STATIC);
    }

    public String toJson(Object o) {
        try {
            return (new StringBuilder("[")).append(serializeObject(o, false)).append("]").toString();
        } catch (EasyJSParsingFieldsException ex) {
            Logger.getLogger(EasyJS.class.getName()).log(Level.SEVERE, null, ex);
            return "[]";
        }
    }

    private StringBuilder serializeObject(Object obj, boolean innerObject) throws EasyJSParsingFieldsException {
        StringBuilder sb = new StringBuilder();
        if (obj == null) {
            throw new EasyJSParsingFieldsException("The object must not be null.");
        }
        if (ancestor.contains(obj)) {
            throw new IllegalStateException("Circular reference found: " + obj);
        }
        ancestor.push(obj);
        try {
            Class cls = Class.forName(obj.getClass().getName());
            boolean first = true;
            for (Field f : cls.getDeclaredFields()) {
                if (!modifiers.contains(f.getModifiers())) {
                    for (Annotation a : f.getDeclaredAnnotations()) {
                        if (a.annotationType().isAssignableFrom(JsonElement.class)) {
                            JsonElement element = (JsonElement) a;
                            String annName = element.id();
                            Object[] keysArray = Utilities.getKeyTypeList().get(element.type());
                            annName = (annName.equals("##default") ? f.getName() : annName);
                            f.setAccessible(true);
                            Object o = f.get(obj);
                            if (o == null) {
                                sb = appendElement(innerObject, sb, keysArray, loadObjectArray(keysArray.length, element.type(), "", annName), first, false, false);
                                first = false;
                            } else {
                                if (!element.type().equals(JsonElement.HTML_IMAGE) && !element.type().equals(JsonElement.HTML_LINK)
                                        && !element.type().equals(JsonElement.HTML_RADIO) && !element.type().equals(JsonElement.HTML_SELECT)) {
                                    sb = appendElement(innerObject, sb, keysArray, loadObjectArray(keysArray.length, element.type(), o, annName), first, false, false);
                                    first = false;
                                } else {
                                    if (element.type().equals(JsonElement.HTML_IMAGE)) {
                                        sb = appendElement(innerObject, sb, keysArray, loadObjectArray(keysArray.length, element.type(), o, annName, element.height(), element.width()), first, false, false);
                                    } else if (element.type().equals(JsonElement.HTML_LINK)) {
                                        sb = appendElement(innerObject, sb, keysArray, loadObjectArray(keysArray.length, element.type(), element.linkValue(), annName, o), first, false, false);
                                    } else if (element.type().equals(JsonElement.HTML_RADIO)) {
                                        sb = appendElement(innerObject, sb, keysArray, loadObjectArray(keysArray.length, element.type(), o, annName, element.name(), element.text(), element.checked()), first, false, false);
                                    } else if (element.type().equals(JsonElement.HTML_SELECT)) {
                                        sb = appendElement(innerObject, sb, keysArray, loadObjectArray(keysArray.length, element.type(), o, annName, element.selected()), first, true, (isCollectionOrArray(o.getClass()) ? true : (isMap(o.getClass()) ? false : false)));
                                    } else {
                                        sb = appendElement(innerObject, sb, keysArray, loadObjectArray(keysArray.length, element.type(), o, annName), first, true, (isCollectionOrArray(o.getClass()) ? true : (isMap(o.getClass()) ? false : false)));
                                    }
                                    first = false;
                                }
                            }
                        }
                    }
                }
            }
        } catch (ClassNotFoundException ex) {
            throw new EasyJSParsingFieldsException("The object to parse could not be created.");
        } catch (IllegalArgumentException ex) {
            throw new EasyJSParsingFieldsException("The object to parse contains Ilegal Arguments");
        } catch (IllegalAccessException ex) {
            throw new EasyJSParsingFieldsException("The object to parse could not be accessed.");
        }
        return sb;
    }

    private Object[] loadObjectArray(int caps, Object... arguments) {
        Object[] $o = new Object[caps];
        int $x = 0;
        for (Object o : arguments) {
            $o[$x] = (o != null ? o : "");
            $x++;
        }
        return $o;
    }
//    

    private static boolean isCollectionOrArray(Class<?> type) {
        return Collection.class.isAssignableFrom(type) || type.isArray();
    }

    private static boolean isMap(Class<?> type) {
        return Map.class.isAssignableFrom(type);
    }
    
    private static boolean isSomeSimpleElement(Class<?> type)
    {
        return (type.isPrimitive() || type.isSynthetic() || type.isAssignableFrom(String.class));
    }
    
    private static boolean isStrictComplexElement(Class<?> type)
    {
        return (!type.isPrimitive() && !type.isSynthetic() && !type.isAssignableFrom(String.class));
    }
    

    private StringBuilder appendElement(boolean innerObject, StringBuilder sb, Object[] keys, Object[] element, boolean first, boolean complex, boolean isCollectionOrArray) throws EasyJSParsingFieldsException {
        if (!first) {
            sb.append(",");
        }
        sb.append("{");
        if (element != null) {
            if (element.length > 0) {
                for (int x = 0; x < keys.length; x++) {
                    sb.append("\"").append(keys[x].toString()).append("\":");
                    if (complex && x == 1) {
                        sb.append((isCollectionOrArray ? appendChildListOrArray(innerObject, new StringBuilder(), element[x]) : appendChildMap(innerObject, new StringBuilder(), element[x])));
                    } else {
                        sb.append("\"").append(element[x].toString()).append("\"");
                    }
                    if (x != (keys.length - 1)) {
                        sb.append(",");
                    }
                }
            }
        }
        sb.append("}");
        return sb;
    }

    private StringBuilder appendChildListOrArray(boolean innerObject, StringBuilder sb, Object element) throws EasyJSParsingFieldsException {
        sb.append("[");
        if (element != null) {
            Object[] co = new Object[0];
            if (isCollectionOrArray(element.getClass())) {
                co = ((Collection) element).toArray();
            } else if (element.getClass().isArray()) {
                co = (Object[]) element;
            }
            if (co.length > 0) {
                for (int x = 0; x < co.length; x++) {
                    sb.append(append(new StringBuilder(), co, x));
                }
            }
        }
        return sb.append("]");
    }

    private StringBuilder append(StringBuilder sb, Object[] o, int index) throws EasyJSParsingFieldsException {
        Object elem = o[index];
        sb.append("{\"").append(appendChildObject(new StringBuilder(), elem)).append("\"}");
        if (index != (o.length - 1)) {
            sb.append(",");
        }
        return sb;
    }

    private StringBuilder appendChildMap(boolean innerObject, StringBuilder sb, Object element) throws EasyJSParsingFieldsException {
        if (element != null) {
            sb.append("[");
            if (isMap(element.getClass())) {
                Map<?, ?> c = (Map<?, ?>) element;
                if (!c.isEmpty()) {
                    Object[] ks = c.keySet().toArray();
                    for (int x = 0; x < ks.length; x++) {
                        sb.append("{\"").append(appendChildObject(new StringBuilder(), ks[x])).append("\":");
                        if (isStrictComplexElement(c.get(ks[x]).getClass())) {
                            sb.append("[").append(appendChildObject(new StringBuilder(), c.get(ks[x]))).append("]}");
                        } else {
                            sb.append("\"").append(appendChildObject(new StringBuilder(), c.get(ks[x]))).append("\"}");
                        }
                        if (x != (ks.length - 1)) {
                            sb.append(",");
                        }
                    }
                }
            }
            sb.append("]");
        }
        return sb;
    }

    private StringBuilder appendChildObject(StringBuilder sb, Object obj) throws EasyJSParsingFieldsException {
        if (isSomeSimpleElement(obj.getClass())) {
            sb.append(obj.toString());
        } else {
            sb.append(serializeObject(obj, true));
        }
        if (sb.charAt(sb.length() - 1) == ']') {
            sb.setCharAt(sb.length() - 1, '}');
        }
        return sb;
    }
}
