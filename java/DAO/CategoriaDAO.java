package DAO;

import Interfaz.CRUDCategoria;
import Modelo.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.MySqlConectar;

public class CategoriaDAO implements CRUDCategoria{

    //para listar categorias
    @Override
    public List<Categoria> findAllCategoria() {
        List<Categoria> lista = new ArrayList<>();
        Connection cn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            cn = new MySqlConectar().getConnection();
            String sql = "SELECT * FROM categoria";
            pstm = cn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setIdCategoria(rs.getInt("idCategoria"));
                categoria.setNombreCategoria(rs.getString("nombreCategoria"));
                categoria.setDescripcion(rs.getString("descripcion"));
                categoria.setCantidadProductos(rs.getInt("cantidadProductos"));
                categoria.setPrecioPromedio(rs.getDouble("precioPromedio"));
                categoria.setPopularidad(rs.getString("popularidad"));
                lista.add(categoria);
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

    
    //para listar por idcategoria
    @Override
    public Categoria findCategoriaById(int idCategoria) {
        Categoria categoria = null;
        Connection cn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            cn = new MySqlConectar().getConnection();
            String sql = "SELECT * FROM categoria WHERE idCategoria = ?";
            pstm = cn.prepareStatement(sql);
            pstm.setInt(1, idCategoria);
            rs = pstm.executeQuery();
            if (rs.next()) {
                categoria = new Categoria();
                categoria.setIdCategoria(rs.getInt("idCategoria"));
                categoria.setNombreCategoria(rs.getString("nombreCategoria"));
                categoria.setDescripcion(rs.getString("descripcion"));
                categoria.setCantidadProductos(rs.getInt("cantidadProductos"));
                categoria.setPrecioPromedio(rs.getDouble("precioPromedio"));
                categoria.setPopularidad(rs.getString("popularidad"));
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
        return categoria;
    }
    
    
    //GUARDA USUARIO
    @Override
    public int save(Categoria categoria) {
        int salida = -1;
        Connection cn = null;
        PreparedStatement pstm = null;
        try {
            cn = new MySqlConectar().getConnection();
            String sql = "INSERT INTO categoria (nombreCategoria, descripcion, cantidadProductos, precioPromedio, popularidad) VALUES (?,?,?,?,?)";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, categoria.getNombreCategoria());
            pstm.setString(2, categoria.getDescripcion());
            pstm.setInt(3, categoria.getCantidadProductos());
            pstm.setDouble(4, categoria.getPrecioPromedio());
            pstm.setString(5, categoria.getPopularidad());
            salida = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(pstm != null)
                    pstm.close();
                if(cn != null)
                    cn.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return salida;    
    }

    //actualiza categoria
    @Override
    public int update(Categoria categoria) {
        int salida = -1;
        Connection cn = null;
        PreparedStatement pstm = null;
        try {
            cn = new MySqlConectar().getConnection();
            String sql = "UPDATE categoria SET nombreCategoria=?, descripcion=?, cantidadProductos=?, precioPromedio=?, popularidad=? WHERE idCategoria=?";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, categoria.getNombreCategoria());
            pstm.setString(2, categoria.getDescripcion());
            pstm.setInt(3, categoria.getCantidadProductos());
            pstm.setDouble(4, categoria.getPrecioPromedio());
            pstm.setString(5, categoria.getPopularidad());
            pstm.setInt(6, categoria.getIdCategoria());
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

    //elimina categoria por id
    @Override
    public int deleteById(int idCategoria) {
        int salida = -1;
        Connection cn = null;
        PreparedStatement pstm = null;
        try {
            cn = new MySqlConectar().getConnection();
            String sql = "DELETE FROM categoria WHERE idCategoria=?";
            pstm = cn.prepareStatement(sql);
            pstm.setInt(1, idCategoria);
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
