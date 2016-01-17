/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmapp.app.altacontratos;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author jmrb
 */
public class ComprobarDatosVistaContratos {
    
    VistaAC vista;
    
    public Boolean ComprobarDatosVistaContratos(VistaAC vistaOrigen) {
        
        this.vista = vistaOrigen;
        
        StringBuilder mensajeAviso =new StringBuilder();
        
        Boolean comprobadoOK = true;
        Boolean tablaHorarioVaciaEsOK = false;
        
        //String mensajeAviso = "";
        
        // Comprobar la tabla de horario
        if(tablaHorarioVaciaEsOK == false)
            if(!vista.hayDatosEnTablaHorario())
            {
                String cabecera = "Tabla de períodos, jornadas y horarios";
                String mensaje =  "¿Es correcto que no haya filas de horarios en la tabla \" Períodos, jornadas y horarios\"?";

                int respuesta = vista.muestraPregunta(cabecera, mensaje);
                if(respuesta == JOptionPane.YES_OPTION)
                    tablaHorarioVaciaEsOK = true;
            }
        
        // Cliente CCC
        if(vista.getComboClienteCCCItemCount() == 0 ||
                (vista.getComboClienteCCCItemCount() > 2 &&
                vista.getComboClienteCCCSelectedIndex() == 0)){
            mensajeAviso.append("No se ha establecido el CCC de contratación del cliente.\n");
            comprobadoOK = false;
        }
        
        // Tipo contrato
        if(vista.getComboTipoContratoSelectedIndex() == 0){
            mensajeAviso.append("No se ha seleccionado el Tipo de contrato.\n");
            comprobadoOK = false;
        }  
        
        // Duración
        if(vista.getComboDuracionContratoSelectedIndex() == 0)
        {
            mensajeAviso.append("No se ha establecido la Duración del contrato.\n");
            comprobadoOK = false;
        }    
        
        // Fechas contrato
        if(vista.getFechaInicioContrato().length() == 0)
        {
            mensajeAviso.append("No se ha establecido la Fecha de Inicio del contrato.\n");
            comprobadoOK = false;
        }
        else if (vista.getEtqDuracionContrato() != null)
            {
                if(!vista.getComboDuracionContratoSelectedItem().toString().equals("Indefinido") && vista.getFechaFinContrato().length() == 0)  
                {
                    mensajeAviso.append("No se ha establecido la Fecha de finalización del contrato.\n");
                    comprobadoOK = false;
                }                
            }

        // Jornada
        if (vista.getComboJornadaSelectedIndex() == 0)
        {
            mensajeAviso.append("No se ha establecido la Jornada del contrato.\n");
            comprobadoOK = false;
        }
        else if (vista.getComboJornadaSelectedItem().equals("Tiempo parcial") &&
                vista.getHorasSemana().length() == 0)
        {
            mensajeAviso.append("No se han establecido las horas de la jornada.\n");
            comprobadoOK = false;
        }
         
        // Dias de la jornada
        ArrayList diasSemana = (ArrayList) vista.getDiasSemana();
        int numeroDias = 0;
        for(Object dia: diasSemana)
            numeroDias = numeroDias + Integer.parseInt(dia.toString());
        if(numeroDias == 0)
        {
             mensajeAviso.append("No se han establecido los días de la Jornada.\n");
            comprobadoOK = false;
        }
              
        // Categoría
        if(vista.getCategoria().length() == 0)
        {
            mensajeAviso.append("No se ha establecido la categoría del trabajador.\n");
            comprobadoOK = false;
        }
        
        if(tablaHorarioVaciaEsOK == false && !vista.hayDatosEnTablaHorario())
        {
            mensajeAviso.append("No se han incluido datos en la \"Tabla de períodos, jornadas y horarios\"\n");
            comprobadoOK = false;
        }
        
        if(comprobadoOK == false)
            vista.muestraError(mensajeAviso.toString());       // Muestra todos los mensajes juntos
        
        return comprobadoOK;
    }
}
