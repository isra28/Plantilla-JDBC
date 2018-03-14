/* 
 * Copyright 2017 Israel Fernández Chiva - israel.1daw@gmail.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.japo.java.app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.japo.java.entities.DataAccessManager;
import org.japo.java.libraries.UtilesDB;

/**
 *
 * @author Israel Fernández Chiva - israel.1daw@gmail.com
 */
public class App {

    public void launchApp() {
        System.out.println("Iniciando acceso a Base de Datos...");
        //separador
        System.out.println("---");
        try (Connection con = UtilesDB.obtenerConexion();
             Statement stmt = con.createStatement(
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_UPDATABLE)) {
            System.out.println("Acceso a Base de Datos INICIADO");
            //separador
            System.out.println("---");
            DataAccessManager dam = new DataAccessManager(con, stmt);
            //Logica de negocio

            System.out.println("---");
            System.out.println("Acceso a Base de Datos FINALIZADO");
        } catch (SQLException e) {
            System.out.println("ERROR: Acceso a Base de Datos CANCELADO");
            System.out.printf("Codigo de error .: %d%n", e.getErrorCode());
            System.out.printf("Estado SQL ......: %s%n", e.getSQLState());
            System.out.printf("Descripción .....: %s%n", e.getLocalizedMessage());
        }
    }
}
