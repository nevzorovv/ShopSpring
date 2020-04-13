<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Shop</title>
    <link type="text/css" rel="stylesheet" href="styles.css" media="all">
</head>
<body>

<p>Login page</p>
<section>
    <form method="post" action="/login">
        <p>Login: <input name="username"/></p>
        <p>Password: <input name="password" type="password"/></p>
        <button>log in</button>
    </form>
</section>

</body>
</html>