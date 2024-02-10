/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_evm;

/**
 *
 * @author PC
 */
public class Hito {
    private float PV;
    private float AC;
    private float EV;

    public Hito(float PV, float AC, float EV) {
        this.PV = PV;
        this.AC = AC;
        this.EV = EV;
    }

    public float getPV() {
        return PV;
    }

    public void setPV(float PV) {
        this.PV = PV;
    }

    public float getAC() {
        return AC;
    }

    public void setAC(float AC) {
        this.AC = AC;
    }

    public float getEV() {
        return EV;
    }

    public void setEV(float EV) {
        this.EV = EV;
    }


}

