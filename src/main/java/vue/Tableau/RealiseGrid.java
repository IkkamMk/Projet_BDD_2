/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vue.Tableau;

import com.vaadin.flow.component.grid.Grid;
import java.util.List;
import objet.Realise;
import objet.typeOperation;

/**
 *
 * @author gille
 */
public class RealiseGrid extends Grid<Realise>{
    private List<Realise> datas;
    public RealiseGrid(List<Realise> datas){
        this.datas = datas;
        
        Column<Realise> cIdMachine = this.addColumn(Realise::getId_machine)
                .setHeader("Id Machine");
        cIdMachine.setWidth("3em");
        cIdMachine.setSortable(true);
        
        Column<Realise> cIdtype = this.addColumn(Realise::getId_type)
                .setHeader("Id Type operation");
        cIdtype.setSortable(true);
        Column<Realise> cDuree = this.addColumn(Realise::getDuree)
                .setHeader("Duree en seconde");
        cDuree.setSortable(true);
         this.setItems(datas);
        // pour affichage compact pour transparents
        /// this.setMaxHeight("12em");
    }
}
