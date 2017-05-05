<%@ page import="java.net.InetAddress" %>
<%@ page import="java.util.Date" %>
<html>
<body>
<h2>Hello World!</h2>
<%! int time=0;%>
<% int port= request.getLocalPort();%>
<h3>port:<%=port%></h3>
<h3>this is you <%=++time%> times vistor  </h3>
<h4>date:<%=new Date().toLocaleString()%></h4>
</body>
</html>
