<%@ page import="org.springframework.security.authentication.AuthenticationCredentialsNotFoundException" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <title>Shop</title>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-default" style="margin-top:45px">
                <div class="panel-heading">
                    <h3 class="panel-title">Форма входа</h3>
                </div>
                <div class="panel-body">
                    <c:if test="${logout}">
                        <div class="alert alert-info" role="alert">You've been logged out successfully.</div>
                    </c:if>
                    <c:if test="${error}">
                        <%--<div class="alert alert-danger" role="alert">Invalid Username or Password!</div>--%>
                        <div class="alert alert-danger" role="alert">${SPRING_SECURITY_LAST_EXCEPTION}</div>
                    </c:if>
                    <c:if test="${regsucc}">
                        <div class="alert alert-success" role="alert"><spring:message code="message.regSucc"></spring:message></div>
                    </c:if>

                    <form method="post">
                        <input name="${_csrf.parameterName}" value="${_csrf.token}" type="hidden">
                        <div class="form-group">
                            <label for="username">Username</label>
                            <input type="text" class="form-control" id="username" placeholder="Username"
                                   name="username" autofocus>
                        </div>
                        <div class="form-group">
                            <label for="password">Password</label>
                            <input type="password" class="form-control" id="password" placeholder="Password"
                                   name="password">
                        </div>
                        <button type="submit" class="btn btn-default">Log in</button>
                        <a href="/registration"><button type="button" class="btn btn-default">Registration</button></a>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>