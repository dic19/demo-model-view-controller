/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package example.mvc.main;

import example.mvc.controller.FabricantesControllerImp;
import example.mvc.controller.IFabricantesController;
import example.mvc.model.EntityManagerProvider;
import example.mvc.model.FabricantesModelJpa;
import example.mvc.model.IFabricantesModel;
import example.mvc.view.FabricantesViewSwing;
import example.mvc.view.IFabricantesView;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author Delcio Amarillo.
 */
public class ModelViewController {
    
    public static void main(String[] args) {
        
        EntityManager entityManager = EntityManagerProvider.getProvider().getEntityManager();
        IFabricantesModel fabricantesModel = new FabricantesModelJpa(entityManager);
        final IFabricantesController controller = new FabricantesControllerImp(fabricantesModel);
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(ModelViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                IFabricantesView view = new FabricantesViewSwing(controller);
                JComponent component = ((FabricantesViewSwing)view).getUIComponent();
                
                JFrame frame = new JFrame("ABMC Fabricantes");
                frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                
                frame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(final WindowEvent e) {
                        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                            @Override
                            protected Void doInBackground() throws Exception {
                                EntityManagerProvider.getProvider().closeResources();
                                return null;
                            }

                            @Override
                            protected void done() {
                                e.getWindow().dispose();
                            }
                        };
                        worker.execute();
                    }
                });
                
                frame.add(component);
                frame.pack();
                frame.setLocationByPlatform(true);
                frame.setVisible(true);
            }
        });
    }
}
