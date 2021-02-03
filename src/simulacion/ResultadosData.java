/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulacion;

/**
 *
 * @author FLX
 */
public class ResultadosData {
    
    int clientesNoEsperanCount;
    int clientesNoAtendidosCount;
    String probabilidadEspera;
    String clientesPromedioSistema;
    String clientesPromedioCola;
    String tiempoPromedioSistema;
    String tiempoPromedioCola;
    String tiempoPromedioEspera;
    String porcentajeUtilGeneral;
    int contCliente;
    String tiempoOciosoServidores;
    String probabilidadEsperaCincoMinutos; 

    public ResultadosData(int clientesNoEsperanCount, int clientesNoAtendidosCount, String probabilidadEspera, String clientesPromedioSistema, String clientesPromedioCola, String tiempoPromedioSistema, String tiempoPromedioCola, String tiempoPromedioEspera, String porcentajeUtilGeneral, int contCliente, String tiempoOciosoServidores, String probabilidadEsperaCincoMinutos) {
        this.clientesNoEsperanCount = clientesNoEsperanCount;
        this.clientesNoAtendidosCount = clientesNoAtendidosCount;
        this.probabilidadEspera = probabilidadEspera;
        this.clientesPromedioSistema = clientesPromedioSistema;
        this.clientesPromedioCola = clientesPromedioCola;
        this.tiempoPromedioSistema = tiempoPromedioSistema;
        this.tiempoPromedioCola = tiempoPromedioCola;
        this.tiempoPromedioEspera = tiempoPromedioEspera;
        this.porcentajeUtilGeneral = porcentajeUtilGeneral;
        this.contCliente = contCliente;
        this.tiempoOciosoServidores = tiempoOciosoServidores;
        this.probabilidadEsperaCincoMinutos = probabilidadEsperaCincoMinutos;
    }

    public int getClientesNoEsperanCount() {
        return clientesNoEsperanCount;
    }

    public int getClientesNoAtendidosCount() {
        return clientesNoAtendidosCount;
    }

    public String getProbabilidadEspera() {
        return probabilidadEspera;
    }

    public String getClientesPromedioSistema() {
        return clientesPromedioSistema;
    }

    public String getClientesPromedioCola() {
        return clientesPromedioCola;
    }

    public String getTiempoPromedioSistema() {
        return tiempoPromedioSistema;
    }

    public String getTiempoPromedioCola() {
        return tiempoPromedioCola;
    }

    public String getTiempoPromedioEspera() {
        return tiempoPromedioEspera;
    }

    public String getPorcentajeUtilGeneral() {
        return porcentajeUtilGeneral;
    }

    public int getContCliente() {
        return contCliente;
    }

    public String getTiempoOciosoServidores() {
        return tiempoOciosoServidores;
    }

    public String getProbabilidadEsperaCincoMinutos() {
        return probabilidadEsperaCincoMinutos;
    }


   

   

   
}
