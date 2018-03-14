/* 
 * Copyright 2018 Israel Fernández Chiva - israel.1daw@gmail.com.
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
package org.japo.java.entities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.japo.java.libraries.UtilesEntrada;

/**
 *
 * @author Israel Fernández Chiva - israel.1daw@gmail.com
 */
public class DataAccessManager {
    //Sentencia SQL

    public static final String DEF_SQL_MOD = "SELECT * FROM modulo";
    public static final String DEF_SQL_ALU = "SELECT * FROM alumno";
    public static final String DEF_SQL_PRO = "SELECT * FROM profesor";

    public static final String CAB_LST_MOD1 = "#   Id  Acrónimo Nombre                      Codigo Horas Curso";
    public static final String CAB_LST_MOD2 = "=== === ======== =========================== ====== ===== =====";
    public static final String CAB_LST_ALU1 = "#   Exp         Nombre   Apellidos             NIF       Nac      Telefono Email                Domicilio";
    public static final String CAB_LST_ALU2 = "=== =========== ======== ===================== ========= ======== ======== ====================";
    public static final String CAB_LST_PRO1 = "#   Id          Nombre      Apellidos          Departamento    Especialidad     Tipo    ";
    public static final String CAB_LST_PRO2 = "=== =========== =========== ================== =============== ================ ========";
    
    public static final String CAB_REG1 = "Proceso de Borrado - Registro %02d";
    public static final String CAB_REG2 = "==================================";

    private Connection con;
    private Statement stmt;

    public DataAccessManager(Connection con) {
        this.con = con;
    }

    public DataAccessManager(Connection con, Statement stmt) {
        this.con = con;
        this.stmt = stmt;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public Statement getStmt() {
        return stmt;
    }

    public void setStmt(Statement stmt) {
        this.stmt = stmt;
    }

    public final void listarModulos() throws SQLException {
        System.out.println("Listado de módulos ... ");
        //separador
        System.out.println("---");
        try (ResultSet rs = getStmt().executeQuery(DEF_SQL_MOD)) {
            if (rs.next()) {
                System.out.println(CAB_LST_MOD1);
                System.out.println(CAB_LST_MOD2);
                do {
                    String fila = String.format("%03d ", rs.getRow());
                    String id = String.format("%-3d", rs.getInt("id"));
                    String acro = String.format("%-9s", rs.getString("acronimo"));
                    String nom = String.format("%-28s ", rs.getString("nombre"));
                    String cod = String.format("%-7s", rs.getString("codigo"));
                    String h = String.format("%-6d", rs.getInt("horasCurso"));
                    String curso = String.format("%d", rs.getInt("curso"));
                    System.out.println(fila + id + acro + nom + cod + h + curso);
                } while (rs.next());
            } else {
                System.out.println("No hay datos que mostrar ...");
            }
        }
    }

    public final void listarModulos(int lineasPagina) throws SQLException {
        if (lineasPagina <= 0) {
            listarModulos();
        } else {
            System.out.println("Listado de módulos ... ");
            //separador
            System.out.println("---");
            try (ResultSet rs = stmt.executeQuery(DEF_SQL_MOD)) {
                if (rs.next()) {
                    boolean nuevaLineaOK;
                    int lineaAct = 1;
                    int paginaAct = 1;
                    do {
                        System.out.printf("Pagina ...: %02d%n", paginaAct);
                        System.out.println("===============");
                        System.out.println(CAB_LST_MOD1);
                        System.out.println(CAB_LST_MOD2);
                        do {
                            String fila = String.format("%03d ", rs.getRow());
                            String id = String.format("%-3d", rs.getInt("id"));
                            String acro = String.format("%-9s", rs.getString("acronimo"));
                            String nom = String.format("%-28s ", rs.getString("nombre"));
                            String cod = String.format("%-7s", rs.getString("codigo"));
                            String h = String.format("%-6d", rs.getInt("horasCurso"));
                            String curso = String.format("%d", rs.getInt("curso"));
                            System.out.println(fila + id + acro + nom + cod + h + curso);

                            lineaAct++;
                            nuevaLineaOK = rs.next();
                        } while (lineaAct <= lineasPagina && nuevaLineaOK);
                        if (nuevaLineaOK) {
                            //separador
                            System.out.println("---");
                            char respuesta = UtilesEntrada.leerOpcion("sSnN", "Siguiente Página (S/N)...:", "ERROR, Entrada Incorrecta");
                            if (respuesta == 's') {
                                paginaAct++;
                                lineaAct = 1;
                                System.out.println("---");
                            } else {
                                nuevaLineaOK = false;
                            }
                        }
                    } while (nuevaLineaOK);
                } else {
                    System.out.println("No hay modulos que mostrar");
                }
            }
        }

    }
    
    public final void borrarModulosInteractivo() throws SQLException {
        System.out.println("Borrado de modulos ... ");
        //separador
        System.out.println("---");
        try (ResultSet rs = stmt.executeQuery(DEF_SQL_MOD)) {
            int regBorrados = 0;
            while (rs.next()) {
                System.out.printf(CAB_REG1 + "%n", rs.getRow());
                System.out.println(CAB_REG2);
                
                System.out.printf("Id .......: %d%n",rs.getInt(1));
                System.out.printf("Acrónimo .: %s%n",rs.getString(2));
                System.out.printf("Nombre ...: %s%n",rs.getString(3));
                System.out.printf("Codigo ...: %s%n",rs.getString(4));
                System.out.printf("Horas ....: %d%n",rs.getInt(5));
                System.out.printf("Curso ....: %d%n",rs.getInt(6));
                
                char respuesta = UtilesEntrada.leerOpcion("SsNn", "Borrar Modulo (S/N ...: )", "ERROR: Entrada incorrecta");
                
                if (respuesta == 'S' || respuesta == 's') {
                    rs.deleteRow();
                    regBorrados++;
                    System.out.println("---");
                    System.out.println("Modulo actual borrado");
                }
                //separador
                System.out.println("---");
            }
            System.out.printf("Se han borrado %d modulos%n", regBorrados);
        }
    }
    
    public final void insertarModulosInteractivo() throws SQLException {
        System.out.println("Inserción de modulos...");
        //separador
        System.out.println("---");
        try (ResultSet rs = stmt.executeQuery(DEF_SQL_MOD)) {
            rs.moveToInsertRow();
            rs.updateInt (1, UtilesEntrada.leerEntero("Id ...: ", "ERROR"));
            rs.updateString (2, UtilesEntrada.leerTexto("Acrónimo ...: "));
            rs.updateString (3, UtilesEntrada.leerTexto("Nombre ...: "));
            rs.updateString (4, UtilesEntrada.leerTexto("Código ...: "));
            rs.updateInt (5, UtilesEntrada.leerEntero("Horas ...: ", "ERROR"));
            rs.updateInt (6, UtilesEntrada.leerEntero("Curso ...: ", "ERROR"));
            //separador
            System.out.println("---");
            
            char respuesta = UtilesEntrada.leerOpcion("SsNn", "Insertar Modulo (S/N) ...: ", "ERROR: Entrada");
            if (respuesta == 's' || respuesta == 'S') {
                rs.insertRow();
                //separador
                System.out.println("---");
                System.out.println("Inserción de datos Completada");
            } else {
                //separador
                System.out.println("---");
                System.out.println("Inserción de datos Cancelada");
            }
            rs.moveToCurrentRow();
        }
    }
}
