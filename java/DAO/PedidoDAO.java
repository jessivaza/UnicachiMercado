package DAO;

import Config.Conexion;
import Modelo.DetallePedido;
import Modelo.Pedido;
import Modelo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PedidoDAO {

    private Connection cn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public int GenerarPedido(Pedido obj) {
        int result = 0;
        try {
            cn = Conexion.getConnection();
            cn.setAutoCommit(false);
            String sql = "INSERT INTO Pedido(id_cli,fecha_ped,total,estado) VALUES(?,NOW(),?,?)";
            ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, obj.getCliente().getIdCliente());
            ps.setDouble(2, obj.getTotal());
            ps.setString(3, obj.getEstado());
            result = ps.executeUpdate();
            if (result > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int idPed = rs.getInt(1);
                    ps = cn.prepareStatement("INSERT INTO Detalle_Pedido(id_ped,id_prod,precio,cantidad) VALUES(?,?,?,?)");
                    for (DetallePedido carrito : obj.getDetalles()) {
                        ps.setInt(1, idPed);
                        ps.setInt(2, carrito.getProducto().getIdProd());
                        ps.setDouble(3, carrito.getProducto().getPrecio());
                        ps.setInt(4, carrito.getCantidad());
                        ps.executeUpdate();
                    }
                    cn.commit();
                }
            }
        } catch (Exception e) {
            try {
                if (cn != null) {
                    cn.rollback();
                    result = 0;
                }
            } catch (SQLException ex) {
            }
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
        return result;
    }

    public ArrayList<Pedido> ListarPorIdCliente(int id) {
        ArrayList<Pedido> lista = new ArrayList<>();
        try {
            cn = Conexion.getConnection();
            String sql = "SELECT * FROM Pedido WHERE id_cli=?";
            ps = cn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                Pedido obj = new Pedido();
                obj.setIdPedido(rs.getInt("id_ped"));
                obj.setFecha(rs.getString("fecha_ped"));
                obj.setTotal(rs.getDouble("total"));
                obj.setEstado(rs.getString("estado"));
                obj.setDetalles(ListarDetallePorIdPed(obj.getIdPedido()));
                lista.add(obj);
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
        return lista;
    }

    public ArrayList<DetallePedido> ListarDetallePorIdPed(int idPed) {
        ArrayList<DetallePedido> lista = new ArrayList<>();
        Connection cn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            cn=Conexion.getConnection();
            String sql = "SELECT p.imagen, p.nombre,d.precio,d.cantidad \n"
                    + "FROM Detalle_Pedido d INNER JOIN producto p ON p.id_prod\n"
                    + "WHERE d.id_ped = ?";
            ps=cn.prepareStatement(sql);
            ps.setInt(1, idPed);
            rs=ps.executeQuery();
            
            while (rs.next()) {                
                Producto p=new Producto();
                DetallePedido d=new DetallePedido();
                p.setImagen(rs.getString("imagen"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                d.setCantidad(rs.getInt("cantidad"));
                d.setProducto(p);
                lista.add(d);
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
        return lista;
    }
}
