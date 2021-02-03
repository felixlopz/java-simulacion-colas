package proyectosimulacion;

public class Cliente {
    private int AT;
    private int DT;
    private int number;
    private int TE;
    private int TS;
    private int queueTime;
    private int ATserver;

    public Cliente(int nroCliente, int tiempoLlegada) {
        this.AT = tiempoLlegada;
        this.DT = 0;
        this.number = nroCliente;
        this.TE = 0;
        this.TS = 0;
        this.queueTime = 0;
        this.ATserver =0;
    }    

    public int getAT() {
        return AT;
    }

    public void setAT(int AT) {
        this.AT = AT;
    }

    public int getDT() {
        return DT;
    }

    public void setDT(int DT) {
        this.DT = DT;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getTE() {
        return TE;
    }

    public void setTE(int TE) {
        this.TE = TE;
    }

    public int getTS() {
        return TS;
    }

    public void setTS(int TS) {
        this.TS = TS;
    }

    public int getQueueTime() {
        return queueTime;
    }

    public void setQueueTime(int queueTime) {
        this.queueTime = queueTime;
    }

    public int getATserver() {
        return ATserver;
    }

    public void setATserver(int ATserver) {
        this.ATserver = ATserver;
    }

    
        
}
