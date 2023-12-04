/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.MG.testVaadin;

import java.sql.Connection;
import java.util.Optional;


public class SessionInfo {

    private Connection conBdD;


    /**
     * @return the conBdD
     */
    public Connection getConBdD() {
        return conBdD;
    }

    /**
     * @param conBdD the conBdD to set
     */
    public void setConBdD(Connection conBdD) {
        this.conBdD = conBdD;
    }


}
