package Interfaz;

import Modelo.ProductoDash;
import java.util.List;

public interface CRUDProducto {
    List<ProductoDash> findAllProducto();
    ProductoDash findProductoById(int idProducto);
    int save(ProductoDash producto);
    int update(ProductoDash producto);
    int deleteById(int idProducto);
    public List<ProductoDash> findProductosByCategoria(int idCategoria);
}
