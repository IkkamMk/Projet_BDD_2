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

/**
 *
 * @author gille
 */
public class Realise {

    private int id_machine;
    private int id_type;
    private int duree;

    public int getId_machine() {
        return id_machine;
    }

    public void setId_machine(int id_machine) {
        this.id_machine = id_machine;
    }

    public int getId_type() {
        return id_type;
    }

    public void setId_type(int id_type) {
        this.id_type = id_type;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public Realise(int id_machine, int id_type, int duree) {
        this.id_machine = id_machine;
        this.id_type = id_type;
        this.duree = duree;
    }

    public static void deleteRealiseAll(Connection conn) throws SQLException {
        if (console.entreeString("Taper VALIDER pour supprimer tout les realise").equals("VALIDER")) {
            try (PreparedStatement st = conn.prepareStatement(
                    "DELETE FROM `realise` WHERE 1")) {
                st.executeUpdate();
                System.err.println("Toutes les realise on ete supprimer");
            } catch (SQLException ex) {
                System.err.println("Erreur supprimer realise");
            }
        } else {
            System.err.println("Vous n'avez pas tape VALIDER, aucuns realise n'est supprime");

        }
    }   
    
    public static void deleteIdMachine (Connection conn)throws SQLException {
        int patId = console.entreeEntier("entrez l'id machines des realisations a SUPPRIMER ");
        try (PreparedStatement st = conn.prepareStatement(
                "DELETE FROM `realise` WHERE id_machine like ?")) {
            st.setInt(1, patId);
            st.executeUpdate();
            System.out.println("Les realise avec id_machine " + patId + " a ete supprimer");
        } catch (SQLException ex) {
            System.out.println("ce numero n'existe probablement pas ou il y a une dependance");
        }
    }
    public static void deleteIdTypeOperations (Connection conn)throws SQLException {
        int patId = console.entreeEntier("entrez l'id type operation des realisations a SUPPRIMER ");
        try (PreparedStatement st = conn.prepareStatement(
                "DELETE FROM `realise` WHERE id_type like ?")) {
            st.setInt(1, patId);
            st.executeUpdate();
            System.out.println("Les realise avec id type operation " + patId + " a ete supprimer");
        } catch (SQLException ex) {
            System.out.println("ce numero n'existe probablement pas ou il y a une dependance");
        }
    }
    public static void deleteIdMachineGraph (Connection conn,int patId)throws SQLException {
        
        try (PreparedStatement st = conn.prepareStatement(
                "DELETE FROM `realise` WHERE id_machine like ?")) {
            st.setInt(1, patId);
            st.executeUpdate();
            System.out.println("Les realise avec id_machine " + patId + " a ete supprimer");
        }
    }
    public static void deleteIdTypeOperationsGraph (Connection conn,int patId)throws SQLException {
        
        try (PreparedStatement st = conn.prepareStatement(
                "DELETE FROM `realise` WHERE id_type like ?")) {
            st.setInt(1, patId);
            st.executeUpdate();
            System.out.println("Les realise avec id type operation " + patId + " a ete supprimer");
        } 
    }
    public static List<Realise> toutRealise(Connection conn) throws SQLException {
        List<Realise> res = new ArrayList<>();
        try (PreparedStatement pst = conn.prepareStatement(
                "select * from realise")) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id_machine");
                    int des = rs.getInt("id_type");
                    int duree = rs.getInt("duree");
                    res.add(new Realise(id,des,duree));
                }
            }
        }
        return res;
    }
    public static List<Realise> toutRealiseIdMachine(Connection conn,int idMachine) throws SQLException {
        List<Realise> res = new ArrayList<>();
        try (PreparedStatement pst = conn.prepareStatement(
                "select * from realise WHERE id_machine like ?")) {
            pst.setInt(1, idMachine);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id_machine");
                    int des = rs.getInt("id_type");
                    int duree = rs.getInt("duree");
                    res.add(new Realise(id,des,duree));
                }
            }
        }
        return res;
    }
    public static List<Realise> toutRealiseIdType(Connection conn,int idType) throws SQLException {
        List<Realise> res = new ArrayList<>();
        try (PreparedStatement pst = conn.prepareStatement(
                "select * from realise WHERE id_type like ?")) {
            pst.setInt(1, idType);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id_machine");
                    int des = rs.getInt("id_type");
                    int duree = rs.getInt("duree");
                    res.add(new Realise(id,des,duree));
                }
            }
        }
        return res;
    }
    @Override
    public String toString() {
        return "Realise{" + "id_machine= " + getId_machine()+ ", Id_type= " + getId_type()+ ", Duree= " + getDuree()+'}';
    }
    public void saveInBDD1(Connection con) throws SQLException {
        
        try (PreparedStatement pst = con.prepareStatement(
                "insert into realise (id_machine,id_type,duree) values (?,?,?)")) {
            pst.setInt(1, this.id_machine);
            pst.setInt(2, this.id_type);
            pst.setInt(3, this.duree);
            pst.executeUpdate();
        }
    }
    public static Realise demande() {
        int id_machine = console.entreeEntier("id_machine : ");
        int id_type = console.entreeEntier("id_type : ");
        int duree = console.entreeEntier("duree : ");
        return new Realise(id_machine,id_type,duree);
    }
}


