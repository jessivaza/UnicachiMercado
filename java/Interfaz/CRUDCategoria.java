package Interfaz;

import Modelo.Categoria;
import java.util.List;

public interface CRUDCategoria {
    List<Categoria> findAllCategoria();
    Categoria findCategoriaById(int idCategoria);
    int save(Categoria categoria);
    int update(Categoria categoria);
    int deleteById(int idCategoria);
}
