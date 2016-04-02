/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.gui.mainWindow;

import edu.nus.iss.SE24PT8.universityStore.gui.components.CheckoutPanel;
import edu.nus.iss.SE24PT8.universityStore.gui.components.Home;
import edu.nus.iss.SE24PT8.universityStore.gui.components.ProductPanel;
import edu.nus.iss.SE24PT8.universityStore.gui.components.category.CategoryPanel;
import edu.nus.iss.SE24PT8.universityStore.gui.components.vendor.VendorPanel;
import edu.nus.iss.SE24PT8.universityStore.gui.framework.SubjectManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import java.awt.Component;


/**
 *
 * @author SE24PT8
 */
public class MainWindow extends javax.swing.JFrame {

    private Timer timer = null;
    private JPanel currentView = null;
    private JPanel homeView = null;
    private ProductPanel productView = null;
    private CategoryPanel catView = null;
    private VendorPanel vendorView = null;
    private CheckoutPanel checkoutView = null; 
    private static MainWindow instance;

    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        setLookAndFeel("Nimbus");

        initComponents();

        setButtonGroupForLeftMenu(); 
        
        initialiseTimer();
        jToggleButtonHomeActionPerformed(null);
        
        SubjectManager.getInstance().addSubject("MainWindow", "MenuClicked");
        
    }

    public static MainWindow getInstance() {
        if(instance == null){
            instance = new MainWindow();
            
        }
        return instance;
	}
    /**
     * To Switch to the new view
     *  1. hide the current view
     *  2. switch to the new view
     * @param newView 
     */
    private void switchView(JPanel newView) {
        jPanelDock.setVisible(false);
        if (currentView != null) {
            jPanelDock.remove(currentView);
        }
        jPanelDock.add(newView);
        currentView = newView;
        jPanelDock.setVisible(true);        
    }

    /**
     * For UI Look and feel to Nimbus
     * @param theLookAndFeel "Nimbus"
     */
    private void setLookAndFeel(String theLookAndFeel)
    {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (theLookAndFeel.equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setButtonGroupForLeftMenu(){
        buttonGroup.add(jToggleButtonCheckOut);
        buttonGroup.add(jToggleButtonMembers);
        buttonGroup.add(jToggleButtonProducts);
        buttonGroup.add(jToggleButtonCategories);
        buttonGroup.add(jToggleButtonVendors);
        buttonGroup.add(jToggleButtonDiscounts);
        buttonGroup.add(jToggleButtonStoreKeepers);
        buttonGroup.add(jToggleButtonReport);
        buttonGroup.add(jToggleButtonLogOut);
    }
    
    /**
     * initialize timer for Timer update
     */
    private void initialiseTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
          @Override
          public void run() {
            jLabelTime.setText( new SimpleDateFormat("dd MMMM, yyyy HH:mm:ss").format(new Date()));
          }
        }, 0, 1000 ); // Infinite loop with 1 sec interval
    }
    
    private void stopTimer() {
        timer.cancel();
        timer = null;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup = new javax.swing.ButtonGroup();
        jPanelTop = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabelTime = new javax.swing.JLabel();
        jLabelStoreKeeperName = new javax.swing.JLabel();
        jScrollPaneLeft = new javax.swing.JScrollPane();
        jPanelLeft = new javax.swing.JPanel();
        jToggleButtonCheckOut = new javax.swing.JToggleButton();
        jToggleButtonMembers = new javax.swing.JToggleButton();
        jToggleButtonProducts = new javax.swing.JToggleButton();
        jToggleButtonCategories = new javax.swing.JToggleButton();
        jToggleButtonVendors = new javax.swing.JToggleButton();
        jToggleButtonDiscounts = new javax.swing.JToggleButton();
        jToggleButtonStoreKeepers = new javax.swing.JToggleButton();
        jToggleButtonReport = new javax.swing.JToggleButton();
        jToggleButtonLogOut = new javax.swing.JToggleButton();
        jPanelMain = new javax.swing.JPanel();
        jScrollPaneMain = new javax.swing.JScrollPane();
        jPanelDock = new javax.swing.JPanel();
        jScrollPaneBottom = new javax.swing.JScrollPane();
        jPanelStatus = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("UniversityStore POS");
        setAlwaysOnTop(true);
        setLocation(300, 200);

        jPanelTop.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("University Store POS");

        jLabelTime.setText("09 April, 2016  09:10:21 AM");

        jLabelStoreKeeperName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelStoreKeeperName.setText("StoreKeeper Name");

        javax.swing.GroupLayout jPanelTopLayout = new javax.swing.GroupLayout(jPanelTop);
        jPanelTop.setLayout(jPanelTopLayout);
        jPanelTopLayout.setHorizontalGroup(
            jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTopLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelTime)
                    .addComponent(jLabelStoreKeeperName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );
        jPanelTopLayout.setVerticalGroup(
            jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTopLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanelTopLayout.createSequentialGroup()
                        .addComponent(jLabelTime)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelStoreKeeperName)))
                .addContainerGap())
        );

        jScrollPaneLeft.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPaneLeft.setMinimumSize(new java.awt.Dimension(23, 300));

        jToggleButtonCheckOut.setText("Checkout");
        jToggleButtonCheckOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonCheckOutActionPerformed(evt);
            }
        });

        jToggleButtonMembers.setText("Members");

        jToggleButtonProducts.setText("Products");
        jToggleButtonProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonProductsActionPerformed(evt);
            }
        });

        jToggleButtonCategories.setText("Categories");
        jToggleButtonCategories.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonCategoriesActionPerformed(evt);
            }
        });

        jToggleButtonVendors.setText("Vendors");
        jToggleButtonVendors.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	jToggleButtonVendorsActionPerformed(evt);
            }
        });

        jToggleButtonDiscounts.setText("Discounts");
        jToggleButtonDiscounts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonDiscountsActionPerformed(evt);
            }
        });

        jToggleButtonStoreKeepers.setText("Store Keepers");

        jToggleButtonReport.setText("Report");

        jToggleButtonLogOut.setText("Log Out");
        jToggleButtonLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonLogOutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelLeftLayout = new javax.swing.GroupLayout(jPanelLeft);
        jPanelLeftLayout.setHorizontalGroup(
        	jPanelLeftLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanelLeftLayout.createSequentialGroup()
        			.addGap(10)
        			.addGroup(jPanelLeftLayout.createParallelGroup(Alignment.LEADING, false)
        				.addComponent(jToggleButtonReport, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
        				.addComponent(jToggleButtonProducts, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(jToggleButtonCategories, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(jToggleButtonVendors, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(jToggleButtonMembers, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(jToggleButtonStoreKeepers, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(jToggleButtonCheckOut, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(jToggleButtonDiscounts, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(jToggleButtonLogOut, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        			.addContainerGap())
        );
        jPanelLeftLayout.setVerticalGroup(
        	jPanelLeftLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanelLeftLayout.createSequentialGroup()
        			.addGap(29)
        			.addComponent(jToggleButtonCheckOut)
        			.addGap(7)
        			.addComponent(jToggleButtonMembers)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(jToggleButtonProducts)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(jToggleButtonCategories)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(jToggleButtonVendors)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(jToggleButtonDiscounts)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(jToggleButtonStoreKeepers)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(jToggleButtonReport)
        			.addPreferredGap(ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
        			.addComponent(jToggleButtonLogOut)
        			.addGap(0))
        );
        jPanelLeftLayout.linkSize(SwingConstants.VERTICAL, new Component[] {jToggleButtonCheckOut, jToggleButtonMembers, jToggleButtonProducts, jToggleButtonCategories, jToggleButtonVendors, jToggleButtonDiscounts, jToggleButtonStoreKeepers, jToggleButtonReport, jToggleButtonLogOut});
        jPanelLeftLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {jToggleButtonCheckOut, jToggleButtonMembers, jToggleButtonProducts, jToggleButtonCategories, jToggleButtonVendors, jToggleButtonDiscounts, jToggleButtonStoreKeepers, jToggleButtonReport, jToggleButtonLogOut});
        jPanelLeft.setLayout(jPanelLeftLayout);

        jScrollPaneLeft.setViewportView(jPanelLeft);

        jPanelDock.setLayout(new java.awt.GridLayout(1, 0));
        jScrollPaneMain.setViewportView(jPanelDock);

        javax.swing.GroupLayout jPanelMainLayout = new javax.swing.GroupLayout(jPanelMain);
        jPanelMain.setLayout(jPanelMainLayout);
        jPanelMainLayout.setHorizontalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneMain, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanelMainLayout.setVerticalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneMain)
        );

        jPanelStatus.setPreferredSize(new java.awt.Dimension(641, 10));

        javax.swing.GroupLayout jPanelStatusLayout = new javax.swing.GroupLayout(jPanelStatus);
        jPanelStatus.setLayout(jPanelStatusLayout);
        jPanelStatusLayout.setHorizontalGroup(
            jPanelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 663, Short.MAX_VALUE)
        );
        jPanelStatusLayout.setVerticalGroup(
            jPanelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 46, Short.MAX_VALUE)
        );

        jScrollPaneBottom.setViewportView(jPanelStatus);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPaneBottom, javax.swing.GroupLayout.DEFAULT_SIZE, 665, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPaneLeft, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPaneLeft, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneBottom, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jToggleButtonCheckOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonCheckOutActionPerformed
        // TODO add your handling code here:
    	if (checkoutView == null) {
    		checkoutView = new CheckoutPanel();
        }
        switchView(checkoutView);
        SubjectManager.getInstance().Update("MainWindow", "MenuClicked", "Checkout");
    }//GEN-LAST:event_jToggleButtonCheckOutActionPerformed

    private void jToggleButtonProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonProductsActionPerformed
        if (productView == null) {
            productView = new ProductPanel();
        }
        switchView(productView);
        SubjectManager.getInstance().Update("MainWindow", "MenuClicked", "Product");
    }//GEN-LAST:event_jToggleButtonProductsActionPerformed

    private void jToggleButtonDiscountsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonDiscountsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButtonDiscountsActionPerformed

    private void jToggleButtonLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonLogOutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButtonLogOutActionPerformed

    private void jToggleButtonHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonHomeActionPerformed
        if (homeView == null) {
            homeView = new Home();
        }
        switchView(homeView);
        SubjectManager.getInstance().Update("MainWindow", "MenuClicked", "Home");
    }//GEN-LAST:event_jToggleButtonHomeActionPerformed

    private void jToggleButtonCategoriesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonCategoriesActionPerformed
    	if (catView == null) {
    		catView = new CategoryPanel();
        }
        switchView(catView);
        
        
    }
    private void jToggleButtonVendorsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonCategoriesActionPerformed
    	if (vendorView == null) {
    		vendorView = new VendorPanel();
        }
        switchView(vendorView);
        
        
    }//GEN-LAST:event_jToggleButtonCategoriesActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                getInstance().setVisible(true);
            }
        });
    }


	// Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelStoreKeeperName;
    private javax.swing.JLabel jLabelTime;
    private javax.swing.JPanel jPanelDock;
    private javax.swing.JPanel jPanelLeft;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JPanel jPanelStatus;
    private javax.swing.JPanel jPanelTop;
    private javax.swing.JScrollPane jScrollPaneBottom;
    private javax.swing.JScrollPane jScrollPaneLeft;
    private javax.swing.JScrollPane jScrollPaneMain;
    private javax.swing.JToggleButton jToggleButtonCategories;
    private javax.swing.JToggleButton jToggleButtonCheckOut;
    private javax.swing.JToggleButton jToggleButtonDiscounts;
    private javax.swing.JToggleButton jToggleButtonLogOut;
    private javax.swing.JToggleButton jToggleButtonMembers;
    private javax.swing.JToggleButton jToggleButtonProducts;
    private javax.swing.JToggleButton jToggleButtonReport;
    private javax.swing.JToggleButton jToggleButtonStoreKeepers;
    private javax.swing.JToggleButton jToggleButtonVendors;
    // End of variables declaration//GEN-END:variables
}
