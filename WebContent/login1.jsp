<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>login</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/style.css" />
<script type="text/javascript">
	function validName() {
		//先取出文本框的数据
		var name = document.getElementById("name").value;
		//alert(name);//打印
		var reg = /^[\\u4e00-\\u9fa5_a-zA-Z0-9-]{1,16}$/;
		;
		var nameobj = document.getElementById("nameinfo");
		//alert(nameobj);
		if (reg.test(name)) {
			nameobj.innerHTML = "录入正确";
			nameobj.style.color = "green";
		} else {
			nameobj.innerHTML = "录入错误";
			nameobj.style.color = "red";
		}
	}

	function change(imageObj) {
		imageObj.src = "createValidCode?date=" + new Date().getTime();
	}
</script>

</head>

<body>
	<div id="wrap">
		<div id="top_content">
			<div id="header">
				<div id="rightheader">
					<p>
						<%@include file="head.jsp"%>
						
						<br />
					</p>
				</div>
				<div id="topheader">
					<h1 id="title">
						<a href="#">main</a>
					</h1>
				</div>
				<div id="navigation"></div>
			</div>
			<div id="content">
				<p id="whereami"></p>
				<h1>login</h1>
				<form action="user_login.action" method="post">
					<table cellpadding="0" cellspacing="0" border="0"
						class="form_table">
						<tr>
							<td valign="middle" align="right">用户名:</td>
							<td valign="middle" align="left"><input type="text"
								class="inputgri" name="username" id="name" onblur="validName();"
								value='<s:property value="username"/>' /> <span
								id="nameinfo">用户名小写大写字母数字6位</span></td>

						</tr>
						<tr>
							<td valign="middle" align="right">密码：</td>
							<td valign="middle" align="left"><input type="password"
								class="inputgri" name="password"
								value='<s:property value="password"/>' /></td>
						</tr>
						<tr>
							<td valign="middle" align="right">验证码：</td>
							<td valign="middle" align="left"><input type="text"
								class="inputgri" name="number" /> <img src="createValidCode"
								alt="验证码" title="点击更换" onclick="change(this);" /></td>
						</tr>
						<tr>
							<td><span><s:property value="errorMsg" /> </span></td>
						</tr>
					</table>
					<p>
						<input type="submit" class="button" value="登录 &raquo;" />
					</p>
				</form>
			</div>
		</div>
		<div id="footer">
			<div id="footer_bg">ABC@126.com</div>
		</div>
	</div>
</body>
</html>

