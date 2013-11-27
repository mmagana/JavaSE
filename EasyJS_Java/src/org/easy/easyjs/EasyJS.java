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
            return (new StringBuilder("var data=[")).append(serializeObject(o)).append(";").toString();
        } catch (EasyJSParsingFieldsException ex) {
            Logger.getLogger(EasyJS.class.getName()).log(Level.SEVERE, null, ex);
            return "var data = [];";
        }
    }

    private StringBuilder serializeObject(Object obj) throws EasyJSParsingFieldsException {
        StringBuilder sb = new StringBuilder();
        if (obj == null) {
            throw new EasyJSParsingFieldsException("The object nust not be null.");
        }
        if (ancestor.contains(obj)) {
            throw new IllegalStateException("Circular reference found: " + obj);
        }
        ancestor.push(obj);
        try {
            Class cls = Class.forName(obj.getClass().getName());
            Field[] fields = cls.getDeclaredFields();
            boolean first = true;

            for (Field f : fields) {
                if (!modifiers.contains(f.getModifiers())) {
                    Annotation[] annotations = f.getDeclaredAnnotations();
                    if (annotations.length > 0) {
                        for (Annotation a : annotations) {
                            if (a instanceof JsonElement) {
                                JsonElement element = (JsonElement) a;
                                String annName = element.name();
                                String type = element.type();
                                String selected = "";
                                int cap = 3;
                                if (type.equals(JsonElement.SELECT)) {
                                    selected = String.valueOf(element.selected());
                                    cap = 4;
                                }
                                if (annName.equals("##default")) {
                                    annName = f.getName();
                                }
                                f.setAccessible(true);
                                Object o = f.get(obj);
                                if (o == null) {
                                    sb = appendElement(sb, loadObjectArray(cap, type, "", annName), first, false, false);
                                    first = false;
                                } else {
                                    if (isCollectionOrArray(o.getClass()) || isMap(o.getClass())) {
                                        if (isCollectionOrArray(o.getClass())) {
                                            if (type.equals(JsonElement.SELECT)) {
                                                sb = appendElement(sb, loadObjectArray(cap, type, o, annName, selected), first, true, true);
                                            } else {
                                                sb = appendElement(sb, loadObjectArray(cap, type, o, annName), first, true, true);
                                            }
                                            first = false;
                                        } else if (isMap(o.getClass())) {
                                            if (type.equals(JsonElement.SELECT)) {
                                                sb = appendElement(sb, loadObjectArray(cap, type, o, annName, selected), first, true, false);
                                            } else {
                                                sb = appendElement(sb, loadObjectArray(cap, type, o, annName), first, true, false);
                                            }
                                            first = false;
                                        }
                                    } else {
                                        sb = appendElement(sb, loadObjectArray(cap, type, o, annName), first, false, false);
                                        first = false;
                                    }
                                }

                            }
                        }
                    } else {
                        throw new EasyJSParsingFieldsException("The values in the object must be annotated in order to Serialize them.");
                    }
                }

            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EasyJS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(EasyJS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(EasyJS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sb.append("]");
    }

    private Object[] loadObjectArray(int cap, Object... arguments) {
        Object[] $o = new Object[cap];
        int $x = 0;
        for (Object o : arguments) {
            $o[$x] = o;
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

    private StringBuilder appendElement(StringBuilder sb, Object[] element, boolean first, boolean complex, boolean isCollectionOrArray) throws EasyJSParsingFieldsException {
        if (!first) {
            sb.append(",");
        }
        if (element != null) {
            if (element.length > 0) {
                sb.append("{\"type\":");
                sb.append("\"").append(element[0].toString()).append("\",");
                if (complex) {
                    sb.append("\"values\":");
                    sb.append((isCollectionOrArray ? appendChildListOrArray(new StringBuilder(), element[1]) : appendChildMap(new StringBuilder(), element[1])));
                } else {
                    sb.append("\"value\":");
                    sb.append("\"").append(element[1]).append("\",");
                }
                sb.append("\"id\":");
                sb.append("\"").append(element[2].toString());
                if (element.length == 4) {
                    sb.append(", \"selected\":");
                    sb.append("\"").append(element[3].toString()).append("\"}");
                } else {
                    sb.append("\"}");
                }
            }
        }
        return sb;
    }

    private StringBuilder appendChildListOrArray(StringBuilder sb, Object element) throws EasyJSParsingFieldsException {
        sb.append("[");
        if (element != null) {
            if (Collection.class.isAssignableFrom(element.getClass())) {
                Collection c = (Collection) element;
                if (!c.isEmpty()) {
                    Object[] co = c.toArray();
                    for (int x = 0; x < co.length; x++) {
                        Object elem = co[x];
                        sb.append("{\"").append(appendChildObject(new StringBuilder(), elem)).append("\"}");
                        if (x != (c.size() - 1)) {
                            sb.append(",");
                        }
                    }
                }
            } else if (element.getClass().isArray()) {
                Object[] c = (Object[]) element;
                if (c.length > 0) {
                    for (int x = 0; x < c.length; x++) {
                        Object elem = c[x];
                        sb.append("{\"").append(appendChildObject(new StringBuilder(), elem)).append("\"}");
                        if (x != (c.length - 1)) {
                            sb.append(",");
                        }
                    }
                }
            }

        }

        return sb.append("],");
    }

    private StringBuilder appendChildMap(StringBuilder sb, Object element) throws EasyJSParsingFieldsException {
        sb.append("[");
        if (element != null) {
            if (Map.class.isAssignableFrom(element.getClass())) {
                Map c = (Map) element;
                if (!c.isEmpty()) {
                    Object[] ks = c.keySet().toArray();
                    for (int x = 0; x < ks.length; x++) {
                        Object key = ks[x];
                        sb.append("{\"").append(appendChildObject(new StringBuilder(), key)).append("\":");
                        Object elem = c.get(ks[x]);
                        sb.append("\"").append(appendChildObject(new StringBuilder(), elem)).append("\"}");
                        if (x != (ks.length - 1)) {
                            sb.append(",");
                        }
                    }
                }
            }
        }

        return sb.append("],");
    }

    private StringBuilder appendChildObject(StringBuilder sb, Object obj) throws EasyJSParsingFieldsException {
        Class<?> clazz = obj.getClass();
        if (clazz.isPrimitive() || clazz.isSynthetic() || clazz.isAssignableFrom(String.class)) {
            sb.append(obj.toString());
        } else {
            sb.append(serializeObject(obj));
        }
        return sb;
    }
}
