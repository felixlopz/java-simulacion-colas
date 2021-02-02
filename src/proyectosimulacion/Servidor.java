package proyectosimulacion;

public class Servidor {
    public int idCliente;
    public int dt;
    public boolean ss;
    public float costo;
    
    
    private int nroServidor;
    private int serverStatus;
    private float porcentajeUtil;
    private float costoServidor;
    private float costoTotalServidor;
    private int tiempoUtil;
    private Cliente atendiendo;
    
    public Servidor(float costo){
        this.costo = costo;
        ss = false;
        idCliente = 0;
        dt = 9999;
    }
    
    public Servidor(int nroServidor,float costoServidor) {
        this.nroServidor = nroServidor;
        this.serverStatus = 0;
        this.porcentajeUtil = 0;
        this.costoServidor = costoServidor;
        this.costoTotalServidor = 0;
        this.tiempoUtil = 0;
        this.atendiendo = null;
        dt = 9999;
    }
    
    
    public int getDt(){
        return dt;
    }
    
    public void setDt(int dt){
        this.dt = dt;
    }
    
    public float getCosto(){
        return costo;
    }
    
    public void setCosto(int costo){
        this.costo = costo;
    }
    
    public boolean estaOcupado(){
        return ss;
    }
    
    public void agregarCliente(int c){
        idCliente = c;
        ss = true;
    }
    
    public int getidCliente(){
        return idCliente;
    }
    
    public void removerCliente(){
        idCliente = 0;
        ss = false;
        dt = 9999;
    }
    
    public float getPorcentajeUtil() {
        return porcentajeUtil;
    }

    public void setPorcentajeUtil(int tiempoMax){
        porcentajeUtil = 100*tiempoUtil/tiempoMax;
    }    

    public int getTiempoUtil() {
        return tiempoUtil;
    }

    public void setTiempoUtil(int tiempoUtil) {
        this.tiempoUtil += tiempoUtil;
    }

    public float getCostoTotalServidor() {
        return costoTotalServidor;
    }

    public void setCostoTotalServidor(float costoTotalServidor) {
        this.costoTotalServidor = costoTotalServidor;
    }

    public int getNroServidor() {
        return nroServidor;
    }

    public void setNroServidor(int nroServidor) {
        this.nroServidor = nroServidor;
    }

    public int getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(int serverStatus) {
        this.serverStatus = serverStatus;
    }

    public float getCostoServidor() {
        return costoServidor;
    }

    public void setCostoServidor(float costoServidor) {
        this.costoServidor = costoServidor;
    }

    public Cliente getAtendiendo() {
        return atendiendo;
    }

    public void setAtendiendo(Cliente atendiendo) {
        this.atendiendo = atendiendo;
    }
    
}
