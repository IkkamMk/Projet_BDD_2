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
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author gille
 */
public class machine {
    private int id;
    private String ref;
    private String des;
    private int puissance;
    private int X;
    private int Y;

    public machine(String ref, String des, int puissance, int X, int Y) {
        this.ref = ref;
        this.des = des;
        this.puissance = puissance;
        this.X = X;
        this.Y = Y;
    }

    public machine( String ref, String des, int puissance) {
        this.id = 0;
        this.ref = ref;
        this.des = des;
        this.puissance = puissance;
        this.X = 100;
        this.Y = 500;
    }

    public machine(int id, String ref, String des, int puissance) {
        this.id = id;
        this.ref = ref;
        this.des = des;
        this.puissance = puissance;
        this.X = 100;
        this.Y = 800;
    }
   
    public int getId() {
        return id;
    }
    public String getRef() {
        return ref;
    }
    public String getDes() {
        return des;
    }
    public int getPuissance() {
        return puissance;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setRef(String ref) {
        this.ref = ref;
    }
    public void setDes(String des) {
        this.des = des;
    }
    public void setPuissance(int puissance) {
        this.puissance = puissance;
    }

    public int getX() {
        return X;
    }

    public void setX(int X) {
        this.X = X;
    }

    public int getY() {
        return Y;
    }

    public void setY(int Y) {
        this.Y = Y;
    }
    
    @Override
    public String toString() {
        return "Machine{" + "id= " + getId() + ", ref= " + getRef() + ", des= " + getDes() +", puissance= " + getPuissance()+ '}';
    }
    
    public void saveInBDD1(Connection con) throws SQLException {
        try (PreparedStatement pst = con.prepareStatement(
                "insert into machine (ref,des,puissance, X, Y) values (?,?,?,?,?)")) {
            pst.setString(1, this.ref);
            pst.setString(2, this.des);
            pst.setInt(3, this.puissance);
            pst.setInt(4, this.X); // J'ai ajouté les coordonnées X
            pst.setInt(5, this.Y); // J'ai ajouté les coordonnées Y
            pst.executeUpdate();
        }
        catch(SQLException ex){
            System.err.println("Cette machine n'as pas été cree, verifier les donnees et reessayez");
            
        } //a rajouter si on veut pas avoir de probleme dans le menu textuel
    }
    
    public static machine demande() {
        String ref = console.entreeString("ref : ");
        String des = console.entreeString("des : ");
        int puissance = console.entreeEntier("puissance");
        return new machine(ref,des,puissance);
    }
    
    public static List<machine> toutesLesMachines(Connection conn) throws SQLException {
        List<machine> res = new ArrayList<>();
        try (PreparedStatement pst = conn.prepareStatement(
                "select * from machine")) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String ref = rs.getString("ref");
                    String des = rs.getString("des");
                    int puissance = rs.getInt("puissance");
                    res.add(new machine(id,ref, des,puissance));
                }
            }
        }
        return res;
    }
    public static List<machine> afficheMachineAvecPatternRef(Connection conn) throws SQLException {
        List<machine> res = new ArrayList<>();
        String patRef = console.entreeString("entrez la ref ");
        try (PreparedStatement st = conn.prepareStatement(
                "SELECT * FROM `machine` WHERE ref like ?")){
            st.setString(1, patRef);
            try(ResultSet r = st.executeQuery()){
               while (r.next()) {
                    int id = r.getInt("id");
                    String ref = r.getString("ref");
                    String des = r.getString("des");
                    int puissance = r.getInt("puissance");
                    res.add(new machine(id, ref, des,puissance));
                }  
            }
        }catch(SQLException ex){
            System.out.println("erreur recherche");
        }
        return res; //renvoie une liste meme si la methode est pour une ref qui est unique donc le resultat est forcement unique
    }
    public static void deleteMachineRef(Connection conn)throws SQLException{
        String patRef = console.entreeString("entrez la ref de la machine a SUPPRIMER ");
        try (PreparedStatement st = conn.prepareStatement(
                "DELETE FROM `machine` WHERE ref like ?")){
            st.setString(1, patRef);
            st.executeUpdate();
            System.err.println("La machine "+patRef+" a ete supprimer");
        }catch(SQLException ex){
            System.out.println("ce numero n'existe probablement pas ou il y a une dependance");
        }
    }
    
    public static void deleteMachineRefGraph(Connection conn,String ref)throws SQLException{
        try (PreparedStatement st = conn.prepareStatement(
                "DELETE FROM `machine` WHERE ref like ?")){
            st.setString(1, ref);
            st.executeUpdate();
            System.out.println("La machine "+ref+" a ete supprimer");
        }
    }
    
    public static void deleteMachineAll(Connection conn)throws SQLException{
        if(console.entreeString("Taper VALIDER pour supprimer toutes les machines").equals("VALIDER")){
        try (PreparedStatement st = conn.prepareStatement(
                "DELETE FROM `machine` WHERE 1")){
            st.executeUpdate();
            System.err.println("Toutes les machines on ete supprimer");
        }catch(SQLException ex){
            System.err.println("Erreur supprimer");
        }
        }else{
            System.err.println("Vous n'avez pas tape VALIDER, aucune machine n'est supprime");    
                
        }
        
        
       
    }
}
