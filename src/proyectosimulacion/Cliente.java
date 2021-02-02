package proyectosimulacion;

public class Cliente {
    private int nroCliente;
    private int tiempoEntreLlegada;
    private int tiempoServicio;
    private int tiempoLlegada;
    private int tiempoSalida;
    private int tiempoCola;
    private int tiempoLlegadaServidor;

    public Cliente(int nroCliente, int tiempoLlegada) {
        this.nroCliente = nroCliente;
        this.tiempoEntreLlegada = 0;
        this.tiempoServicio = 0;
        this.tiempoLlegada = tiempoLlegada;
        this.tiempoSalida = 0;
        this.tiempoCola = 0;
        this.tiempoLlegadaServidor =0;
    }    

    public int getTiempoLlegadaServidor() {
        return tiempoLlegadaServidor;
    }

    public void setTiempoLlegadaServidor(int tiempoLlegadaServidor) {
        this.tiempoLlegadaServidor = tiempoLlegadaServidor;
    }

    public int getNroCliente() {
        return nroCliente;
    }

    public int getTiempoCola() {
        return tiempoCola;
    }

    public void setTiempoCola(int tiempoCola) {
        this.tiempoCola = tiempoCola;
    }

    public void setNroCliente(int nroCliente) {
        this.nroCliente = nroCliente;
    }

    public int getTiempoEntreLlegada() {
        return tiempoEntreLlegada;
    }

    public void setTiempoEntreLlegada(int tiempoEntreLlegada) {
        this.tiempoEntreLlegada = tiempoEntreLlegada;
    }

    public int getTiempoServicio() {
        return tiempoServicio;
    }

    public void setTiempoServicio(int tiempoServicio) {
        this.tiempoServicio = tiempoServicio;
    }   

    public int getTiempoLlegada() {
        return tiempoLlegada;
    }

    public void setTiempoLlegada(int tiempoLlegada) {
        this.tiempoLlegada = tiempoLlegada;
    }

    public int getTiempoSalida() {
        return tiempoSalida;
    }

    public void setTiempoSalida(int tiempoSalida) {
        this.tiempoSalida = tiempoSalida;
    }
        
}
