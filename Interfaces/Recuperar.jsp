<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Restablecer Contraseña</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                background-color: #f8f9fa;
            }
            .reset-password-form {
                background: #fff;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                text-align: center;
            }
            .reset-password-form h2 {
                margin-bottom: 20px;
            }
            .reset-password-form p {
                margin-bottom: 20px;
                color: #6c757d;
            }
            .reset-password-form .form-control {
                border: 2px solid #e6e6e6;
                border-radius: 30px;
                padding: 20px;
                font-size: 14px;
            }
            .reset-password-form .btn {
                width: 100%;
                padding: 15px;
                border-radius: 30px;
                background-color: #f8f9fa;
                color: #6c757d;
            }
            .reset-password-form .btn:hover {
                background-color: #e2e6ea;
            }
            .reset-password-form a {
                display: block;
                margin-top: 20px;
                color: #6c757d;
            }
        </style>
    </head>
    <body>
        <div class="reset-password-form">
            <h2>Restablecer contraseña</h2>
            <p>Introduce tu correo electrónico y te enviaremos una nueva contraseña.</p>
            <form id="resetPasswordForm" method="post" action="../ServletCorreoUnicachi?accion=recuperar">
                <div class="form-group">
                    <input type="email" class="form-control" id="email" name="correo" placeholder="Correo electrónico" required>
                </div>
                <button type="submit" class="btn btn-primary">Enviar</button>
            </form>

            <a href="../Ingresar.jsp">¿Recordar tu contraseña? Iniciar sesión</a>

        </div>
        <script>
            document.getElementById('resetPasswordForm').addEventListener('submit', function (event) {
                var email = document.getElementById('email').value;
                if (!validateEmail(email)) {
                    event.preventDefault();
                    alert('Por favor, ingresa un correo electrónico válido.');
                }
            });

            function validateEmail(email) {
                var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@(([^<>()[\]\.,;:\s@"]+\.)+[^<>()[\]\.,;:\s@"]{2,})$/i;
                return re.test(String(email).toLowerCase());
            }
        </script>
    </body>
</html>
