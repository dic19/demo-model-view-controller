/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package example.mvc.view;

import example.mvc.controller.IFabricantesController;
import example.mvc.model.Fabricante;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 * @author Delcio Amarillo
 */
public class FabricantesViewSwing implements IFabricantesView {
    
    private IFabricantesController controller;
    private Fabricante nuevoFabricante, fabricanteAModificar;
    private final List<Fabricante> fabricantesAEliminar;
    
    private JPanel content;
    private JTable table;
    private Action agregarAction, modificarAction, eliminarAction, consultarAction;
    private GenericDomainTableModel<Fabricante> tableModel;
    
    public FabricantesViewSwing(IFabricantesController controller) {
        this.controller = controller;
        this.fabricantesAEliminar = new ArrayList<>();
    }
    
    @Override
    public void setFabricantesController(IFabricantesController controller) {
        this.controller = controller;
    }
    
    private void initActions() {
        agregarAction = new AbstractAction("Agregar nuevo") {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatosFabricante datosFabricante = new DatosFabricante(null, null);
                JComponent component = datosFabricante.getUIComponent();
                
                int opcion = JOptionPane.showConfirmDialog ( 
                                              null
                                            , component
                                            , "Nuevo fabricante"
                                            , JOptionPane.OK_CANCEL_OPTION
                                            , JOptionPane.PLAIN_MESSAGE
                );
                
                if (opcion == JOptionPane.OK_OPTION) {
                    String nombre = datosFabricante.getNombreFabricante();
                    Date fechaFundacion = datosFabricante.getFechaFundacion();
                    nuevoFabricante = new Fabricante(nombre, fechaFundacion);
                    doAgregarFabricante();
                }
            }
        };
        
        modificarAction = new AbstractAction("Modificar") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int viewIndex = table.getSelectedRow();
                if (viewIndex > -1) {
                    int modelIndex = table.convertRowIndexToModel(viewIndex);
                    
                    fabricanteAModificar = tableModel.getDomainObject(modelIndex);
                    String nombre = fabricanteAModificar.getNombre();
                    Date fechaFundacion = fabricanteAModificar.getFechaFundacion();
                    
                    DatosFabricante datosFabricante = new DatosFabricante(nombre, fechaFundacion);
                    JComponent component = datosFabricante.getUIComponent();
                    
                    int opcion = JOptionPane.showConfirmDialog (
                                                null
                                              , component
                                              , "Modificar fabricante"
                                              , JOptionPane.OK_CANCEL_OPTION
                                              , JOptionPane.PLAIN_MESSAGE
                    );

                    if (opcion == JOptionPane.OK_OPTION) {
                        nombre = datosFabricante.getNombreFabricante();
                        fechaFundacion = datosFabricante.getFechaFundacion();
                        fabricanteAModificar.setNombre(nombre);
                        fabricanteAModificar.setFechaFundacion(fechaFundacion);
                        doModificarFabricante();
                    }
                }
            }
        };
        
        eliminarAction = new AbstractAction("Eliminar") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] viewIndexes = table.getSelectedRows();
                if (viewIndexes.length > 0) {
                    fabricantesAEliminar.clear();
                    for (int viewIndex : viewIndexes) {
                        int modelIndex = table.convertRowIndexToModel(viewIndex);
                        Fabricante fabricante = tableModel.getDomainObject(modelIndex);
                        fabricantesAEliminar.add(fabricante);
                    }                    
                    doEliminarFabricantes();
                }
            }
        };
        
        consultarAction = new AbstractAction("Actualizar tabla") {
            @Override
            public void actionPerformed(ActionEvent e) {
                doConsultarFabricantes();
            }
        };
    }
    
    private void initTableAndModel() {
        List header = Arrays.asList(new Object[] {
            "Id", "Nombre", "Fecha de fundación"
        });
        
        tableModel = new GenericDomainTableModel<Fabricante>(header) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0: return BigInteger.class;
                    case 1: return String.class;
                    case 2: return Date.class;
                }
                throw new ArrayIndexOutOfBoundsException(columnIndex);
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                Fabricante fabricante = getDomainObject(rowIndex);
                switch (columnIndex) {
                    case 0: return fabricante.getId();
                    case 1: return fabricante.getNombre();
                    case 2: return fabricante.getFechaFundacion();
                }
                throw new ArrayIndexOutOfBoundsException(columnIndex);
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
            
            @Override
            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {}
        };
        
        table = new JTable(tableModel);
        table.setAutoCreateRowSorter(true);
        doConsultarFabricantes();
    }
    
    private void initContent() {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.add(new JButton(agregarAction));
        buttonsPanel.add(new JButton(modificarAction));
        buttonsPanel.add(new JButton(eliminarAction));
        buttonsPanel.add(new JButton(consultarAction));
        
        content = new JPanel(new BorderLayout(8, 8));
        content.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        content.add(scrollPane, BorderLayout.CENTER);
        content.add(buttonsPanel, BorderLayout.PAGE_END);
    }
    
    private void initComponents() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                initActions();
                initTableAndModel();
                initContent();
            }
        };
        
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(runnable);
        } else {
            runnable.run();
        }
    }
    
    public JComponent getUIComponent() {
        if (content == null) {
            initComponents();
        }
        return content;
    }

    @Override
    public void doAgregarFabricante() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                controller.agregarFabricante(FabricantesViewSwing.this);
                return null;
            }
        };
        worker.execute();
    }
    
    @Override
    public Fabricante getNuevoFabricante() {
        return nuevoFabricante;
    }

    @Override
    public void notificarFabricanteAgregado(final Boolean resultado) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (resultado) {
                    tableModel.addRow(nuevoFabricante);
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Ha ocurrido un error al agregar un nuevo fabricante.")
                          .append("%n")
                          .append("Por favor contacte al administrador");
                    
                    String mensaje = String.format(sb.toString());
                    
                    JOptionPane.showMessageDialog (
                                    null
                                  , mensaje
                                  , "Error"
                                  , JOptionPane.WARNING_MESSAGE
                    );
                }
                nuevoFabricante = null;
            }
        };

        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(runnable);
        } else {
            runnable.run();
        }
    }

    @Override
    public void doModificarFabricante() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                controller.modificarFabricante(FabricantesViewSwing.this);
                return null;
            }
        };
        worker.execute();
    }
    
    @Override
    public Fabricante getFabricanteAModificar() {
        return fabricanteAModificar;
    }

    @Override
    public void notificarFabricanteModificado(final Boolean resultado) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (resultado) {
                    tableModel.notifyDomainObjectUpdated(fabricanteAModificar);
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Ha ocurrido un error al modificar el fabricante seleccionado.")
                          .append("%n")
                          .append("Por favor contacte al administrador");
                    
                    String mensaje = String.format(sb.toString());
                    
                    JOptionPane.showMessageDialog (
                                    null
                                  , mensaje
                                  , "Error"
                                  , JOptionPane.WARNING_MESSAGE
                    );
                }
                fabricanteAModificar = null;
            }
        };
            
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(runnable);
        } else {
            runnable.run();
        }
    }

    @Override
    public void doEliminarFabricantes() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                controller.eliminarFabricantes(FabricantesViewSwing.this);
                return null;
            }
        };
        worker.execute();
    }
    
    @Override
    public List<Fabricante> getFabricantesAEliminar() {
        return fabricantesAEliminar;
    }

    @Override
    public void notificarFabricantesEliminados(final Boolean resultado) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (resultado) {
                    
                    for (Fabricante fabricante : fabricantesAEliminar) {
                        tableModel.deleteRow(fabricante);
                    }
                    
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Ha ocurrido un error al eliminar los fabricantes seleccionados.")
                          .append("%n")
                          .append("Por favor contacte al administrador");
                    
                    String mensaje = String.format(sb.toString());
                    
                    JOptionPane.showMessageDialog (
                                    null
                                  , mensaje
                                  , "Error"
                                  , JOptionPane.WARNING_MESSAGE
                    );
                }

                fabricantesAEliminar.clear();
            }
        };
        
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(runnable);
        } else {
            runnable.run();
        }
    }

    @Override
    public void doConsultarFabricantes() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void,Void> () {
            @Override
            protected Void doInBackground() throws Exception {
                controller.getListaFabricantes(FabricantesViewSwing.this);
                return null;
            }
        };
        worker.execute();
    }
    
    @Override
    public void setListaFabricantes(final List<Fabricante> fabricantes) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                tableModel.clearTableModelData();
                tableModel.addRows(fabricantes);
            }
        };
        
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(runnable);
        } else {
            runnable.run();
        }
    }
}