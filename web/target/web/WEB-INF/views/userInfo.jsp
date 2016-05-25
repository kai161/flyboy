<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>用户信息页面</title>
</head>
<body>
 <h1>用户信息</h1>
 <div>
    <c:forEach items="${list}" var="element">
        <p>${element.username}</p>
        <p>${element.password}</p>
        <p>${element.name}</p>
        <p>${element.mobile}</p>
        <p>${element.email}</p>
    </c:forEach>

 </div>
</body>
</html>
