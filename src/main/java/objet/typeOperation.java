/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objet;

import Utils.console;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.print.DocFlavor;

/**
 *
 * @author gille
 */
public class typeOperation {
    private int id;
    private String des;

    public typeOperation(String des) {
        this.id = 0;
        this.des = des;
    }

    public typeOperation(int id, String des) {
        this.id = id;
        this.des = des;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
    
    @Override
    public String toString() {
        return "Type d'operation{" + "id= " + getId() + ", des= " + getDes() +'}';
    }
    public void saveInBDD1(Connection con) throws SQLException {
        
        try (PreparedStatement pst = con.prepareStatement(
                "insert into type_operation (des) values (?)")) {
            pst.setString(1, this.des);
            pst.executeUpdate();
        }
    }
    public static typeOperation demande() {
        String des = console.entreeString("des : ");
        return new typeOperation(des);
    }
    public static List<typeOperation> toutLesTypeOperation(Connection conn) throws SQLException {
        List<typeOperation> res = new ArrayList<>();
        try (PreparedStatement pst = conn.prepareStatement(
                "select * from type_operation")) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String des = rs.getString("des");
                    res.add(new typeOperation(id,des));
                }
            }
        }
        return res;
    }
   
    public static void deleteTypeOperationId(Connection conn)throws SQLException{
        int patId = console.entreeEntier("entrez l'id du type d'operation a SUPPRIMER ");
        try (PreparedStatement st = conn.prepareStatement(
                "DELETE FROM `type_operation` WHERE id like ?")){
            st.setInt(1, patId);
            st.executeUpdate();
            System.out.println("Le type d'operation "+patId+" a ete supprimer");
        }catch(SQLException ex){
            System.out.println("ce numero n'existe probablement pas ou il y a une dependance");
        }
    }
    public static void deleteTypeOperationAll(Connection conn)throws SQLException{
        if(console.entreeString("Taper VALIDER pour supprimer tout les types d'operations").equals("VALIDER")){
        try (PreparedStatement st = conn.prepareStatement(
                "DELETE FROM `type_operation` WHERE 1")){
            st.executeUpdate();
            System.err.println("Toutes les types d'operations on ete supprimer");
        }catch(SQLException ex){
            System.err.println("Erreur supprimer type operation");
        }
        }else{
            System.err.println("Vous n'avez pas tape VALIDER, aucuns types d'operations n'est supprime");    
                
        }
        
        
       
    }
    public static void deleteTypeOperationIdGraph(Connection conn, int id) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement(
                "DELETE FROM `type_operation` WHERE id like ?")) {
            st.setInt(1, id);
            st.executeUpdate();
            System.err.println("Le type d'operation " + id + " a ete supprimer");
        }
    }
}
