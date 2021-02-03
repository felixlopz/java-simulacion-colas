package simulacion;

public class Servidor {
    private int number;
    private int status;
    private float utilPercentage;
    public int idCliente;
    public int dt;
    public boolean ss;
    public float costo;
    private float cost;
    private float totalCost;
    private int utilTime;
    private Cliente client;
    
    public Servidor(float costo){
        this.costo = costo;
        ss = false;
        idCliente = 0;
        dt = 9999;
    }
    
    public Servidor(int nroServidor,float costoServidor) {
        this.number = nroServidor;
        this.status = 0;
        this.utilPercentage = 0;
        this.cost = costoServidor;
        this.totalCost = 0;
        this.utilTime = 0;
        this.client = null;
        dt = 999;
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
        dt = 999;
    }
    
    public float getUtilPercentage() {
        return utilPercentage;
    }

    public void setPorcentajeUtil(int tiempoMax){
        utilPercentage = 100*utilTime/tiempoMax;
    }    

    public int getUtilTime() {
        return utilTime;
    }

    public void setUtilTime(int utilTime) {
        this.utilTime += utilTime;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public Cliente getClient() {
        return client;
    }

    public void setClient(Cliente client) {
        this.client = client;
    }
    
}
