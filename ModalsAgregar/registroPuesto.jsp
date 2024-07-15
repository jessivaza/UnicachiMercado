<%-- 
    Document   : registroPuesto
    Created on : 6 jun. 2024, 13:08:18
    Author     : Brandon
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registrar Puesto</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Incluir jQuery desde un CDN -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <div class="container">
        <h2>Registrar Puesto</h2>
        <form action="../ServletPuestos?accion=guardar" method="post">
            <div class="mb-3">
                <label for="categoria" class="form-label">Categoría</label>
                <select class="form-select" id="categoria" name="idCategoriaPuPag" required>
                    <option value="" selected>[Seleccione una categoría]</option>
                </select>
            </div>
            <div class="mb-3">
                <label for="producto" class="form-label">Producto</label>
                <select class="form-select" id="producto" name="idProductoPuPag" required>
                    <option value="" selected>[Seleccione un producto]</option>
                </select>
            </div>
            <div class="mb-3">
                <label for="cliente" class="form-label">Cliente</label>
                <select class="form-select" id="cliente" name="idClientePuPag" required>
                    <option value="" selected>[Seleccione un cliente]</option>
                </select>
            </div>
            <div class="mb-3">
                <label for="dueño" class="form-label">Dueño del Puesto</label>
                <input type="text" class="form-control" id="dueño" name="duenoPuPag" required>
            </div>
            <div class="mb-3">
                <label for="estado" class="form-label">Estado</label>
                <select class="form-select" id="estado" name="estadoPuPag" required>
                    <option value="true">Abierto</option>
                    <option value="false">Cerrado</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Registrar</button>
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

<!--esto es para el cuadro de productos-->
<script>
    $(document).ready(function () {
        // Realizar una solicitud AJAX al servlet
        $.ajax({
            url: '../ServletProductoJSON', // Ruta del servlet, asumiendo que está configurado correctamente
            type: 'GET',
            dataType: 'json',
            success: function (productos) {
                var select = $('#producto');
                $.each(productos, function (index, producto) {
                    select.append($('<option></option>').val(producto.idProducto).text(producto.descripcion));
                });
            },
            error: function (xhr, status, error) {
                console.error('Error al cargar los productos. Estado de la solicitud:', status);
            }
        });
    });
</script>

<!-- esto es para el cuadro de clientes -->
<script>
    $(document).ready(function () {
        // Realizar una solicitud AJAX al servlet
        $.ajax({
            url: '../ServletClienteJSON', // Ruta del servlet, asumiendo que está configurado correctamente
            type: 'GET',
            dataType: 'json',
            success: function (clientes) {
                var select = $('#cliente');
                $.each(clientes, function (index, cliente) {
                    select.append($('<option></option>').val(cliente.idCliente).text(cliente.nombre + ' ' + cliente.apellido));
                });
            },
            error: function (xhr, status, error) {
                console.error('Error al cargar los clientes. Estado de la solicitud:', status);
            }
        });
    });
</script>
    
    
</body>
</html>
