## 包讲解
### GUI:嵌入工程的图形化界面
>com.embed.GUI
>>ChFileFrame  文件路径选择器<br>
>>ChImageFrame  图像路径选择器<br>
>>EmbedFrame  调用ChFileFrame、ChImageFrame并继承MyFrame的布局，以及嵌入过程的触发操作实现<br>
>>MyFrame  嵌入工程GUI布局<br>
### Process: 文件嵌入载体图像的过程
>com.embed.Process<br>
>>EmbedProcess  算法的核心接口，文件嵌入(隐藏)到图像中<br>
### Start: 工程的入口
>com.embed.start<br>
>>Start 嵌入过程的程序入口<br>
