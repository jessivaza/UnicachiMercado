<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <title>UNICACHI - Iniciar sesión</title>
    <link rel="icon" href="IMG/index_u/logo1.png" type="image/x-icon">
    <link href="CSS/cssIngresar.css" rel="stylesheet" type="text/css"/>
</head>
<body>
    <div class="container">
        <div class="login-container">
            <img src="IMG/img/logo1.png" alt="Ícono de inicio de sesión"/>
            <h2>Mercado Unicachi</h2>
            <form action="ServletLogin" method="post"> <!-- Asegúrate de que este sea el nombre correcto -->
                <div class="mb-3">
                    <label for="username" class="form-label">Username:</label>
                    <input type="text" id="username" name="username" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Password:</label>
                    <input type="password" id="password" name="password" class="form-control" required>
                </div>
                <div class="d-grid">
                    <input type="submit" value="Login" class="btn btn-primary">
                </div>
            </form>
            <div class="mt-3">
                <span class="enlace"><a href="Interfaces/Recuperar.jsp">¿Olvidaste la contraseña?</a></span>
                <span class="enlace"><a href="Interfaces/Registrarse.jsp">Registrarse</a></span>
                <span class="enlace"><a href="index.html">Modo Invitado</a></span>
            </div>
            <% 
                String mensaje = (String) request.getAttribute("mensaje");
                if (mensaje != null) {
            %>
            <div class="alert alert-danger mt-3">
                <%= mensaje %>
            </div>
            <% 
                } 
            %>
        </div>
    </div>
    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
