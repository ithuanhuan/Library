<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>  
    <title>My JSP 'mainDemojsp.jsp' starting page</title>
  </head>
  <body>
    <%
    out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">"
				+ "<html>"
				+ "<head>"
				+"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
				+"<title>山东理工大学图书馆</title>"
				+"<style type=\"text/css\">"
				+"body{"
				+"background-size: 100%;"
				+"text-align:left;"
				+"background-image:url('/LibraryServlet/img/bookph3.jpg');"
				+"background-repeat:no-repeat;"
				+"height: 200%;"
				+"width: 100%;}"
				+"#para2{"
				+"text-align:center;"
				+"font-size:25px;"
				+"color:red;"
				+"font-size:40px;}"
				+"img{"
				+"position:fixed;bottom:0px;right:1px}\n"
				+".pitem{margin-left:80px;font-size:17px;}"
				+"</style></head><body><p id=\"para2\">山东理工大学图书馆</p>"
				
				);		
     %>
  </body>
</html>
