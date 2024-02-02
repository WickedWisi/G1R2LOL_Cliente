/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author Egitz.
 */
public class FormatErrorException extends Exception{
    
       public FormatErrorException(String message) {
        super(message);
    }
}
