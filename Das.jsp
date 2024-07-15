<%@page import="Modelo.Categoria"%>
<%@page import="DAO.CategoriaDAO"%>
<%@page import="Modelo.ProductoDash"%>
<%@page import="DAO.ProductoDashDAO"%>
<%@ page import="Modelo.ClienteDash" %>
<%@ page import="DAO.ClienteDashDAO" %>
<%@ page import="Modelo.Puesto" %>
<%@ page import="DAO.PuestoDAO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Bienvenido Administrador</title>
        <meta name="format-detection" content="telephone=no">
        <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta charset="utf-8">
        <link rel="icon" href="IMG/img/logo1.png" type="image/x-icon">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="CSS/css/cssDash.css" rel="stylesheet" type="text/css"/>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <!-- Incluir jQuery desde un CDN -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            /*
             $(document).ready(function(){
             // Hide all sections initially
             $('.section').hide();
             
             // Show the summary section by default
             $('#summary').show();
             
             // Handle sidebar link clicks
             $('.sidebar a').click(function(e) {
             e.preventDefault();
             var targetSection = $(this).attr('href');
             $('.section').hide();
             $(targetSection).show();
             });
             });*/

            $(document).ready(function () {
                // Inicia la selección mostrando el resumen ejecutivo por defecto
                $('#summary').show();
                // Restaura la selección desde la memoria
                var selectedSection = localStorage.getItem('selectedSection');
                if (selectedSection) {
                    $(selectedSection).show();
                }

                // Maneja la selección al hacer clic en los enlaces de la barra lateral
                $('.sidebar a').click(function (e) {
                    e.preventDefault();
                    var targetSection = $(this).attr('href');
                    $('.section').hide();
                    $(targetSection).show();
                    // Guarda la selección en la memoria
                    localStorage.setItem('selectedSection', targetSection);
                });
                // Recarga la página cuando se cierra cualquier modal
                $('.modal').on('hidden.bs.modal', function () {
                    location.reload();
                });
            });
        </script>
        <style>
            .img{
                width: 85px;
            }
           
        </style>
    </head>
    <body>
        <div class="sidebar">
            <h2>ADM0001</h2>
            <ul>
                <li><a href="#summary">Resumen Ejecutivo</a></li>
                <li><a href="#productos">Productos</a></li>
                <li><a href="#categoria">Categorias</a></li>
                <li><a href="#puestos">Puestos</a></li>
                <li><a href="#Clientes">Clientes</a></li>
            </ul>
            <div class="text-center mt-4">
                    <form action="Ingresar.jsp" method="post">
                        <!-- Utilizando Bootstrap para el botón de salir -->
                        <button class="btn btn-light" type="submit">Salir</button>
                    </form>
                </div>
        </div>

        <div class="content">
            <h1>Bienvenido Administrador</h1>

            <div id="summary" class="section">
                <h2>Resumen Ejecutivo</h2>
                <div class="stats-container">
                    <!-- Canvas para el gráfico circular -->
                    <div class="stats-box" style="display: inline-block;">
                        <canvas id="graficoPequeno" width="350" height="350"></canvas>
                    </div>

                    <!-- Canvas para el gráfico de barras -->
                    <div class="stats-box" style="display: inline-block;">
                        <canvas id="graficoBarras" width="350" height="350"></canvas>
                    </div>

                    <!-- Canvas para el gráfico de líneas -->
                    <div class="stats-box" style="display: inline-block;">
                        <canvas id="graficoLineas" width="350" height="350"></canvas>
                    </div>
                </div>
            </div>
        </div>
        <div  
            <canvas id="graficoPequeno" width="400" height="400"></canvas>
            <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
            <script>

            var nombresCategoriasJson = '<%= session.getAttribute("nombresCategoriasJson") %>';
            var cantidadesCategoriasJson = '<%= session.getAttribute("cantidadesCategoriasJson") %>';
            // Parsear los datos JSON
            var nombresCategorias = JSON.parse(nombresCategoriasJson);
            var cantidadesCategorias = JSON.parse(cantidadesCategoriasJson);
            // Código para el gráfico circular
            var ctx1 = document.getElementById('graficoPequeno').getContext('2d');
            var graficoPequeno = new Chart(ctx1, {
                type: 'doughnut',
                data: {
                    labels: nombresCategorias,
                    datasets: [{
                            data: cantidadesCategorias,
                            backgroundColor: ['#E57373', '#81C784', '#64B5F6', '#BA68C8', '#FFD54F', '#FF8A65', '#4DB6AC', '#D4E157'],
                            hoverBackgroundColor: ['#FFCDD2', '#C8E6C9', '#BBDEFB', '#E1BEE7', '#FFF9C4', '#FFCCBC', '#B2DFDB', '#DCEDC8']
                        }]
                },
                options: {
                    responsive: false,
                    maintainAspectRatio: false
                }
            });
            </script>
        </div>  
        <div
            <canvas id="graficoBarras" width="400" height="400"></canvas>
            <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
            <script>

            var nombresCategoriasJson = '<%= session.getAttribute("nombresCategoriasJson") %>';
            var cantidadesCategoriasJson = '<%= session.getAttribute("cantidadesCategoriasJson") %>';

            // Parsear los datos JSON
            var nombresCategorias = JSON.parse(nombresCategoriasJson);
            var cantidadesCategorias = JSON.parse(cantidadesCategoriasJson);

            // Código para el gráfico de barras
            var ctx1 = document.getElementById('graficoBarras').getContext('2d');
            var graficoPequeno = new Chart(ctx1, {
                type: 'bar',
                data: {
                    labels: nombresCategorias,
                    datasets: [{
                            label: 'Cantidades',
                            data: cantidadesCategorias,
                            backgroundColor: ['#E57373', '#81C784', '#64B5F6', '#BA68C8', '#FFD54F', '#FF8A65', '#4DB6AC', '#D4E157'],
                            hoverBackgroundColor: ['#FFCDD2', '#C8E6C9', '#BBDEFB', '#E1BEE7', '#FFF9C4', '#FFCCBC', '#B2DFDB', '#DCEDC8']
                        }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    scales: {
                        x: {
                            beginAtZero: true
                        },
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });
            </script>
        </div>
        <script>
            // Código para el gráfico de líneas
            var ctx3 = document.getElementById('graficoLineas').getContext('2d');
            var graficoLineas = new Chart(ctx3, {
                type: 'line',
                data: {
                    labels: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio'],
                    datasets: [{
                            label: 'Ventas 2021',
                            data: [${sessionScope.cantidaProducto}, 800, 600, 700, 900, 750],
                            backgroundColor: 'rgba(255, 99, 132, 0.2)',
                            borderColor: 'rgba(255, 99, 132, 1)',
                            borderWidth: 1
                        },
                        {
                            label: 'Ventas 2022',
                            data: [600, 750, 700, 850, 950, 800],
                            backgroundColor: 'rgba(54, 162, 235, 0.2)',
                            borderColor: 'rgba(54, 162, 235, 1)',
                            borderWidth: 1
                        },
                        {
                            label: 'Ventas 2023',
                            data: [700, 900, 800, 950, 1000, 850],
                            backgroundColor: 'rgba(255, 206, 86, 0.2)',
                            borderColor: 'rgba(255, 206, 86, 1)',
                            borderWidth: 1
                        }]
                },
                options: {
                    responsive: false,
                    maintainAspectRatio: false,
                    scales: {
                        yAxes: [{
                                ticks: {
                                    beginAtZero: true
                                }
                            }]
                    }
                }
            });

        </script>
        <div id="productos" class="section" style="margin-left: 15%;">
            <h2>Productos</h2>
            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#registroProductoModal">
                    Agregar Producto
                </button>

                <div style="background: black">
                </div>
                <form action="ServletProducto?accion=exel" method="post" style="text-align: right;">
                    <button type="submit"  class="btn btn-success">Exportar a Excel</button>
                </form>
                <form action="ServletProducto?accion=pdf" method="post" style="text-align: right;">
                    <button type="submit" id="exportarPDFButton" class="btn btn-danger" >Exportar a PDF</button>
                </form>
            </div>
            <table class="table">
                <thead>
                    <tr>
                        <th class="text-center">Id Producto</th>
                        <th class="text-center">Descripción</th>
                        <th class="text-center">Precio</th>
                        <th class="text-center">Cantidad</th>
                        <th class="text-center">Popularidad</th>
                        <th class="text-center">Categoria</th>
                        <th class="text-center">Imagen</th>
                        <th class="text-center">Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                       ProductoDashDAO productoDashDAO = new ProductoDashDAO();
                        List<ProductoDash> listaProductosDash = productoDashDAO.findAllProducto();
                        for (ProductoDash producto : listaProductosDash) { 
                    %>
                    <tr>
                        <td class="text-center"><%= producto.getIdProducto() %></td>
                        <td class="text-center"><%= producto.getDescripcion() %></td>
                        <td class="text-center"><%= producto.getPrecio() %></td>
                        <td class="text-center"><%= producto.getCantidad() %></td>
                        <td class="text-center"><%= producto.getPopularidad() %></td>
                        <td class="text-center"><%= producto.getIdCategoria() %></td>
                        <td class="text-center">
                            <img class="img" src="IMG/img/<%= producto.getImagen() %>" </td> 
                        <td class="text-center">
                            <button class="btn btn-warning editar-producto" data-bs-toggle="modal" data-bs-target="#editarProductoModal" 
                                    data-id="<%= producto.getIdProducto() %>"
                                    data-descripcion="<%= producto.getDescripcion() %>"
                                    data-precio="<%= producto.getPrecio() %>"
                                    data-cantidad="<%= producto.getCantidad() %>"
                                    data-popularidad="<%= producto.getPopularidad() %>"
                                    data-categoria="<%= producto.getIdCategoria() %>"
                                    data-imagen="<%= producto.getImagen() %>">
                                Editar
                            </button>
                            <button class="btn btn-danger" onclick="eliminarElemento('producto', <%= producto.getIdProducto() %>)">Eliminar</button>
                        </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>

        <!-- Modal Producto -->
        <div class="modal fade" id="registroProductoModal" tabindex="-1" aria-labelledby="registroProductoModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="registroProductoModalLabel">Agregar Producto</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <iframe src="ModalsAgregar/registroProducto.jsp" style="width: 100%; height: 400px; border: none;"></iframe>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal Producto editar -->
        <div class="modal fade" id="editarProductoModal" tabindex="-1" aria-labelledby="editarProductoModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-height">
                <div class="modal-content modal-height">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editarProductoModalLabel">Editar Producto</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <h2>Editar Producto</h2>
                        <form id="productoForm" action="ServletProducto?accion=editar" method="POST">
                            <div class="mb-3">
                                <label for="descripcion" class="form-label">Descripción</label>
                                <input type="text" class="form-control" id="descripcion" name="descripcionPag" required>
                            </div>
                            <div class="mb-3">
                                <label for="precio" class="form-label">Precio</label>
                                <input type="number" class="form-control" id="precio" name="precioPag" required>
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
                                <label for="categoriaCaja" class="form-label">Categoría</label>
                                <select class="form-select" id="productoFormCategoria" name="idCategoriaPag" required>
                                    <option value="" selected>[Seleccione una categoría]</option>
                                </select>
                            </div>
                            <input type="hidden" id="idProducto" name="idProductoPag" value="<%= request.getParameter("idProducto") %>">
                            <button type="submit" class="btn btn-primary">Guardar</button>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>




        <div id="categoria" class="section" style="margin-left: 15%;">
            <h2>Categorías</h2>
            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#registroCategoriaModal">
                    Agregar Categoría
                </button>
                <form action="ServletCategoria?accion=exel" method="post" style="text-align: right;">
                    <button type="submit"  class="btn btn-success">Exportar a Excel</button>
                </form>
                <form action="ServletCategoria?accion=pdf" method="post" style="text-align: right;">
                    <button type="submit" id="exportarPDFButton" class="btn btn-danger" >Exportar a PDF</button>
                </form>
            </div>
            <table>
                <thead>
                    <tr>
                        <th class="text-center">Id Categoría</th>
                        <th class="text-center">Nombre Categoría</th>
                        <th class="text-center">Descripción</th>
                        <th class="text-center">Cantidad de Productos</th>
                        <th class="text-center">Precio Promedio</th>
                        <th class="text-center">Popularidad</th>
                        <th class="text-center">Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        CategoriaDAO categoriaDAO = new CategoriaDAO();
                        List<Categoria> listaCategorias = categoriaDAO.findAllCategoria();
                        for (Categoria categoria : listaCategorias) { 
                    %>
                    <tr>
                        <td class="text-center"><%= categoria.getIdCategoria() %></td>
                        <td class="text-center"><%= categoria.getNombreCategoria() %></td>
                        <td class="text-center"><%= categoria.getDescripcion() %></td>
                        <td class="text-center"><%= categoria.getCantidadProductos() %></td>
                        <td class="text-center"><%= categoria.getPrecioPromedio() %></td>
                        <td class="text-center"><%= categoria.getPopularidad() %></td>
                        <td class="text-center">
                            <button class="btn btn-warning editar-categoria" data-bs-toggle="modal" data-bs-target="#editarCategoriaModal" 
                                    data-id="<%= categoria.getIdCategoria() %>"
                                    data-nombre="<%= categoria.getNombreCategoria() %>"
                                    data-descripcion="<%= categoria.getDescripcion() %>"
                                    data-cantidad="<%= categoria.getCantidadProductos() %>"
                                    data-precio="<%= categoria.getPrecioPromedio() %>"
                                    data-popularidad="<%= categoria.getPopularidad() %>">
                                Editar
                            </button>

                            <button class="btn btn-danger" onclick="eliminarElemento('categoria', <%= categoria.getIdCategoria() %>)">Eliminar</button>
                        </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>

        <!-- Modal Categoria -->
        <div class="modal fade" id="registroCategoriaModal" tabindex="-1" aria-labelledby="registroCategoriaModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="registroCategoriaModalLabel">Agregar Categoría</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <iframe src="ModalsAgregar/registroCategorias.jsp" style="width: 100%; height: 400px; border: none;"></iframe>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal Categoria editar -->
        <div class="modal fade" id="editarCategoriaModal" tabindex="-1" aria-labelledby="editarCategoriaModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-height">
                <div class="modal-content modal-height">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editarCategoriaModalLabel">Editar Categoría</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <h2>Editar Categoría</h2>
                        <form id="categoriaForm" action="ServletCategoria?accion=editar" method="post">
                            <div class="mb-3">
                                <label for="nombreCategoria" class="form-label">Nombre Categoría</label>
                                <input type="text" class="form-control" id="nombreCategoria" name="nombreCPag"  required>
                            </div>
                            <div class="mb-3">
                                <label for="descripcion" class="form-label">Descripción</label>
                                <input type="text" class="form-control" id="descripcionC" name="descripcionCPag"  required>
                            </div>
                            <div class="mb-3">
                                <label for="cantidadProductos" class="form-label">Cantidad de Productos</label>
                                <input type="number" class="form-control" id="cantidadProductos" name="cantidadProPag"  required>
                            </div>
                            <div class="mb-3">
                                <label for="precioPromedio" class="form-label">Precio Promedio</label>
                                <input type="number" class="form-control" id="precioPromedio" name="precioCPag"   required>
                            </div>
                            <div class="mb-3">
                                <label for="popularidad" class="form-label">Popularidad</label>
                                <select class="form-select" id="popularidadC" name="popularidadCPag" required>
                                    <option value="Alta">Alta</option>
                                    <option value="Baja">Baja</option>
                                    <option value="Media">Media</option>
                                </select>
                            </div>
                            <input  type="hidden" id="idCategoria" name="idCategoriaPag" value="<%= request.getParameter("idCategoria") %>">
                            <button type="submit" class="btn btn-primary">Guardar</button>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>
           
                    
                    
        <div id="puestos" class="section" style="margin-left: 15%;">
            <h2>Puestos</h2>
            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#registroPuestoModal">
                    Agregar Puesto
                </button>
                <form action="ServletPuestos?accion=exel" method="post" style="text-align: right;">
                    <button type="submit" class="btn btn-success">Exportar a Excel</button>
                </form>
                <form action="ServletPuestos?accion=pdf" method="post" style="text-align: right;">
                    <button type="submit" id="exportarPDFButton" class="btn btn-danger" name="formato" value="pdf">Exportar a PDF</button>
                </form>
            </div>
            <table>
                <thead>
                    <tr>
                        <th class="text-center">Id Puesto</th>
                        <th class="text-center">Id Categoría</th>
                        <th class="text-center">Id Producto</th>
                        <th class="text-center">Id Cliente</th>
                        <th class="text-center">Dueño del Puesto</th>
                        <th class="text-center">Estado</th>
                        <th class="text-center">Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        PuestoDAO dao = new PuestoDAO();
                        List<Puesto> listaPuestos = dao.findAllPuestos();
                        for (Puesto puesto : listaPuestos) { 
                    %>
                    <tr>
                        <td class="text-center"><%= puesto.getIdPuesto() %></td>
                        <td class="text-center"><%= puesto.getIdCategoria() %></td>
                        <td class="text-center"><%= puesto.getIdProducto() %></td>
                        <td class="text-center"><%= puesto.getIdCliente() %></td>
                        <td class="text-center"><%= puesto.getDueño() %></td>
                        <td class="text-center"><%= puesto.isEstado() %></td>
                        <td class="text-center">
                            <button class="btn btn-warning editar-puesto" data-bs-toggle="modal" data-bs-target="#editarPuestoModal" 
                                    data-id="<%= puesto.getIdPuesto() %>"
                                    data-categoria="<%= puesto.getIdCategoria() %>"
                                    data-producto="<%= puesto.getIdProducto() %>"
                                    data-cliente="<%= puesto.getIdCliente() %>"
                                    data-dueno="<%= puesto.getDueño() %>"
                                    data-estadot="<%= puesto.isEstado() %>">
                                Editar
                            </button>
                             <button class="btn btn-danger" onclick="eliminarElemento('puesto', <%= puesto.getIdPuesto() %>)">Eliminar</button>
                        </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>

        <!-- Modal Puesto -->
        <div class="modal fade" id="registroPuestoModal" tabindex="-1" aria-labelledby="registroPuestoModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="registroPuestoModalLabel">Agregar Puesto</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <iframe src="ModalsAgregar/registroPuesto.jsp" style="width: 100%; height: 400px; border: none;"></iframe>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal Puesto editar -->
        <div class="modal fade" id="editarPuestoModal" tabindex="-1" aria-labelledby="editarPuestoModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-height">
                <div class="modal-content modal-height">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editarPuestoModalLabel">Editar Puesto</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <h2>Editar Puesto</h2>
                        <form id="puestoForm" action="ServletPuestos?accion=editar" method="post">
                            <div class="mb-3">
                                <label for="categoriaCaja" class="form-label">Categoría</label>
                                <select class="form-select" id="categoriaFormCategoria" name="idCategoriaPuPag" required>
                                    <option value="" selected>[Seleccione una categoría]</option>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="producto" class="form-label">Producto</label>
                                <select class="form-select" id="tiposProducto" name="idProductoPuPag" required>
                                    <option value="" selected>[Seleccione un producto]</option>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="clientee" class="form-label">Cliente</label>
                                <select class="form-select" id="clienteet" name="idClientePuPag" required>
                                    <option value="" selected>[Seleccione un cliente]</option>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="dueno" class="form-label">Dueño del Puesto</label>
                                <input type="text" class="form-control" id="duenos" name="duenoPuPag" value="" required>
                            </div>
                            <div class="mb-3">
                                <label for="estado" class="form-label">Estado</label>
                                <select class="form-select" id="estador" name="estadoPuPag" required>
                                    <option value="true">Abierto</option>
                                    <option value="false">Cerrado</option>
                                </select>
                            </div>
                            <input type="hidden" id="idPuestoP" name="idPuestoPuPag">
                            <button type="submit" class="btn btn-primary">Guardar</button>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>
                    


        <div id="Clientes" class="section" style="margin-left: 15%;">
            <h2>Clientes</h2>
            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#registroClienteModal">
                    Agregar Cliente
                </button>
                <form action="ServletCliente?accion=exel" method="post" style="text-align: right;">
                    <button type="submit"  class="btn btn-success">Exportar a Excel</button>
                </form>
                <form action="ServletCliente?accion=pdf" method="post" style="text-align: right;">
                    <button type="submit" id="exportarPDFButton" class="btn btn-danger" >Exportar a PDF</button>
                </form>

            </div>
            <table>
                <thead>
                    <tr>
                        <th class="text-center">Id cliente</th>
                        <th class="text-center">Nombre</th>
                        <th class="text-center">Apellido</th>
                        <th class="text-center">Dirección</th>
                        <th class="text-center">DNI</th>
                        <th class="text-center">Email</th>
                        <th class="text-center">Contraseña</th>
                        <th class="text-center">Teléfono</th>
                        <th class="text-center">Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        ClienteDashDAO clienteDashDAO = new ClienteDashDAO();
                        List<ClienteDash> listaClientes = clienteDashDAO.findAllClientesDash();
                        for (ClienteDash cliente : listaClientes) { 
                    %>
                    <tr>
                        <td class="text-center"><%= cliente.getIdCliente() %></td>
                        <td class="text-center"><%= cliente.getNombre() %></td>
                        <td class="text-center"><%= cliente.getApellido() %></td>
                        <td class="text-center"><%= cliente.getDireccion() %></td>
                        <td class="text-center"><%= cliente.getDni() %></td>
                        <td class="text-center"><%= cliente.getEmail() %></td>
                        <td class="text-center"><%= cliente.getContrasena() %></td>
                        <td class="text-center"><%= cliente.getTelefono() %></td>
                        <td class="text-center">
                            <button class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#editarClienteModal" 
                                    data-id="<%= cliente.getIdCliente() %>"
                                    data-nombre="<%= cliente.getNombre() %>"
                                    data-apellido="<%= cliente.getApellido() %>"
                                    data-direccion="<%= cliente.getDireccion() %>"
                                    data-dni="<%= cliente.getDni() %>"
                                    data-email="<%= cliente.getEmail() %>"
                                    data-contrasena="<%= cliente.getContrasena() %>"
                                    data-telefono="<%= cliente.getTelefono() %>">
                                Editar
                            </button>
                            <button class="btn btn-danger" onclick="eliminarElemento('cliente', <%= cliente.getIdCliente() %>)">Eliminar</button>
                        </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
   <!-- Modal Agregar Cliente -->
        <div class="modal fade" id="registroClienteModal" tabindex="-1" aria-labelledby="registroClienteModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="registroClienteModalLabel">Agregar Cliente</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <iframe src="ModalsAgregar/registroClientes.jsp" style="width: 100%; height: 400px; border: none;"></iframe>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal Editar Cliente -->
        <div class="modal fade" id="editarClienteModal" tabindex="-1" aria-labelledby="editarClienteModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-height">
                <div class="modal-content modal-height">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editarClienteModalLabel">Editar Cliente</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <h2>Editar Cliente</h2>
                        <form id="clienteForm" action="ServletUsuario?accion=editar" method="post">
                            <div class="mb-3">
                                <label for="nombre" class="form-label">Nombre</label>
                                <input type="text" class="form-control" id="nombre" name="nombreCliePag" required>
                            </div>
                            <div class="mb-3">
                                <label for="apellido" class="form-label">Apellido</label>
                                <input type="text" class="form-control" id="apellido" name="apellidoCliePag" required>
                            </div>
                            <div class="mb-3">
                                <label for="direccion" class="form-label">Dirección</label>
                                <input type="text" class="form-control" id="direccion" name="direccionCliePag" required>
                            </div>
                            <div class="mb-3">
                                <label for="dni" class="form-label">DNI</label>
                                <input type="text" class="form-control" id="dni" name="dniCliePag" required>
                            </div>
                            <div class="mb-3">
                                <label for="email" class="form-label">Email</label>
                                <input type="email" class="form-control" id="email" name="emailCliePag" required>
                            </div>
                            <div class="mb-3">
                                <label for="contrasena" class="form-label">Contraseña</label>
                                <input type="password" class="form-control" id="contrasena" name="contrasenaCliePag" required>
                            </div>
                            <div class="mb-3">
                                <label for="telefono" class="form-label">Teléfono</label>
                                <input type="text" class="form-control" id="telefono" name="telefonoCliePag" required>
                            </div>
                            <input type="hidden" id="idCliente" name="idClientePag">
                            <button type="submit" class="btn btn-primary">Guardar</button>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>

            
                    
<script src="js/accion.js" type="text/javascript"></script>
<!-- manda datos a form editar productos (usa la clase del boton editar) -->
<script>
    $(document).ready(function() {
        $('.editar-producto').click(function() {
            var idProducto = $(this).data('id');
            var descripcion = $(this).data('descripcion');
            var precio = $(this).data('precio');
            var cantidad = $(this).data('cantidad');
            var popularidad = $(this).data('popularidad');
            var categoria = $(this).data('categoria');

            $('#idProducto').val(idProducto);
            $('#descripcion').val(descripcion);
            $('#precio').val(precio);
            $('#cantidad').val(cantidad);
            $('#popularidad').val(popularidad);
            $('#productoFormCategoria').val(categoria); // Asignar valor a la entrada de categoría en el formulario de edición.
        });
    });
</script>

<!-- envia datos a form editar categoria (usa la clase del boton editar)-->
<script>
    $(document).ready(function() {
        $('.editar-categoria').click(function() {
            var idCategoria = $(this).data('id');
            var nombreCategoria = $(this).data('nombre');
            var descripcion = $(this).data('descripcion');
            var cantidadProductos = $(this).data('cantidad');
            var precioPromedio = $(this).data('precio');
            var popularidad = $(this).data('popularidad');

            $('#idCategoria').val(idCategoria);
            $('#nombreCategoria').val(nombreCategoria);
            $('#descripcionC').val(descripcion);
            $('#cantidadProductos').val(cantidadProductos);
            $('#precioPromedio').val(precioPromedio);
            $('#popularidadC').val(popularidad);
        });
    });
</script>
                         
<!-- evvia dato al form puesto(usa la clase del boton editar) -->
<script>
    $(document).ready(function() {
        $('.editar-puesto').click(function() {
            var idPuesto = $(this).data('id');
            var idCategoria = $(this).data('categoria');
            var idProducto = $(this).data('producto');
            var idCliente = $(this).data('cliente');
            var dueno = $(this).data('dueno');
            var estado = $(this).data('estadot');

            $('#idPuestoP').val(idPuesto);
            $('#categoriaFormCategoria').val(idCategoria);
            $('#tiposProducto').val(idProducto);
            $('#clienteet').val(idCliente);
            $('#duenos').val(dueno);
            $('#estador').val(estado);
        });
    });
</script>




<!-- envia dato al form usuario (usa la clase del boton editar)-->
<script>
    $(document).ready(function() {
        $('.btn-warning').click(function() {
            var idCliente = $(this).data('id');
            var nombre = $(this).data('nombre');
            var apellido = $(this).data('apellido');
            var direccion = $(this).data('direccion');
            var dni = $(this).data('dni');
            var email = $(this).data('email');
            var contrasena = $(this).data('contrasena');
            var telefono = $(this).data('telefono');

            $('#idCliente').val(idCliente);
            $('#nombre').val(nombre);
            $('#apellido').val(apellido);
            $('#direccion').val(direccion);
            $('#dni').val(dni);
            $('#email').val(email);
            $('#contrasena').val(contrasena);
            $('#telefono').val(telefono);
        });
    });
</script>



<!-- lista llena los cuadros de categorias -->
<script>
    $(document).ready(function () {
        // Función para cargar clientes en un campo de selección específico
        function cargarCategoriaEnSelect(selector) {
            // Realizar una solicitud AJAX al servlet
            $.ajax({
                url: 'ServletCategoriaJSON', // Ruta del servlet, asumiendo que está configurado correctamente
                type: 'GET',
                dataType: 'json',
                success: function (categorias) {
                    var select = $(selector);
                    $.each(categorias, function (index, categoria) {
                        select.append($('<option></option>').val(categoria.idCategoria).text(categoria.nombreCategoria));
                    });
                },
                error: function (xhr, status, error) {
                    console.error('Error al cargar los clientes. Estado de la solicitud:', status);
                }
            });
        }
        
        // Llamar a la función para cada formulario
        cargarCategoriaEnSelect('#productoFormCategoria');
        cargarCategoriaEnSelect('#categoriaFormCategoria');
        // Repetir el mismo patrón para los formularios restantes
    });
</script>

<!--esto es para el cuadro de productos-->
<script>
    $(document).ready(function () {
        // Realizar una solicitud AJAX al servlet
        $.ajax({
            url: 'ServletProductoJSON', // Ruta del servlet, asumiendo que está configurado correctamente
            type: 'GET',
            dataType: 'json',
            success: function (productos) {
                var select = $('#tiposProducto');
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
            url: 'ServletClienteJSON', // Ruta del servlet, asumiendo que está configurado correctamente
            type: 'GET',
            dataType: 'json',
            success: function (clientes) {
                var select = $('#clienteet');
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

<!-- Script para eliminar elementos usa los botones de eliminar -->
<script>
    function eliminarElemento(tipo, id) {
        // Determina la acción y el nombre del parámetro basándose en el tipo
        let action, paramName;

        switch(tipo) {
            case 'producto':
                action = 'ServletProducto?accion=eliminar';
                paramName = 'idProductoPag';
                break;
            case 'categoria':
                action = 'ServletCategoria?accion=eliminar';
                paramName = 'idCategoriaPag';
                break;
            case 'puesto':
                action = 'ServletPuestos?accion=eliminar';
                paramName = 'idPuestoPuPag';
                break;
            case 'cliente':
                action = 'ServletUsuario?accion=eliminar';
                paramName = 'idClientePag';
                break;
            default:
                console.error('Tipo no reconocido:', tipo);
                return;
        }

        // Crea un formulario oculto
        const form = document.createElement('form');
        form.method = 'post';
        form.action = action;
        form.style.display = 'none'; // Para ocultar el formulario
        
        // Crea un input para enviar el ID del elemento
        const idInput = document.createElement('input');
        idInput.type = 'hidden';
        idInput.name = paramName; // Nombre del parámetro ajustado
        idInput.value = id;
        
        // Agrega el input al formulario
        form.appendChild(idInput);
        
        // Agrega el formulario al cuerpo del documento
        document.body.appendChild(form);
        
        // Envía el formulario
        form.submit();
        
        // Recarga la página después de 1 segundo
        setTimeout(function() {
            location.reload();
        }, 1000);
    }
</script>
</body>
</html>
