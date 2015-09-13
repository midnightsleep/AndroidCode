# AndroidCode
##摘要
这个仓库用来存放我的安卓代码，包括我自己写的一些小项目，或者down的别人的一些美化的demo
##内容
###(1)tieba
#####项目描述
    这个是一个安卓版本的百度贴吧辅助工具，名曰“贴吧查贴助手”。百度贴吧可以设置隐藏发帖记录，通过这个APP
    可以根据贴吧ID搜索到其发帖纪录。
#####实现原理
    1.利用DefaultHttpClient类获取网页源代码
    2.通过Pattern与Matcher类从源代码中抓取需要的内容
    3.建立一个MyBaseAdapter类继承于BaseAdapter,用于对ListView控件进行数据适配，因为需要数据动态改变时，这
    里使用适配器的notifyDataSetChanged()方法
    4.在ListView控件的每个Item上添加点击事件，通过浏览器打开帖子的地址
    5.不要忘了在Manifest.xml文件中添加访问网络的权限
#####后续开发
    1.界面美化
    2.ListView 的item的点击事件以贴吧客户端打开。（据说百度的Applink可以对网址进行分析，如果你输入了贴吧的
    网址，而你手机上有贴吧客户端，那么这个网址会自动以客户端的形式打开。好像并不可以。）
#####下载地址
    我竟然给它发布了(⊙o⊙)…  
    [贴吧查贴助手](http://anzhuoyuan.com/app/info/appid/224170.html )

###(2)LoginDemo
#####项目描述
    这个是我在CSDN上看到的一个很棒的登录界面的demo，存了下来。

###(3)3.7
#####项目描述
    这是一门课的大作业，选了安卓版的云笔记，当时没接触Android，买了本Android从入门到精通，从书里找了一个代码，然后改
    的，这个是实现了注册的功能（后来改了选择，做了个小游戏）

###(4)云笔记
#####项目描述
    这个在3.7的基础上实现了注册，登录验证，添加笔记功能。


##个人邮箱：wh01096045@163.com
##个人博客：[csdn博客](http://blog.csdn.net/wh01096045)