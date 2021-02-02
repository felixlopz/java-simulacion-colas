package proyectosimulacion;

import java.util.ArrayList;
import java.util.List;

import proyectosimulacion.ResultadosData;
import proyectosimulacion.gui.EstadisticasServidor;
import proyectosimulacion.gui.Resultados;
import proyectosimulacion.gui.MostrarTablaEventos;

public class Simulacion {
    private int tiempoSimulacion;
    private int tiempoMax;
    private int at;
    private int dt;
    private int numeroEvento;
    private String tipoEvento;
    private int contadorCliente;
    private int numeroCliente;
    private int clientesNoEsperan;
    private int clientesEsperan;
    private int clientesNoAtendidos;
    private float probabilidadEsperar;
    private float costoEspera;
    private float porcentajeUtilGeneral;
    private float tiempoPromedioSistema;
    private float tiempoPromedioCola;
    private float tiempoPromedioEspera;
    private float clientesPromedioSistema;
    private float clientesPromedioCola;
    private float cantidadMaxSistema;
    private int tiempoMaxSimulacion;
    private String unidadTiempo1;
    private String unidadTiempo2;
    private List<Servidor> servidores;
    private List<Cliente> cola;
    private ArrayList<int[][]> dias;
    private int[][] tiemposServicio;
    private int[][] servidorArchivo;
    //private Output output; Busca el output
    private String numAleatorioTELL;
    private String numAleatorioST;
    private String tell;
    private String st;
    private int numeroDeServidores;

    public Simulacion(int tiempoMaxSimulacion,int tiempoMax,String unidadTiempo1,String unidadTiempo2,float costoEspera,int cantidadServidores, int costoServidores,ArrayList<int[][]> dias,int[][] tiemposServicio,int cantidadMaxSistema) {
        this.tiempoSimulacion = 0;
        this.at = 0;
        this.dt = 9999;
        this.numeroEvento = 0;
        this.tipoEvento ="";
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
        this.cantidadMaxSistema = cantidadMaxSistema;
        this.costoEspera = costoEspera;
        this.unidadTiempo1 = unidadTiempo1; 
        this.unidadTiempo2 = unidadTiempo2;
        this.cola = new ArrayList<Cliente>();
        this.servidores = new ArrayList<Servidor>();
        this.tiempoMax = tiempoMax;
        this.tiempoMaxSimulacion = tiempoMaxSimulacion;
        this.numAleatorioTELL ="";
        this.numAleatorioST ="";
        this.numeroDeServidores = cantidadServidores;
        this.st="";
        this.tell="";
        switch (unidadTiempo1) {
            case "semana    ":  
                this.tiempoMaxSimulacion*=7;
                break;
            case "mes":
                this.tiempoMaxSimulacion*=30;
                break;
            case "ano":
                this.tiempoMaxSimulacion*=360;
                break;
            default:
                break;
        }
        
        //this.output = new Output();
        for (int i=0;i<cantidadServidores;i++) {
            Servidor servidor = new Servidor(i, costoServidores);
            this.servidores.add(servidor);
        }        
        this.dias= dias;
        this.tiemposServicio= tiemposServicio;
             
    }
    
    //INICIO SETTERS AND GETTERS//
    public int getTiempoSimulacion() {
        return tiempoSimulacion;
    }
    
    public void setTiempoSimulacion(int tiempoSimulacion) {
        this.tiempoSimulacion = tiempoSimulacion;
    }

    public int getClientesNoAtendidos() {
        return clientesNoAtendidos;
    }

    public void setClientesNoAtendidos(int clientesNoAtendidos) {
        this.clientesNoAtendidos = clientesNoAtendidos;
    }

    public int getTiempoMaxSimulacion() {
        return tiempoMaxSimulacion;
    }

    public void setTiempoMaxSimulacion(int tiempoMaxSimulacion) {
        this.tiempoMaxSimulacion = tiempoMaxSimulacion;
    }

    public float getCantidadMaxSistema() {
        return cantidadMaxSistema;
    }

    public void setCantidadMaxSistema(float cantidadMaxSistema) {
        this.cantidadMaxSistema = cantidadMaxSistema;
    }
        
    public float getClientesPromedioSistema() {
        return clientesPromedioSistema;
    }

    public void setClientesPromedioSistema(float clientesPromedioSistema) {
        this.clientesPromedioSistema = clientesPromedioSistema;
    }

    public float getClientesPromedioCola() {
        return clientesPromedioCola;
    }

    public void setClientesPromedioCola(float clientesPromedioCola) {
        this.clientesPromedioCola = clientesPromedioCola;
    } 

    public float getTiempoPromedioEspera() {
        return tiempoPromedioEspera;
    }

    public void setTiempoPromedioEspera(float tiempoPromedioEspera) {
        this.tiempoPromedioEspera = tiempoPromedioEspera;
    }

    public float getTiempoPromedioSistema() {
        return tiempoPromedioSistema;
    }

    public void setTiempoPromedioSistema(float tiempoPromedioSistema) {
        this.tiempoPromedioSistema = tiempoPromedioSistema;
    }

    public float getTiempoPromedioCola() {
        return tiempoPromedioCola;
    }

    public void setTiempoPromedioCola(float tiempoPromedioCola) {
        this.tiempoPromedioCola = tiempoPromedioCola;
    }

    public float getPorcentajeUtilGeneral() {
        return porcentajeUtilGeneral;
    }

    public void setPorcentajeUtilGeneral(float porcentajeUtilGeneral) {
        this.porcentajeUtilGeneral = porcentajeUtilGeneral;
    }

    public int getTiempoMax() {
        return tiempoMax;
    }

    public void setTiempoMax(int tiempoMax) {
        this.tiempoMax = tiempoMax;
    }

    public int getAt() {
        return at;
    }

    public void setAt(int at) {
        this.at = at;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public int getNumeroEvento() {
        return numeroEvento;
    }

    public void setNumeroEvento(int numeroEvento) {
        this.numeroEvento = numeroEvento;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public float getProbabilidadEsperar() {
        return probabilidadEsperar;
    }

    public void setProbabilidadEsperar(float probabilidadEsperar) {
        this.probabilidadEsperar = probabilidadEsperar;
    }

    public int getContadorCliente() {
        return contadorCliente;
    }

    public void setContadorCliente(int contadorCliente) {
        this.contadorCliente = contadorCliente;
    }

    public int getNumeroCliente() {
        return numeroCliente;
    }

    public void setNumeroCliente(int numeroCliente) {
        this.numeroCliente = numeroCliente;
    }

    public int getClientesNoEsperan() {
        return clientesNoEsperan;
    }

    public void setClientesNoEsperan(int clientesNoEsperan) {
        this.clientesNoEsperan = clientesNoEsperan;
    }

    public int getClientesEsperan() {
        return clientesEsperan;
    }

    public void setClientesEsperan(int clientesEsperan) {
        this.clientesEsperan = clientesEsperan;
    }

    public float getCostoEspera() {
        return costoEspera;
    }

    public void setCostoEspera(float costoEspera) {
        this.costoEspera = costoEspera;
    }

    public String getUnidadTiempo1() {
        return unidadTiempo1;
    }

    public void setUnidadTiempo1(String unidadTiempo1) {
        this.unidadTiempo1 = unidadTiempo1;
    }

    public String getUnidadTiempo2() {
        return unidadTiempo2;
    }

    public void setUnidadTiempo2(String unidadTiempo2) {
        this.unidadTiempo2 = unidadTiempo2;
    }

    public List<Servidor> getServidores() {
        return servidores;
    }

    public void setServidores(List<Servidor> servidores) {
        this.servidores = servidores;
    }

    public List<Cliente> getCola() {
        return cola;
    }

    public void setCola(List<Cliente> cola) {
        this.cola = cola;
    }
    
    //FINAL SETTERS AND GETTERS//
    
    public int servidoresOcupados(List<Servidor> servidores){ // si todos los servidores estan ocupados devuelve 1
        int ocupados = 0;
        for (Servidor i:servidores){
            if (i.getServerStatus() == 1){
                ocupados++;
            }
        }
        if (ocupados == servidores.size()){
            return 1;
        }
        return 0;
    }
    
    public int servidoresDesocupados(List<Servidor> servidores){ //si todos los servidores estan libres devuelve 1
        int desocupados = 0;
        for (Servidor i:servidores){
            if (i.getServerStatus() == 0){
                desocupados++;
            }
        }
        if (desocupados == servidores.size()){
            return 1;
        }
        return 0;
    }
    
    public int generarNumeroAleatorio(){             //Genera un numero aleatorio
        int numero = (int) (Math.random() * 99);
        return numero;
    }
    
    public int elegirServidor(List<Servidor> servidores){     //si hay algun servidor desocupado devuelve el numero de ese servidor
        if (servidoresOcupados(servidores)==1){
            return -1;
        }
        while(true){
            for (Servidor i:servidores){
                if (i.getServerStatus()==0){
                    return i.getNroServidor();
                }
            }        
        }
    }
    
    public int generarTiempoServicio(){
        int numAleatorio =0;
        int limInf = 0;
        int limSup = this.tiemposServicio[0][1]-1;
        numAleatorio = generarNumeroAleatorio();       
        for(int i=0; i<this.tiemposServicio.length ; i++){
            if(numAleatorio<=limSup && numAleatorio>=limInf){
                this.numAleatorioST= Integer.toString(numAleatorio);
                return this.tiemposServicio[i][0]; 
            }
            if (i!=tiemposServicio.length-1){
                limInf=limSup+1; 
                limSup+=this.tiemposServicio[i+1][1];
            }
        }
        return 0;
    }
 
    public int generarTiempoEntreLlegadas(int diaActual){
        int numAleatorio =0;
        int limInf = 0;
        int limSup = 0;
        numAleatorio = generarNumeroAleatorio();        
        
        for(int i=0;i<this.dias.get(diaActual).length;i++){
            limSup += this.dias.get(diaActual)[i][1]-1; 
            if(numAleatorio<=limSup && numAleatorio>=limInf){
                this.numAleatorioTELL= Integer.toString(numAleatorio);
                return this.dias.get(diaActual)[i][0]; 
            }
            limInf = limSup+1;
        }        
        return 0;
    }
    
    public int Simular(){        
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
        
        while (tiempoSimulacion<tiempoMaxSimulacion){           
            while (sigCiclo == 0){
                String stringServidores = "";
                salida=0;
                menorDT=9999;
                tiempoAnterior=tiempoSim;           
                cEnSistema=clientesEnSistema;
                lCola=longitudCola;
                numeroEvento++;                
                if (tiempoSim<tiempoMax && at<dt){
                    if (clientesEnSistema<cantidadMaxSistema){
                        tipoEvento="Llegada";
                        tiempoSim=at;
                        contadorCliente++;
                        clientesEnSistema++;
                        numeroCliente=contadorCliente;
                        Cliente cliente = new Cliente(contadorCliente,tiempoSim);
                        servidorEscogido=this.elegirServidor(servidores);                        
                        if (servidorEscogido!=-1){            
                            servidores.get(servidorEscogido).setServerStatus(1);
                            servidores.get(servidorEscogido).setAtendiendo(cliente);
                            clientesNoEsperan++;
                            cliente.setTiempoServicio(this.generarTiempoServicio());
                            this.st= Integer.toString(cliente.getTiempoServicio());
                            dt=tiempoSim+cliente.getTiempoServicio();
                            cliente.setTiempoSalida(dt);
                            cliente.setTiempoEntreLlegada(this.generarTiempoEntreLlegadas(diaActual));
                            this.tell=Integer.toString(cliente.getTiempoEntreLlegada());
                            at=tiempoSim+cliente.getTiempoEntreLlegada();
                            cliente.setTiempoLlegadaServidor(tiempoSim);
                            for(Servidor i:servidores){
                                if (i.getAtendiendo()!= null){    
                                    if(i.getAtendiendo().getTiempoSalida()<menorDT){
                                        menorDT=i.getAtendiendo().getTiempoSalida();
                                        i.setDt(i.getAtendiendo().getTiempoSalida());
                                    }
                                }
                            }
                            dt=menorDT;                    
                        }
                        else{
                            cliente.setTiempoEntreLlegada(this.generarTiempoEntreLlegadas(diaActual));
                            this.tell=Integer.toString(cliente.getTiempoEntreLlegada());
                            at=tiempoSim+cliente.getTiempoEntreLlegada();
                            cola.add(cliente);
                            longitudCola++;
                            clientesEsperan++;  
                        }
                    }
                    else{
                        at+=this.generarTiempoEntreLlegadas(diaActual);
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
                        tiempoSim=dt;
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
                            dt=tiempoSim+cola.get(0).getTiempoServicio();
                            cola.get(0).setTiempoSalida(dt);
                            servidores.get(servidorLiberado).setAtendiendo(cola.get(0));
                            servidores.get(servidorLiberado).setServerStatus(1);
                            cola.get(0).setTiempoLlegadaServidor(tiempoSim);
                            cola.remove(0);
                        }
                        else{
                            menorDT=9999;
                            if (servidoresDesocupados(servidores)==1){
                                dt=9999;
                            }
                            else{
                                for(Servidor i:servidores){
                                    if (i.getAtendiendo()!= null){    
                                        if(i.getAtendiendo().getTiempoSalida()<menorDT){
                                            menorDT=i.getAtendiendo().getTiempoSalida();
                                        }
                                    }
                                }
                                dt=menorDT;
                            }
                        }
                        if (tiempoSim>=tiempoMax && clientesEnSistema==0){
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
                for(Servidor i : servidores){
                    stringServidores += "S" + Integer.toString(i.getNroServidor()+1) + ": " + Integer.toString(i.getServerStatus()) + " ";
                    
                }
                
                eventos.agregarFila(numeroEvento, tipoEvento, numeroCliente, tiempoSim, numAleatorioTELL,tell,numAleatorioST,st, longitudCola, at, servidores);
                
                //output.setVisible(true);
                //output.agregarFila(numeroEvento, tipoEvento, numeroCliente, tiempoSim,stringServidores,longitudCola,numAleatorioTELL,tell,numAleatorioST,st,at, dt);               
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
            at = 0;
            dt = 9999;
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
        
       
        // Imprime Datos 
        
        /*output.imprimirDatosEntrada(this.tiempoSimulacion, this.tiempoMax, unidadTiempo1, unidadTiempo2, costoEspera,this.dias.get(diaActual),tiemposServicio,this.cantidadMaxSistema);
        output.imprimirEstadisticas(clientesNoEsperan, clientesNoAtendidos, probabilidadEsperar, clientesPromedioSistema, clientesPromedioCola, 
                tiempoPromedioSistema, tiempoPromedioCola, tiempoPromedioEspera, porcentajeUtilGeneral,contCliente,tiempoOciosoServidores,probabilidadEsperaCincoMinutos);
        output.imprimirEstadisticasServidores(estadisticasServidores);
        output.cerrarWriter(); */
        
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
           String.format("%2.02f", tiempoOciosoServidores) + ' ' + unidadTiempo2,
           String.format("%2.02f", probabilidadEsperaCincoMinutos)     
         );
                 
          new Resultados(tiempoSimulacion, eventos, data, stats ).setVisible(true);
              
        return 1;   
    }           
    
}    

