/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.projet_m3;

import Utils.console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import objet.Realise;
import objet.machine;
import objet.typeOperation;

/**
 *
 * @author gille
 */
public class Gestion {


    public Connection conn;

    public Gestion(Connection conn) {
        this.conn = conn;
    }
    
    public static Connection connectGeneralMySQL(String host,int port, String database,String user, String pass)throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://" + host + ":" + port
                + "/" + database,
                user, pass);
        con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        return con;
    }
    
    public static Connection connectSurServeurM3() throws SQLException {
        return connectGeneralMySQL("92.222.25.165", 3306,
                "m3_dgilles01", "m3_dgilles01",
                "9d614641");
    }
    
    public static void debut() {
        try (Connection con = connectSurServeurM3()) {
            System.out.println("connecté");
            Gestion gestionnaire = new Gestion(con);
            gestionnaire.menuPrincipal();
        } catch (SQLException ex) {
            throw new Error("Connection impossible", ex);
        }
    }
    
    public void deleteSchema() throws SQLException {
        try (Statement st = this.conn.createStatement()) {
           try {
                st.executeUpdate("alter table realise drop constraint fk_realise_id_machines");
                st.executeUpdate("alter table realise drop constraint fk_realise_id_type");
                st.executeUpdate("alter table operations drop constraint fk_operations_id_type");
                st.executeUpdate("alter table operations drop constraint fk_operations_id_produit");
                st.executeUpdate("alter table precedenceop drop constraint fk_precedenceop_opavant");
                st.executeUpdate("alter table precedenceop drop constraint fk_precedenceop_opapres");
            } catch (SQLException ex) {
                System.out.println("Probleme alter talber drop constraint");
            }
            try {
                st.executeUpdate("drop table machine");
                st.executeUpdate("drop table realise");
                st.executeUpdate("drop table type_operation");
                st.executeUpdate("drop table operations");
                st.executeUpdate("drop table precedenceop");
                st.executeUpdate("drop table produit");
            } catch (SQLException ex) {
                System.out.println("Probleme drop table");
            }
        }
    }
    
    public void creeSchema() throws SQLException {
        this.conn.setAutoCommit(false);
        try (Statement st = this.conn.createStatement()) {  
            st.executeUpdate(
                    "create table machine(\n"
                    + "    id int not null primary key AUTO_INCREMENT,\n"
                    + "    ref varchar(30) unique not null,\n"
                    + "    des varchar(30) not null,\n"
                    + "    puissance float"
                    + ")\n");
            st.executeUpdate(
                    "create table realise(\n"
                    + "    id_machine int not null,\n"
                    + "    id_type int not null,\n"
                    + "    duree int\n"
                    + ")\n");
            st.executeUpdate(
                    "create table type_operation(\n"
                    + "    id int not null primary key AUTO_INCREMENT,\n"
                    + "    des varchar(30) not null\n"
                    + ")\n");
            st.executeUpdate(
                    "create table operations(\n"
                    + "    id int not null primary key AUTO_INCREMENT,\n"
                    + "    id_type int not null,\n"        
                    + "    id_produit int not null\n"
                    + ")\n");
            st.executeUpdate(
                    "create table precedenceop(\n"
                    + "    opavant int not null,\n"
                    + "    opapres int not null\n"
                    + ")\n");
            st.executeUpdate(
                    "create table produit(\n"
                    + "    id int not null primary key AUTO_INCREMENT,\n"
                    + "    ref varchar(30) unique,\n"        
                    + "    des varchar(300)\n"
                    + ")\n");
            //Maintenant les liens sont ajouter
            
            st.executeUpdate(
                    "alter table realise \n"
                    + "    add constraint fk_realise_id_machines \n"
                    + "    foreign key (id_machine) references machine(id) \n"
            );
            st.executeUpdate(
                    "alter table realise \n"
                    + "    add constraint fk_realise_id_type \n"
                    + "    foreign key (id_type) references type_operation(id) \n"
            );
            st.executeUpdate(
                    "alter table operations \n"
                    + "    add constraint fk_operations_id_type \n"
                    + "    foreign key (id_type) references type_operation(id) \n"
            );
            st.executeUpdate(
                    "alter table operations \n"
                    + "    add constraint fk_operations_id_produit \n"
                    + "    foreign key (id_produit) references produit(id) \n"
            );
            st.executeUpdate(
                    "alter table precedenceop \n"
                    + "    add constraint fk_precedenceop_opavant \n"
                    + "    foreign key (opavant) references operations(id) \n"
            );
            st.executeUpdate(
                    "alter table precedenceop \n"
                    + "    add constraint fk_precedenceop_opapres \n"
                    + "    foreign key (opapres) references operations(id) \n"
            );
            
            
            
            this.conn.commit();
        } catch (SQLException ex) {
            this.conn.rollback();
            System.out.println("probleme creation schema");
            throw ex;
        } finally {
            this.conn.setAutoCommit(true);
        }
    }
    
    public void menuPrincipal() {
        int rep = -1;
        while (rep != 0) {
            int i = 1;
            System.out.println("Menu principal");
            System.out.println("==============");
            System.out.println((i++) + ") supprimer schéma");
            System.out.println((i++) + ") créer schéma");
            System.out.println((i++) + ") Menu machine");
            System.out.println((i++) + ") Menu type d'operation");
            System.out.println((i++) + ") Menu realise");
            System.out.println("0) Fin");
            rep = console.entreeEntier("Votre choix : ");
            try {
                int j = 1;
                if (rep == j++) {
                    this.deleteSchema();
                } else if (rep == j++) {
                    this.creeSchema();
                } else if (rep == j++) {
                    this.menuMachine();
                } else if (rep == j++) {
                    this.menuTypeOperation();
                } else if (rep == j++) {
                    this.menuRealise();
                }
            } catch (SQLException ex) {
                System.out.println("erreur menu principal");
            }
        }
    }
    
    public void menuMachine(){
        int rep = -1;
        while (rep != 0) {
            int i = 1;
            System.out.println("Menu machine");
            System.out.println("==============");
            System.out.println((i++) + ") Créer machine");
            System.out.println((i++) + ") Lister machine");
            System.out.println((i++) + ") Chercher une machine avec la ref");
            System.out.println((i++) + ") Supprimer machine avec la ref");
            System.out.println((i++) + ") Supprimer toutes les machines");
            System.out.println("0) retour menu principale");
            rep = console.entreeEntier("Votre choix : ");
            try {
                int j = 1;
                if (rep == j++) {
                    machine nouveau = machine.demande();
                    nouveau.saveInBDD1(this.conn);
                }else if (rep == j++) {
                    List<machine> machines = machine.toutesLesMachines(this.conn);
                    System.out.println(machines.size() + " machines : ");
                    System.out.println(Utils.ListUtils.enumerateList(machines));
                }else if (rep == j++) {
                    List<machine> recherche = machine.afficheMachineAvecPatternRef(this.conn);
                    System.out.println(recherche.size() + " machine : ");
                    System.out.println(Utils.ListUtils.enumerateList(recherche));
                }else if (rep == j++) {
                    machine.deleteMachineRef(this.conn);
                
                } else if (rep == j++) {
                    machine.deleteMachineAll(this.conn);
                } 
            } catch (SQLException ex) {
                System.out.println("erreur menu machine");
            }
        }
    }
    
    public void menuTypeOperation(){
        int rep = -1;
        while (rep != 0) {
            int i = 1;
            System.out.println("Menu type d'operation");
            System.out.println("==============");
            System.out.println((i++) + ") Créer un type d'operation");
            System.out.println((i++) + ") Lister les type d'operation");
            System.out.println((i++) + ") Supprimer un type d'operation");
            System.out.println((i++) + ") Supprimer toutes les types d'operations");
            System.out.println("0) retour menu principale");
            rep = console.entreeEntier("Votre choix : ");
            try {
                int j = 1;
                if (rep == j++) {
                    typeOperation nouveau = typeOperation.demande();
                    nouveau.saveInBDD1(this.conn);
                }else if (rep == j++) {
                    List<typeOperation> typeOperations = typeOperation.toutLesTypeOperation(this.conn);
                    System.out.println(typeOperations.size() + " types d'operations : ");
                    System.out.println(Utils.ListUtils.enumerateList(typeOperations));
                }else if (rep == j++) {
                    typeOperation.deleteTypeOperationId(this.conn);
                } else if (rep == j++) {
                    typeOperation.deleteTypeOperationAll(this.conn);
                } 
            } catch (SQLException ex) {
                System.out.println("erreur menu type d'operation");
            }
        }
    }
    public void menuRealise(){
        int rep = -1;
        while (rep != 0) {
            int i = 1;
            System.out.println("Menu Realise");
            System.out.println("==============");
            System.out.println((i++) + ") Créer une classe realise");
            System.out.println((i++) + ") Lister les realisation");
            System.out.println((i++) + ") Supprimer une realisation par id machine");
            System.out.println((i++) + ") Supprimer une realisation par id type");
            System.out.println((i++) + ") Supprimer toutes les realisations");
            System.out.println("0) retour menu principale");
            rep = console.entreeEntier("Votre choix : ");
            try {
                int j = 1;
                if (rep == j++) {
                    Realise nouveau = Realise.demande();
                    nouveau.saveInBDD1(this.conn);
                }else if (rep == j++) {
                    List<Realise> Realisations = Realise.toutRealise(this.conn);
                    System.out.println(Realisations.size() + " Realisations : ");
                    System.out.println(Utils.ListUtils.enumerateList(Realisations));
                }else if (rep == j++) {
                    Realise.deleteIdMachine(this.conn);
                } else if (rep == j++) {
                    Realise.deleteIdTypeOperations(this.conn);
                } else if (rep == j++) {
                    Realise.deleteRealiseAll(this.conn);
                } 
            } catch (SQLException ex) {
                System.out.println("erreur menu realise");
            }
        }
    }
   
    public static void main(String[] args) {
        debut();
    }
}
