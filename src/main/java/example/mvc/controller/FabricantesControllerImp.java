/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package example.mvc.controller;

import example.mvc.model.Fabricante;
import example.mvc.model.IFabricantesModel;
import example.mvc.view.IFabricantesView;
import java.util.List;

/**
 * @author Delcio Amarillo
 */
public class FabricantesControllerImp implements IFabricantesController {
    
    private IFabricantesModel model;
    
    public FabricantesControllerImp(IFabricantesModel model) {
        this.model = model;
    }

    @Override
    public void agregarFabricante(IFabricantesView view) {
        Fabricante fabricante = view.getNuevoFabricante();
        int codigoOperacion = model.insertar(fabricante);
        Boolean resultado = (codigoOperacion == IFabricantesModel.EXITO);
        view.notificarFabricanteAgregado(resultado);
    }

    @Override
    public void modificarFabricante(IFabricantesView view) {
        Fabricante fabricante = view.getFabricanteAModificar();
        int codigoOperacion = model.modificar(fabricante);
        Boolean resultado = (codigoOperacion == IFabricantesModel.EXITO);
        view.notificarFabricanteModificado(resultado);
    }

    @Override
    public void eliminarFabricantes(IFabricantesView view) {
        List<Fabricante> fabricantes = view.getFabricantesAEliminar();
        int codigOperacion = model.eliminar(fabricantes);
        Boolean resultado = (codigOperacion == IFabricantesModel.EXITO);
        view.notificarFabricantesEliminados(resultado);
    }
    
    @Override
    public void getListaFabricantes(IFabricantesView view) {
        List<Fabricante> fabricantes = model.getFabricantes();
        view.setListaFabricantes(fabricantes);
    }

    @Override
    public void setFabricantesModel(IFabricantesModel model) {
        this.model = model;
    }
}
