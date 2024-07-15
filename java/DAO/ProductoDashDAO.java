package DAO;

import Interfaz.CRUDProducto;
import Modelo.ProductoDash;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.MySqlConectar;

public class ProductoDashDAO implements CRUDProducto {

    //Lista los productos
    @Override
    public List<ProductoDash> findAllProducto() {
        List<ProductoDash> lista = new ArrayList<>();
        Connection cn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            cn = new MySqlConectar().getConnection();
            String sql = "SELECT * FROM productos";
            pstm = cn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                ProductoDash producto = new ProductoDash();
                producto.setIdProducto(rs.getInt("IdProducto"));
                producto.setDescripcion(rs.getString("Descripcion"));
                producto.setPrecio(rs.getDouble("Precio"));
                producto.setCantidad(rs.getInt("Cantidad"));
                producto.setPopularidad(rs.getString("Popularidad"));
                producto.setIdCategoria(rs.getInt("idCategoria"));
                producto.setImagen(rs.getString("imagen"));
                lista.add(producto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return lista;
    }

    @Override
    public ProductoDash findProductoById(int idProducto) {
        ProductoDash producto = null;
        Connection cn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            cn = new MySqlConectar().getConnection();
            String sql = "SELECT * FROM productos WHERE IdProducto = ?";
            pstm = cn.prepareStatement(sql);
            pstm.setInt(1, idProducto);
            rs = pstm.executeQuery();
            if (rs.next()) {
                producto = new ProductoDash();
                producto.setIdProducto(rs.getInt("IdProducto"));
                producto.setDescripcion(rs.getString("Descripcion"));
                producto.setPrecio(rs.getDouble("Precio"));
                producto.setCantidad(rs.getInt("Cantidad"));
                producto.setPopularidad(rs.getString("Popularidad"));
                producto.setIdCategoria(rs.getInt("idCategoria"));
               producto.setImagen(rs.getString("imagen"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return producto;
    }

@Override
public int save(ProductoDash producto) {
    int salida = -1;
    Connection cn = null;
    PreparedStatement pstm = null;
    try {
        cn = new MySqlConectar().getConnection();
        String sql = "INSERT INTO productos (Descripcion, Precio, Cantidad, Popularidad, idCategoria, imagen) VALUES (?,?,?,?,?,?)";
        pstm = cn.prepareStatement(sql);
        pstm.setString(1, producto.getDescripcion());
        pstm.setDouble(2, producto.getPrecio());
        pstm.setInt(3, producto.getCantidad());
        pstm.setString(4, producto.getPopularidad());
        pstm.setInt(5, producto.getIdCategoria());
        pstm.setString(6, producto.getImagen());
        salida = pstm.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (pstm != null) {
                pstm.close();
            }
            if (cn != null) {
                cn.close();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
    return salida;
}

    //actualiza productos
@Override
public int update(ProductoDash producto) {
    int salida = -1;
    Connection cn = null;
    PreparedStatement pstm = null;
    try {
        cn = new MySqlConectar().getConnection();
        String sql = "UPDATE productos SET Descripcion=?, Precio=?, Cantidad=?, Popularidad=?, idCategoria=?, imagen=? WHERE IdProducto=?";
        pstm = cn.prepareStatement(sql);
        pstm.setString(1, producto.getDescripcion());
        pstm.setDouble(2, producto.getPrecio());
        pstm.setInt(3, producto.getCantidad());
        pstm.setString(4, producto.getPopularidad());
        pstm.setInt(5, producto.getIdCategoria());
        pstm.setString(6, producto.getImagen());
        pstm.setInt(7, producto.getIdProducto());
        salida = pstm.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (pstm != null) {
                pstm.close();
            }
            if (cn != null) {
                cn.close();
            }
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
    }
    return salida;
}

    @Override
    public int deleteById(int idProducto) {
        int salida = -1;
        Connection cn = null;
        PreparedStatement pstm = null;
        try {
            cn = new MySqlConectar().getConnection();
            String sql = "DELETE FROM productos WHERE IdProducto=?";
            pstm = cn.prepareStatement(sql);
            pstm.setInt(1, idProducto);
            salida = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return salida;
    }

    // Lista los productos por idCategoria
    @Override
    public List<ProductoDash> findProductosByCategoria(int idCategoria) {
        List<ProductoDash> lista = new ArrayList<>();
        Connection cn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            cn = new MySqlConectar().getConnection();
            String sql = "SELECT * FROM productos WHERE idCategoria = ?";
            pstm = cn.prepareStatement(sql);
            pstm.setInt(1, idCategoria); // Set the idCategoria parameter
            rs = pstm.executeQuery();
            while (rs.next()) {
                ProductoDash producto = new ProductoDash();
                producto.setIdProducto(rs.getInt("IdProducto"));
                producto.setDescripcion(rs.getString("Descripcion"));
                producto.setPrecio(rs.getDouble("Precio"));
                producto.setCantidad(rs.getInt("Cantidad"));
                producto.setPopularidad(rs.getString("Popularidad"));
                producto.setIdCategoria(rs.getInt("idCategoria"));
                producto.setImagen(rs.getString("imagen"));
                lista.add(producto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return lista;
    }
}
