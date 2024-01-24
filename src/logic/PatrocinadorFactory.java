/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 *
 * @author Egoitz
 */
public class PatrocinadorFactory {
    
    public PatrocinadorInterface getFactory() {
        PatrocinadorInterface pInt;
        
        pInt = new PatrocinadorRESTfulClient();
        
        return pInt;
    }
}
