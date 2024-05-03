<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Latest compiled and minified CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <!-- Latest compiled JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
<div class="container mt-5">
    <div class="row">
        <div class="col-12 mx-auto">
            <div class="d-flex justify-content-between">
                <h3>User detail ${id}</h3>
            </div>
            <hr/>
            <div class="card" style="width: 60%;">
                <div class="card-header">
                    User Information
                </div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">
                        Id: ${user.id}
                    </li>
                    <li class="list-group-item">
                        Email: ${user.email}
                    </li>
                    <li class="list-group-item">
                        FullName: ${user.fullName}
                    </li>
                    <li class="list-group-item">
                        Phone: ${user.phone}
                    </li>
                    <li class="list-group-item">
                        Address: ${user.address}
                    </li>
                </ul>
            </div>
            <div class="mt-3 mb-3">
                <a href="/admin/user" type="button" class="btn btn-outline-success">
                    Back
                </a>
            </div>
        </div>
    </div>
</div>
</body>
</html>