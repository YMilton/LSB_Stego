# LSB_Stego
The Steganography of LSB, which is dynamic compensation.
It implements by Java.

# LSB隐写算法实现
## 配置java环境变量后可执行项目
## 嵌入工程
![](https://github.com/YMilton/LSB_Stego/blob/master/imgs/嵌入.png)<br>

### 运行工程embed.jar
1.通过浏览选择载体图像，获取载体图像的路径；<br>
2.点击按钮浏览选择待加密的文件，获取加密文件的路径；<br>
3.为了安全性考虑，对文件先用密码做加密操作，确认两次输入密码一致；<br>
## 提取工程
![](https://github.com/YMilton/LSB_Stego/blob/master/imgs/提取.png)<br>

### 运行工程extract.jar
1.通过浏览获取嵌入密码文件的载密图像路径；<br>
2.输入针对文件加密的密码；<br>
