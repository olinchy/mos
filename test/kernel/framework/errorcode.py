#coding=gbk
#******************************************************************************
# 用例名称：XXX 
# 保留编号：         
# 其它说明：XXX 
# 修改日期        版本号     修改人       修改内容     修改编号 
# ------------------------------------------------------------------------- 
# 2011/10/2  V1.0   张廷全       创建               
#******************************************************************************

#说明：ErrorCode模块已经添加到Device、TestCase类，在这两个类的子类里
#均可以使用本模块里的函数；

import sys



#1、******************错误码********************#
#错误码为32位整数
#最高4bit表示各子系统：框架、驱动，用例等
#次高4bit用来表示子系统中的模块：比如驱动子系统中的8250驱动、testcenter驱动等
#16~23bit表示错误类型，比如连接设备错误，函数入参相关错，文件相关错误
#12~15bit： 表示子模块，如8250驱动中的Config模块
#；0~11bit：错误码使用者，需要各模块负责人合理规划，自定义使用。

#错误码起始
ERR_START=0x00000000

#错误类型，目前定义这几种错误类型，使用时,错误类型只有与具体模块、子模块结合起来才有意义；
#同样的，错误码中填充了错误类型，才能针对不同的错误进行处理
ERR_TYPE_START=ERR_START+0x00000000
# 0xXX01XXXX:连接失败，比如telnet失败等

ERR_TYPE_CONNECT=ERR_TYPE_START+0x00010000
#0xXX02XXXX: 入参有误
ERR_TYPE_INPUT=ERR_TYPE_START+0x00020000
#0xXX03XXXX: 工程xml文件不存在、打开失败等
ERR_TYPE_PROJECTFILE=ERR_TYPE_START+0x00030000
#0xXX04XXXX: 用例脚本文件不存在、打开失败等
ERR_TYPE_CASEFILE=ERR_TYPE_START+0x00040000
#0xXX05XXXX: 用例数据xml文件不存在、打开失败等
ERR_TYPE_CASEDATAFILE=ERR_TYPE_START+0x00050000
#0xXX06XXXX: 用例数据字典self.dataSet数据项有问题，比如，不存在self.dataSet["Key1"]等
ERR_TYPE_CASEDATASET=ERR_TYPE_START+0x00060000
#配置失败
ERR_TYPE_CONFIG=ERR_TYPE_START+0x00070000
#未知失败，尽量不要使用
ERR_TYPE_UNKNOWN=ERR_TYPE_START+0x00ff0000

#Framework ErrorCode
#1、框架错误码
ERR_FRAME_START=ERR_START+0x10000000


#2、驱动错误码
ERR_DRIVER_START=ERR_START+0x20000000

#2.1LMT或者将来的命令行，使用的错误码
ERR_DRIVER_NR8250_START=ERR_DRIVER_START+0x01000000


#2.2TestCenter驱动使用的错误码
ERR_DRIVER_STC_START=ERR_DRIVER_START+0x02000000

#2.3FTB500使用的错误码
ERR_DRIVER_FTB500_START=ERR_DRIVER_START+0x03000000

#3、测试用例使用的错误码
ERR_CASE_START=ERR_START+0x30000000

#3.1 配置相关测试用例错误码
ERR_CASE_CFG_START=ERR_CASE_START+0x01000000

#3.2 以太网测试用例错误码
ERR_CASE_ETH_START=ERR_CASE_START+0x02000000

#3.3 TDM测试用例错误码
ERR_CASE_TDM_START=ERR_CASE_START+0x03000000

#4、子模块使用的错误码
#以低16位的前4个bit来标识子模块
#以FTB500驱动为例说明：FTB500驱动，分为几个子模块：
#IoCommand,Config,Alarm,Result，FTB_500（对外接口）
#这几个模块的错误码起始可以这样：
ERR_DRIVER_FTB500_IOCOMMAND_START = ERR_DRIVER_FTB500_START+0x00001000
ERR_DRIVER_FTB500_CONFIG_START=ERR_DRIVER_FTB500_START+0x00002000
ERR_DRIVER_FTB500_ALARM_START=ERR_DRIVER_FTB500_START+0x00003000
ERR_DRIVER_FTB500_RESULT_START=ERR_DRIVER_FTB500_START+0x00004000
ERR_DRIVER_FTB500_INTERFACE_START=ERR_DRIVER_FTB500_START+0x00005000

#5、函数使用的错误码命名，请在模块、子模块的基础上写，以FTB500，Config模块的ConfigE1函数
ERR_DRIVER_FTB500_CONFIG_CONFIGE1=ERR_DRIVER_FTB500_CONFIG_START+0x00000001

#6、函数分支错误码命名,在函数错误码基础上进行，比如，ConfigE1的一个异常分支
ERR_DRIVER_FTB500_CONFIG_CONFIGE1_EXCEPT=ERR_DRIVER_FTB500_CONFIG_CONFIGE1+0x00000001

#7、错误码的使用
#举例说明，比如在FTB_500驱动模块的Config子模块，其中的ConfigE1函数，走到了一个异常，比如数据self.dataSet异常，
#那么，在函数返回前，需要打印错误码，以及文件名、行号，打印级别定为debug,此时在return错误码之前，
#对异常进行打印，如下：
#self.logger.debug("%s,%s,%s,%d" %(hex(ERR_CASE_TDM_E1TEST+self.ErrorCode.ERR_TYPE_CASEDATASET),sys._getframe().f_code.co_filename,sys._getframe().f_code.co_name,sys._getframe().f_lineno))

#如果是该函数自身，比如该函数为A,A自己出现的错误，返回该A或者A分支的错误码，如果是在A中调用其他函数B,B返回值不为0，
#那么，直接在A中返回 B的返回值。

#8、错误处理
#在函数调用中，如果调用失败，我们可以根据返回的错误码，知道是何种类型的错误，
#对于，无法恢复的错误，比如工程文件不存在，只好停止软件运行；可以恢复的错误，比如telnet断开，
#则需要进行处理，比如Reconnect，在驱动里面，无论是否做这种处理，都需要提供可以处理这种异常的函数。

#9、注意事项
#错误码是16进制数，因此每位可以出现的字符是：0~f

#获取文件名 函数名 行号
#sys._getframe().f_code.co_filename,sys._getframe().f_code.co_name,sys._getframe().f_lineno


#2、*********************调用链************************#

#使用方法：在驱动或者测试用例里面使用，如果某函数的返回结果为失败，
#可以在该函数调用前加入语句：self.ErrorCode.StartTrace(),在该函数执行
#结束之后，再加入self.ErrorCode.PrintTrace(),即可获取该函数中的调用链；
#例如：
#self.ErrorCode.StartTrace()
#Fun(parameters)
#self.ErrorCode.PrintTrace()


#trace item
class FileFunLine(object):
    def __init__(self,File,Fun,Line):
        self.File=File
        self.Fun=Fun
        self.Line=Line

#save fun list
FunList=[]#存放调用链
ClearFlg=False  #是否清空调用链
ErrorNum=0     #放置存放的错误信息
#trace fun
def traceit(frame, event, arg): 
    if event == "call": 
        try: 
            raise Exception 
        except: 
            try:
                f = sys.exc_info()[2].tb_frame.f_back
            except:
                pass
        try:
            if f.f_code.co_filename.find(sys.prefix)<0 and f.f_code.co_filename.find(".py")>0:
                
                Temp=FileFunLine(f.f_code.co_filename, f.f_code.co_name, f.f_lineno )
                if QueryClearFlg():
                    FunList[:]=[]
                    ResetClearFlg()
                FunList.append(Temp)
        except:
            pass
    return traceit 
    
sys.settrace(traceit) 

#查询是否清空标志
def QueryClearFlg():
    global ClearFlg
    return ClearFlg

#设置清空标志

def SetClearFlg():
    global ClearFlg
    ClearFlg=True
#置位清空标志
def ResetClearFlg():
    global ClearFlg
    ClearFlg=False

#start trace
def StartTrace():
    SetClearFlg()
    
def PrintTrace():
    global FunList
    try:
        for Item in FunList:
            if Item.Fun!="PrintTrace":
                print Item.File,Item.Fun,Item.Line
    except:
        print "Error occur ,print trace"








