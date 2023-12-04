/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gille
 */
public class PrecedenceOp {
    private int opavant;
    private int opapres;
    
    public PrecedenceOp ( int opavant, int opapres){
        this.opapres = opapres;
        this.opavant = opavant;
    }

    public int getOpavant() {
        return opavant;
    }

    public void setOpavant(int opavant) {
        this.opavant = opavant;
    }

    public int getOpapres() {
        return opapres;
    }

    public void setOpapres(int opapres) {
        this.opapres = opapres;
    }
    
    
    public void saveInBDD1(Connection con) throws SQLException {
        try (PreparedStatement pst = con.prepareStatement(
                "insert into precedenceop (opavant,opapres) values (?,?)")) {
            pst.setInt(1, this.opavant);
            pst.setInt(2, this.opapres);
            pst.executeUpdate();
        }
}
  @Override
    public String toString() {
        return "Precedence operation{" + "opavant= " + getOpavant()+ ", opapres= " + getOpapres()+'}';
    }
  public static List<PrecedenceOp> toutesLesPrecedenceOperations(Connection conn) throws SQLException {
        List<PrecedenceOp> res = new ArrayList<>();
        try (PreparedStatement pst = conn.prepareStatement(
                "select * from precedenceop")) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int opavant = rs.getInt("opavant");
                    int opapres = rs.getInt("opapres");
                    res.add(new PrecedenceOp(opavant,opapres));
                }
            }
        }
        return res;
    }
  
  public static List<PrecedenceOp> RechercheLesPrecedenceOperationsAvant(Connection conn, int operationavant) throws SQLException {
        List<PrecedenceOp> res = new ArrayList<>();
        try (PreparedStatement pst = conn.prepareStatement(
                "select * from precedenceop where opavant like ?")) {
            pst.setInt(1, operationavant);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int opavant = rs.getInt("opavant");
                    int opapres = rs.getInt("opapres");
                    res.add(new PrecedenceOp(opavant,opapres));
                }
            }
        }
        return res;
    }
  public static List<PrecedenceOp> RechercheLesPrecedenceOperationsApres(Connection conn, int operationapres) throws SQLException {
        List<PrecedenceOp> res = new ArrayList<>();
        try (PreparedStatement pst = conn.prepareStatement(
                "select * from precedenceop where opapres like ?")) {
            pst.setInt(1, operationapres);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int opavant = rs.getInt("opavant");
                    int opapres = rs.getInt("opapres");
                    res.add(new PrecedenceOp(opavant,opapres));
                }
            }
        }
        return res;
    }
public static void deletePrecedenceOpAvantGraph(Connection conn,int opavant)throws SQLException{
        try (PreparedStatement st = conn.prepareStatement(
                "DELETE FROM `precedenceop` WHERE opavant like ?")){
            st.setInt(1, opavant);
            st.executeUpdate();
            System.out.println("Les liens d'operations avec qui succede a l'operation"+opavant+" ont été supprimé");
        }
    }  
public static void deletePrecedenceOpApresGraph(Connection conn,int opapres)throws SQLException{
        try (PreparedStatement st = conn.prepareStatement(
                "DELETE FROM `precedenceop` WHERE opapres like ?")){
            st.setInt(1, opapres);
            st.executeUpdate();
            System.out.println("Les liens d'operations avec qui precede a l'operation"+opapres+" ont été supprimé");
        }
    } 
}
    
