/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.fsa;

import cl.fsa.modelo.Dominio;
import cl.fsa.service.ServiceBase;
import java.sql.SQLException;

/**
 *
 * @author fseguel
 */
public class Main {

    public static void main(String[] args) {
        ServiceBase sb = new ServiceBase();
        try {
            for (Dominio dm : sb.getListaDominio("DOM_AFP_FONDOS")) {
                System.out.println(dm.toString());
            }
        } catch (SQLException ex) {
            System.out.println("ERROR : " + ex);
        }
    }
}
