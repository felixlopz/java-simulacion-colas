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
        
        
        MostrarTablaEventos eventos = new MostrarTablaEventos (this.canitdadServidores );
        
        while (actualTime<this.tiempoTotal){           
            while (sigCiclo == 0){
                salida=0;
                menorDT=9999;
                tiempoAnterior=tiempoSim;           
                cEnSistema=clientesEnSistema;
                lCola=longitudCola;
                eventNumber++;                
                if ( tiempoSim<tiempoSecundario && AT < DT ){
                    if (clientesEnSistema<capacidadMaxima){
                        eventType="Llegada";
                        tiempoSim=AT;
                        clienteCounter++;
                        clientesEnSistema++;
                        clienteNumber=clienteCounter;
                        Cliente cliente = new Cliente(clienteCounter,tiempoSim);
                        servidorEscogido=this.selectServer(servers);                        
                        if (servidorEscogido!=-1){            
                            servers.get(servidorEscogido).setServerStatus(1);
                            servers.get(servidorEscogido).setAtendiendo(cliente);
                            clientesNoEsperanCounter++;
                            cliente.setTiempoServicio(this.generarTiempoServicio());
                            this.TS= Integer.toString(cliente.getTiempoServicio());
                            DT=tiempoSim+cliente.getTiempoServicio();
                            cliente.setTiempoSalida(DT);
                            cliente.setTiempoEntreLlegada(this.generarTiempoEntreLlegadas(diaActual));
                            this.TE=Integer.toString(cliente.getTiempoEntreLlegada());
                            AT=tiempoSim+cliente.getTiempoEntreLlegada();
                            cliente.setTiempoLlegadaServidor(tiempoSim);
                            for(Servidor i:servers){
                                if (i.getAtendiendo()!= null){    
                                    if(i.getAtendiendo().getTiempoSalida()<menorDT){
                                        menorDT=i.getAtendiendo().getTiempoSalida();
                                        i.setDt(i.getAtendiendo().getTiempoSalida());
                                    }
                                }
                            }
                            DT=menorDT;                    
                        }
                        else{
                            cliente.setTiempoEntreLlegada(this.generarTiempoEntreLlegadas(diaActual));
                            this.TE=Integer.toString(cliente.getTiempoEntreLlegada());
                            AT=tiempoSim+cliente.getTiempoEntreLlegada();
                            queue.add(cliente);
                            longitudCola++;
                            clientesEsperanCounter++;  
                        }
                    }
                    else{
                        AT+=this.generarTiempoEntreLlegadas(diaActual);
                        clientesNoAtendidosCounter++;
                    }
                }
                else{
                    if(clientesEnSistema>0){
                        eventType="Salida";
                        TS="";
                        TE="";
                        numeroAleatorioTS="";
                        numeroAleatorioTE="";
                        tiempoSim=DT;
                        clientesEnSistema--;
                        for(Servidor i:servers){
                            if (i.getAtendiendo()!= null){
                                salida=1;
                                if(i.getAtendiendo().getTiempoSalida()<menorDT){
                                    menorDT=i.getAtendiendo().getTiempoSalida();
                                    servidorLiberado=i.getNroServidor();
                                }
                            }
                        }
                        if (salida==1){
                            servers.get(servidorLiberado).setTiempoUtil(tiempoSim-
                            servers.get(servidorLiberado).getAtendiendo().getTiempoLlegadaServidor());
                            servers.get(servidorLiberado).getAtendiendo().setTiempoSalida(tiempoSim);
                            clienteNumber=servers.get(servidorLiberado).getAtendiendo().getNroCliente();
                            tPromedioSistema += servers.get(servidorLiberado).getAtendiendo().getTiempoSalida()-
                            servers.get(servidorLiberado).getAtendiendo().getTiempoLlegada();
                            tPromedioCola += servers.get(servidorLiberado).getAtendiendo().getTiempoCola();
                            servers.get(servidorLiberado).setServerStatus(0);
                            servers.get(servidorLiberado).setAtendiendo(null);
                        }
                        if (longitudCola > 0){
                            longitudCola--;
                            queue.get(0).setTiempoCola(tiempoSim - queue.get(0).getTiempoLlegada());
                            if (queue.get(0).getTiempoCola() > 5)
                                clientesCincoMinutos++;
                            queue.get(0).setTiempoServicio(this.generarTiempoServicio());
                            this.TS= Integer.toString(queue.get(0).getTiempoServicio());
                            DT=tiempoSim+queue.get(0).getTiempoServicio();
                            queue.get(0).setTiempoSalida(DT);
                            servers.get(servidorLiberado).setAtendiendo(queue.get(0));
                            servers.get(servidorLiberado).setServerStatus(1);
                            queue.get(0).setTiempoLlegadaServidor(tiempoSim);
                            queue.remove(0);
                        }
                        else{
                            menorDT=9999;
                            if (areServersFree(servers)){
                                DT=9999;
                            }
                            else{
                                for(Servidor i:servers){
                                    if (i.getAtendiendo()!= null){    
                                        if(i.getAtendiendo().getTiempoSalida()<menorDT){
                                            menorDT=i.getAtendiendo().getTiempoSalida();
                                        }
                                    }
                                }
                                DT=menorDT;
                            }
                        }
                        if (tiempoSim>=tiempoSecundario && clientesEnSistema==0){
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
                
                eventos.agregarFila(eventNumber, 
                    eventType, 
                    clienteNumber, 
                    tiempoSim, 
                    numeroAleatorioTE,
                    TE,numeroAleatorioTS,
                    TS, 
                    longitudCola, 
                    AT, 
                    servers
                );                
            }            
            actualTime++;
            contCliente+=clienteCounter;
            clientesEnSistema = 0;
            sigCiclo = 0;
            if (clientesEsperanCounter != 0){
                tPromedioEspera=tPromedioCola/clientesEsperanCounter;
            }
            else{
                tPromedioEspera = 0;
            }
            
            tPromedioSistema/=clienteCounter;
            tPromedioCola/=clienteCounter;
            cPromedioSistema/=tiempoSim;
            cPromedioCola/=tiempoSim;
            tiempoTotal+=tiempoSim;         
            tiempoEsperaProm+=tPromedioEspera; 
            tiempoSistemaProm+=tPromedioSistema;
            tiempoColaProm+=tPromedioCola;
            clientesSistemaProm+=cPromedioSistema;
            clientesColaProm+=cPromedioCola;            
            tPromedioEspera = 0;
            tPromedioSistema = 0;
            tPromedioCola = 0;
            cPromedioSistema = 0;
            cPromedioCola = 0;
            clienteCounter = 0;
            tiempoSim = 0;
            AT = 0;
            DT = 9999;
            eventNumber = 0;
            longitudCola = 0;
            
            eventos.agregarFilaSeparadora();
        }
        float tiempoOciosoServidores = 0;
            for (Servidor i:servers){                
                tiempoOciosoServidores += tiempoTotal - i.getTiempoUtil();
            }
        tiempoOciosoServidores /= this.servers.size();
        float auxiliarDivision = contCliente;
        float probabilidadEsperaCincoMinutos = clientesCincoMinutos/auxiliarDivision;
        tiempoEsperaProm/=actualTime; 
        tiempoSistemaProm/=actualTime;
        tiempoColaProm/=actualTime;
        clientesSistemaProm/=actualTime;
        clientesColaProm/=actualTime;
        probabilidadEsperar=((float)clientesEsperanCounter/contCliente);
        String estadisticasServidores="";      
        for(Servidor i:servers){
            i.setPorcentajeUtil(tiempoTotal);
            estadisticasServidores+= "Porcentaje de utilizacion del servidor " + String.valueOf(i.getNroServidor()) + ": " + String.valueOf(i.getPorcentajeUtil()) + "\n";
            utilizacionServidoresPercentage+=i.getPorcentajeUtil();
            i.setCostoTotalServidor(i.getCostoServidor()*tiempoTotal);
            estadisticasServidores+="Costo del servidor " + String.valueOf(i.getNroServidor()) + ": " + i.getCostoTotalServidor() + "\n";
        }                         
        utilizacionServidoresPercentage/=servers.size();
           
        EstadisticasServidor stats = new EstadisticasServidor(servers);
        
        ResultadosData data = new ResultadosData(
           clientesNoEsperanCounter,
           clientesNoAtendidosCounter,
           String.format("%2.02f", probabilidadEsperar),
           String.format("%2.02f", clientesSistemaProm),
           String.format("%2.02f", clientesColaProm),
           String.format("%2.02f", tiempoSistemaProm),
           String.format("%2.02f", tiempoColaProm),
           String.format("%2.02f", tiempoEsperaProm), // Segunda entrega
String.format("%2.02f", utilizacionServidoresPercentage),
           contCliente,
           String.format("%2.02f", tiempoOciosoServidores) + ' ' + unidadTiempoSecundaria,
           String.format("%2.02f", probabilidadEsperaCincoMinutos)     
         );
                 
          new Resultados(actualTime, eventos, data, stats ).setVisible(true);
              
        return 1;   
    }           
    
}    

