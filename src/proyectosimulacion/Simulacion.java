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
    
    public Servidor selectServer( List<Servidor> servidores ){ 
        if (areServersFull(servidores)){
            return null;
        }
        for (Servidor server:servidores){
            if ( server.getServerStatus() == 0 ) return server;
        }            
        return null;
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
    
    public Servidor getServerWithLowestDT ( List<Servidor> servidores, int comparator){
    
        int lowestValue = comparator;        
        Servidor lowestServer = null;
        
        for(Servidor server:servers){
            if ( server.getAtendiendo() != null ){    
                if( server.getAtendiendo().getTiempoSalida() < lowestValue){
                    lowestValue = server.getAtendiendo().getTiempoSalida();
                    lowestServer = server;
                }
            }
        }
        return lowestServer;
    };
    
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
        
        int TM = 0;
        int lowestDT = 9999;
        int queueLength = 0;
        int clientesSistemaCounter = 0;
        int clienteCounter = 0;
        int clientesCincoMinutosCounter = 0;

        float tiempoEsperaPromAux = 0;
        float tiempoSistemaPromAux = 0;
        float tiempoColaPromAux = 0;

        // Auxiliares
        int lasTimeAux = 0;
        int tiempoTotalAux =  0;
        int clientesEnSistemaAux = 0;
        int indexOfDay = 0;
        Boolean hasNextDay = false;
        int queueLengthAux = 0;
        
        float clientesSistemaPromAux = 0;
        float clientesColaPromAux = 0;
        
        
        MostrarTablaEventos eventos = new MostrarTablaEventos ( this.canitdadServidores );
        
        while ( this.actualTime < this.tiempoTotal ){           
            while ( !hasNextDay  ){
                
                // Inicializacion
                lowestDT = 9999;
                lasTimeAux = TM;           
                clientesEnSistemaAux = clientesSistemaCounter;
                queueLengthAux = queueLength;
                this.eventNumber++;          
                
                 // Condicion para procesar salidas o llegadas
                if ( TM < this.tiempoSecundario && this.AT < this.DT ){
                    
                    // Procesar llegada del cliente
                    
                    if ( clientesSistemaCounter < this.capacidadMaxima ){ // Verificar que el sistema no este a su maxima capacidad
                        
                        this.eventType = "Llegada";
                        TM = this.AT; // Establecer tiempo de llegada
                        this.clienteCounter++; // Aumenta la Cantidad de clientes
                        clientesSistemaCounter++; // Aumenta la Cantidad de clientes en sistema
                        this.clienteNumber = this.clienteCounter; // Se le asigna un numero
                        
                        Cliente cliente = new Cliente(clienteCounter,TM); // Creacion del objeto cliente
                        Servidor selectedServer = this.selectServer(servers); // Busca el servidor para ingresar al cliente 
                        
                        if ( selectedServer != null ){  // Si encontro un servidor disponible
                            this.clientesNoEsperanCounter++; // Aumenta contador clientes que no esperan
 
                            selectedServer.setServerStatus(1); // Cambiar estado del servidor
                            selectedServer.setAtendiendo(cliente); // Cliente atendio

                            // Genera tiempo de servicio
                            int tiempoServicioAux = this.generarTiempoServicio();
                            cliente.setTiempoServicio(tiempoServicioAux); 
                            this.TS = Integer.toString(tiempoServicioAux); 
                            
                            // Establecemos DT
                            this.DT = TM + tiempoServicioAux; 
                            cliente.setTiempoSalida(DT); 
                            
                            // Genera tiempo entre llegada
                            int tiempoEntreLlegadaAuxiliar = this.generarTiempoEntreLlegadas(indexOfDay); 
                            cliente.setTiempoEntreLlegada( tiempoEntreLlegadaAuxiliar );
                            this.TE = Integer.toString(tiempoEntreLlegadaAuxiliar);
                            
                            // Establecemos AT
                            this.AT = TM + tiempoEntreLlegadaAuxiliar;
                            cliente.setTiempoLlegadaServidor(TM );
                            
                            // Se busca el proximo DT ( con el menor valor )
                            lowestDT = getLowestDT(this.servers, lowestDT );
                            this.DT = lowestDT;                    
                        }
                        else{ // Si no hay servidores disponible
                            
                            // Actualizamos la cola y sus contadores
                            queueLength++;
                            this.clientesEsperanCounter++;
                            this.queue.add(cliente);
                            
                            // Generar tiempo entre llegada
                            int tiempoEntreLlegadaAuxiliar = this.generarTiempoEntreLlegadas(indexOfDay); 
                            cliente.setTiempoEntreLlegada( tiempoEntreLlegadaAuxiliar );
                            this.TE = Integer.toString(tiempoEntreLlegadaAuxiliar);
                            
                            // Establecemos AT
                            this.AT = TM + tiempoEntreLlegadaAuxiliar;
                        }
                    }
                    else{
                        // Se aumenta el contador de clientes no atendidos
                        AT += this.generarTiempoEntreLlegadas(indexOfDay ); // Se programa la siguiente llegada
                        this.clientesNoAtendidosCounter++;
                    }
                }
                else{
                    
                    // Procesar Salida del cliente del cliente
                    
                    if( clientesSistemaCounter > 0 ){ // Si tenemos clientes en el sistema
                        clientesSistemaCounter--;
                        
                        // Preparamos la salida en la tabla de eventos
                        this.eventType = "Salida";
                        this.TS= "";
                        this.TE= "";
                        this.numeroAleatorioTS= "";
                        this.numeroAleatorioTE= "";
                        
                        // Establecemos DT
                        TM = this.DT;
                        
                        // Limpieando servidor utilizado y cliente
                        Servidor freeServer = getServerWithLowestDT(this.servers, lowestDT);
                        if ( freeServer != null){
                            
                            freeServer.setTiempoUtil(TM - freeServer.getAtendiendo().getTiempoLlegadaServidor());
                            freeServer.getAtendiendo().setTiempoSalida(TM );
                            this.clienteNumber = freeServer.getAtendiendo().getNroCliente();
                            
                            // Sumando al promedio de tiempos
                            tiempoSistemaPromAux += freeServer.getAtendiendo().getTiempoSalida()- freeServer.getAtendiendo().getTiempoLlegada();
                            tiempoColaPromAux += freeServer.getAtendiendo().getTiempoCola();
                            
                            freeServer.setServerStatus(0);
                            freeServer.setAtendiendo(null);
                        }          
                        
                        if ( queueLength > 0){ // Verificacion el tamano de la cola 
                            queueLength--;
                            
                            int index = 0;
                            Cliente leavingClient = this.queue.get(index);
                            
                            // Establecemos el tiempo en cola para el cliente
                            leavingClient.setTiempoCola(TM - leavingClient.getTiempoLlegada() );
                            if (leavingClient.getTiempoCola() > 5)
                                clientesCincoMinutosCounter++;
                            
                            // Generamos el tiempo de servicio
                            leavingClient.setTiempoServicio( this.generarTiempoServicio() );
                            this.TS = Integer.toString(leavingClient.getTiempoServicio());
                            
                            // Eablecemos DT
                            this.DT = TM + leavingClient.getTiempoServicio();
                            
                            leavingClient.setTiempoSalida(this.DT); // Guardamos DT en el cliente
                            
                            // Asignamos el cliente al un servidor
                            freeServer.setAtendiendo(leavingClient);
                            freeServer.setServerStatus(1);
                            
                            leavingClient.setTiempoLlegadaServidor(TM); // Establecemos tiempo llegada al servidor
                            this.queue.remove(index); // Eliminamos de la cola
                        }
                        else{ // TamanoCola igual a 0
                            lowestDT = 9999;
                            if ( areServersFree(this.servers) ){
                                this.DT = 9999;
                            }
                            else{
                                // Buscamos el menor DT
                                lowestDT = getLowestDT(this.servers, lowestDT );
                                this.DT = lowestDT;
                            }
                        }
                        
                        // Verificamos el tiempo de la simulacion
                        if ( TM >= this.tiempoSecundario && clientesSistemaCounter == 0 ){
                            
                            hasNextDay = true; // Bandera
                            
                            // Cambiamos el dia de la distribucion de llegadas
                            if (indexOfDay != 4)
                                indexOfDay++; 
                            else {
                                indexOfDay = 0;
                            }
                        }
                    }
                }
                
                // Promedios
                clientesSistemaPromAux += (TM - lasTimeAux) * clientesEnSistemaAux;
                clientesColaPromAux += (TM - lasTimeAux) * queueLengthAux;              
                
                // Escritura de la fila en la tabla de enventos
                eventos.agregarFila(this.eventNumber, 
                    this.eventType, 
                    this.clienteNumber, 
                    TM, 
                    this.numeroAleatorioTE,
                    this.TE,
                    this.numeroAleatorioTS,
                    this.TS, 
                    queueLength, 
                    this.AT, 
                    this.servers
                );    
                
            } // Fin del tiempo secundario
            
            this.actualTime++;
            clienteCounter += this.clienteCounter;
            clientesSistemaCounter = 0;
            hasNextDay = false; // Bandera
            if ( this.clientesEsperanCounter != 0){
                tiempoEsperaPromAux = tiempoColaPromAux / this.clientesEsperanCounter;
            }
            else{
                tiempoEsperaPromAux = 0;
            }
            
            tiempoSistemaPromAux /= this.clienteCounter;
            tiempoColaPromAux /= this.clienteCounter;
            clientesSistemaPromAux /= TM;
            clientesColaPromAux /= TM;
            tiempoTotalAux += TM;         
            this.tiempoEsperaProm += tiempoEsperaPromAux; 
            this.tiempoSistemaProm += tiempoSistemaPromAux;
            this.tiempoColaProm += tiempoColaPromAux;
            this.clientesSistemaProm += clientesSistemaPromAux;
            this.clientesColaProm += clientesColaPromAux; 
            
            // Reset de promedios y contadores
            tiempoEsperaPromAux = 0;
            tiempoSistemaPromAux = 0;
            tiempoColaPromAux = 0;
            clientesSistemaPromAux = 0;
            clientesColaPromAux = 0;
            this.clienteCounter = 0;
            TM = 0;
            this.AT = 0;
            this.DT = 9999;
            this.eventNumber = 0;
            queueLength = 0;
            
            eventos.agregarFilaSeparadora(); // Agregar fila para un nuevo dia
            
        }// Fin del tiempoTotal
        
        
        float tiempoOciosoServidores = 0;
            for (Servidor i: this.servers){                
                tiempoOciosoServidores += tiempoTotalAux - i.getTiempoUtil();
            }
        tiempoOciosoServidores /= this.servers.size();
        float auxiliarDivision = clienteCounter;
        float probabilidadEsperaCincoMinutos = clientesCincoMinutosCounter/auxiliarDivision;
        this.tiempoEsperaProm /= this.actualTime; 
        this.tiempoSistemaProm /= this.actualTime;
        this.tiempoColaProm/= this.actualTime;
        this.clientesSistemaProm /= this.actualTime;
        this.clientesColaProm /= this.actualTime;
        this.probabilidadEsperar =((float) this.clientesEsperanCounter/clienteCounter);
        
        String estadisticasServidores="";      
        for(Servidor i: this.servers){
            i.setPorcentajeUtil(tiempoTotalAux );
            estadisticasServidores+= "Porcentaje de utilizacion del servidor " + String.valueOf(i.getNroServidor()) + ": " + String.valueOf(i.getPorcentajeUtil()) + "\n";
            this. utilizacionServidoresPercentage += i.getPorcentajeUtil();
            i.setCostoTotalServidor(i.getCostoServidor() * tiempoTotalAux);
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
           clienteCounter,
           String.format("%2.02f", tiempoOciosoServidores) + ' ' + this.unidadTiempoSecundaria,
           String.format("%2.02f", probabilidadEsperaCincoMinutos)     
         );
                 
          new Resultados(this.actualTime, eventos, data, stats ).setVisible(true);
              
        return 1;   
    }           
    
}    

