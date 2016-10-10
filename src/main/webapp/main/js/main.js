//模块化
var mainModule = angular.module('mainModule', ['ui.router',
				'ngResource', 'ngSanitize','ngWebSocket']);

/**
 * websocket连接
 */
mainModule.factory('websocket',function($http){
	var wb = {};
	var ws;//WebSocket对象
	
	wb.connection = function(userId){
		var wsUrl = "ws://192.168.60.136:8080/websocket/"+userId;//支持WebSocket协议的服务器端地址
        ws = new WebSocket(wsUrl);
		//注册各类回调
        ws.onopen = function () {
        	//尝试向服务端发送消息
            ws.send("web端登陆");
//            ws.onmessage = function (receiveMsg) {
            	//关闭连接
//				ws.close();
//				//清掉数据
////				alert('您在其他地方已登录，请重新登陆，若非本人操作，建议您修改密码');
//				$('#noticeModal').modal('show');
//				$('#noticeModal').on('hide.bs.modal', function () {
//					window.location.href= KQTJ.config.login_url;
//				})
//				sessionStorage.removeItem("token");
//            };
        };
        ws.onclose = function () {
    		console.log('连接被关闭.');
		};
		ws.onmessage = function(message) {
	        listener(JSON.parse(message.data));
	    };
	};
	wb.sendMessage = function(message){
	    if(ws&&ws!=null){
	        ws.send(JSON.stringify(message));
	    }
	}
	return wb;
});
mainModule.factory('myWebsocket', ['$rootScope','$websocket',function($rootScope,$websocket) {
  // Open a WebSocket connection 
  var dataStream = $websocket('ws://192.168.60.136:8080/websocket/'+sessionStorage.getItem('userId'));
 
  var collection = [];
 
  dataStream.onMessage(function(message) {
  	$rootScope.message = message.data;
    collection.push(message);
    $('#info-alert').show();
  });
 
  var methods = {
    collection: collection,
    get: function() {
      dataStream.send(JSON.stringify({ action: 'get' }));
    },
    send:function(message){
       dataStream.send(JSON.stringify(message));
    }
  };
 
  return methods;
}]);
//全局配置
mainModule.run(function($rootScope, $state, $http, $stateParams, $location,$timeout,$window,myWebsocket) {
	$rootScope.$state = $state;
	$rootScope.$stateParams = $stateParams;
	$rootScope.message = "";
	$rootScope.token = sessionStorage.getItem('token');
	// 路由调整完成后根据state添加标志
	$rootScope.$on('$stateChangeSuccess', 
		function(event, toState, toParams, fromState){
          var toStateUrl = toState.url;
          
		});
});

///路由配置
mainModule.config(['$stateProvider','$urlRouterProvider',function($stateProvider,$urlRouterProvider) {
	$stateProvider.state('home',{
		url : '/home',               //home
		templateUrl : 'index.html'
	}).state('list',{
		url : '/list',               //关于我
		templateUrl : 'wz.html'
	}).state('detail',{
		url : '/detail/:id',               //经验
		templateUrl : 'wzxq.html'
	}).state('miner',{
		url : '/miner',               //技能
		templateUrl : '/miner'
	});
	$urlRouterProvider.otherwise('/list');   //默认home
}]);
//mainCtrl
mainModule.controller('mainCtrl', ['$scope','$rootScope','myService', function($scope,$rootScope,myService){
    var token = sessionStorage.getItem('token');
    $scope.userName = sessionStorage.getItem('userName');
    $scope.hotActDatas = [];
    var loginStatus = false;
    //未登录
    if(!token || token=='null'){
    	$('.dl-li').show();
    	$('.gr-li').hide();
    	loginStatus = false;
    }else{
    	var _url = '../login/getUserDetail';
 		myService.setUrl(_url);  //方法设置路径
 		myService.setToken(token);
 		//call请求回调
		myService.requestToken().then(function(res){
		  if(res.success){
		  	$rootScope.userInfoDetail = res.data;
		  }
		});
        $('.dl-li').hide();
    	$('.gr-li').show();
    	loginStatus = true;
    }
    getHotAct();
    getNewAct();
    $('.responsive-nav').find('option').each(function(){
        var $this = $(this);
        if(loginStatus){
            //登录
        	if($this.val()=='login'){
        	    $this.remove();
        	}
        }else{
            //未登录
        	if($this.val()=='me' || $this.val()=='zx'){
        	    $this.remove();
        	}
        }
    })
    
    /****注销******/
	$scope.loginout = function(){
		var _url = '../login/userLogout';
 		myService.setUrl(_url);  //方法设置路径
 		myService.setToken(token);
 		//call请求回调
		myService.requestToken().then(function(res){
		  if(res.success){
		  	sessionStorage.removeItem("token");
			sessionStorage.removeItem("userName");
			sessionStorage.removeItem("userId");
			window.location.href= "/";
		  }
		});
	}
    
    /**获取热门文章**/
    function getHotAct(){
 		var _url = '../actical/getHotActList';
 		myService.setUrl(_url);  //方法设置路径
 		//call请求回调
		myService.requestData().then(function(res){
		  if(res.success){
		  	$scope.hotActDatas = res.data;
		  }
		});
    }
    
    /**获取最新文章**/
    function getNewAct(){
 		var _url = '../actical/getNewActList';
 		myService.setUrl(_url);  //方法设置路径
 		//call请求回调
		myService.requestData().then(function(res){
		  if(res.success){
		  	$scope.newActDatas = res.data;
		  }
		});
    }
    /**意见反馈弹出框**/
    $scope.yjfkClick = function(){
        $("#xgmmModal").modal('show');
	}
}])

//意见反馈controller 
mainModule.controller('yjfkCtrl', ['$scope','myService', '$http',function($scope,myService,$http){
  $("#editPassForm").validate({
  	debug:true,
    rules: {
      up_title: {
        required: true,
        minlength: 2,
     	maxlength:14
      },
      up_email: {
        required: true
      },
      up_text: {
        required: true,
        minlength: 5,
        maxlength:500
      }
    },
    messages: {
      up_title: {
        required: "请输入标题",
        minlength: "标题长度不能小于 2个字符",
        maxlength:"标题长度不能多于14个字符"
      },
      up_email: {
        required: "请输入邮箱"
      },
      up_text: {
        required: "请输入建议",
        minlength: "建议长度不能小于 6个字母",
        maxlength: "建议长度不能多于500个字符"
      }
    },
    submitHandler: function(form) {
	    var data = {
	        title:$scope.up_title,
	        email:$scope.up_email,
	        text:$scope.up_text
	    };
		var _url = '../mails/sendMail';
 		myService.setUrl(_url);  //方法设置路径
 		myService.setData(data);
 		$('#yj-subtn').attr({"disabled":"disabled"});
 		//call请求回调
		myService.requestData().then(function(res){
		  if(res.success){
		  	$('#yj-subtn').removeAttr("disabled");
		  	$("#xgmmModal").modal('hide');
			$('.info-sz-dyn').show();
			$('.sz-dyn').empty();
			var _html ='<strong>发送成功</strong>';
	    	$('.sz-dyn').append(_html);
	    	$('.info-sz-dyn').fadeOut(4000, function () {
	  		});
		  }else{
		      alert(res.message);
		  }
		});
	}  
  })
}])

//文章列表
mainModule.controller('actCtrl', ['$scope','myService', '$http',function($scope,myService,$http){
	var token = sessionStorage.getItem('token');
	 // 默认显示页面为3条
	var pageSize =10;
	 // 当前页为第一页
	var currentPage = 1;
	 // 结果集
	$scope.actDatas = [];
	var _data = {};
	//先调用一次获取第一页的数据
	getActList(1,_data);
	
	function initPage(pages){
	 	$('#pagination').twbsPagination({
			 totalPages: pages,
			 visiblePages: 5,
			 first: '首页',
			 prev: '上一页',
			 next: '下一页',
			 last: '末页',
			 onPageClick: function (event, page) {
				 getActList(page,_data);
			 }
		 });
	 }
	 $('#act-search-btn').click(function(){
	     _data = {searchText:$('#searchText').val()};
	     getActList(1,_data);
	 })
 	function getActList(currentPage,_data){
 		var _url = '../actical/getActList/paging/'+currentPage + '/' + pageSize;
 		myService.setUrl(_url);  //方法设置路径
 		myService.setData(_data);
 		//call请求回调
		myService.requestData().then(function(res){
		  if(res.success){
		  	$scope.actDatas = res.data.list;
		  	$scope.counts = res.data.recordCount;//总共的条数
			$scope.pageCount = res.data.pageCount;//总共的页数
			for(var i=0,len=$scope.actDatas.length;i<len;i++){
		  	    $scope.actDatas[i].titleImgArr =  $scope.actDatas[i].titleImg?$scope.actDatas[i].titleImg.split(","):[];
		  	}
			if($scope.actDatas.length > 0){
				 initPage($scope.pageCount);
			 }
		  }
		});
	 }
	 //删除文章
	 $scope.del = function(id,aId,index){
	 	if(!token || token=='null'){
		     alert('您没有权限这样操作喔！');
	 	}else{
	 	    if(confirm("确定要删除吗？")){
		     	var _url = '../actical/delAct/'+aId
		 		myService.setUrl(_url);  //方法设置路径
		 		myService.setToken(token);
		 		//call请求回调
				myService.requestToken().then(function(res){
				  if(res.success){
				  	$("#xgmmModal").modal('hide');
					$('.info-sz-dyn').show();
					$('.sz-dyn').empty();
					var _html ='<strong>删除成功</strong>';
			    	$('.sz-dyn').append(_html);
			    	$('.info-sz-dyn').fadeOut(4000, function () {
			  		});
			  		$scope.actDatas.splice(index,1);
				  	}else{
				  	    alert(resp.message);
				  	}
				});
		     }
	 	}
	 }
}]);

//文章详情
mainModule.controller('wzxqCtrl', ['$scope','$stateParams','$http','myWebsocket','myService',
function($scope,$stateParams,$http,myWebsocket,myService){
	var token = sessionStorage.getItem('token');
	$scope.commentFlag = false;          //回复引用框判断标志
	$scope.commentInContent = "";        //引用的评论内容
	$scope.plData = {};
	//获取文章详情
    getActDetail();
	
	if(!token || token=='null'){//为登录
	    $('.pl-content').hide();
	}else{
	    $('.ts-dl').hide();
	}
	
	 // 默认显示页面为3条
	var pageSize =5;
	 // 当前页为第一页
	var currentPage = 1;
	//获取评论列表
	$scope.plListDatas = [];//评论list的对象
	//先调用一次获取评论第一页的数据
	getPlList(1);
	
	function initPage(pages){
	 	$('#pagination').twbsPagination({
			 totalPages: pages,
			 visiblePages: 5,
			 first: '首页',
			 prev: '上一页',
			 next: '下一页',
			 last: '末页',
			 onPageClick: function (event, page) {
				 getPlList(page);
			 }
		 });
	 }
	//提交评论
	$scope.submit = function(){
		
		if(!token || token=='null'){
		    alert('评论前请先登录');
		    $('.ts-dl').show();
		    $('.pl-content').hide();
		}else{
			var _url = '../actical/saveReplay',
        	_data = $scope.plData;
	        _data.invitationId = $stateParams.id;
	        myService.setUrl(_url);  //方法设置路径
			myService.setData(_data);
			myService.setToken(token);
			//call请求回调
			myService.requestTokenAndData().then(function(res){
			  if(res.success){
			  	$scope.plData.content = "";
			  	$("#xgmmModal").modal('hide');
				$('.info-sz-dyn').show();
				$('.sz-dyn').empty();
				var _html ='<strong>评论成功</strong>';
		    	$('.sz-dyn').append(_html);
		    	$('.info-sz-dyn').fadeOut(4000, function () {
		  		});
		  	    getPlList(1);
		  	    if($scope.plData.replayUserId !=null && $scope.plData.replayUserId !='undefined' ){
		  	        sendWebsocket(sessionStorage.getItem('userId'),$scope.plData.replayUserId);
		  	    }
		  	    sendWebsocket(sessionStorage.getItem('userId'),$scope.actData.userInfo.id);
			  }else{
			  	    alert(resp.message);
			  	}
			});
		}
	}
	
	function sendWebsocket(form,to){
	    var message = {form:form,to:to,text:'您有新消息，点击查看'};
		myWebsocket.send(message);
	}
	
	/**
	 * 点击回复按钮响应事件
	 */
	$scope.comment = function(id,userId,content){
	    $scope.plData.replayId = id;          //回复引用的id
	    $scope.plData.replayUserId = userId;          //回复引用人的id
	    $scope.plData.fpUserId = $scope.actData.userInfo.id; //文章发布人的id
	    $scope.commentFlag = true;
	    $scope.commentInContent = content;
	    $('.commentBlock').removeClass('ng-hide');
	    $('.commentBlock').addClass('ng-show');
	    $('#comment').focus();
	}
	
	/**
	 * 取消引用
	 */
	$scope.cancelComment = function(){
		$('.commentBlock').removeClass('ng-show');
		$('.commentBlock').addClass('ng-hide');
	    delete($scope.plData.replayId);         
	    delete($scope.plData.replayUserId);         
	    delete($scope.plData.fpUserId);         
	}
	
	/**
	 * 删除评论
	 */
	$scope.delcomment = function(replayId){
		
	 	if(!token || token=='null'){
		     alert('您没有权限这样操作喔！');
	 	}else{
	 	    if(confirm("确定要删除吗？")){
		     	var _url = '../actical/delReplay/'+replayId;
				myService.setUrl(_url);  //方法设置路径
				myService.setToken(token);
				//call请求回调
				myService.requestToken().then(function(res){
				  if(res.success){
				  	$("#xgmmModal").modal('hide');
					$('.info-sz-dyn').show();
					$('.sz-dyn').empty();
					var _html ='<strong>删除成功</strong>';
			    	$('.sz-dyn').append(_html);
			    	$('.info-sz-dyn').fadeOut(4000, function () {
			  		});
				  	getPlList(1);
				  }else{
				    alert(res.message);
				  }
				});
		     }
	 	}
	}
	
	/**
	 * 获取文章详情
	 */
	function getActDetail(){
	    var _url = '../actical/getActDetail',
        _data={id:$stateParams.id};
		myService.setUrl(_url);  //方法设置路径
		myService.setData(_data);
		//call请求回调
		myService.requestData().then(function(res){
		  if(res.success){
		  	$scope.actData = res.data[0];
		  	$scope.plData.fpUserId = $scope.actData.userInfo.id;
		  }
		});
	}
	
	/**
	 *  获取评论列表
	 */
	function getPlList(currentPage){
	    $http({
		   method: 'post',
		   url: './actical/getplList/paging/'+currentPage+'/'+pageSize+'/'+$stateParams.id
		}).success(function(res){
		  	if(res.success){
		  		$scope.plListDatas = res.data.list;
		  		$scope.counts = res.data.recordCount;//总共的条数
				$scope.pageCount = res.data.pageCount;//总共的页数
				if($scope.plListDatas.length > 0){
					 initPage($scope.pageCount);
				 }
		  	}
		}) 
	}
}]);
mainModule.filter('imgfilter',function() {
  return function(url){
    if(url){
      return "http://192.168.60.136:8080"+url;
    }
  }
});
mainModule.filter('delfilter',['$rootScope',function($rootScope) {
  return function(userId){
  	var userInfo = $rootScope.userInfoDetail
	if(!userInfo || userInfo=='null'){
	    return false
	}
    if(userInfo.id==1 || userId==userInfo.id){
      return true
    }else{
      return false
    }
  }
}]);
mainModule.filter('to_trusted', ['$sce', function ($sce) {
	return function (text) {
	    return $sce.trustAsHtml(text);
	};
}]);
mainModule.directive('moduleClick', ['$state',function($state){
  return {
    link: function($scope, iElm, iAttrs) {
      iElm.bind('click', function(event) {
      	if($('.menu-me-container').is(":hidden")){
      	    $('.menu-me-container').show();
      	}else{
      	    $('.menu-me-container').hide();
      	}
      });
    }
  };
}]);
mainModule.directive('closeAlert', ['$state',function($state){
  return {
    link: function($scope, iElm, iAttrs) {
      iElm.bind('click', function(event) {
      	$('#info-alert').hide();
      });
    }
  };
}]);
mainModule.directive('returnInfo', ['$state',function($state){
  return {
    link: function($scope, iElm, iAttrs) {
      iElm.bind('click', function(event) {
      	location.href='../user.html#/myInfo';
      });
    }
  };
}]);
/**
  *普通ajax请求公共服务
  */
 mainModule.factory('myService',['$http','$q',function($http,$q){
    var service = {},
        baseUrl /*:String*/= '../',
        _url /*:String*/= '',
        _finalUrl /*:String*/= '',
        _token /*:String*/= '',
        _deviceSystem /*:Boolean*/=0,
        _data  /*:Object*/= {};
    
    /**
     * 处理请求路径
     */
    var makeUrl = function(){
      _url = _url.split(' ').join('+');
      _finalUrl = baseUrl +_url;
      return _finalUrl
    }
    
    /**
     * 设置请求路径
     */
    service.setUrl = function(url){
      _url = url;
    }
    
    service.setToken = function(token){
      _token = token;
    }
    
    service.setData = function(data){
      _data = data;
    }
    
    service.setDeviceSystem = function(deviceSystem){
      _deviceSystem = deviceSystem;
    }
    /**
     * 获取请求路径
     */
    service.getUrl = function(){
      return _url;
    }
    
    service.getToken = function(){
      return _token;
    }
    
    service.getData = function(){
      return _data;
    }
    
    /**
     *  $q.defer() 构建的 deffered 实例的几个方法的作用。
     *  如果异步操作成功，则用resolve方法将Promise对象的状态变为“成功”（即从pending变为resolved）；如果异步操作失败，
     *  则用reject方法将状态变为“失败”（即从pending变为rejected）。最后返回 deferred.promise ，我们就可以链式调用then方法。
     */
    service.callItuns = function(){
      makeUrl();
      //通过 调用 $q.defferd 返回deffered对象以链式调用
      /**
       * deffered 对象的方法
       * 1.resolve(value)：在声明resolve()处，表明promise对象由pending状态转变为resolve。 成功状态
       * 2.reject(reason)：在声明resolve()处，表明promise对象由pending状态转变为rejected。失败状态
       * 3.notify(value) ：在声明notify()处，表明promise对象unfulfilled状态，在resolve或reject之前可以被多次调用。
       */
      var defrred = $q.defer();
      $http({
	     method: 'post', url: _finalUrl
	  }).success(function(resp){
	       defrred.resolve(resp);
	  }) 
	  /**
	   * 返回promise对象
	   * 1.then(errorHandler, fulfilledHandler, progressHandler)：
	   * then方法用来监听一个Promise的不同状态。errorHandler监听failed状态，
	   * fulfilledHandler监听fulfilled状态，progressHandler监听unfulfilled（未完成）状态。
	   * 此外,notify 回调可能被调用 0到多次，提供一个进度指示在解决或拒绝（resolve和rejected）之前。
	   * 2.catch(errorCallback) —— promise.then(null, errorCallback) 的快捷方式
	   * 3.finally(callback) ——让你可以观察到一个 promise 是被执行还是被拒绝, 但这样做不用修改最后的 value值。
	   *  这可以用来做一些释放资源或者清理无用对象的工作,不管promise 被拒绝还是解决。 更多的信息请参阅 完整文档规范.
	   */
	  return defrred.promise;
    }
    /**
     * 请求中带data参数
     */
    service.requestData = function(){
      makeUrl();
      //通过 调用 $q.defferd 返回deffered对象以链式调用
      var defrred = $q.defer();
      $http({
	     method: 'post', url: _finalUrl,data:_data
	  }).success(function(resp){
	       defrred.resolve(resp);
	  }) 
	  return defrred.promise;
    }
    /**
     * 请求带token
     */
    service.requestToken = function(){
      makeUrl();
      //通过 调用 $q.defferd 返回deffered对象以链式调用
      var defrred = $q.defer();
      $http({
	     method: 'post', url: _finalUrl,headers : {'token' : _token}
	  }).success(function(resp){
	       defrred.resolve(resp);
	  }) 
	  return defrred.promise;
    }
    /**
     * 请求带token and data
     */
    service.requestTokenAndData = function(){
      makeUrl();
      //通过 调用 $q.defferd 返回deffered对象以链式调用
      var defrred = $q.defer();
      $http({
	     method: 'post', url: _finalUrl,data:_data,headers : {'token' : _token}
	  }).success(function(resp){
	       defrred.resolve(resp);
	  }) 
	  return defrred.promise;
    }
    /**
     * 请求带token and data and deviceSystem
     */
    service.requestTokenAndDataDev = function(){
      makeUrl();
      //通过 调用 $q.defferd 返回deffered对象以链式调用
      var defrred = $q.defer();
      $http({
	     method: 'post', url: _finalUrl,data:_data,headers : {'token' : _token,'deviceSystem' : _deviceSystem}
	  }).success(function(resp){
//	     if(resp.code == 0){
	       defrred.resolve(resp);
//	     }
	  }) 
	  return defrred.promise;
    }
    return service;
 }]);