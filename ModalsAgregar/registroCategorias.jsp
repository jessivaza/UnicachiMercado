<%-- 
    Document   : registroCategorias
    Created on : 6 jun. 2024, 12:53:58
    Author     : Brandon
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registrar Categoría</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h2>Registrar Categoría</h2>
        <form action="../ServletCategoria?accion=guardar" method="post">
            <div class="mb-3">
                <label for="nombreCategoria" class="form-label">Nombre de Categoría</label>
                <input type="text" class="form-control" id="nombreCategoria" name="nombreCPag" required>
            </div>
            <div class="mb-3">
                <label for="descripcion" class="form-label">Descripción</label>
                <input type="text" class="form-control" id="descripcion" name="descripcionCPag" required>
            </div>
            <div class="mb-3">
                <label for="cantidadProductos" class="form-label">Cantidad de Productos</label>
                <input type="number" class="form-control" id="cantidadProductos" name="cantidadProPag" required>
            </div>
            <div class="mb-3">
                <label for="precioPromedio" class="form-label">Precio Promedio</label>
                <input type="number" class="form-control" id="precioPromedio" name="precioCPag" step="0.01" required>
            </div>
            <div class="mb-3">
                <label for="popularidad" class="form-label">Popularidad</label>
                <select class="form-select" id="popularidad" name="popularidadCPag" required>
                    <option value="Alta">Alta</option>
                    <option value="Baja">Baja</option>
                    <option value="Media">Media</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Registrar</button>
        </form>
    </div>
</body>
</html>