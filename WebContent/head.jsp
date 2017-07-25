<%@ page language="java" import="java.util.*,java.text.*"
	pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>My JSP 'head.jsp' starting page</title>

<script type="text/javascript">
	function startTime() {
		var today = new Date();
		var a = today.getHours();
		var b = today.getMinutes();
		var c = today.getSeconds();
		b = checkTime(b);
		c = checkTime(c);
		document.getElementById('txt').innerHTML = a + ":" + b + ":" + c
		d = setTimeout('startTime()', 500)
	}
	function checkTime(i) {
		if (i < 10) {
			i = "0" + i;
		}
		return i;
	}
</script>


</head>

<body  onLoad="startTime()">
<div id="txt"></div>
	<%
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
		out.println(sf.format(date));
	%>
</body>
</html>
