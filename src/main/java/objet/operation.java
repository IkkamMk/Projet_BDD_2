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
public class operation {

    private int id;
    private int id_type;
    private int id_produit;

    public operation(int id_type, int id_produti) {
        this.id_type = id_type;
        this.id_produit = id_produti;
    }

    public operation(int id, int id_type, int id_produti) {
        this.id = id;
        this.id_type = id_type;
        this.id_produit = id_produti;
    }

    public int getId() {
        return id;
    }

    public int getId_type() {
        return id_type;
    }

    public int getId_produit() {
        return id_produit;
    }

    @Override
    public String toString() {
        return "Realise{" + "id= " + getId() + ", Id_type= " + getId_type() + ", Id_produit= " + getId_produit() + '}';
    }

    public void saveInBDD1(Connection con) throws SQLException {

        try (PreparedStatement pst = con.prepareStatement(
                "insert into operations (id_type,id_produti) values (?,?)")) {
            pst.setInt(1, this.id_type);
            pst.setDouble(2, this.id_produit);
            pst.executeUpdate();
        }
    }

    public static List<operation> toutesLesOperations(Connection conn) throws SQLException {
        List<operation> res = new ArrayList<>();
        try (PreparedStatement pst = conn.prepareStatement(
                "select * from operations")) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int id_type = rs.getInt("id_type");
                    int id_produti = rs.getInt("id_produti");

                    res.add(new operation(id, id_type, id_produti));
                }
            }
        }

        return res;
    }

    public static void deleteOperationIdGraph(Connection conn, int id) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement(
                "DELETE FROM `operation` WHERE id like ?")) {
            st.setInt(1, id);
            st.executeUpdate();
            System.err.println("L'operation " + id + " a ete supprimer");
        }
    }
}
