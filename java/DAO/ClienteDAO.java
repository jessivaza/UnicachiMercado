package DAO;

import Config.Conexion;
import Modelo.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClienteDAO {

    private Connection cn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

     
    public int Guardar(Cliente obj) {
        int result = 0;
        try {
            cn = Conexion.getConnection();
            String sql = "insert into Cliente(nombres, apellidos,telefono,correo,password,dni,direccion,ruc,denominacion) values(?,?,?,?,?,?,?,?,?)";
            ps = cn.prepareStatement(sql);
            ps.setString(1, obj.getNombres().trim());
            ps.setString(2, obj.getApellidos().trim());
            ps.setString(3, obj.getTelefono().trim());
            ps.setString(4, obj.getCorreo().trim());
            ps.setString(5, obj.getPassword());
            ps.setString(6, obj.getDni().trim());
            ps.setString(7, obj.getDireccion().trim());
            ps.setString(8, obj.getRuc().trim());
            ps.setString(9, obj.getDenominacion().trim());

            result = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
            }
        }
        return result;
    }

    public boolean ExisteCorreo(String correo) {

        try {
            cn = Conexion.getConnection();
            String sql = "select count(1) from Cliente where correo = ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, correo);
            rs = ps.executeQuery();

            if (rs.next()) {
                return (rs.getInt(1) > 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
            }
        }
        return false;
    }
}
