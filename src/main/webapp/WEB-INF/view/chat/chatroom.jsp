<%--
  Created by IntelliJ IDEA.
  User: xhuanlee
  Date: 2017/3/19
  Time: 10:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String contextPath = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" +
        request.getServerPort() + contextPath + "/";
%>
<html>
<head>
    <title>聊天室</title>
    <link rel="stylesheet" href="<%=basePath%>static/jslib/layui/css/layui.css">
</head>
<body>
    <input id="basePath" value="<%=basePath%>">
    <div class="page-div">
        <div class="user-div"></div>
        <div class="message-div-container">
            <div id="message-show" class="message"></div>
            <div class="input-message">
                <textarea id="input" class="layui-textarea"></textarea>
                <button id="send" class="layui-btn layui-btn-primary"></button>
            </div>
        </div>
    </div>
    <script src="<%=basePath%>static/jslib/jquery/jquery-3.2.0.min.js"></script>
    <script src="<%=basePath%>static/jslib/sockjs/sockjs.min.js"></script>
    <script src="<%=basePath%>static/jslib/layui/layui.js"></script>
    <script>
        var basePath = $("#basePath").val();
        var web_socket_user_key = "${username}";
        var websocket;
        $(function () {
            layui.use(["layer", "form"], function () {
                var layer = layui.layer,
                    layForm = layui.form();
                if(web_socket_user_key == undefined || web_socket_user_key == "") {
                    layer.prompt({
                        formType: 0,
                        title: "请输入您的用户名",
                    }, function(value, index, elem){
                        value = value == undefined ? "" : $.trim(value);
                        if(value == "") {
                            layer.msg("请输入用户名", {icon : 4, anim : 4})
                            return;
                        }
                        $.getJSON(basePath + "login/in", {
                            userName : value
                        }, function (data) {
                           if(data.RESULT == "success") {
                               layer.close(index);
                               websocket = new SockJS(basePath + "websocket/server/sockjs");
                               initSocket();
                           }
                        });
                    });
                } else {
                    initSocket();
                }

                var initSocket = function () {
                    websocket.onopen = function(evnt) {
                        openSocket(evnt);
                    }
                    websocket.onclose = function(evnt) {
                        closeSocket(evnt);
                    }
                    websocket.onmessage = function(evnt) {
                        receiveSocket(evnt);
                    }
                    websocket.onerror = function(evnt) {
                        errorSocket(evnt);
                    }
                }

                var openSocket = function(evnt) {
                    layer.msg("socket 已连接", {icon : 3, anim : 3});
                };

                var closeSocket = function (evnt) {
                    layer.msg("socket 已断开连接", {icon : 3, anim : 3});
                };

                var receiveSocket = function (evnt) {
                    var _liElem = $("<li></li>")
                    _liElem.text(evnt.data);
                    $("#message-show").append(_liElem)
                };
                
                var errorSocket = function (evnt) {
                    layer.msg("socket 连接异常", {icon : 3, anim : 3});
                }

                $(document).on("click", "#send", function (evnt) {
                    $.getJSON(basePath + "message/send", {
                        message : $("#input").val()
                    }, function (data) {
                        layer.msg(data.MSG, {icon : 6, anim : 3});
                    })
                })
            })
        })

    </script>
</body>
</html>
