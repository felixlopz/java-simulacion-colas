package proyectosimulacion;

import proyectosimulacion.ResultadosData;
import proyectosimulacion.gui.EstadisticasServidor;
import proyectosimulacion.gui.Resultados;
import proyectosimulacion.gui.MostrarTablaEventos;

import java.util.ArrayList;
import java.util.List;

public class Simulacion {
    
   // Parametros entrada archivo
    private final int capacidadMaxima;
    private final int tiempoTotal;
    private final String unidadTiempoTotal;
    private final int tiempoSecundario;
    private final String unidadTiempoSecundaria;
    private final int canitdadServidores;
    private final int costoEsperaCliente;
    
    // Distribuciones
    private final ArrayList<int[][]> distribucionLlegadas;
    private final int[][] distribucionServicio;

    
    // Variables de simulacion
    private int DT;
    private int AT;
    private int actualTime;
    private int clienteNumber;

    private String eventType;
    private int eventNumber;
    
    private String numeroAleatorioTE;
    private String TE;

    private String numeroAleatorioTS;
    private String TS;
    
    private List<Cliente> queue;
    private List<Servidor> servers;

    // Contadores simulacion
    private int clienteCounter;
    private int clientesEsperanCounter;
    private int clientesNoEsperanCounter;
    private int clientesNoAtendidosCounter;
    
   
    // Promedios
    private float clientesSistemaProm;
    private float clientesColaProm;
    private float tiempoColaProm;
    private float tiempoSistemaProm;
    private float tiempoEsperaProm;
    
    private float probabilidadEsperar;
    private float utilizacionServidoresPercentage;
    
    
    public Simulacion (
        int capacidadMaxima, 
        int tiempoTotal, 
        String unidadTiempoTotal, 
        int tiempoSecundario, 
        String unidadTiempoSecundaria,
        int cantidadServidores,
        int costoServidor,
        int costoEsperaCliente,
        ArrayList distribucionLlegadasArray,
        int [][] distribucionServicio
    ){
        
       // Parametos de entrada de archivos
        this.capacidadMaxima = capacidadMaxima;
        this.tiempoTotal = tiempoTotal;
        this.unidadTiempoTotal = unidadTiempoTotal; 
        this.tiempoSecundario = tiempoSecundario;
        this.unidadTiempoSecundaria = unidadTiempoSecundaria;
        this.canitdadServidores = cantidadServidores;
        this.costoEsperaCliente = costoEsperaCliente;
        
        // Tablas de distribuciones
        this.distribucionLlegadas = distribucionLlegadasArray;
        this.distribucionServicio = distribucionServicio;
        
        initSimulationVariables( cantidadServidores, costoServidor );
       
    }
    
    public void initSimulationVariables (int cantidadServidores, int costoServidor){
        
         // Inicializacion para la simulacion
        
        this.DT = 9999;
        this.AT = 0;
        this.actualTime = 0;
        this.clienteNumber = 0;

        this.eventNumber = 0;
        this.eventType = "";
         
        this.numeroAleatorioTE = "";
        this.TE= "";
        this.numeroAleatorioTS = "";
        this.TS= "";

        this.queue = new ArrayList();
        this.servers = new ArrayList();
        
        this.clienteCounter = 0;
        this.clientesEsperanCounter = 0;
        this.clientesNoEsperanCounter = 0;
        this.clientesNoAtendidosCounter = 0;

        this.clientesColaProm = 0;
        this.clientesSistemaProm = 0;
        this.tiempoColaProm = 0;
        this.tiempoSistemaProm = 0;
        this.tiempoEsperaProm = 0;
        
        this.probabilidadEsperar = 0;
        this.utilizacionServidoresPercentage = 0;
    
          // Inicializacion lista de servidores
        for (int i=0;i<cantidadServidores;i++) {
            Servidor servidor = new Servidor(i, costoServidor);
            this.servers.add(servidor);
        }     
        
    };
    
    
    public int randomNumber(){  
        return (int) (Math.random() * 99);
    }
    
    public Boolean areServersFull( List<Servidor> servidores ){ 
        for (Servidor server : servidores){
           if( server.getServerStatus() == 0) return false;
        }
        return true;
    }
    
    public Boolean areServersFree( List<Servidor> servidores ){ 
        for (Servidor server : servidores){
           if( server.getServerStatus() == 1) return false;
        }
        return true;
    }
    
    public int selectServer( List<Servidor> servidores ){ 
        if (areServersFull(servidores)){
            return -1;
        }
        for (Servidor server:servidores){
            if ( server.getServerStatus() == 0 ) return server.getNroServidor();
        }            
        return -1;
    }
    
    public int getLowestDT ( List<Servidor> servidores, int comparator){
    
        int lowestValue = comparator;
                
        for(Servidor server:servers){
            if ( server.getAtendiendo() != null ){    
                if( server.getAtendiendo().getTiempoSalida() < lowestValue){
                    lowestValue = server.getAtendiendo().getTiempoSalida();
                    server.setDt(server.getAtendiendo().getTiempoSalida());
                }
            }
        }
        return lowestValue;
    }
    
    public int generarTiempoServicio(){
        int numAleatorio =0;
        int limInf = 0;
        int limSup = this.distribucionServicio[0][1]-1;
        numAleatorio = randomNumber();       
        for(int i=0; i<this.distribucionServicio.length ; i++){
            if(numAleatorio<=limSup && numAleatorio>=limInf){
                this.numeroAleatorioTS= Integer.toString(numAleatorio);
                return this.distribucionServicio[i][0]; 
            }
            if (i!=distribucionServicio.length-1){
                limInf=limSup+1; 
                limSup+=this.distribucionServicio[i+1][1];
            }
        }
        return 0;
    }
 
    public int generarTiempoEntreLlegadas(int diaActual){
        int numAleatorio =0;
        int limInf = 0;
        int limSup = 0;
        numAleatorio = randomNumber();        
        
        for(int i=0;i<this.distribucionLlegadas.get(diaActual).length;i++){
            limSup += this.distribucionLlegadas.get(diaActual)[i][1]-1; 
            if(numAleatorio<=limSup && numAleatorio>=limInf){
                this.numeroAleatorioTE= Integer.toString(numAleatorio);
                return this.distribucionLlegadas.get(diaActual)[i][0]; 
            }
            limInf = limSup+1;
        }        
        return 0;
    }
    
    public int start(){     
        
        int salida= 0;
        int menorDT= 9999;
        int servidorLiberado = 9999;
        int clientesEnSistema = 0;
        int servidorEscogido = 0;
        int longitudCola = 0;
        int tiempoAnterior = 0;
        int tiempoSim = 0;
        int contCliente = 0;
        float tPromedioEspera = 0;
        float tPromedioCola = 0;
        float tPromedioSistema = 0;
        float cPromedioSistema = 0;
        float cPromedioCola = 0;
        int tiempoTotal=  0;        
        int sigCiclo = 0;
        int cEnSistema = 0;
        int lCola = 0;
        int diaActual = 0;
        float clientesCincoMinutos =0;
        
        
        MostrarTablaEventos eventos = new MostrarTablaEventos ( this.canitdadServidores );
        
        while ( this.actualTime < this.tiempoTotal ){           
            while ( sigCiclo == 0 ){
                salida=0;
                menorDT=9999;
                tiempoAnterior=tiempoSim;           
                cEnSistema=clientesEnSistema;
                lCola=longitudCola;
                this.eventNumber++;          
                
                 // Condicion para procesar salidas o llegadas
                if ( tiempoSim < this.tiempoSecundario && this.AT < this.DT ){
                    
                    // Procesar llegada del cliente
                    
                    if ( clientesEnSistema < this.capacidadMaxima ){ // Verificar que el sistema no este a su maxima capacidad
                        
                        this.eventType = "Llegada";
                        tiempoSim = this.AT; // Establecer tiempo de llegada
                        this.clienteCounter++; // Aumenta la Cantidad de clientes
                        clientesEnSistema++; // Aumenta la Cantidad de clientes en sistema
                        this.clienteNumber = this.clienteCounter; // Se le asigna un numero
                        
                        Cliente cliente = new Cliente(clienteCounter,tiempoSim); // Creacion del objeto cliente
                        servidorEscogido = this.selectServer(servers); // Busca el servidor para ingresar al cliente 
                        
                        if ( servidorEscogido!= -1 ){  // Si encontro un servidor disponible
                            this.clientesNoEsperanCounter++; // Aumenta contador clientes que no esperan
 
                            this.servers.get(servidorEscogido).setServerStatus(1); // Cambiar estado del servidor
                            this.servers.get(servidorEscogido).setAtendiendo(cliente); // Cliente atendio

                            // Genera tiempo de servicio
                            int tiempoServicioAux = this.generarTiempoServicio();
                            cliente.setTiempoServicio(tiempoServicioAux); 
                            this.TS = Integer.toString(tiempoServicioAux); 
                            
                            // Establecemos DT
                            this.DT = tiempoSim + tiempoServicioAux; 
                            cliente.setTiempoSalida(DT); 
                            
                            // Genera tiempo entre llegada
                            int tiempoEntreLlegadaAuxiliar = this.generarTiempoEntreLlegadas(diaActual); 
                            cliente.setTiempoEntreLlegada( tiempoEntreLlegadaAuxiliar );
                            this.TE = Integer.toString(tiempoEntreLlegadaAuxiliar);
                            
                            // Establecemos AT
                            this.AT = tiempoSim + tiempoEntreLlegadaAuxiliar;
                            cliente.setTiempoLlegadaServidor( tiempoSim );
                            
                            // Se busca el proximo DT ( con el menor valor )
                            menorDT = getLowestDT( this.servers, menorDT );
                            this.DT = menorDT;                    
                        }
                        else{ // Si no hay servidores disponible
                            
                            // Actualizamos la cola y sus contadores
                            longitudCola++;
                            this.clientesEsperanCounter++;
                            this.queue.add(cliente);
                            
                            // Generar tiempo entre llegada
                            int tiempoEntreLlegadaAuxiliar = this.generarTiempoEntreLlegadas(diaActual); 
                            cliente.setTiempoEntreLlegada( tiempoEntreLlegadaAuxiliar );
                            this.TE = Integer.toString(tiempoEntreLlegadaAuxiliar);
                            
                            // Establecemos AT
                            this.AT = tiempoSim + tiempoEntreLlegadaAuxiliar;
                        }
                    }
                    else{
                        // Se aumenta el contador de clientes no atendidos
                        AT += this.generarTiempoEntreLlegadas( diaActual ); // Se programa la siguiente llegada
                        this.clientesNoAtendidosCounter++;
                    }
                }
                else{
                    
                    // Procesar Salida del cliente del cliente
                    
                    if( clientesEnSistema > 0 ){ // Si tenemos clientes en el sistema
                        clientesEnSistema--;
                        
                        // Preparamos la salida en la tabla de eventos
                        this.eventType = "Salida";
                        this.TS= "";
                        this.TE= "";
                        this.numeroAleatorioTS= "";
                        this.numeroAleatorioTE= "";
                        
                        // Establecemos DT
                        tiempoSim = this.DT;
                        
                        for( Servidor i: this.servers ){
                            if ( i.getAtendiendo() != null ){
                                salida = 1;
                                if(i.getAtendiendo().getTiempoSalida() < menorDT){
                                    menorDT = i.getAtendiendo().getTiempoSalida();
                                    servidorLiberado = i.getNroServidor();
                                }
                            }
                        }
                        
                        if ( salida == 1 ){
                            
                            this.servers.get(servidorLiberado).setTiempoUtil(
                                tiempoSim - this.servers.get(servidorLiberado).getAtendiendo().getTiempoLlegadaServidor()
                            );
                            
                            this.servers.get(servidorLiberado).getAtendiendo().setTiempoSalida( tiempoSim );
                            this.clienteNumber = this.servers.get(servidorLiberado).getAtendiendo().getNroCliente();
                            tPromedioSistema += this.servers.get(servidorLiberado).getAtendiendo().getTiempoSalida()-
                            this.servers.get(servidorLiberado).getAtendiendo().getTiempoLlegada();
                            tPromedioCola += this.servers.get(servidorLiberado).getAtendiendo().getTiempoCola();
                            this.servers.get(servidorLiberado).setServerStatus(0);
                            this.servers.get(servidorLiberado).setAtendiendo(null);
                            
                        }
                        
                        if ( longitudCola > 0){ // Verificacion el tamano de la cola 
                            longitudCola--;
                            
                            int index = 0;
                            
                           
                            // Establecemos el tiempo en cola para el cliente
                            this.queue.get(index).setTiempoCola( tiempoSim - this.queue.get(index).getTiempoLlegada() );
                            if (this.queue.get(index).getTiempoCola() > 5)
                                clientesCincoMinutos++;
                            
                            // Generamos el tiempo de servicio
                            this.queue.get(index).setTiempoServicio( this.generarTiempoServicio() );
                            this.TS = Integer.toString(queue.get(index).getTiempoServicio());
                            
                            // Eablecemos DT
                            this.DT = tiempoSim + this.queue.get(index).getTiempoServicio();
                            
                            this.queue.get(index).setTiempoSalida(this.DT); // Guardamos DT en el cliente
                            
                            // Asignamos el cliente al un servidor
                            this.servers.get(servidorLiberado).setAtendiendo(this.queue.get(index));
                            this.servers.get(servidorLiberado).setServerStatus(1);
                            
                            this.queue.get(index).setTiempoLlegadaServidor(tiempoSim); // Establecemos tiempo llegada al servidor
                            this.queue.remove(index); // Eliminamos de la cola
                        }
                        else{
                            menorDT = 9999;
                            if (areServersFree(this.servers)){
                                this.DT = 9999;
                            }
                            else{
                                for(Servidor i: this.servers){
                                    if (i.getAtendiendo()!= null){    
                                        if(i.getAtendiendo().getTiempoSalida()< menorDT){
                                            menorDT= i.getAtendiendo().getTiempoSalida();
                                        }
                                    }
                                }
                                this.DT = menorDT;
                            }
                        }
                        
                        if (tiempoSim >= this.tiempoSecundario && clientesEnSistema ==0 ){
                            sigCiclo = 1;
                            if (diaActual != 4)
                                diaActual++; 
                            else {
                                diaActual = 0;
                            }
                        }
                    }
                }
                
                cPromedioSistema += (tiempoSim-tiempoAnterior)*cEnSistema;
                cPromedioCola += (tiempoSim-tiempoAnterior)*lCola;              
                
                eventos.agregarFila(this.eventNumber, 
                    this.eventType, 
                    this.clienteNumber, 
                    tiempoSim, 
                    this.numeroAleatorioTE,
                    this.TE,
                    this.numeroAleatorioTS,
                    this.TS, 
                    longitudCola, 
                    this.AT, 
                    this.servers
                );    
                
            } // Fin del tiempo secundario
            
            this.actualTime++;
            contCliente += this.clienteCounter;
            clientesEnSistema = 0;
            sigCiclo = 0;
            if ( this.clientesEsperanCounter != 0){
                tPromedioEspera=tPromedioCola / this.clientesEsperanCounter;
            }
            else{
                tPromedioEspera = 0;
            }
            
            tPromedioSistema /= this.clienteCounter;
            tPromedioCola /= this.clienteCounter;
            cPromedioSistema /= tiempoSim;
            cPromedioCola /= tiempoSim;
            tiempoTotal += tiempoSim;         
            this.tiempoEsperaProm += tPromedioEspera; 
            this.tiempoSistemaProm += tPromedioSistema;
            this.tiempoColaProm+=tPromedioCola;
            this.clientesSistemaProm+=cPromedioSistema;
            this.clientesColaProm+=cPromedioCola;            
            tPromedioEspera = 0;
            tPromedioSistema = 0;
            tPromedioCola = 0;
            cPromedioSistema = 0;
            cPromedioCola = 0;
            this.clienteCounter = 0;
            tiempoSim = 0;
            this.AT = 0;
            this.DT = 9999;
            this.eventNumber = 0;
            longitudCola = 0;
            
            eventos.agregarFilaSeparadora();
            
        }// Fin del tiempoTotal
        
        
        float tiempoOciosoServidores = 0;
            for (Servidor i: this.servers){                
                tiempoOciosoServidores += tiempoTotal - i.getTiempoUtil();
            }
        tiempoOciosoServidores /= this.servers.size();
        float auxiliarDivision = contCliente;
        float probabilidadEsperaCincoMinutos = clientesCincoMinutos/auxiliarDivision;
        this.tiempoEsperaProm /= this.actualTime; 
        this.tiempoSistemaProm /= this.actualTime;
        this.tiempoColaProm/= this.actualTime;
        this.clientesSistemaProm /= this.actualTime;
        this.clientesColaProm /= this.actualTime;
        this.probabilidadEsperar =((float) this.clientesEsperanCounter/contCliente);
        
        String estadisticasServidores="";      
        for(Servidor i: this.servers){
            i.setPorcentajeUtil(tiempoTotal);
            estadisticasServidores+= "Porcentaje de utilizacion del servidor " + String.valueOf(i.getNroServidor()) + ": " + String.valueOf(i.getPorcentajeUtil()) + "\n";
            this. utilizacionServidoresPercentage += i.getPorcentajeUtil();
            i.setCostoTotalServidor(i.getCostoServidor()*tiempoTotal);
            estadisticasServidores+="Costo del servidor " + String.valueOf(i.getNroServidor()) + ": " + i.getCostoTotalServidor() + "\n";
        }                         
        this.utilizacionServidoresPercentage /= this.servers.size();
           
        EstadisticasServidor stats = new EstadisticasServidor(this.servers);
        
        ResultadosData data = new ResultadosData(
           this.clientesNoEsperanCounter,
           this.clientesNoAtendidosCounter,
           String.format("%2.02f", this.probabilidadEsperar),
           String.format("%2.02f", this.clientesSistemaProm),
           String.format("%2.02f", this.clientesColaProm),
           String.format("%2.02f", this.tiempoSistemaProm),
           String.format("%2.02f", this.tiempoColaProm),
           String.format("%2.02f", this.tiempoEsperaProm), // Segunda entrega
String.format("%2.02f", this.utilizacionServidoresPercentage),
           contCliente,
           String.format("%2.02f", tiempoOciosoServidores) + ' ' + this.unidadTiempoSecundaria,
           String.format("%2.02f", probabilidadEsperaCincoMinutos)     
         );
                 
          new Resultados(this.actualTime, eventos, data, stats ).setVisible(true);
              
        return 1;   
    }           
    
}    

