package vistas;

import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import simulacion.Servidor;

public class MostrarTablaEventos extends javax.swing.JFrame {
    int nroServidores;
    int cantColumnas;
    String [] titulos;
    
    public MostrarTablaEventos(int Servidores) {
        initComponents();
        nroServidores = Servidores;
        cantColumnas = 10 + nroServidores  * 2;
        
        //TITULOS
        titulos = new String[cantColumnas];
        titulos[0] = "Evento";
        titulos[1] = "Tipo";
        titulos[2] = "Cliente";
        titulos[3] = "TM";
        for(int i = 4; i < nroServidores + 4; i++ ){
            titulos[i] = "S" + (i - 3);
        }
        titulos[nroServidores + 4] = "Nro alet. TE";
        titulos[nroServidores + 5] = "TE";
        titulos[nroServidores + 6] = "Nro alet. TS";
        titulos[nroServidores + 7] = "TS";
        titulos[nroServidores + 8] = "WL";
        titulos[nroServidores + 9] = "AT";
        for(int i = nroServidores + 10; i < cantColumnas; i++){
            titulos[i] = "DT" + (i - nroServidores - 9);
        }
        DefaultTableModel model = (DefaultTableModel) tablaEventosCmp.getModel();
        model.setColumnIdentifiers(titulos);
        
        //Condicion inicial
        Object[] fila = new Object[cantColumnas];
        fila[0] = "0";
        fila[1] = "Cond.ini";
        fila[2] = "-";
        fila[3] = "0";
        for(int i = 4; i < nroServidores + 4; i++ ){
            fila[i] = "0";
        }
        fila[nroServidores + 4] = "-";
        fila[nroServidores + 5] = "-";
        fila[nroServidores + 6] = "-";
        fila[nroServidores + 7] = "-";
        fila[nroServidores + 8] = "0";
        fila[nroServidores + 9] = "0";
        for(int i = nroServidores + 10; i < cantColumnas; i++){
            fila[i] = "999";
        }
        model.addRow(fila);
        
        tablaEventosCmp.setModel(model);
        tablaEventosCmp.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    public void agregarFilaSeparadora(){
        DefaultTableModel model = (DefaultTableModel) tablaEventosCmp.getModel();
        model.setColumnIdentifiers(titulos);
        
        for(int x = 0; x < 3; x++){
            Object[] fila = new Object[cantColumnas];
            fila[0] = "";
            fila[1] = "";
            fila[2] = "";
            fila[3] = "";
            for(int i = 4; i < nroServidores + 4; i++ ){
                fila[i] = "";
            }
            fila[nroServidores + 4] = "";
            fila[nroServidores + 5] = "";
            fila[nroServidores + 6] = "";
            fila[nroServidores + 7] = "";
            fila[nroServidores + 8] = "";
            fila[nroServidores + 9] = "";
            for(int i = nroServidores + 10; i < cantColumnas; i++){
                fila[i] = "";
            }
            model.addRow(fila);
        }
        
        //Condicion inicial
        Object[] fila = new Object[cantColumnas];
        fila[0] = "0";
        fila[1] = "Cond.ini";
        fila[2] = "-";
        fila[3] = "0";
        for(int i = 4; i < nroServidores + 4; i++ ){
            fila[i] = "0";
        }
        fila[nroServidores + 4] = "-";
        fila[nroServidores + 5] = "-";
        fila[nroServidores + 6] = "-";
        fila[nroServidores + 7] = "-";
        fila[nroServidores + 8] = "0";
        fila[nroServidores + 9] = "0";
        for(int i = nroServidores + 10; i < cantColumnas; i++){
            fila[i] = "999";
        }
        model.addRow(fila);
        
        tablaEventosCmp.setModel(model);
        tablaEventosCmp.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
    
    public void agregarFila(int evento, String tipo, int idCliente, int tiempoSimulacion, String numAleatorioTELL, String tell, String numAleatorioST, String st,  int wl, int at, List servidores ){
        
        DefaultTableModel model = (DefaultTableModel) tablaEventosCmp.getModel();
        model.setColumnIdentifiers(titulos);
        
        Object[] fila = new Object[cantColumnas];
        
        fila[0] = Integer.toString(evento);
        fila[1] = tipo;
        fila[2] = Integer.toString(idCliente);
        fila[3] = Integer.toString(tiempoSimulacion);
        
        
        
        Servidor servidor = null;
        int index = 4;
        for( Object servidorObj : servidores){
           servidor = (Servidor) servidorObj;   
           fila[index] = servidor.getStatus();
           index++;
        }
        
        fila[nroServidores + 4] = numAleatorioTELL; // Numero Aleatorio TELL
        fila[nroServidores + 5] = tell; // TELL
        fila[nroServidores + 6] = numAleatorioST; // numAleatorioST
        fila[nroServidores + 7] = st; // ST
        
        fila[nroServidores + 8] = Integer.toString(wl);
        fila[nroServidores + 9] = Integer.toString(at);
        
        servidor = null;
        index = nroServidores + 10;
        for( Object servidorObj : servidores){
           servidor = (Servidor) servidorObj;   
           fila[index] = servidor.getDT();
           index++;
        }
        
        model.addRow(fila);
        
        tablaEventosCmp.setModel(model);
        tablaEventosCmp.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); 
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaEventosCmp = new javax.swing.JTable();
        aceptarBoton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Tabla de Eventos");

        tablaEventosCmp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tablaEventosCmp);

        aceptarBoton.setText("Aceptar");
        aceptarBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptarBotonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1050, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(aceptarBoton)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(469, 469, 469)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(aceptarBoton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void aceptarBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptarBotonActionPerformed
        this.dispose();
    }//GEN-LAST:event_aceptarBotonActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aceptarBoton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaEventosCmp;
    // End of variables declaration//GEN-END:variables
}
