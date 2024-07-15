package DAO;

import Interfaz.CRUDPuestoInforme;
import Modelo.PuestoInforme;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.MySqlConectar;




public class PuestoInformeDAO implements CRUDPuestoInforme{
    
    
    @Override
    public List<PuestoInforme> findAllPuestoInforme() {
        List<PuestoInforme> lista = new ArrayList<>();
        Connection cn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            cn = new MySqlConectar().getConnection();
            String sql = "SELECT " +
                         "    pu.idPuesto AS 'ID del Puesto', " +
                         "    c.nombreCategoria AS 'Nombre de la Categoría', " +
                         "    pr.Descripcion AS 'Descripción del Producto', " +
                         "    CONCAT(cl.nombre, ' ', cl.apellido) AS 'Nombre Cliente', " +
                         "    pu.dueño AS 'Dueño del Puesto', " +
                         "    CASE " +
                         "        WHEN pu.estado = 1 THEN 'Abierto' " +
                         "        ELSE 'Cerrado' " +
                         "    END AS 'Estado' " +
                         "FROM " +
                         "    puestos pu " +
                         "JOIN " +
                         "    categoria c ON pu.idCategoria = c.idCategoria " +
                         "JOIN " +
                         "    productos pr ON pu.idProducto = pr.IdProducto " +
                         "JOIN " +
                         "    cliente cl ON pu.idCliente = cl.idCliente";
            pstm = cn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                PuestoInforme puestoInforme = new PuestoInforme();
                puestoInforme.setIdPuestoInforme(rs.getInt("ID del Puesto"));
                puestoInforme.setCategoriaPuestoInforme(rs.getString("Nombre de la Categoría"));
                puestoInforme.setProductoPuestoInforme(rs.getString("Descripción del Producto"));
                puestoInforme.setClientePuestoInforme(rs.getString("Nombre Cliente"));
                puestoInforme.setDuenoPuestoInforme(rs.getString("Dueño del Puesto"));
                puestoInforme.setEstadoPuestoInforme(rs.getString("Estado"));
                lista.add(puestoInforme);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar adecuadamente las excepciones en tu aplicación
        } finally {
            // Cerrar recursos en un bloque finally para garantizar que se cierren incluso si ocurre una excepción
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace(); // Manejar adecuadamente las excepciones en tu aplicación
            }
        }
        return lista;
    }

    @Override
    public PuestoInforme findPuestoById(int idPuesto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int savePuesto(PuestoInforme puesto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int updatePuesto(PuestoInforme puesto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int deletePuestoById(int idPuesto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
