<?php
// Đọc biến môi trường
$app_env = getenv('APP_ENV') ?: 'development';
$app_name = getenv('APP_NAME') ?: 'My PHP App';
?>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title><?= $app_name ?></title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f0f2f5;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .card {
            background: white;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.1);
            text-align: center;
            min-width: 400px;
        }
        h1 { color: #4A90D9; }
        .badge {
            display: inline-block;
            padding: 6px 16px;
            border-radius: 20px;
            background: #e8f4fd;
            color: #4A90D9;
            font-weight: bold;
            margin: 10px 0;
        }
        .info-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            text-align: left;
        }
        .info-table td {
            padding: 8px 12px;
            border-bottom: 1px solid #eee;
        }
        .info-table td:first-child {
            font-weight: bold;
            color: #666;
            width: 40%;
        }
    </style>
</head>
<body>
    <div class="card">
        <h1>🐘 <?= $app_name ?></h1>
        <div class="badge">Môi trường: <?= strtoupper($app_env) ?></div>

        <table class="info-table">
            <tr>
                <td>PHP Version</td>
                <td><?= phpversion() ?></td>
            </tr>
            <tr>
                <td>Server</td>
                <td><?= $_SERVER['SERVER_SOFTWARE'] ?></td>
            </tr>
            <tr>
                <td>Hostname</td>
                <td><?= gethostname() ?></td>
            </tr>
            <tr>
                <td>Thời gian</td>
                <td><?= date('d/m/Y H:i:s') ?></td>
            </tr>
            <tr>
                <td>Hệ điều hành</td>
                <td><?= PHP_OS ?></td>
            </tr>
        </table>

        <p style="margin-top:20px">
            <a href="info.php" style="color:#4A90D9">
                Xem PHP Info →
            </a>
        </p>
    </div>
</body>
</html>