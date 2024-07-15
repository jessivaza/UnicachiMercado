<%-- 
    Document   : prueba
    Created on : 6 jun. 2024, 19:30:09
    Author     : Brandon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Listado de Categorías</title>
    <!-- Incluir jQuery desde un CDN -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>

<div class="mb-3">
    <label for="categoria" class="form-label">Categoría</label>
    <select class="form-select" id="categoria" name="categoria" required>
        <option value="" selected>[Seleccione una categoría]</option>
    </select>
</div>

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