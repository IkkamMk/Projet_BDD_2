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
public class operation {

    private int id;
    private int id_type;
    private int id_produit;
    private String TypeOperation;

    public operation(int id_type, int id_produti) {
        this.id=0;
        this.id_type = id_type;
        this.id_produit = id_produti;
    }

    public String getTypeOperation() {
        return TypeOperation;
    }

    public void setTypeOperation(String IdEtTypeOperation) {
        this.TypeOperation = IdEtTypeOperation;
    }

    public operation(int id, int id_type, int id_produti) {
        this.id = id;
        this.id_type = id_type;
        this.id_produit = id_produti;
    }
public operation(int id, int id_type, int id_produti,String TypeOperation) {
        this.id = id;
        this.id_type = id_type;
        this.id_produit = id_produti;
        this.TypeOperation = TypeOperation;
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
        return "Operation{ " +getTypeOperation()+ " : id= " + getId() + ", Id_type= " + getId_type() + ", Id_produit= " + getId_produit() + '}';
    }

    public void saveInBDD1(Connection con) throws SQLException {

        try (PreparedStatement pst = con.prepareStatement(
                "insert into operations (id_type,id_produit) values (?,?)")) {
            pst.setInt(1, this.id_type);
            pst.setInt(2, this.id_produit);
            pst.executeUpdate();
        }catch (SQLException ex) {
            Notification.show("Erreur saveInBDD1 operation");

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
                    int id_produti = rs.getInt("id_produit");

                    res.add(new operation(id, id_type, id_produti));
                }
            }
        }

        return res;
    }
    public static List<operation> listeOpProd(Connection conn,int id_produit) throws SQLException {
        List<operation> res = new ArrayList<>();
        try (PreparedStatement pst = conn.prepareStatement(
                "select * from operations join type_operation on id_type = type_operation.id WHERE id_produit like ?")) {
            pst.setInt(1, id_produit);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int id_type = rs.getInt("id_type");
                    int id_prod = rs.getInt("id_produit");
                    String type_operation = rs.getString("des");
                    res.add(new operation(id, id_type, id_prod, type_operation ));
                }
            }catch (SQLException ex) {
            Notification.show("Erreur listeOpProd");

        }
        }

        return res;
    }
    
    //Utiliser dans le menu produit pour ajouter des operations avec des precedence
    public static int getOperation (Connection conn,int id_prod, int id_ty) throws SQLException {
        int resultat = 0;
        try (PreparedStatement pst = conn.prepareStatement(
                "select * from operations where (id_produit like ? AND id_type like ?);")) {
            
            pst.setInt(1, id_prod);
            pst.setInt(2, id_ty);
            try (ResultSet rs = pst.executeQuery()) {
               while (rs.next()) {
                    resultat = rs.getInt("id");
                    }
                
            }catch(SQLException ex){
                Notification.show("erreur getIdoperation");
            }
        }catch(SQLException ex){
                Notification.show("erreur getIdoperation2");
            }
        
        return resultat;
    }
    
    public static void deleteOperationIdGraph(Connection conn, int id) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement(
                "DELETE FROM `operations` WHERE id like ?")) {
            st.setInt(1, id);
            st.executeUpdate();
            System.err.println("L'operation " + id + " a ete supprimer");
        }
    }
}
