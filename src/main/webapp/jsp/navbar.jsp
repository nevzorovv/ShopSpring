<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    <title>Shop</title>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">


    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="/mainpage">Mainpage<span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">[Reserved Link]</a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <c:if test="${user == 'Guest'}">
                        Guest
                    </c:if>
                    <c:if test="${user != 'Guest'}">
                        ${user.firstName} ${user.lastName}
                    </c:if>
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <c:if test="${user.role == 'ADMIN'}">
                        <a class="dropdown-item" href="#">My profile</a>
                        <a class="dropdown-item" href="/addproduct">Add product</a>
                        <a class="dropdown-item" href="/order_management">Order Management</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="/logout">Log out</a>
                    </c:if>
                    <c:if test="${user.role == 'USER'}">
                        <a class="dropdown-item" href="#">My profile</a>
                        <a class="dropdown-item" href="/shoppingcart">Shopping cart <c:if test="${countItems != 0}">(${countItems})</c:if></a>
                        <a class="dropdown-item" href="/myorders">My orders[reserved]</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="/logout">Log out</a>
                    </c:if>
                    <c:if test="${user == 'Guest'}">
                        <a class="dropdown-item" href="/signin">Sign in</a>
                    </c:if>
                </div>
            </li>

        </ul>
        <form class="form-inline my-2 my-lg-0">
            <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
        </form>
    </div>
</nav>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

</body></html>