(function($) {
    var em = [
              {'id':1,'phrase':'[可爱]','url':'1.gif'},{'id':2,'phrase':'[坏笑]','url':'2.gif'},
              {'id':3,'phrase':'[憨笑]','url':'3.gif'},{'id':4,'phrase':'[财迷]','url':'4.gif'},
              {'id':5,'phrase':'[偷笑]','url':'5.gif'},{'id':6,'phrase':'[斜眼]','url':'6.gif'},
              {'id':7,'phrase':'[流泪]','url':'7.gif'},{'id':8,'phrase':'[可怜]','url':'8.gif'},
              {'id':9,'phrase':'[鄙视]','url':'9.gif'},{'id':10,'phrase':'[威武]','url':'10.gif'},
              
              {'id':11,'phrase':'[色]','url':'11.gif'},{'id':12,'phrase':'[嘘]','url':'12.gif'},
              {'id':13,'phrase':'[敲打]','url':'13.gif'},{'id':14,'phrase':'[爱心]','url':'14.gif'},
              {'id':15,'phrase':'[心碎]','url':'15.gif'},{'id':16,'phrase':'[弱]','url':'16.gif'},
              {'id':17,'phrase':'[发怒]','url':'17.gif'},{'id':18,'phrase':'[ok]','url':'18.gif'},
              {'id':19,'phrase':'[握手]','url':'19.gif'},{'id':20,'phrase':'[强]','url':'20.gif'},
              
              {'id':21,'phrase':'[胜利]','url':'21.gif'},{'id':22,'phrase':'[亲亲]','url':'22.gif'},
              {'id':23,'phrase':'[调皮]','url':'23.gif'},{'id':24,'phrase':'[得意]','url':'24.gif'},
              {'id':25,'phrase':'[流汗]','url':'25.gif'},{'id':26,'phrase':'[晕]','url':'26.gif'},
              {'id':27,'phrase':'[惊讶]','url':'27.gif'},{'id':28,'phrase':'[疑问]','url':'28.gif'},
              {'id':29,'phrase':'[囧]','url':'29.gif'},{'id':30,'phrase':'[睡]','url':'30.gif'},
            ];
    //textarea设置光标位置
    function setCursorPosition(ctrl, pos) {
        if(ctrl.setSelectionRange) {
            ctrl.focus();
            ctrl.setSelectionRange(pos, pos);
        } else if(ctrl.createTextRange) {// IE Support
            var range = ctrl.createTextRange();
            range.collapse(true);
            range.moveEnd('character', pos);
            range.moveStart('character', pos);
            range.select();
        }
    }

    //获取多行文本框光标位置
    function getPositionForTextArea(obj)
    {
        var Sel = document.selection.createRange();
        var Sel2 = Sel.duplicate();
        Sel2.moveToElementText(obj);
        var CaretPos = -1;
        while(Sel2.inRange(Sel)) {
            Sel2.moveStart('character');
            CaretPos++;
        }
       return CaretPos ;

    }

    $.fn.extend({
        jqfaceedit : function(options) {
            var defaults = {
                txtAreaObj : '', //TextArea对象
                containerObj : '', //表情框父对象
                textareaid: 'msg',//textarea元素的id
                popName : '', //iframe弹出框名称,containerObj为父窗体时使用
                emotions : em, //表情信息json格式，id表情排序号 phrase表情使用的替代短语url表情文件名
                top : 0, //相对偏移
                left : 0 //相对偏移
            };
            
            var options = $.extend(defaults, options);
            var cpos=0;//光标位置，支持从光标处插入数据
            var textareaid = options.textareaid;
            
            return this.each(function() {
                var Obj = $(this);
                var container = options.containerObj;
                if ( document.selection ) {//ie
                    options.txtAreaObj.bind("click keyup",function(e){//点击或键盘动作时设置光标值
                        e.stopPropagation();
                        cpos = getPositionForTextArea(document.getElementById(textareaid)?document.getElementById(textareaid):window.frames[options.popName].document.getElementById(textareaid));
                    });
                }
                $(Obj).bind("click", function(e) {
                    e.stopPropagation();
                    var faceHtml = '<div id="face">';
                    faceHtml += '<div id="facebox">';
                    faceHtml += '<div id="face_detail" class="facebox clearfix"><ul>';

                    for( i = 0; i < options.emotions.length; i++) {
                        faceHtml += '<li text=' + options.emotions[i].phrase + ' type=' + i + '><img title=' + options.emotions[i].phrase + ' src="../kqtj/images/emotions/'+ options.emotions[i].url + '"  style="cursor:pointer; position:relative;"   /></li>';
                    }
                    faceHtml += '</ul></div>';
                    faceHtml += '</div><div class="arrow arrow_t"></div></div>';

                    container.find('#face').remove();
                    container.append(faceHtml);
                    
                    $('html,body').animate({scrollTop:$(".main").height()}, 700);//让滚动条滚到最底部
                    
                    container.find("#face_detail ul >li").bind("click", function(e) {
                        var txt = $(this).attr("text");
                        var faceText = txt;

                        var tclen = options.txtAreaObj.val().length;
                        
                        var tc = document.getElementById(textareaid);
                        if ( options.popName ) {
                            tc = window.frames[options.popName].document.getElementById(textareaid);
                        }
                        var pos = 0;
                        if( typeof document.selection != "undefined") {//IE
                            options.txtAreaObj.focus();
                            setCursorPosition(tc, cpos);//设置焦点
                            document.selection.createRange().text = faceText;
                            //计算光标位置
                            pos = getPositionForTextArea(tc); 
                        } else {//火狐
                            //计算光标位置
                            pos = tc.selectionStart + faceText.length;
                            options.txtAreaObj.val(options.txtAreaObj.val().substr(0, tc.selectionStart) + faceText + options.txtAreaObj.val().substring(tc.selectionStart, tclen));
                        }
                        cpos = pos;
                        setCursorPosition(tc, pos);//设置焦点
                        container.find("#face").remove();

                    });
                    //关闭表情框
                    container.find(".f_close").bind("click", function() {
                        container.find("#face").remove();
                    });
                    //处理js事件冒泡问题
                    $('body').bind("click", function(e) {
                        e.stopPropagation();
                        container.find('#face').remove();
                        $(this).unbind('click');
                    });
                    if(options.popName != '') {
                        $(window.frames[options.popName].document).find('body').bind("click", function(e) {
                            e.stopPropagation();
                            container.find('#face').remove();
                        });
                    }
                    container.find('#face').bind("click", function(e) {
                        e.stopPropagation();
                    });
                    var offset = $(e.target).offset();
                    offset.top += options.top;
                    offset.left += options.left;
                    container.find("#face").css(offset).show();
                });
            });
        },
        //表情文字符号转换为html格式
        emotionsToHtml : function(options) {
            return this.each(function() {
                var msgObj = $(this);
                var rContent = msgObj.html();

                var regx = /(\[[\u4e00-\u9fa5]*\w*\]){1}/g;
                //正则查找“[]”格式
                var rs = rContent.match(regx);
                if(rs) {
                    for( i = 0; i < rs.length; i++) {
                        for( n = 0; n < em.length; n++) {
                            if(em[n].phrase == rs[i]) {
                                var t = "<img src='../kqtj/images/emotions/"  + em[n].url + "' />";
                                rContent = rContent.replace(rs[i], t);
                                break;
                            }
                        }
                    }
                }
                msgObj.html(rContent);
            });
        }
    })
})(jQuery);
