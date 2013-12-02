/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easy.easyjs;

import java.util.Stack;
import org.easy.easyjs.util.Utilities;

/**
 *
 * @author mm29950
 */
public class EasyMemoryStack<T> extends Stack<T>{    

  /**
   * Adds a new element to the top of the stack.
   *
   * @param obj the object to add to the stack
   * @return the object that was added
   */
  @Override
  public T push(T obj) {
    Utilities.isNull(obj);
    return super.push(obj);
  }

  /**
   * Performs a memory reference check to see it the {@code obj} exists in
   * the stack.
   *
   * @param obj the object to search for in the stack
   * @return true if this object is already in the stack otherwise false
   */
  @Override
  public boolean contains(Object obj) {
    if (obj == null) {
      return false;
    }

    for (T stackObject : this ){
      if (obj == stackObject) {
        return true;
      }
    }
    return false;
  }
}
