

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Registrar Producto</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Incluir jQuery desde un CDN -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    </head>
    <body>
        <div class="container">
            <h2>Registrar Producto</h2>
            <form id="productoForm" action="../ServletProducto?accion=guardar" method="POST">
                <div class="mb-3">
                    <label for="descripcion" class="form-label">Descripción</label>
                    <input type="text" class="form-control" id="descripcion" name="descripcionPag" required>
                </div>
                <div class="mb-3">
                    <label for="precio" class="form-label">Precio</label>
                    <input type="number" class="form-control" id="precio" name="precioPag" step="0.01" required>
                </div>
                <div class="mb-3">
                    <label for="cantidad" class="form-label">Cantidad</label>
                    <input type="number" class="form-control" id="cantidad" name="cantidadPag" required>
                </div>
                <div class="mb-3">
                    <label for="popularidad" class="form-label">Popularidad</label>
                    <select class="form-select" id="popularidad" name="popularidadPag" required>
                        <option value="Alta">Alta</option>
                        <option value="Baja">Baja</option>
                        <option value="Media">Media</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="categoria" class="form-label">Categoría</label>
                    <select class="form-select" id="categoria" name="idCategoriaPag" required>
                        <option value="" selected>[Seleccione una categoría]</option>
                    </select>
                </div>
              <button type="submit" class="btn btn-primary">Guardar</button>

            </form>

        </div>


        <!--esto es para el caudro de categorias-->
        <script>
            $(document).ready(function () {
                // Realizar una solicitud AJAX al servlet
                $.ajax({
                    url: '../ServletCategoriaJSON',
                    type: 'GET',
                    dataType: 'json',
                    success: function (categorias) {
                        var select = $('#categoria');
                        $.each(categorias, function (index, categoria) {
                            select.append($('<option></option>').val(categoria.idCategoria).text(categoria.nombreCategoria));
                        });
                    },
                    error: function (xhr, status, error) {
                        console.error('Error al cargar las categorías. Estado de la solicitud:', status);
                    }
                });
            });
        </script>




    </body>
</html>