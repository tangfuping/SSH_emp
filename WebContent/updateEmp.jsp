<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%@page import="entity.*"%>
<html>
<head>
<title>update Emp</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/style.css" />
</head>
<body>
	<div id="wrap">
		<div id="top_content">
			<%@include file="head.jsp" %> 
			<div id="content">
				<p id="whereami"></p>
				<h1>修改员工:</h1>
				<%
					Employee e = (Employee) request.getAttribute("employee");
				%>
				<form action="emp_updateEmp.action" method="post">
					<table cellpadding="0" cellspacing="0" border="0"
						class="form_table">
						<tr>
							<td valign="middle" align="right">id:</td>

							<td valign="middle" align="left"><input type="text"
								class="inputgri" name="t_id" value="<%=e.getT_id()%>" />
							</td>

						</tr>
						<tr>
							<td valign="middle" align="right">姓名:</td>
							<td valign="middle" align="left"><input type="text"
								class="inputgri" name="empname" value="<%=e.getEmpname()%>" />
							</td>
						</tr>
						<tr>
							<td valign="middle" align="right">薪水:</td>
							<td valign="middle" align="left"><input type="text"
								class="inputgri" name="empsalary" value="<%=e.getEmpsalary()%>" />
							</td>
						</tr>
						<tr>
							<td valign="middle" align="right">年龄:</td>
							<td valign="middle" align="left"><input type="text"
								class="inputgri" name="empage" value="<%=e.getEmpage()%>" />
							</td>
						</tr>
					</table>
					<p>
						<input type="submit" class="button" value="提交" />
					</p>
				</form>
			</div>
		</div>
		<%-- <%@include file="footer.jsp" %> --%>
	</div>
</body>
</html>
