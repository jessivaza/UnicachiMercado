/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Interfaz.CRUDCliente;
import Modelo.ClienteDash;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.MySqlConectar;

public  class ClienteDashDAO implements CRUDCliente {
        
    //lista los cliente usuarios
    @Override
    public List<ClienteDash> findAllClientesDash() {
        List<ClienteDash> lista = new ArrayList<>();
        Connection cn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            cn = new MySqlConectar().getConnection();
            String sql = "SELECT * FROM clientedash";
            pstm = cn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                ClienteDash cliente = new ClienteDash();
                cliente.setIdCliente(rs.getInt("idCliente"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setDni(rs.getString("dni")); 
                cliente.setEmail(rs.getString("email"));
                cliente.setContrasena(rs.getString("contrasena"));
                cliente.setTelefono(rs.getString("telefono"));
                lista.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lista;
    }

    //lista el cliente usuario por id
    @Override
    public ClienteDash findClienteById(int idClienteDash) {
        ClienteDash cliente = null;
        Connection cn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            cn = new MySqlConectar().getConnection();
            String sql = "SELECT * FROM clientedash WHERE idCliente = ?";
            pstm = cn.prepareStatement(sql);
            pstm.setInt(1, idClienteDash);
            rs = pstm.executeQuery();
            if (rs.next()) {
                cliente = new ClienteDash();
                cliente.setIdCliente(rs.getInt("idCliente"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setDni(rs.getString("dni")); 
                cliente.setEmail(rs.getString("email"));
                cliente.setContrasena(rs.getString("contrasena"));
                cliente.setTelefono(rs.getString("telefono"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cliente;
    }

    
    
    //GUARDA CLIENTE USUARIO 
    @Override
   public int saveCliente(ClienteDash cliente) {
        int salida = -1;
        Connection cn = null;
        PreparedStatement pstm = null;
        try {
            cn = new MySqlConectar().getConnection();
            String sql = "INSERT INTO clientedash (nombre, apellido, direccion, dni, email, contrasena, telefono) VALUES (?,?,?,?,?,?,?)"; // Se agrega dni
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, cliente.getNombre());
            pstm.setString(2, cliente.getApellido());
            pstm.setString(3, cliente.getDireccion());
            pstm.setString(4, cliente.getDni()); 
            pstm.setString(5, cliente.getEmail());
            pstm.setString(6, cliente.getContrasena());
            pstm.setString(7, cliente.getTelefono());
            salida = pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstm != null) pstm.close();
                if (cn != null) cn.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return salida;
    }

    
    //actualiza cliente usuario
    @Override
    public int updateCliente(ClienteDash cliente) {
        int salida = -1;
        Connection cn = null;
        PreparedStatement pstm = null;
        try {
            cn = new MySqlConectar().getConnection();
            String sql = "UPDATE clientedash SET nombre=?, apellido=?, direccion=?, dni=?, email=?, contrasena=?, telefono=? WHERE idCliente=?";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, cliente.getNombre());
            pstm.setString(2, cliente.getApellido());
            pstm.setString(3, cliente.getDireccion());
            pstm.setString(4, cliente.getDni()); 
            pstm.setString(5, cliente.getEmail());
            pstm.setString(6, cliente.getContrasena());
            pstm.setString(7, cliente.getTelefono());
            pstm.setInt(8, cliente.getIdCliente());
            salida = pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstm != null) pstm.close();
                if (cn != null) cn.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return salida;
    }


    //Borra el cliente usuario por id
    @Override
    public int deleteClienteById(int idClienteDash) {
        int salida = -1;
        Connection cn = null;
        PreparedStatement pstm = null;
        try {
            cn = new MySqlConectar().getConnection();
            String sql = "DELETE FROM clientedash WHERE idCliente=?";
            pstm = cn.prepareStatement(sql);
            pstm.setInt(1, idClienteDash);
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
    
    //para iniciar sesion
    @Override
    public ClienteDash InicioSesion(String nombreUsuario, String contrasena) {
        ClienteDash cliente = null;
        Connection cn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            cn = new MySqlConectar().getConnection();
            String sql = "SELECT * FROM clientedash WHERE nombre = ? AND contrasena = ?";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, nombreUsuario);
            pstm.setString(2, contrasena);
            rs = pstm.executeQuery();
            if (rs.next()) {
                cliente = new ClienteDash();
                cliente.setIdCliente(rs.getInt("idCliente"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setDni(rs.getString("dni")); 
                cliente.setEmail(rs.getString("email"));
                cliente.setContrasena(rs.getString("contrasena"));
                cliente.setTelefono(rs.getString("telefono"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
                if (cn != null) cn.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return cliente;
    }

 

}
