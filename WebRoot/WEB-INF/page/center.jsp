<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <style>
    	/*#spanTitle{
    		width:110px;
    	}
    	#tab1{
    		width:115px;
    		height:36px;
    		margin-right:0px;
    	}*/
    </style>
    <title>食堂管理系统</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

  <link rel="stylesheet" href="${pageContext.request.contextPath }/css/default.css" type="text/css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath }/css/main.css" type="text/css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css" type="text/css"/>

  <script type="text/javascript" src="${pageContext.request.contextPath }/js/menu.js"></script>
  <script type="text/javascript">
	  //修改标题
	  function show_title(str){
	  	  var href = "${pageContext.request.contextPath }/index.jsp";
		  document.getElementById("spanTitle").innerHTML=str;
		  var oIfram = document.getElementById("subIfram");
		  switch(str){
		  	case "类型信息":
		  		href = "${pageContext.request.contextPath}/control/info/ftmanager_listUI.do";
		  		break;
		  	case "食品信息":
		  		href = "${pageContext.request.contextPath}/control/info/fmanager_listUI.do";
		  		break;
		  	 case "供货商信息":
		  	 	href = "${pageContext.request.contextPath}/control/info/splmanager_listUI.do";
		  	 	break;
		  	 case "管理员信息":
		  	 	href = "${pageContext.request.contextPath}/control/info/usersmanager_listUI.do";
		  	 	break;
		  	 case "进货信息":
		  	 	href = "${pageContext.request.contextPath}/control/stock/stockmanager_listUI.do";
		  	 	break;	
		  }
		  oIfram.src = href;
	  }
  </script>
  </head>
  
<body onload="javascript:border_left('left_tab1','left_menu_cnt1');">
    <form id="form1">
        <table id="indextablebody" cellpadding="0">
            <thead>
                <tr>
                    <th>
                        <div id="logo" title="用户管理后台">
                        </div>
                    </th>
                    <th>
                        <a style="color: #16547E">用户 ：admin</a>&nbsp;&nbsp; <a style="color: #16547E">身份 ：超级管理员</a>&nbsp;&nbsp;
                        <a href="javascript:window.location.reload()" target="content3">隐藏工作台</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                        <a href="javascript:window.location.reload()" target="content3">管理首页</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                        <a href="help" target="_blank">使用帮助</a>
                    </th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td class="menu">
                        <ul class="bigbtu">
                            <li id="now01"><a title="安全退出" href="#">安全退出</a></li>
                            <li id="now02"><a title="更新缓冲" href="#">更新缓冲</a></li>
                        </ul>
                    </td>
                    <td class="tab">
                        <ul id="tabpage1">
                            <li id="tab1" title="管理首页"><span id="spanTitle">管理首页</span></li>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td class="t1">
                        <div id="contents">
                            <table cellpadding="0">
                                <tr class="t1">
                                    <td>
                                        <div class="menu_top">
                                        </div>
                                    </td>
                                </tr>
                                <tr class="t2">
                                    <td>
                                        <div id="menu" class="menu">
                                            <ul class="tabpage2">
                                                <li id="left_tab1" title="基本信息" onClick="javascript:border_left('left_tab1','left_menu_cnt1');"><span>基本信息</span></li>
                                            </ul>
                                            <div id="left_menu_cnt1" class="left_menu_cnt">
                                                <ul id="dleft_tab1">
                                                    <li id="dleft_tab1_now11"><a title="类型信息" href="javascript:show_title('类型信息');" target="content3"><span>类型信息</span></a></li>
                                                    <li id="dleft_tab1_now12"><a title="食品信息" href="javascript:show_title('食品信息');" target="content3"><span>食品信息</span></a></li>
                                                    <li id="dleft_tab1_now13"><a title="供货商信息" href="javascript:show_title('供货商信息');" target="content3"><span>供货商信息</span></a></li>
                                                    <li id="dleft_tab1_now14"><a title="管理员信息" href="javascript:show_title('管理员信息');" target="content3"><span>管理员信息</span></a></li>
                                                </ul>
                                            </div>                                          
                                            <div class="clear"> </div>
                                            <ul class="tabpage2">
                                                <li id="left_tab2" title="进货管理" onClick="javascript:border_left('left_tab2','left_menu_cnt2');"><span>进货管理</span></li>
                                            </ul>
                                            <div id="left_menu_cnt2" class="left_menu_cnt">
                                                <ul id="dleft_tab2">
                                                    <li id="dleft_tab2_now11"><a title="进货信息" href="javascript:show_title('进货信息');" target="content3"><span>进货信息</span></a></li>
                                                    <li id="dleft_tab2_now12"><a title="进货分析" href="javascript:show_title('进货分析');" target="content3"><span>进货分析</span></a></li>
                                                    <li id="dleft_tab2_now13"><a title="库存分析" href="javascript:show_title('库存分析');" target="content3"><span>库存分析</span></a></li>
                                                </ul>
                                            </div>                                          
                                            <div class="clear"> </div>
                                            <ul class="tabpage2">
                                                <li id="left_tab3" title="销售管理" onClick="javascript:border_left('left_tab3','left_menu_cnt3');"><span>销售管理</span></li>
                                            </ul>
                                            <div id="left_menu_cnt3" class="left_menu_cnt">
                                                <ul id="dleft_tab3">
                                                    <li id="dleft_tab3_now11"><a title="销售信息" href="javascript:show_title('销售信息');" target="content3"><span>销售信息</span></a></li>
                                                    <li id="dleft_tab3_now12"><a title="销售分析" href="javascript:show_title('销售分析');" target="content3"><span>销售分析</span></a></li>
                                                </ul>
                                            </div>                                          
                                            <div class="clear"> </div>    
                                            <ul class="tabpage2">
                                                <li id="left_tab4" title="销售管理" onClick="javascript:border_left('left_tab4','left_menu_cnt4');"><span>销售管理</span></li>
                                            </ul>
                                            <div id="left_menu_cnt4" class="left_menu_cnt">
                                                <ul id="dleft_tab4">
                                                    <li id="dleft_tab4_now11"><a title="报损信息" href="javascript:show_title('报损信息');" target="content3"><span>报损信息</span></a></li>
                                                    <li id="dleft_tab4_now12"><a title="报损分析" href="javascript:show_title('报损分析');" target="content3"><span>报损分析</span></a></li>
                                                </ul>
                                            </div>                                          
                                            <div class="clear"> </div>                                                                                     
                                        </div>
                                        <tr class="t3">
                                            <td>
                                                <div class="menu_end">
                                                </div>
                                            </td>
                                        </tr>
                            </table>
                        </div>
                    </td>
                    <td class="t2">
                        <div id="cnt">
                            <div id="dtab1">
                                <iframe id="subIfram"  name="content3" src="${pageContext.request.contextPath }/index.jsp" frameborder="0"></iframe>
                            </div>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</body>
</html>
