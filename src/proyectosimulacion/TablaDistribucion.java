package proyectosimulacion;

import java.util.ArrayList;

public class TablaDistribucion {
    
    public ArrayList<Integer> valor;           //Valor de TE o TS
    public ArrayList<Float> probabilidad;      //Probabilidad de que ocurra
    public ArrayList<Float> frecAcumulada;     //Sumatoria de probabilidades, debe de dar 1 = 100%
    int tamaño;                         //Cantidad de valores con sus probabilidades en la tabla
    
    //Inicializacion de la tabla
    public TablaDistribucion(){
        valor = new ArrayList<>();
        probabilidad = new ArrayList<>();
        frecAcumulada = new ArrayList<>();
        tamaño = 0;
    }
    
    //Agregar valor y su probabilidad
    public void agregarEntrada(int valor, float probabilidad){
        this.valor.add(valor);
        this.probabilidad.add(probabilidad);
        if(tamaño > 0){
            frecAcumulada.add(frecAcumulada.get(tamaño - 1) + probabilidad);
        }else{
            frecAcumulada.add(probabilidad);
        }
        tamaño ++;
    }
    
    //Remover un valor y su probabilidad
    public void removerEntrada(int i){
       valor.remove(i);
       probabilidad.remove(i);
       frecAcumulada.remove(i);
       tamaño --;
       recalcularFrecAcumulada();
    }
    
    //Calcular la sumatoria de la probabilidad
    private void recalcularFrecAcumulada(){
        float frecAnterior = 0;
        for(int i = 0; i < tamaño; i++){
            frecAcumulada.set(i, probabilidad.get(i) + frecAnterior);
            frecAnterior = probabilidad.get(i) + frecAnterior;
        }
    }
    
    public int getValor(int i) {
        return valor.get(i);
    }

    public float getProbabilidad(int i) {
        return probabilidad.get(i);
    }

    public float getFrecAcumulada(int i) {
        return frecAcumulada.get(i);
    }

    public int getTamaño() {
        return tamaño;
    }
    
}
