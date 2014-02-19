<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
 
 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${requestScope.serviceName} Status Page</title>
 
 
 
Howdy !! Your ${requestScope.serviceName} status page.
<c:if test="${requestScope.tomcatStatus == 1}">
<br>
Your service ${requestScope.serviceName} is ${requestScope.currentStatus}
<br>
<a href="/TomcatCheck/status?type=start">Start ${requestScope.serviceName}</a>
</c:if>
<c:if test="${requestScope.tomcatStatus == 4}">
<br>
<b>${requestScope.serviceName} is up and ${requestScope.currentStatus}!!!!</b>
<br>
<a href="/TomcatCheck/status?type=stop">Stop ${requestScope.serviceName}</a>
</c:if>
<br>
<a href="/TomcatCheck/status?type=restart">Restart ${requestScope.serviceName}</a>