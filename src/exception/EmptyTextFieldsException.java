/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author Egoitz. .
 */
public class EmptyTextFieldsException extends Exception{
    
    public EmptyTextFieldsException(String message) {
        super(message);
    }
}
