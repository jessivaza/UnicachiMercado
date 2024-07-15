package DAO;


import Interfaz.CRUDPuesto;
import Modelo.Puesto;
import Persistencia.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.MySqlConectar;

public class PuestoDAO implements CRUDPuesto {

    //Lista puesto
    @Override
    public List<Puesto> findAllPuestos() {
        List<Puesto> lista = new ArrayList<>();
        Connection cn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            cn = new MySqlConectar().getConnection();
            String sql = "SELECT * FROM puestos";
            pstm = cn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                Puesto puesto = new Puesto();
                puesto.setIdPuesto(rs.getInt("idPuesto"));
                puesto.setIdCategoria(rs.getInt("idCategoria"));
                puesto.setIdProducto(rs.getInt("idProducto"));
                puesto.setIdCliente(rs.getInt("idCliente"));
                puesto.setDueño(rs.getString("dueño"));
                puesto.setEstado(rs.getBoolean("estado"));
                lista.add(puesto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstm != null)
                    pstm.close();
                if (cn != null)
                    cn.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return lista;
    }
    
    
    //obtiene puesto por id
    @Override
    public Puesto findPuestoById(int idPuesto) {
        Puesto puesto = null;
        Connection cn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            cn = new MySqlConectar().getConnection();
            String sql = "SELECT * FROM puestos WHERE idPuesto = ?";
            pstm = cn.prepareStatement(sql);
            pstm.setInt(1, idPuesto);
            rs = pstm.executeQuery();
            if (rs.next()) {
                puesto = new Puesto();
                puesto.setIdPuesto(rs.getInt("idPuesto"));
                puesto.setIdCategoria(rs.getInt("idCategoria"));
                puesto.setIdProducto(rs.getInt("idProducto"));
                puesto.setIdCliente(rs.getInt("idCliente"));
                puesto.setDueño(rs.getString("dueño"));
                puesto.setEstado(rs.getBoolean("estado"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstm != null)
                    pstm.close();
                if (cn != null)
                    cn.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return puesto;
    }
    
    
    //esto guarda puesto
    @Override
    public int savePuesto(Puesto puesto) {
        int salida = -1;
        Connection cn = null;
        PreparedStatement pstm = null;
        try {
            cn = new MySqlConectar().getConnection();
            String sql = "INSERT INTO puestos (idCategoria, idProducto, idCliente, dueño, estado) VALUES (?, ?, ?, ?, ?)";
            pstm = cn.prepareStatement(sql);
            pstm.setInt(1, puesto.getIdCategoria());
            pstm.setInt(2, puesto.getIdProducto());
            pstm.setInt(3, puesto.getIdCliente());
            pstm.setString(4, puesto.getDueño());
            pstm.setBoolean(5, puesto.isEstado());
            salida = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstm != null)
                    pstm.close();
                if (cn != null)
                    cn.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return salida;    
    }
    
    //esto actualiza puesto
    @Override
    public int updatePuesto(Puesto puesto) {
        int salida = -1;
        Connection cn = null;
        PreparedStatement pstm = null;
        try {
            cn = new MySqlConectar().getConnection();
            String sql = "UPDATE puestos SET idCategoria=?, idProducto=?, idCliente=?, dueño=?, estado=? WHERE idPuesto=?";
            pstm = cn.prepareStatement(sql);
            pstm.setInt(1, puesto.getIdCategoria());
            pstm.setInt(2, puesto.getIdProducto());
            pstm.setInt(3, puesto.getIdCliente());
            pstm.setString(4, puesto.getDueño());
            pstm.setBoolean(5, puesto.isEstado());
            pstm.setInt(6, puesto.getIdPuesto());
            salida = pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstm != null)
                    pstm.close();
                if (cn != null)
                    cn.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return salida;
    }

    //elimina puesto por id
    @Override
    public int deletePuestoById(int idPuesto) {
        int salida = -1;
        Connection cn = null;
        PreparedStatement pstm = null;
        try {
            cn = new MySqlConectar().getConnection();
            String sql = "DELETE FROM puestos WHERE idPuesto=?";
            pstm = cn.prepareStatement(sql);
            pstm.setInt(1, idPuesto);
            salida = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstm != null)
                    pstm.close();
                if (cn != null)
                    cn.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return salida;
    }

    
    


}
