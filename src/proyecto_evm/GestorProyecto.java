/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_evm;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class GestorProyecto {
    private List<Proyecto> proyectos = new ArrayList<>();

    public GestorProyecto() {
    }
    // Método para crear un proyecto sin hitos, luego se le agregaran en una tabla

    public boolean crearProyecto(String nombre, int numeroHito, boolean prorrateado, float BAC){
            Proyecto nuevo = new Proyecto(nombre, numeroHito);
            proyectos.add(nuevo);
            nuevo.setBAC(BAC);
            nuevo.setProrrateado(prorrateado);
        return true;
        }
    // Método para crear un proyecto con hitos y asignar valores a los hitos
    public boolean crearProyectoHitos(String nombre, int numeroHito, boolean prorrateado, float[] PV, float[] AC, float[] EV) {
       Proyecto proyecto = obtenerProyectoPorNombre(nombre);

       // Verificar si el proyecto existe
       if (proyecto == null) {
           JOptionPane.showMessageDialog(null, "El proyecto no existe.");
           return false;
       }

       // Verificar si el número de hito es válido
       if (numeroHito <= 0 || numeroHito > proyecto.getNumHitos()) {
           JOptionPane.showMessageDialog(null, "Número de hito no válido.");
           return false;
       }

       float PV_Acumulado = 0;

       for (int i = 0; i < numeroHito; i++) {
           if (prorrateado) {
               float PV_Hito = proyecto.getBAC() / numeroHito;
               Hito hito = new Hito(PV_Hito, AC[i], EV[i]);
               proyecto.agregarHito(hito);
               PV_Acumulado += PV_Hito;
           } else {
               if (PV[i] + PV_Acumulado > proyecto.getBAC()) {
                   JOptionPane.showMessageDialog(null, "El PV de un hito no puede superar el BAC total.");
                   return false;
               }

               Hito hito = new Hito(PV[i], AC[i], EV[i]);
               proyecto.agregarHito(hito);
               PV_Acumulado += PV[i];
           }
       }

       return true;
   }


    public Proyecto obtenerProyectoPorNombre(String nombre) {
        for (Proyecto proyecto : proyectos) {
            if (proyecto.getNombre().equals(nombre)) {
                return proyecto;
            }
        }
        return null;
    }
    // Método para revisar el estado del proyecto y realizar sus respectivos calculos

    public String[][] revisarProyecto(String nombre, int numeroHito, int variacion) {
        Proyecto proyectoSeleccionado = obtenerProyectoPorNombre(nombre);

        if (proyectoSeleccionado != null) {
            List<Hito> hitos = proyectoSeleccionado.getHitos();

            // Verifica que el número de hito ingresado sea válido
            if (numeroHito <= 0 || numeroHito > hitos.size()) {
                System.out.println("Número de hito no válido");
                return null;
            }

            float PV_Acumulado = 0;
            float AC_Acumulado = 0;
            float EV_Acumulado = 0;

            for (int i = 0; i < numeroHito; i++) {
                Hito hito = hitos.get(i);
                PV_Acumulado += hito.getPV();
                AC_Acumulado += hito.getAC();
                EV_Acumulado += hito.getEV();
            }

            Hito hito = hitos.get(numeroHito - 1);
            float CV = EV_Acumulado - AC_Acumulado;
            float CPI = (AC_Acumulado != 0) ? EV_Acumulado / AC_Acumulado : 0; // Maneja división por cero
            float SV = EV_Acumulado - PV_Acumulado;
            float SPI = (PV_Acumulado != 0) ? EV_Acumulado / PV_Acumulado : 0; // Maneja división por cero
            float TCPI = (proyectoSeleccionado.getBAC() - EV_Acumulado) / ((proyectoSeleccionado.getBAC() - AC_Acumulado) != 0 ? (proyectoSeleccionado.getBAC() - AC_Acumulado) : 1); // Maneja división por cero

            float EAC = 0;
            float ETC = 0;

            if (variacion == 1) {
                EAC = (CPI != 0) ? proyectoSeleccionado.getBAC() / CPI : 0; // Maneja división por cero
                ETC = EAC - AC_Acumulado;
            }
            if (variacion == 2) {
                ETC = proyectoSeleccionado.getBAC() - EV_Acumulado;
                EAC = AC_Acumulado + ETC;
            }

            float VAC = proyectoSeleccionado.getBAC() - EAC;

            // Organizar los resultados en tres columnas (nombre, valor, análisis)
            String[][] resultados = new String[15][3];

            resultados[0] = new String[]{"PV", String.valueOf(hito.getPV()), ""};
            resultados[1] = new String[]{"AC", String.valueOf(hito.getAC()), ""};
            resultados[2] = new String[]{"EV", String.valueOf(hito.getEV()), ""};
            resultados[3] = new String[]{"BAC", String.valueOf(proyectoSeleccionado.getBAC()), ""};
            resultados[4] = new String[]{"PV Acumulado", String.valueOf(PV_Acumulado), ""};
            resultados[5] = new String[]{"AC Acumulado", String.valueOf(AC_Acumulado), ""};
            resultados[6] = new String[]{"EV Acumulado", String.valueOf(EV_Acumulado), ""};
            resultados[7] = new String[]{"CV", String.valueOf(CV), (CV < 0) ? "El proyecto es ineficiente" : (CV == 0) ? "El proyecto sigue lo planificado" : "El proyecto es eficiente"};
            resultados[8] = new String[]{"SV", String.valueOf(SV), (SV < 0) ? "El proyecto va con retraso, respecto a lo planificado" : (SV == 0) ? "El proyecto sigue lo planificado" : "El proyecto va adelantado, respecto a lo planificado"};
            resultados[9] = new String[]{"CPI", String.valueOf(CPI), "Por cada $ gastado se trabaja " + CPI + " $ "};
            resultados[10] = new String[]{"SPI", String.valueOf(SPI), "Estamos progresando a un " + SPI + " % de lo planeado "};
            resultados[11] = new String[]{"TCPI", String.valueOf(TCPI), (TCPI < 0) ? "El proyecto es eficiente y tiene holgura para gastar más sin exceder el presupuesto original" : (TCPI == 0) ? "El proyecto sigue lo planificado" : "El proyecto es deficiente y excede el presupuesto original"};
            resultados[12] = new String[]{"EAC", String.valueOf(EAC), "El proyecto costará, desde su inicio hasta su finalizar, " + EAC + " $" };
            resultados[13] = new String[]{"ETC", String.valueOf(ETC), "Se necesita " + ETC + " $ para completar el proyecto " };
            resultados[14] = new String[]{"VAC", String.valueOf(VAC), (VAC < 0) ? "El proyecto terminará con un costo por encima del presupuesto, \nexiste una diferencia de " + VAC + " $" : (VAC == 0) ? "El proyecto terminará de acuerdo con el costo del presupuesto" : "El proyecto terminará con un costo por debajo del presupuesto, existe una diferencia de " + VAC + " $"};

            return resultados;
        }
        return null;
    }

    // Método para obtener datos de todos los proyectos para tabla de Crear y revisar
    public String[][] obtenerDatosProyectos() {
        String[][] datosProyectos = new String[proyectos.size()][3];

        for (int i = 0; i < proyectos.size(); i++) {
            Proyecto proyecto = proyectos.get(i);
            datosProyectos[i][0] = proyecto.getNombre();
            datosProyectos[i][1] = String.valueOf(proyecto.getNumHitos());
            datosProyectos[i][2] = String.valueOf(proyecto.getBAC());
        }

        return datosProyectos;
    }
    // Método para obtener datos de hitos de un proyecto específico se usaran en la tabla de hitos
    public String[][] obtenerDatosHitos(String nombreProyecto) {
        Proyecto proyecto = obtenerProyectoPorNombre(nombreProyecto);

        if (proyecto != null) {
            List<Hito> hitos = proyecto.getHitos();
            String[][] datosHitos = new String[hitos.size()][3];

            for (int i = 0; i < hitos.size(); i++) {
                Hito hito = hitos.get(i);
                datosHitos[i][0] = String.valueOf(hito.getPV());
                datosHitos[i][1] = String.valueOf(hito.getAC());
                datosHitos[i][2] = String.valueOf(hito.getEV());
            }

            return datosHitos;
        } else {
            return null;
        }
    }

    public boolean eliminarProyecto(String nombreProyecto) {
            Proyecto proyectoAEliminar = obtenerProyectoPorNombre(nombreProyecto);

            if (proyectoAEliminar != null) {
                proyectos.remove(proyectoAEliminar);
                return true;
            } else {
                return false; 
            }
        }

    public boolean existeProyecto(String nombre) {
        for (Proyecto proyecto : proyectos) {
            if (proyecto.getNombre().equals(nombre)) {
                return true;
            }
        }
        return false;
    }
    // Método para modificar un hito específico en un proyecto
    public boolean modificarHito(String nombreProyecto, int numeroHito, float nuevoPV, float nuevoAC, float nuevoEV) {
        Proyecto proyecto = obtenerProyectoPorNombre(nombreProyecto);

        // Verificar si el proyecto existe
        if (proyecto == null) {
            JOptionPane.showMessageDialog(null, "El proyecto no existe.");
            return false;
        }

        // Verificar si el número de hito es válido
        if (numeroHito <= 0 || numeroHito > proyecto.getNumHitos()) {
            JOptionPane.showMessageDialog(null, "Número de hito no válido.");
            return false;
        }

        List<Hito> hitos = proyecto.getHitos();

        // Verificar si el número de hito a modificar es válido
        if (numeroHito <= 0 || numeroHito > hitos.size()) {
            JOptionPane.showMessageDialog(null, "Número de hito a modificar no válido.");
            return false;
        }

        // Modificar el hito en la posición especificada
        Hito hitoModificado = new Hito(nuevoPV, nuevoAC, nuevoEV);
        hitos.set(numeroHito - 1, hitoModificado);

        return true;
    }



}

