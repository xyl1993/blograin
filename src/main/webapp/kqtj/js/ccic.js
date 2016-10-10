$.prototype.serializeObject = function() {
	var obj = new Object();
	$.each(this.serializeArray(), function(index, param) {
		if (!(param.name in obj)) {
			obj[param.name] = $.trim(param.value) || '';
		}
	});
	return obj;
};

var KQTJ = {
	sysErrors : {
		9001 : '服务器状态异常',
		9002 : '资源不存在',
		9003 : '不可删除的数据'
	},
	sysErrors_EN : {
		9001 : 'Server state exception',
		9002 : 'Resources are not there',
		9003 : 'Non deleted data'
	},
	bizErrors : {
		8001 : '参数为空',
		8002 : '参数类型不正确',
		8003 : '参数长度超出',
		8004 : '用户不存在',
		8009 : '用户名或密码错误',
		8015 : '验证码输入错误',
		8019 : '原密码错误',
		8049 : '参数验证不通过',
		8067 : '该手机号已注册',
		8068 : '该邮箱已注册',
		8070 : '电子邮箱不正确'
	},
	bizErrors_EN : {
		8001 : 'Parameter is null',
		8002 : 'Parameter type is incorrect',
		8003 : 'Parameter length',
		8004 : 'Users do not exist',
		8009 : 'User name or password error',
		8015 : 'Verification code input error',
		8019 : 'Original password error',
		8049 : 'Parameter verification does not pass',
		8067 : 'The phone number has been registered',
		8068 : 'The mailbox has been registered',
		8070 : 'Electronic mail is not correct'
	},
	//配置信息
	config: {
		//WebSocket服务器
		web_socket_server: 'ws://192.168.60.28:8080',
		url_share : "http://192.168.60.28:8080",
		//登陆地址
		login_url : '/kqtj',
		//首页学习特点地址
		xxtd_url : 'main/index.html#/xxtd',
		//提问
		ask_url : '../main/index.html#/askToOne//',
		//背景图片地址
		xxtdImg_url : '../kqtj/images/img1.png',
		xxtdImgE_url : '../kqtj/images/img1E.png',
		min_xxtdImg_url : '../kqtj/images/top1.png',
		min_xxtdImgE_url : '../kqtj/images/top1E.png',
		
		kcszImg_url : '../kqtj/images/img2.png',
		kcszImgE_url : '../kqtj/images/img2E.png',
		min_kcszImg_url : '../kqtj/images/top2.png',
		min_kcszImgE_url : '../kqtj/images/top2E.png',
		
		zyImg_url : '../kqtj/images/img3.png',
		zyImgE_url : '../kqtj/images/img3E.png',
		min_zyImg_url : '../kqtj/images/top3.png',
		min_zyImgE_url : '../kqtj/images/top3E.png',
		
		yqlImg_url : '../kqtj/images/img4.png',
		yqlImgE_url : '../kqtj/images/img4E.png',
		min_yqlImg_url : '../kqtj/images/top4.png',
		min_yqlImgE_url : '../kqtj/images/top4E.png',
		
		questionImg_url : '../kqtj/images/img5.png',
		questionImgE_url : '../kqtj/images/img5E.png'

	},
	
	gradeList:['1', '2', '3','4', '5', '6','7', '8', '9','10']
};



