<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
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
                <h3>Delete User with id = ${id}</h3>
            </div>
            <hr/>
            <div class="alert alert-danger" role="alert">
                Are you sure want to delete this user ?
            </div>
            <form:form action="/admin/user/delete" modelAttribute="newUser" method="post">
                <div class="mb-3 d-none">
                    <label class="form-label">Id:</label>
                    <form:input value="${id}" type="text" class="form-control" path="id"/>
                </div>
                <button type="submit" class="btn btn-outline-danger">Delete</button>
            </form:form>
        </div>
    </div>
</div>
</body>
</html>