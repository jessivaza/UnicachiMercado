<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Revisa tu Correo</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                background-color: #f8f9fa;
            }
            .confirmation-message {
                background: #fff;
                padding: 40px;
                border-radius: 10px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                text-align: center;
            }
            .confirmation-message h1 {
                color: #333;
                margin-bottom: 20px;
            }
            .confirmation-message p {
                color: #666;
                margin-bottom: 20px;
            }
            .confirmation-message .email {
                color: #007bff;
                font-weight: bold;
                font-size: 1.2em;
            }
            .confirmation-message .footer {
                margin-top: 20px;
                color: #999;
                font-size: 0.9em;
            }
        </style>
    </head>
    <body>
        <div class="confirmation-message">
            <h1>Revisa tu Correo</h1>
            <p>Te hemos enviado un correo electrónico a la siguiente dirección:</p>
            <p class="email">${sessionScope.correo}</p>
            <p>Por favor, verifica tu bandeja de entrada para más detalles. Si no ves el correo, revisa tu carpeta de spam o correo no deseado.</p>
            <div class="footer">
                <p>Gracias por usar nuestro servicio.</p>
            </div>
        </div>
    </body>
</html>
