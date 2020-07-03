# PushBox
java 自动推箱子算法
通过纯文本文件导入地图，
Map.W = 10（设置地图宽度）
Map.H = 10（设置地图高度）

输入：  

8888888888
8000010008
8008828008
8008008008
8000008008
8008208008
8008008008
8008888008
8001400008
8888888888
// 0:空位
// 1：目标位置
// 2：箱子位置
// 8:墙壁位置
// 4:站立位置
// 3：摆放正确


图形化为：  
##########  
#00001000#  
#00##2#00#  
#00#00#00#  
#00000#00#  
#00#20#00#  
#00#00#00#  
#00####00#  
#00100000#  
##########  



输出结果，倒叙：
（#代表墙壁，1代表目标位置，2代表箱子位置，3代表以达到目标位置的箱子）  
##########  
#00003000#  
#00##0#00#  
#00#00#00#  
#00000#00#  
#00#00#00#  
#00#00#00#  
#00####00#  
#00300000#  
##########  

##########  
#00003000#  
#00##0#00#  
#00#00#00#  
#00000#00#  
#00#00#00#  
#00#00#00#  
#00####00#  
#02100000#  
##########  

##########  
#00003000#  
#00##0#00#  
#00#00#00#  
#00000#00#  
#00#00#00#  
#00#00#00#  
#02####00#  
#00100000#  
##########  

##########  
#00003000#  
#00##0#00#  
#00#00#00#  
#00000#00#  
#00#00#00#  
#02#00#00#  
#00####00#  
#00100000#  
##########  

##########  
#00003000#  
#00##0#00#  
#00#00#00#  
#00000#00#  
#02#00#00#  
#00#00#00#  
#00####00#  
#00100000#  
##########  

##########  
#00003000#  
#00##0#00#  
#00#00#00#  
#02000#00#  
#00#00#00#  
#00#00#00#  
#00####00#  
#00100000#  
##########  

##########  
#00021000#  
#00##0#00#  
#00#00#00#  
#02000#00#  
#00#00#00#  
#00#00#00#  
#00####00#  
#00100000#  
##########  

##########  
#00021000#  
#00##0#00#  
#00#00#00#  
#00200#00#  
#00#00#00#  
#00#00#00#  
#00####00#  
#00100000#  
##########  

##########  
#00021000#  
#00##0#00#  
#00#00#00#  
#00020#00#  
#00#00#00#  
#00#00#00#  
#00####00#  
#00100000#  
##########  

##########  
#00021000#  
#00##0#00#  
#00#00#00#  
#00000#00#  
#00#20#00#  
#00#00#00#  
#00####00#  
#00100000#  
##########  

##########  
#00003000#  
#00##0#00#  
#00#00#00#  
#00000#00#  
#00#20#00#  
#00#00#00#  
#00####00#  
#00100000#  
##########  

##########  
#00001000#  
#00##2#00#  
#00#00#00#  
#00000#00#  
#00#20#00#  
#00#00#00#  
#00####00#  
#00100000#  
##########  