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
        <p>${element.id} - ${element.username} - ${element.password} - ${element.name} - ${element.mobile} - ${element.email} - ${element.referred_count} - ${element.create_time}   </p>
    </c:forEach>

 </div>
</body>
</html>
