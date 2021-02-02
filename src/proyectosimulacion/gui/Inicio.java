package proyectosimulacion.gui;

import datos.Datos;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import proyectosimulacion.Cliente;
import proyectosimulacion.Servidor;
import proyectosimulacion.TablaDistribucion;
import proyectosimulacion.ResultadosData;

import java.io.FileReader;
import java.io.File;
import java.net.URL;
import java.util.Iterator;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import proyectosimulacion.Simulacion;

public class Inicio extends javax.swing.JFrame {
    TablaDistribucion distribLlegadas;    
    TablaDistribucion distribServicios;
    ArrayList<Servidor> costos;
    int tamañoServ;
    int tiempoSimulacion;
    
    public Simulacion simulacion;
    
    public File file;
    public Scanner s;
    public int[][] st ;
    public int[][] at ;
    //public int[][] servidores ;
    public int cicloPrimarioArchivo;
    public int cicloSecundarioArchivo;
    public String unidadTiempo1;
    public String unidadTiempo2;
    public float costoEspera;
    public int maxSistema;
    
    public Inicio() {
        distribLlegadas = new TablaDistribucion();
        distribServicios = new TablaDistribucion();
        tiempoSimulacion = 1;
        tamañoServ = 0;
        costos = new ArrayList<>();
        initComponents();
        this.setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        simularBoton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        botonAgregarLlegada = new javax.swing.JButton();
        botonBorrarLlegada = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        listaDistribucionCmpte = new java.awt.List();
        jLabel2 = new javax.swing.JLabel();
        tiempoSimulacionCampo = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        botonAgregarSt = new javax.swing.JButton();
        botonBorrarServicio = new javax.swing.JButton();
        listaDistribucionCmpte1 = new java.awt.List();
        botonAgregarServidor = new javax.swing.JButton();
        botonQuitarServidor = new javax.swing.JButton();
        servidores = new java.awt.List();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        botonArchivo = new javax.swing.JButton();

        jMenu1.setText("jMenu1");

        jMenuItem1.setText("jMenuItem1");

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        simularBoton.setText("Simular");
        simularBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simularBotonActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Distribucion de Llegadas");

        botonAgregarLlegada.setText("+");
        botonAgregarLlegada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarLlegadaActionPerformed(evt);
            }
        });

        botonBorrarLlegada.setText("-");
        botonBorrarLlegada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBorrarLlegadaActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setText("Simulación de Colas");

        listaDistribucionCmpte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listaDistribucionCmpteActionPerformed(evt);
            }
        });

        jLabel2.setText("Tiempo de Simulacion:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Distribucion de Servicios");

        botonAgregarSt.setText("+");
        botonAgregarSt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarStActionPerformed(evt);
            }
        });

        botonBorrarServicio.setText("-");
        botonBorrarServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBorrarServicioActionPerformed(evt);
            }
        });

        listaDistribucionCmpte1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listaDistribucionCmpte1ActionPerformed(evt);
            }
        });

        botonAgregarServidor.setText("+");
        botonAgregarServidor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarServidorActionPerformed(evt);
            }
        });

        botonQuitarServidor.setText("-");
        botonQuitarServidor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonQuitarServidorActionPerformed(evt);
            }
        });

        servidores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                servidoresActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Servidores");

        jLabel9.setText("Min");

        botonArchivo.setText("Simular desde archivo");
        botonArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonArchivoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(listaDistribucionCmpte, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(54, 54, 54)
                                        .addComponent(botonAgregarLlegada, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(botonBorrarLlegada)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGap(29, 29, 29)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(listaDistribucionCmpte1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(3, 3, 3)
                                                .addComponent(jLabel5))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(26, 26, 26)
                                        .addComponent(botonAgregarSt, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(botonBorrarServicio)
                                        .addGap(51, 51, 51))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tiempoSimulacionCampo, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(servidores, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(60, 60, 60)
                                        .addComponent(jLabel7))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(botonAgregarServidor, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(botonQuitarServidor)
                                .addGap(48, 48, 48))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(botonArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(simularBoton))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(240, 240, 240)
                        .addComponent(jLabel4)))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel4)
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGap(40, 40, 40)
                                    .addComponent(servidores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addGap(18, 18, 18)
                                    .addComponent(listaDistribucionCmpte1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addGap(18, 18, 18)
                                    .addComponent(listaDistribucionCmpte, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(botonBorrarServicio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(botonAgregarSt))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(botonBorrarLlegada)
                                .addComponent(botonAgregarLlegada))))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(botonQuitarServidor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(botonAgregarServidor)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tiempoSimulacionCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(simularBoton)
                    .addComponent(jLabel9)
                    .addComponent(botonArchivo))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonAgregarLlegadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAgregarLlegadaActionPerformed
        JTextField campoValor = new JTextField();
        JTextField campoProbabilidad = new JTextField();
        Object[] mensaje = {
            "Valor: ", campoValor,
            "Probabilidad: ", campoProbabilidad
        };
        int valor = 0;
        float probabilidad = 0;
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, "Agregar Probabilidad", JOptionPane.OK_CANCEL_OPTION);
        if(respuesta == JOptionPane.OK_OPTION){
            try{
                valor = Integer.parseInt(campoValor.getText());
            }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(this, "Valor inválido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try{
                probabilidad = Float.parseFloat(campoProbabilidad.getText());
            }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(this, "Probabilidad inválida", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            distribLlegadas.agregarEntrada(valor, probabilidad);
            listaDistribucionCmpte.add(valor+ " - " + probabilidad * 100 + "% - " + distribLlegadas.getFrecAcumulada(distribLlegadas.getTamaño() - 1) * 100 +"%");

        }
    }//GEN-LAST:event_botonAgregarLlegadaActionPerformed
    
    private void recargarListaLlegadas(){
        listaDistribucionCmpte.removeAll();
        for(int i = 0; i < distribLlegadas.getTamaño(); i++){
            listaDistribucionCmpte.add(distribLlegadas.getValor(i) + " - " + distribLlegadas.getProbabilidad(i) * 100 + "% - " + distribLlegadas.getFrecAcumulada(i) * 100 +"%");
        }
    }
    
    private void botonBorrarLlegadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBorrarLlegadaActionPerformed
        int i = listaDistribucionCmpte.getSelectedIndex();
        if(i != -1){
            distribLlegadas.removerEntrada(i);
            recargarListaLlegadas();
        }
    }//GEN-LAST:event_botonBorrarLlegadaActionPerformed

    @SuppressWarnings("empty-statement")
    private void simularBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simularBotonActionPerformed
        tiempoSimulacion = (Integer ) tiempoSimulacionCampo.getValue();
        
        if(tiempoSimulacion <= 0 ){
            JOptionPane.showMessageDialog(this, "El tiempo de simulación debe ser al menos 1", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if(distribLlegadas.getTamaño() == 0){
            JOptionPane.showMessageDialog(this, "Falta agregar la distribucion de tiempos de llegada", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(distribLlegadas.getFrecAcumulada(distribLlegadas.getTamaño() - 1) != 1){
            JOptionPane.showMessageDialog(this, "Las probabilidades deben sumar 1", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(distribServicios.getTamaño() == 0){
            JOptionPane.showMessageDialog(this, "Falta agregar la distribucion de tiempos de servicio", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(distribServicios.getFrecAcumulada(distribServicios.getTamaño() - 1) != 1){
            JOptionPane.showMessageDialog(this, "Las probabilidades deben sumar 1", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(tamañoServ <= 0){
            JOptionPane.showMessageDialog(this, "Debe tener almenos 1 servidor", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        
    }//GEN-LAST:event_simularBotonActionPerformed

   
    
    private void listaDistribucionCmpteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listaDistribucionCmpteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_listaDistribucionCmpteActionPerformed

    private void botonAgregarStActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAgregarStActionPerformed
        JTextField campoValor = new JTextField();
        JTextField campoProbabilidad = new JTextField();
        Object[] mensaje = {
            "Valor: ", campoValor,
            "Probabilidad: ", campoProbabilidad
        };
        int valor = 0;
        float probabilidad = 0;
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, "Agregar Probabilidad", JOptionPane.OK_CANCEL_OPTION);
        if(respuesta == JOptionPane.OK_OPTION){
            try{
                valor = Integer.parseInt(campoValor.getText());
            }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(this, "Valor inválido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try{
                probabilidad = Float.parseFloat(campoProbabilidad.getText());
            }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(this, "Probabilidad inválida", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            distribServicios.agregarEntrada(valor, probabilidad);
            listaDistribucionCmpte1.add(valor+ " - " + probabilidad * 100 + "% - " + distribServicios.getFrecAcumulada(distribServicios.getTamaño() - 1) * 100 +"%");

        }
    }//GEN-LAST:event_botonAgregarStActionPerformed

    private void recargarLista(){
        String fila;
        int n = listaDistribucionCmpte1.getItemCount();
        for(int i = 0; i < n; i++){
            listaDistribucionCmpte1.remove(0);
        }
        for(int i = 0; i < distribServicios.getTamaño(); i++){
            fila = distribServicios.getValor(i) + ", " + distribServicios.getProbabilidad(i);
            listaDistribucionCmpte1.add(fila, i);
        }
    }
    
    private void botonBorrarServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBorrarServicioActionPerformed
        int i = listaDistribucionCmpte1.getSelectedIndex();
        if(i != -1){
            distribServicios.removerEntrada(i);
            recargarLista();
        }
    }//GEN-LAST:event_botonBorrarServicioActionPerformed

    private void listaDistribucionCmpte1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listaDistribucionCmpte1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_listaDistribucionCmpte1ActionPerformed

    private void botonAgregarServidorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAgregarServidorActionPerformed
        JTextField campoCosto = new JTextField();
        Object[] mensaje = {
            "Costo: ", campoCosto,
        };
        float costo = 0;
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, "Agregar Costo", JOptionPane.OK_CANCEL_OPTION);
        if(respuesta == JOptionPane.OK_OPTION){
            try{
                costo = Float.parseFloat(campoCosto.getText());
            }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(this, "Valor inválido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Servidor serv = new Servidor(costo);
            costos.add(serv);
            tamañoServ++;
            servidores.add("Servidor " + (tamañoServ) +", Costo: " + costo);
        }
    }//GEN-LAST:event_botonAgregarServidorActionPerformed

    private void botonQuitarServidorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonQuitarServidorActionPerformed
        int i = servidores.getSelectedIndex();
        if(i != -1){
            costos.remove(i);
            tamañoServ--;
            recargarListaServidores();
        }
    }//GEN-LAST:event_botonQuitarServidorActionPerformed

    private void recargarListaServidores(){
        String fila;
        int n = servidores.getItemCount();
        int i = 0;
        for(; i < n; i++){
            servidores.remove(0);
        }
        i = 0;
        for(Servidor serv : costos){
            fila = "Servidor " + (i+1) +", Costo: " + serv.getCosto();
            servidores.add(fila, i);
            i++;
        }
    }
    
    private void servidoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_servidoresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_servidoresActionPerformed

    private Boolean isDistributionValid(int [][] values){
        
        int probabilidadIndex = 1;
        int probabilidadCounter = 0;
        for (int row = 0; row < values.length; row++){
           probabilidadCounter += values[row][probabilidadIndex];
        }
        return probabilidadCounter == 100;
    };
    
    
    private void printDataFromJsonFile (
        String fileName,
        int capacidadMaxima, 
        int tiempoTotal, 
        String unidadTiempoTotal, 
        int tiempoSecundario, 
        String unidadTiempoSecundaria,
        int cantidadServidores,
        int costoServidor,
        int costoEsperaCliente,
        ArrayList distribucionLLegadasArray,
        int [][] distribucionServicio
    ){
        
        System.out.println("Se leyo el archivo" + fileName + "exitosamente");
        System.out.println("Capacida maxima del sistema: " + capacidadMaxima);
        System.out.println("Tiempo total a simular: " + tiempoTotal);
        System.out.println("La unidad de tiempo total: " + unidadTiempoTotal);
        System.out.println("Tiempo Secundario a Simular " + tiempoSecundario);
        System.out.println("La unidad de tiempo secundaria: " + unidadTiempoSecundaria);
        System.out.println("Cantidad de servidores: " + cantidadServidores);
        System.out.println("Costo de servidores: " + costoServidor);
        System.out.println("Costo de esperar un cliente: " + costoEsperaCliente);
        System.out.println("\n** Distribucion de LLegadas **");
        
        int dayIndex = 1;
        for ( Object objeto : distribucionLLegadasArray ){
            
            int [][] distribucion = (int [][]) objeto;
            
            System.out.println("\nDia Numero: " + dayIndex++);
             for (int row = 0; row < distribucion.length; row++){
               System.out.println("Cantidad: " + distribucion[row][0] + " - Probabilidad: " + distribucion[row][1] + "%");
             }
        
        }
        
        System.out.println("\n** Distribucion de Servicios **");
        
         for (int row = 0; row < distribucionServicio.length; row++){
            System.out.println("Cantidad: " + distribucionServicio[row][0] + " - Probabilidad: " + distribucionServicio[row][1] + "%");
        }
    };
    
    private void botonArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonArchivoActionPerformed
        
        String fileName = "datosbbva.json";
        
        URL path = Datos.class.getResource(fileName);
        File file = new File(path.getFile());
       //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        
        try (FileReader reader = new FileReader(file);){
            
            //JSON parser object to parse read file
            JSONObject obj = (JSONObject) jsonParser.parse(reader);
            
            // Capacidad Maxima del sistema
            int capacidadMaxima = ((Long) obj.get("capacidadMaxima")).intValue();

            // Tiempo total a simular
             JSONObject tiempoTotalObj = (JSONObject) obj.get("tiempoTotal");
            int tiempoTotal =  ((Long) tiempoTotalObj.get("cantidad")).intValue();
            String unidadTiempoTotal = tiempoTotalObj.get("unidad").toString();
            
            // Tiempo secundario total a simular
            JSONObject tiempoSecundarioObj = (JSONObject) obj.get("tiempoSecundario");
            int tiempoSecundario = ((Long) tiempoSecundarioObj.get("cantidad")).intValue();
            String unidadTiempoSecundaria = tiempoSecundarioObj.get("unidad").toString();
            
            // Servidores
            JSONObject servidores = (JSONObject) obj.get("servidores");
            int cantidadServidores =  ((Long) servidores.get("cantidad")).intValue(); // Cantidad
            int costoServidor =  ((Long) servidores.get("costo")).intValue(); // Cost
                        
            // Costo espera cliente
            int costoEsperaCliente = ((Long) obj.get("costoEsperaCliente")).intValue();

            // Distribucion de llegada
            JSONArray distribucionLlegadaJSONArray = (JSONArray) obj.get("distribucionLlegada");
            ArrayList<int[][]> distribucionLlegadasArray = new ArrayList<int[][]>();
            
            for ( Object dia : distribucionLlegadaJSONArray ) {
                // Dentro del objeto dia de la distribucion de llegada
                                
                JSONObject diaObj = (JSONObject) dia; // Cast del objeto dia
                JSONArray tiemposArray = (JSONArray) diaObj.get("tiempos");
                
                int baseIndex = 0;
                int[][] tiemposLlegada = new int[tiemposArray.size()][2];
                
                
                 for( Object distribucion: tiemposArray ) {
                    JSONObject distribucionObj = (JSONObject) distribucion; // Cast de distribucion al objeto 
                    tiemposLlegada[ baseIndex ][0] = ((Long) distribucionObj.get("cantidad")).intValue();
                    float probabilidadFloat =  ((Double) distribucionObj.get("probabilidad")).floatValue();
                    tiemposLlegada[ baseIndex ][1] = Math.round(probabilidadFloat * 100);
                    baseIndex++;
                 }; 
                 // Validacion de de la probabilidad de cada distribucion en la distribucion de llegadas
                if (isDistributionValid(tiemposLlegada)){
                    distribucionLlegadasArray.add(tiemposLlegada);
                }else{
                    JOptionPane.showMessageDialog(null, "La probabilidad de las tablas de distribucion de llegada debe ser 1");
                    return;
                }
            };
            
            // Distribucion de servicio
            JSONArray distribucionServicioJSONArray = (JSONArray) obj.get("distribucionServicio");
            int baseIndex = 0;
            int[][] distribucionServicio = new int[distribucionServicioJSONArray.size()][2];
            for( Object distribucion: distribucionServicioJSONArray ) {
               JSONObject distribucionObj = (JSONObject) distribucion; // Cast de distribucion al objeto 
               distribucionServicio[ baseIndex ][0]= ((Long) distribucionObj.get("tiempo")).intValue();
                float probabilidadFloat =  ((Double) distribucionObj.get("probabilidad")).floatValue();
                distribucionServicio[ baseIndex ][1] = Math.round(probabilidadFloat * 100);
               baseIndex++;
            };
            
            // Validacion de de la probabilidad de la distribucion de servicios
            if (!isDistributionValid(distribucionServicio)){
                JOptionPane.showMessageDialog(null, "La probabilidad de distribucion de servicio debe ser 1");
                return;  
            }
            
            printDataFromJsonFile(
                fileName, 
                capacidadMaxima,
                tiempoTotal, 
                unidadTiempoTotal,
                tiempoSecundario,
                unidadTiempoSecundaria,
                cantidadServidores,
                costoServidor,
                costoEsperaCliente, 
                distribucionLlegadasArray, 
                distribucionServicio 
            );
            
            simulacion = new Simulacion (
                capacidadMaxima,
                tiempoTotal,
                unidadTiempoTotal,
                tiempoSecundario,
                unidadTiempoSecundaria,
                cantidadServidores,
                costoServidor,
                costoEsperaCliente, 
                distribucionLlegadasArray,
                distribucionServicio
            );
            
            this.simulacion.iniciar();   
            
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Error en el formato del archivo");
        }
    }//GEN-LAST:event_botonArchivoActionPerformed

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
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Inicio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAgregarLlegada;
    private javax.swing.JButton botonAgregarServidor;
    private javax.swing.JButton botonAgregarSt;
    private javax.swing.JButton botonArchivo;
    private javax.swing.JButton botonBorrarLlegada;
    private javax.swing.JButton botonBorrarServicio;
    private javax.swing.JButton botonQuitarServidor;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private java.awt.List listaDistribucionCmpte;
    private java.awt.List listaDistribucionCmpte1;
    private java.awt.List servidores;
    private javax.swing.JButton simularBoton;
    private javax.swing.JSpinner tiempoSimulacionCampo;
    // End of variables declaration//GEN-END:variables
}
