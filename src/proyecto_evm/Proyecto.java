/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_evm;

/**
 *
 * @author PC
 */
import java.util.ArrayList;
import java.util.List;

public class Proyecto {
    private String nombre;
    private int numHitos;
    private float BAC;
    private boolean prorrateado;
    private List<Hito> hitos = new ArrayList<>();

    // Constructor de la clase
    public Proyecto(String nombre, int numHitos) {
        this.nombre = nombre;
        this.numHitos = numHitos;
        this.BAC = 0;  // Se inicializa el presupuesto en 0, se espera que se establezca posteriormente
    }

    // Método para agregar un nuevo hito al proyecto con valores específicos
    public void agregarHito(float PV, float AC, float EV) {
        hitos.add(new Hito(PV, AC, EV));
    }

    public String getNombre() {
        return nombre;
    }

    public int getNumHitos() {
        return numHitos;
    }

    public float getBAC() {
        return BAC;
    }

    public boolean getProrrateado() {
        return prorrateado;
    }

    public void setProrrateado(boolean prorrateado) {
        this.prorrateado = prorrateado;
    }

    // Método para establecer el presupuesto en su totalidad del proyecto (BAC)
    public void setBAC(float BAC) {
        this.BAC = BAC;
    }

    public List<Hito> getHitos() {
        return hitos;
    }

    // Método para limpiar la lista de hitos asociados al proyecto
    public void limpiarHitos() {
        hitos.clear();
    }

    // Método para agregar un hito existente al proyecto
    public void agregarHito(Hito hito) {
        hitos.add(hito);
    }

    // Método para calcular la suma acumulativa del Valor Planeado (PV) de todos los hitos
    public float calcularPVAcumulado() {
        float PVAcumulado = 0;

        for (Hito hito : hitos) {
            PVAcumulado += hito.getPV();
        }

        return PVAcumulado;
    }
}
