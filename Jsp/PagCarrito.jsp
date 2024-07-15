<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="Modelo.DetallePedido"%>
<%@ page import="Modelo.Producto"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="utils.Carrito"%>

<%
    Carrito carritoUtil = new Carrito();
    ArrayList<DetallePedido> carrito = carritoUtil.ObtenerSesion(request);
    double total = carritoUtil.ImporteTotal(carrito);
    // Depuración: Imprimir el contenido del carrito
    System.out.println("Contenido del carrito: " + carrito);
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mi Carrito</title>
        <meta name="format-detection" content="telephone=no">
        <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta charset="utf-8">
        <link rel="icon" href="IMG/img/logo1.png" type="image/x-icon">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link href="../CSS/cssCarrito.css" rel="stylesheet" type="text/css"/>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    </head>
    <body>
        <jsp:include page="navegacion.jsp"/>
        <jsp:include page="Mensaje.jsp"/>
        <div class="container-fluid mt-4">
            <div class="row">
                <div class="col-lg-7">
                    <h5>Carrito</h5>
                    <hr />
                    <div class="card">
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered table-striped">
                                    <thead class="bg-warning text-dark">
                                        <tr>
                                            <th>Imagen</th>
                                            <th>Producto</th>
                                            <th>Precio (S/)</th>
                                            <th>Cantidad</th>
                                            <th>Importe (S/)</th>
                                            <th>Acción</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <% 
                                            if (carrito == null || carrito.size() == 0) {
                                        %>
                                        <tr class="text-center">
                                            <td colspan="6">Carrito Vacio!</td>
                                        </tr>
                                        <% 
                                            } else {
                                                for (int i = 0; i < carrito.size(); i++) {
                                                    DetallePedido item = carrito.get(i);
                                                    Producto producto = item.getProducto();
                                        %>
                                        <tr>
                                            <td>
                                                <img src="../IMG/img/<%= producto.getImagen() %>" width="50" height="60">
                                            </td>
                                            <td><%= producto.getNombre() %></td>
                                            <td><%= producto.getPrecio() %></td>
                                            <td><%= item.getCantidad() %></td>
                                            <td><%= item.Importe() %></td>
                                            <td>
                                                <a href="CarritoControlador?accion=eliminar&indice=<%= i %>" title="Eliminar" class="btn btn-danger btn-sm">
                                                    <i class="fa fa-trash-alt"></i> Eliminar
                                                </a>
                                            </td>
                                        </tr>
                                        <% 
                                                } // Cierra el bucle for
                                            } // Cierra el bloque else
                                        %>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="contenedor">
                        <div class="payment-info">
                            <form id="purchaseForm" action="PedidoControlador" method="post" onsubmit="return validateForm()">
                                <div class="d-flex justify-content-between align-items-center">
                                    <span>Detalles de la Compra</span>
                                    <img class="rounded" src="https://i.imgur.com/WU501C8.jpg" width="30" alt="Avatar">
                                </div>
                                <span class="type d-block mt-3 mb-1">Tipo de tarjeta</span>
                                <label class="radio"> 
                                    <input type="radio" name="card" value="payment" checked> 
                                    <span><img width="30" src="https://img.icons8.com/color/48/000000/mastercard.png" alt="Mastercard"/></span> 
                                </label>

                                <label class="radio"> 
                                    <input type="radio" name="card" value="payment"> 
                                    <span><img width="30" src="https://img.icons8.com/officel/48/000000/visa.png" alt="Visa"/></span> 
                                </label>

                                <label class="radio"> 
                                    <input type="radio" name="card" value="payment"> 
                                    <span><img width="30" src="https://img.icons8.com/ultraviolet/48/000000/amex.png" alt="Amex"/></span> 
                                </label>

                                <label class="radio"> 
                                    <input type="radio" name="card" value="payment"> 
                                    <span><img width="30" src="https://img.icons8.com/officel/48/000000/paypal.png" alt="Paypal"/></span> 
                                </label>
                                <div class="form-group">
                                    <label class="credit-card-label" for="cardName">Nombre en la tarjeta</label>
                                    <input type="text" id="cardName" name="cardName" class="form-control credit-inputs border" placeholder="Nombre en la tarjeta" required>
                                </div>
                                <div class="form-group">
                                    <label class="credit-card-label" for="cardNumber">Número de tarjeta</label>
                                    <input type="text" id="cardNumber" name="cardNumber" class="form-control credit-inputs border" placeholder="0000 0000 0000 0000" maxlength="14" oninput="this.value = this.value.slice(0, 14)" required>
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <label class="credit-card-label" for="cardExp">Fecha de expiración</label>
                                        <input type="text" id="cardExp" name="cardExp" class="form-control credit-inputs border" placeholder="12/24" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label class="credit-card-label" for="cardCVV">CVV</label>
                                        <input type="text" id="cardCVV" name="cardCVV" class="form-control credit-inputs border" placeholder="CVV" maxlength="3" oninput="this.value = this.value.slice(0, 3)" required>
                                    </div>
                                </div>

                                <hr class="line">
                                <div class="d-flex justify-content-between information">
                                    <span>Subtotal </span>
                                    <span>$<%= total -(total * 0.18) %></span>
                                </div>
                                <div class="d-flex justify-content-between information">
                                    <span>IGV (18%)</span>
                                    <span>$<%= total * 0.18 %></span>
                                </div>
                                <div class="d-flex justify-content-between information">
                                    <span>Total</span>
                                    <span>$<%= total %></span>
                                </div>
                                <hr class="line">
                                <span class="type d-block mt-3 mb-1">Tipo de comprobante</span>
                                <div class="document-group">
                                    <label class="radio">
                                        <input type="radio" name="document" value="factura" checked>
                                        <span>Factura</span>
                                    </label>
                                    <input type="hidden" name="accion" value="generar_factura">
                                    <button id="facturaButton" <%= carrito.size() == 0 ? "disabled" : "" %> type="submit" class="btn btn-primary btn-sm">
                                        <div class="d-flex justify-content-between">
                                            <span><i class="fa fa-credit-card"></i>FACTURA</span>
                                        </div>
                                    </button>
                                </div>
                                &nbsp;  
                                <div class="document-group">
                                    <label class="radio">
                                        <input type="radio" name="document" value="boleta">
                                        <span>Boleta</span>
                                    </label>
                                    <input type="hidden" name="accion" value="generar_boleta">
                                    <button id="boletaButton" <%= carrito.size() == 0 ? "disabled" : "" %> type="submit" class="btn btn-primary btn-sm">
                                        <div class="d-flex justify-content-between">
                                            <span><i class="fa fa-credit-card"></i>BOLETA</span>
                                        </div>
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
        <script>
                                function validateForm() {
                                    const cardName = document.getElementById('cardName').value.trim();
                                    const cardNumber = document.getElementById('cardNumber').value.trim();
                                    const cardExp = document.getElementById('cardExp').value.trim();
                                    const cardCVV = document.getElementById('cardCVV').value.trim();

                                    if (!cardName || !cardNumber || !cardExp || !cardCVV) {
                                        Swal.fire({
                                            icon: 'warning',
                                            title: 'Todos los campos son obligatorios',
                                            text: 'Por favor, completa todos los campos del formulario.',
                                            confirmButtonText: 'OK'
                                        });
                                        return false;
                                    }
                                    return true;
                                }
        </script>
        <script>
            function updateButtons() {
                const boletaRadio = document.querySelector('input[name="document"][value="boleta"]');
                const facturaRadio = document.querySelector('input[name="document"][value="factura"]');
                const boletaButton = document.getElementById('boletaButton');
                const facturaButton = document.getElementById('facturaButton');

                if (boletaRadio.checked) {
                    boletaButton.disabled = false;
                    facturaButton.disabled = true;
                } else if (facturaRadio.checked) {
                    boletaButton.disabled = true;
                    facturaButton.disabled = false;
                }
            }

            document.addEventListener('DOMContentLoaded', (event) => {
                updateButtons(); // Llama a la función al cargar la página

                const radios = document.querySelectorAll('input[name="document"]');
                radios.forEach((radio) => {
                    radio.addEventListener('change', updateButtons);
                });
            });
        </script>
    </body>
</html>