<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:import url="/WEB-INF/views/layout/headCSS.jsp"></c:import>

</head>
<body id="page-top">
    <!-- Page Wrapper -->
    <div id="wrapper">
    	<!-- sidebar -->
    	<c:import url="/WEB-INF/views/layout/sidebar.jsp"></c:import>
    	
    	 <div id="content-wrapper" class="d-flex flex-column">
    	 	<div id="content">
    	 		
    	 		<c:import url="/WEB-INF/views/layout/topbar.jsp"></c:import>
    	 		
    	 		<div class="container-fluid">
    	 			<div>
    	 				<h3>${param.message}</h3>
    	 				<spring:message code="${param.message}" var="msg"></spring:message>
    	 				<h3>${msg}</h3>
    	 			</div>
    	 		
    	 			<form:form modelAttribute="memberVO" method="post">
    	 			  <div class="form-group">
    	 			  	<form:label path="username">Username</form:label>
					    <form:input id="username" path="username" cssClass="form-control"/>
					    <form:errors path="username"></form:errors>
					  </div>
					  
					  <div class="form-group">
					    <form:label path="password">Password</form:label>
					    <form:password path="password" cssClass="form-control" id="password"/>
					    <form:errors path="password"></form:errors>
					  </div>
					  
					  <div class="form-group">
					  	<label for="remember">RememberMe</label>
					    <input type="checkbox" id="remember" name="remember-me" class="form-control">
					  </div>
					  	  				  					  				  					  					  
    	 			
    	 			  <button type="submit" class="btn btn-primary">Submit</button>
    	 			
    	 			</form:form>
    	 			    	 			
    	 		</div>
    	 	
    	 	</div>
    	 	
    	 	<c:import url="/WEB-INF/views/layout/footer.jsp"></c:import>
    	 
    	 </div>
    	
    </div>
    
    

<c:import url="/WEB-INF/views/layout/footjs.jsp"></c:import>
<script type="text/javascript">
	let m = '${msg}';
	
	if(m != ''){
		alert(m);
	}
	
	history.replaceState({}, null, location.pathname);
</script>
</body>
</html>