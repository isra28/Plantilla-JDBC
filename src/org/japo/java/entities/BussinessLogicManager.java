/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.japo.java.entities;

import java.sql.SQLException;
import org.japo.java.libraries.UtilesEntrada;

/**
 *
 * @author Israel Fernández Chiva - israel.1daw@gmail.com
 */
public class BussinessLogicManager {

    public static final String TXT_MENU
            = "GESTIÓN DE MÓDULOS - MENÚ PRINCIPAL%n"
            + "===================================%n"
            + "  [A] Alta de Módulos%n"
            + "  [B] Baja de Módulos%n"
            + "  [C] Consulta de Módulos%n"
            + "  [M] Modificación de Módulos%n"
            + "---%n"
            + "  [S] Salir del Programa%n"
            + "---";
    public static final String OPC_MENU = "AaBbCcMmSs";
    public static final int LINEAS_PAGINA = 5;

    public static final void launchMenu(DataAccessManager dam) throws SQLException {
        char opcion = 0;
        do {
            System.out.printf(TXT_MENU);
            opcion = UtilesEntrada.leerOpcion(OPC_MENU, "Seleccionar opción ...: ", "ERROR: Entrada");
            try {
                switch (opcion) {
                    case 'A':
                    case 'a':
                        dam.insertarModulosInteractivo();
                        break;
                    case 'B':
                    case 'b':
                        dam.borrarModulosInteractivo();
                        break;
                    case 'C':
                    case 'c':
                        dam.listarModulos(LINEAS_PAGINA);
                        break;
                    case 'M':
                    case 'm':
                        dam.modificarModulosInteractivo();
                }

            } catch (SQLException e) {
                System.out.println("ERROR: Acceso a Base de Datos CANCELADO");
                System.out.printf("Codigo de error .: %d%n", e.getErrorCode());
                System.out.printf("Estado SQL ......: %s%n", e.getSQLState());
                System.out.printf("Descripción .....: %s%n", e.getLocalizedMessage());
            }
        } while (opcion != 'S' && opcion != 's');
        
    
    }
}
