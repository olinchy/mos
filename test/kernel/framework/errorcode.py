#coding=gbk
#******************************************************************************
# �������ƣ�XXX 
# ������ţ�         
# ����˵����XXX 
# �޸�����        �汾��     �޸���       �޸�����     �޸ı�� 
# ------------------------------------------------------------------------- 
# 2011/10/2  V1.0   ��͢ȫ       ����               
#******************************************************************************

#˵����ErrorCodeģ���Ѿ���ӵ�Device��TestCase�࣬�����������������
#������ʹ�ñ�ģ����ĺ�����

import sys



#1��******************������********************#
#������Ϊ32λ����
#���4bit��ʾ����ϵͳ����ܡ�������������
#�θ�4bit������ʾ��ϵͳ�е�ģ�飺����������ϵͳ�е�8250������testcenter������
#16~23bit��ʾ�������ͣ����������豸���󣬺��������ش��ļ���ش���
#12~15bit�� ��ʾ��ģ�飬��8250�����е�Configģ��
#��0~11bit��������ʹ���ߣ���Ҫ��ģ�鸺���˺���滮���Զ���ʹ�á�

#��������ʼ
ERR_START=0x00000000

#�������ͣ�Ŀǰ�����⼸�ִ������ͣ�ʹ��ʱ,��������ֻ�������ģ�顢��ģ���������������壻
#ͬ���ģ�������������˴������ͣ�������Բ�ͬ�Ĵ�����д���
ERR_TYPE_START=ERR_START+0x00000000
# 0xXX01XXXX:����ʧ�ܣ�����telnetʧ�ܵ�

ERR_TYPE_CONNECT=ERR_TYPE_START+0x00010000
#0xXX02XXXX: �������
ERR_TYPE_INPUT=ERR_TYPE_START+0x00020000
#0xXX03XXXX: ����xml�ļ������ڡ���ʧ�ܵ�
ERR_TYPE_PROJECTFILE=ERR_TYPE_START+0x00030000
#0xXX04XXXX: �����ű��ļ������ڡ���ʧ�ܵ�
ERR_TYPE_CASEFILE=ERR_TYPE_START+0x00040000
#0xXX05XXXX: ��������xml�ļ������ڡ���ʧ�ܵ�
ERR_TYPE_CASEDATAFILE=ERR_TYPE_START+0x00050000
#0xXX06XXXX: ���������ֵ�self.dataSet�����������⣬���磬������self.dataSet["Key1"]��
ERR_TYPE_CASEDATASET=ERR_TYPE_START+0x00060000
#����ʧ��
ERR_TYPE_CONFIG=ERR_TYPE_START+0x00070000
#δ֪ʧ�ܣ�������Ҫʹ��
ERR_TYPE_UNKNOWN=ERR_TYPE_START+0x00ff0000

#Framework ErrorCode
#1����ܴ�����
ERR_FRAME_START=ERR_START+0x10000000


#2������������
ERR_DRIVER_START=ERR_START+0x20000000

#2.1LMT���߽����������У�ʹ�õĴ�����
ERR_DRIVER_NR8250_START=ERR_DRIVER_START+0x01000000


#2.2TestCenter����ʹ�õĴ�����
ERR_DRIVER_STC_START=ERR_DRIVER_START+0x02000000

#2.3FTB500ʹ�õĴ�����
ERR_DRIVER_FTB500_START=ERR_DRIVER_START+0x03000000

#3����������ʹ�õĴ�����
ERR_CASE_START=ERR_START+0x30000000

#3.1 ������ز�������������
ERR_CASE_CFG_START=ERR_CASE_START+0x01000000

#3.2 ��̫����������������
ERR_CASE_ETH_START=ERR_CASE_START+0x02000000

#3.3 TDM��������������
ERR_CASE_TDM_START=ERR_CASE_START+0x03000000

#4����ģ��ʹ�õĴ�����
#�Ե�16λ��ǰ4��bit����ʶ��ģ��
#��FTB500����Ϊ��˵����FTB500��������Ϊ������ģ�飺
#IoCommand,Config,Alarm,Result��FTB_500������ӿڣ�
#�⼸��ģ��Ĵ�������ʼ����������
ERR_DRIVER_FTB500_IOCOMMAND_START = ERR_DRIVER_FTB500_START+0x00001000
ERR_DRIVER_FTB500_CONFIG_START=ERR_DRIVER_FTB500_START+0x00002000
ERR_DRIVER_FTB500_ALARM_START=ERR_DRIVER_FTB500_START+0x00003000
ERR_DRIVER_FTB500_RESULT_START=ERR_DRIVER_FTB500_START+0x00004000
ERR_DRIVER_FTB500_INTERFACE_START=ERR_DRIVER_FTB500_START+0x00005000

#5������ʹ�õĴ���������������ģ�顢��ģ��Ļ�����д����FTB500��Configģ���ConfigE1����
ERR_DRIVER_FTB500_CONFIG_CONFIGE1=ERR_DRIVER_FTB500_CONFIG_START+0x00000001

#6��������֧����������,�ں�������������Ͻ��У����磬ConfigE1��һ���쳣��֧
ERR_DRIVER_FTB500_CONFIG_CONFIGE1_EXCEPT=ERR_DRIVER_FTB500_CONFIG_CONFIGE1+0x00000001

#7���������ʹ��
#����˵����������FTB_500����ģ���Config��ģ�飬���е�ConfigE1�������ߵ���һ���쳣����������self.dataSet�쳣��
#��ô���ں�������ǰ����Ҫ��ӡ�����룬�Լ��ļ������кţ���ӡ����Ϊdebug,��ʱ��return������֮ǰ��
#���쳣���д�ӡ�����£�
#self.logger.debug("%s,%s,%s,%d" %(hex(ERR_CASE_TDM_E1TEST+self.ErrorCode.ERR_TYPE_CASEDATASET),sys._getframe().f_code.co_filename,sys._getframe().f_code.co_name,sys._getframe().f_lineno))

#����Ǹú�����������ú���ΪA,A�Լ����ֵĴ��󣬷��ظ�A����A��֧�Ĵ����룬�������A�е�����������B,B����ֵ��Ϊ0��
#��ô��ֱ����A�з��� B�ķ���ֵ��

#8��������
#�ں��������У��������ʧ�ܣ����ǿ��Ը��ݷ��صĴ����룬֪���Ǻ������͵Ĵ���
#���ڣ��޷��ָ��Ĵ��󣬱��繤���ļ������ڣ�ֻ��ֹͣ������У����Իָ��Ĵ��󣬱���telnet�Ͽ���
#����Ҫ���д�������Reconnect�����������棬�����Ƿ������ִ�������Ҫ�ṩ���Դ��������쳣�ĺ�����

#9��ע������
#��������16�����������ÿλ���Գ��ֵ��ַ��ǣ�0~f

#��ȡ�ļ��� ������ �к�
#sys._getframe().f_code.co_filename,sys._getframe().f_code.co_name,sys._getframe().f_lineno


#2��*********************������************************#

#ʹ�÷��������������߲�����������ʹ�ã����ĳ�����ķ��ؽ��Ϊʧ�ܣ�
#�����ڸú�������ǰ������䣺self.ErrorCode.StartTrace(),�ڸú���ִ��
#����֮���ټ���self.ErrorCode.PrintTrace(),���ɻ�ȡ�ú����еĵ�������
#���磺
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
FunList=[]#��ŵ�����
ClearFlg=False  #�Ƿ���յ�����
ErrorNum=0     #���ô�ŵĴ�����Ϣ
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

#��ѯ�Ƿ���ձ�־
def QueryClearFlg():
    global ClearFlg
    return ClearFlg

#������ձ�־

def SetClearFlg():
    global ClearFlg
    ClearFlg=True
#��λ��ձ�־
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








