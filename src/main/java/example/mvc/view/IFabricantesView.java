/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package example.mvc.view;

import example.mvc.controller.IFabricantesController;
import example.mvc.model.Fabricante;
import java.util.List;

/**
 * @author Delcio Amarillo
 */
public interface IFabricantesView {
    
    public void doAgregarFabricante();
    
    public Fabricante getNuevoFabricante();
    
    public void notificarFabricanteAgregado(Boolean resultado);
    
    public void doModificarFabricante();
    
    public Fabricante getFabricanteAModificar();
    
    public void notificarFabricanteModificado(Boolean resultado);
    
    public void doEliminarFabricantes();
    
    public List<Fabricante> getFabricantesAEliminar();
    
    public void notificarFabricantesEliminados(Boolean resultado);
    
    public void doConsultarFabricantes();
    
    public void setListaFabricantes(List<Fabricante> fabricantes);
    
    public void setFabricantesController(IFabricantesController controller);
}
