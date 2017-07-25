<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
     <head>

        <meta charset="utf-8">
        <title>Fullscreen Login</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <!-- CSS -->
        <link rel="stylesheet" href="css11/reset.css">
        <link rel="stylesheet" href="css11/supersized.css">
        <link rel="stylesheet" href="css11/style.css">
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
		imageObj.src = "createValidCode.action?date=" + new Date().getTime();
	}
</script>

        <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
        <!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->

    </head>

    <body oncontextmenu="return false">

        <div class="content">
            <h1>登录</h1>
            <form action="user_login.action" method="post">
					<table cellpadding="0" cellspacing="0" border="0"
						class="form_table">
						<div>
						<tr>
						
								<td valign="middle" align="left"><input type="text" placeholder="用户名"
								class="inputgri" name="username" id="name" onblur="validName();"
								value='' />
								<br><br>
								 <span
								id="nameinfo">用户名小写大写字母数字</span></td>

						</tr>
						</div>
						<tr>
						
							<td valign="middle" align="left"><input type="password"
								class="inputgri" name="password" placeholder="密码"
								value='' />
							</td>
						</tr>
						<tr>
						
							
							<td valign="middle" align="left"><input type="text"
								class="inputgri" name="number" placeholder="验证码"
								value='' /> 
								<br><br>
								<img
								src="createValidCode.action" alt="验证码" title="点击更换"
								onclick="change(this);" />
							</td>
						</tr>
						<tr>
							<td><span name="errorMsg">
							</span>
							</td>
						</tr>
						<tr>
						<td>
						<input type="submit" class="button" name="submit" value="Submit &raquo;">
						</td>
						</tr>
					</table>
					<!-- <p>
						<input type="submit" class="button" value="Submit &raquo;">
					</p> -->
				</form>
            <div class="connect">
                <p>If we can only encounter each other rather than stay with each other,then I wish we had never encountered.</p>
				<p style="margin-top:20px;">如果只是遇见，不能停留，不如不遇见。</p>
            </div>
        </div>
		<div class="alert" style="display:none">
			<h2>消息</h2>
			<div class="alert_con">
				<p id="ts"></p>
				<p style="line-height:70px"><a class="btn">确定</a></p>
			</div>
		</div>

        <!-- Javascript -->
		<script src="http://apps.bdimg.com/libs/jquery/1.6.4/jquery.min.js" type="text/javascript"></script>
        <script src="js/supersized.3.2.7.min.js"></script>
        <script src="js/supersized-init.js"></script>
		<script>
		
		$(".btn").click(function(){
			is_hide();
		})
		var u = $("input[name=username]");
		var p = $("input[name=password]");
		$("#submit").live('click',function(){
			if(u.val() == '' || p.val() =='')
			{
				$("#ts").html("用户名或密码不能为空~");
				is_show();
				return false;
			}
			else{
				var reg = /^[0-9A-Za-z]+$/;
				if(!reg.exec(u.val()))
				{
					$("#ts").html("用户名错误");
					is_show();
					return false;
				}
			}
		});
		window.onload = function()
		{
			$(".connect p").eq(0).animate({"left":"0%"}, 600);
			$(".connect p").eq(1).animate({"left":"0%"}, 400);
		}
		function is_hide(){ $(".alert").animate({"top":"-40%"}, 300) }
		function is_show(){ $(".alert").show().animate({"top":"45%"}, 300) }
		</script>
    </body>

</html>
