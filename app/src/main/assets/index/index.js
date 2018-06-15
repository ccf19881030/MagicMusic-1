mui.init({swipeBack: false
,gestureConfig: {tap: true,doubletap: true,longtap: true}});

var 列表框1 = new 列表框("列表框1",true,列表框1_表项被单击,null);
if(mui.os.plus){
    mui.plusReady(function() {
        主窗口_创建完毕();
        
    });
}else{
    window.onload=function(){ 
        主窗口_创建完毕();
        
    }
}

function 主窗口_创建完毕(){
列表框1.添加项目("网络","http://yinyue.kuwo.cn/yy/cate_27.htm","","")
列表框1.添加项目("DJ","http://yinyue.kuwo.cn/yy/cate_28.htm" ,"","")
列表框1.添加项目("经典","http://yinyue.kuwo.cn/yy/cate_44.htm","","")
列表框1.添加项目("情歌","http://yinyue.kuwo.cn/yy/cate_77857.htm","","")
列表框1.添加项目("铃声","http://yinyue.kuwo.cn/yy/cate_75.htm","","")
列表框1.添加项目("小清新","http://yinyue.kuwo.cn/yy/cate_58.htm","","")
列表框1.添加项目("儿歌","http://yinyue.kuwo.cn/yy/cate_70.htm","","")
列表框1.添加项目("影视","http://yinyue.kuwo.cn/yy/cate_56.htm","","")
列表框1.添加项目("慢摇","http://yinyue.kuwo.cn/yy/cate_48.htm","","")
列表框1.添加项目("中国风","http://yinyue.kuwo.cn/yy/cate_57.htm","","")
列表框1.添加项目("轻音乐","http://yinyue.kuwo.cn/yy/cate_60.htm","","")
列表框1.添加项目("70后","http://yinyue.kuwo.cn/yy/cate_45.htm","","")
列表框1.添加项目("80后","http://yinyue.kuwo.cn/yy/cate_46.htm","","")
列表框1.添加项目("90后","http://yinyue.kuwo.cn/yy/cate_47.htm","","")
列表框1.添加项目("动漫","http://yinyue.kuwo.cn/yy/cate_63.htm","","")
列表框1.添加项目("校园","http://yinyue.kuwo.cn/yy/cate_61.htm","","")
列表框1.添加项目("相声小品","http://yinyue.kuwo.cn/yy/cate_65.htm","","")
列表框1.添加项目("评书","http://yinyue.kuwo.cn/yy/cate_79.htm")
列表框1.添加项目("有声读物","http://yinyue.kuwo.cn/yy/cate_17250.htm","","")
列表框1.添加项目("戏剧","http://yinyue.kuwo.cn/yy/cate_72.htm","","")
列表框1.添加项目("器乐","http://yinyue.kuwo.cn/yy/cate_67.htm","","")
列表框1.添加项目("民歌","http://yinyue.kuwo.cn/yy/cate_68.htm","","")
列表框1.添加项目("民乐","http://yinyue.kuwo.cn/yy/cate_69.htm","","")
列表框1.添加项目("对唱","http://yinyue.kuwo.cn/yy/cate_54.htm","","")
}

function 列表框1_表项被单击(项目索引,项目标题,项目标记){
	窗口操作.打开指定网址(项目标记,1);
}