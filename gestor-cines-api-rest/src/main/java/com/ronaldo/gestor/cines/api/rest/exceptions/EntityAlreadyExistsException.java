/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ronaldo.gestor.cines.api.rest.exceptions;

/**
 *
 * @author ronaldo
 */
public class EntityAlreadyExistsException extends Exception {

       public EntityAlreadyExistsException() {
       }

       public EntityAlreadyExistsException(String message) {
              super(message);
       }

}
