package proyectosimulacion;

import proyectosimulacion.ResultadosData;
import proyectosimulacion.gui.EstadisticasServidor;
import proyectosimulacion.gui.Resultados;
import proyectosimulacion.gui.MostrarTablaEventos;

import java.util.ArrayList;
import java.util.List;

public class Simulacion {
    private int tiempoSimulacion;
    private int tiempoSecundario;
    private int AT;
    private int DT;
    private int numeroEvento;
    private String tipoEvento;
    private int contadorCliente;
    private int numeroCliente;
    private int clientesNoEsperan;
    private int clientesEsperan;
    private int clientesNoAtendidos;
    private float probabilidadEsperar;
    private float costoEsperaCliente;
    private float porcentajeUtilGeneral;
    private float tiempoPromedioSistema;
    private float tiempoPromedioCola;
    private float tiempoPromedioEspera;
    private float clientesPromedioSistema;
    private float clientesPromedioCola;
    private float capacidadMaxima;
    private int tiempoTotal;
    private String unidadTiempoTotal;
    private String unidadTiempoSecundaria;
    private List<Servidor> servidores;
    private List<Cliente> cola;
    private ArrayList<int[][]> distribucionLlegadas;
    private int[][] distribucionServicio;
    
    private String numAleatorioTELL;
    private String numAleatorioST;
    private String tell;
    private String st;
    private int numeroDeServidores;
    
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
        this.costoEsperaCliente = costoEsperaCliente;
        
        // Tablas de distribuciones
        this.distribucionLlegadas = distribucionLlegadasArray;
        this.distribucionServicio = distribucionServicio;
        
        initSimulationVariables( cantidadServidores, costoServidor );
       
    }
    
    public void initSimulationVariables (int cantidadServidores, int costoServidor){
        
         // Inicializacion para la simulacion
        this.cola = new ArrayList();
        this.servidores = new ArrayList();
        this.numAleatorioTELL = "";
        this.numAleatorioST = "";
        this.numeroDeServidores = cantidadServidores;
        this.st= "";
        this.tell= "";
        this.tiempoSimulacion = 0;
        this.AT = 0;
        this.DT = 9999;
        this.numeroEvento = 0;
        this.tipoEvento = "";
        this.contadorCliente = 0;
        this.clientesNoEsperan = 0;
        this.clientesEsperan = 0;
        this.numeroCliente = 0;
        this.probabilidadEsperar = 0;
        this.porcentajeUtilGeneral = 0;
        this.tiempoPromedioSistema = 0;
        this.tiempoPromedioCola = 0;
        this.tiempoPromedioEspera = 0;
        this.clientesPromedioCola = 0;
        this.clientesPromedioSistema = 0;
        this.clientesNoAtendidos = 0;
    
          // Inicializacion lista de servidores
        for (int i=0;i<cantidadServidores;i++) {
            Servidor servidor = new Servidor(i, costoServidor);
            this.servidores.add(servidor);
        }     
        
        // Ajuste del tiempoTotal * unidad de tiempo
        switch (unidadTiempoTotal) {
            case "week":  
                this.tiempoTotal*=5;
                break;
            case "month":
                this.tiempoTotal*=20;
                break;
            case "year":
                this.tiempoTotal*=240;
                break;
            default:
                break;
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
                if ( server.getServerStatus() == 0 ){
                    return server.getNroServidor();
                }
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
                this.numAleatorioST= Integer.toString(numAleatorio);
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
                this.numAleatorioTELL= Integer.toString(numAleatorio);
                return this.distribucionLlegadas.get(diaActual)[i][0]; 
            }
            limInf = limSup+1;
        }        
        return 0;
    }
    
    public int iniciar(){        
        int salida=0;
        int menorDT=9999;
        int servidorLiberado=9999;
        int clientesEnSistema=0;
        int servidorEscogido=0;
        int longitudCola=0;
        int tiempoAnterior=0;
        int tiempoSim = 0;
        int contCliente = 0;
        float tPromedioEspera = 0;
        float tPromedioCola = 0;
        float tPromedioSistema = 0;
        float cPromedioSistema = 0;
        float cPromedioCola = 0;
        int tiempoTotal = 0;        
        int sigCiclo = 0;
        int cEnSistema = 0;
        int lCola = 0;
        int diaActual = 0;
        float clientesCincoMinutos =0;
        
        
        MostrarTablaEventos eventos = new MostrarTablaEventos (this.numeroDeServidores );
        
        while (tiempoSimulacion<this.tiempoTotal){           
            while (sigCiclo == 0){
                salida=0;
                menorDT=9999;
                tiempoAnterior=tiempoSim;           
                cEnSistema=clientesEnSistema;
                lCola=longitudCola;
                numeroEvento++;                
                if ( tiempoSim<tiempoSecundario && AT < DT ){
                    if (clientesEnSistema<capacidadMaxima){
                        tipoEvento="Llegada";
                        tiempoSim=AT;
                        contadorCliente++;
                        clientesEnSistema++;
                        numeroCliente=contadorCliente;
                        Cliente cliente = new Cliente(contadorCliente,tiempoSim);
                        servidorEscogido=this.selectServer(servidores);                        
                        if (servidorEscogido!=-1){            
                            servidores.get(servidorEscogido).setServerStatus(1);
                            servidores.get(servidorEscogido).setAtendiendo(cliente);
                            clientesNoEsperan++;
                            cliente.setTiempoServicio(this.generarTiempoServicio());
                            this.st= Integer.toString(cliente.getTiempoServicio());
                            DT=tiempoSim+cliente.getTiempoServicio();
                            cliente.setTiempoSalida(DT);
                            cliente.setTiempoEntreLlegada(this.generarTiempoEntreLlegadas(diaActual));
                            this.tell=Integer.toString(cliente.getTiempoEntreLlegada());
                            AT=tiempoSim+cliente.getTiempoEntreLlegada();
                            cliente.setTiempoLlegadaServidor(tiempoSim);
                            for(Servidor i:servidores){
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
                            this.tell=Integer.toString(cliente.getTiempoEntreLlegada());
                            AT=tiempoSim+cliente.getTiempoEntreLlegada();
                            cola.add(cliente);
                            longitudCola++;
                            clientesEsperan++;  
                        }
                    }
                    else{
                        AT+=this.generarTiempoEntreLlegadas(diaActual);
                        clientesNoAtendidos++;
                    }
                }
                else{
                    if(clientesEnSistema>0){
                        tipoEvento="Salida";
                        st="";
                        tell="";
                        numAleatorioST="";
                        numAleatorioTELL="";
                        tiempoSim=DT;
                        clientesEnSistema--;
                        for(Servidor i:servidores){
                            if (i.getAtendiendo()!= null){
                                salida=1;
                                if(i.getAtendiendo().getTiempoSalida()<menorDT){
                                    menorDT=i.getAtendiendo().getTiempoSalida();
                                    servidorLiberado=i.getNroServidor();
                                }
                            }
                        }
                        if (salida==1){
                            servidores.get(servidorLiberado).setTiempoUtil(tiempoSim-
                            servidores.get(servidorLiberado).getAtendiendo().getTiempoLlegadaServidor());
                            servidores.get(servidorLiberado).getAtendiendo().setTiempoSalida(tiempoSim);
                            numeroCliente=servidores.get(servidorLiberado).getAtendiendo().getNroCliente();
                            tPromedioSistema += servidores.get(servidorLiberado).getAtendiendo().getTiempoSalida()-
                            servidores.get(servidorLiberado).getAtendiendo().getTiempoLlegada();
                            tPromedioCola += servidores.get(servidorLiberado).getAtendiendo().getTiempoCola();
                            servidores.get(servidorLiberado).setServerStatus(0);
                            servidores.get(servidorLiberado).setAtendiendo(null);
                        }
                        if (longitudCola>0){
                            longitudCola--;
                            cola.get(0).setTiempoCola(tiempoSim - cola.get(0).getTiempoLlegada());
                            if (cola.get(0).getTiempoCola() > 5)
                                clientesCincoMinutos++;
                            cola.get(0).setTiempoServicio(this.generarTiempoServicio());
                            this.st= Integer.toString(cola.get(0).getTiempoServicio());
                            DT=tiempoSim+cola.get(0).getTiempoServicio();
                            cola.get(0).setTiempoSalida(DT);
                            servidores.get(servidorLiberado).setAtendiendo(cola.get(0));
                            servidores.get(servidorLiberado).setServerStatus(1);
                            cola.get(0).setTiempoLlegadaServidor(tiempoSim);
                            cola.remove(0);
                        }
                        else{
                            menorDT=9999;
                            if (areServersFree(servidores)){
                                DT=9999;
                            }
                            else{
                                for(Servidor i:servidores){
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
                
                eventos.agregarFila(numeroEvento, 
                    tipoEvento, 
                    numeroCliente, 
                    tiempoSim, 
                    numAleatorioTELL,
                    tell,numAleatorioST,
                    st, 
                    longitudCola, 
                    AT, 
                    servidores
                );                
            }            
            tiempoSimulacion++;
            contCliente+=contadorCliente;
            clientesEnSistema = 0;
            sigCiclo = 0;
            if (clientesEsperan != 0){
                tPromedioEspera=tPromedioCola/clientesEsperan;
            }
            else{
                tPromedioEspera = 0;
            }
            
            tPromedioSistema/=contadorCliente;
            tPromedioCola/=contadorCliente;
            cPromedioSistema/=tiempoSim;
            cPromedioCola/=tiempoSim;
            tiempoTotal+=tiempoSim;         
            tiempoPromedioEspera+=tPromedioEspera; 
            tiempoPromedioSistema+=tPromedioSistema;
            tiempoPromedioCola+=tPromedioCola;
            clientesPromedioSistema+=cPromedioSistema;
            clientesPromedioCola+=cPromedioCola;            
            tPromedioEspera = 0;
            tPromedioSistema = 0;
            tPromedioCola = 0;
            cPromedioSistema = 0;
            cPromedioCola = 0;
            contadorCliente = 0;
            tiempoSim = 0;
            AT = 0;
            DT = 9999;
            numeroEvento = 0;
            longitudCola = 0;
            
            eventos.agregarFilaSeparadora();
        }
        float tiempoOciosoServidores = 0;
            for (Servidor i:servidores){                
                tiempoOciosoServidores += tiempoTotal - i.getTiempoUtil();
            }
        tiempoOciosoServidores /= this.servidores.size();
        float auxiliarDivision = contCliente;
        float probabilidadEsperaCincoMinutos = clientesCincoMinutos/auxiliarDivision;
        tiempoPromedioEspera/=tiempoSimulacion; 
        tiempoPromedioSistema/=tiempoSimulacion;
        tiempoPromedioCola/=tiempoSimulacion;
        clientesPromedioSistema/=tiempoSimulacion;
        clientesPromedioCola/=tiempoSimulacion;
        probabilidadEsperar=((float)clientesEsperan/contCliente);
        String estadisticasServidores="";      
        for(Servidor i:servidores){
            i.setPorcentajeUtil(tiempoTotal);
            estadisticasServidores+= "Porcentaje de utilizacion del servidor " + String.valueOf(i.getNroServidor()) + ": " + String.valueOf(i.getPorcentajeUtil()) + "\n";
            porcentajeUtilGeneral+=i.getPorcentajeUtil();
            i.setCostoTotalServidor(i.getCostoServidor()*tiempoTotal);
            estadisticasServidores+="Costo del servidor " + String.valueOf(i.getNroServidor()) + ": " + i.getCostoTotalServidor() + "\n";
        }                         
        porcentajeUtilGeneral/=servidores.size();
           
        EstadisticasServidor stats = new EstadisticasServidor(servidores);
        
        ResultadosData data = new ResultadosData(
           clientesNoEsperan,
           clientesNoAtendidos,
           String.format("%2.02f", probabilidadEsperar),
           String.format("%2.02f", clientesPromedioSistema),
           String.format("%2.02f", clientesPromedioCola),
           String.format("%2.02f", tiempoPromedioSistema),
           String.format("%2.02f", tiempoPromedioCola),
           String.format("%2.02f", tiempoPromedioEspera), // Segunda entrega
           String.format("%2.02f", porcentajeUtilGeneral),
           contCliente,
           String.format("%2.02f", tiempoOciosoServidores) + ' ' + unidadTiempoSecundaria,
           String.format("%2.02f", probabilidadEsperaCincoMinutos)     
         );
                 
          new Resultados(tiempoSimulacion, eventos, data, stats ).setVisible(true);
              
        return 1;   
    }           
    
}    

