/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objet;

import com.vaadin.flow.component.notification.Notification;
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
public class Produit {
    private int id;
    private String ref;
    private String des;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Produit( String ref, String des) {
        this.id = 0;
        this.ref = ref;
        this.des = des;
    }

    public Produit(int id, String ref, String des) {
        this.id = id;
        this.ref = ref;
        this.des = des;
    }
    
    
    @Override
    public String toString() {
        return "Produit{" + "id= " + getId() + ", ref= " + getRef() + ", des= " + getDes() + '}';
    }
    public static List<Produit> toutLesProduits(Connection conn) throws SQLException {
        List<Produit> res = new ArrayList<>();
        try (PreparedStatement pst = conn.prepareStatement(
                "select * from produit")) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String ref = rs.getString("ref");
                    String des = rs.getString("des");
                    res.add(new Produit(id,ref, des));
                }
            }
        }
        return res;
    }
    public static void deleteProduitRef(Connection conn,String ref)throws SQLException{
        try (PreparedStatement st = conn.prepareStatement(
                "DELETE FROM `produit` WHERE ref like ?")){
            st.setString(1, ref);
            st.executeUpdate();
            System.out.println("Le produit "+ref+" a ete supprimer");
            Notification.show("Le produit "+ref+" a ete supprimer");
        }
    }
    public void saveInBDD1(Connection con) throws SQLException {
        try (PreparedStatement pst = con.prepareStatement(
                "insert into produit (ref,des) values (?,?)")) {
            pst.setString(1, this.ref);
            pst.setString(2, this.des);
            pst.executeUpdate();
        }
        
    }
}
