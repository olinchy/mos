#coding=gbk
'''
Created on 2011-8-19

@author: ����
'''
import os
import logging
import logging.handlers
import time
import types

class LogManager( object ):
    '''
    ����������־����
    '''


    CRITICAL = 50
    FATAL = CRITICAL
    ERROR = 40
    WARNING = 30
    WARN = WARNING
    INFO = 20
    DEBUG = 10
    NOTSET = 0

    def __init__( self, root ):
        '''
        Constructor
        projName ��ָ��ʶ�������Ƶ��ַ���
        '''
        reportDir = time.strftime( "%Y%m%d%H%M%S", time.localtime() )
        self.reportsRootDir = root

        self.FileLogHandler = None
        self.CmdLogHandler = None
        self.CmdLogLevel = logging.DEBUG
        self.FileLogLevel = logging.DEBUG

        self.createLog( "framework" )

    def createLog( self, Filename, LogItemName = "" ):
        '''
         
        Filename ��ָҪ�������־�����ƣ���ȥ����׺���ļ���������Ҫ����Ϊtest1.log����Ϊtest1
        ͨ��������ÿ����������һ����־�ļ�����FileName����Ϊ������������������
        LogItemName ����������־����ʾ����־������һ���ֵģ�����ĳ���������������־����������������Ժ�����ÿ�����Ժ����ְ�������������ݣ�
        �Ϳ��Բ���LogItemName����ʶ���֡� �����������飬��Ĭ�Ϻ�FileNameһ��
        '''
        if not LogItemName:
            LogItemName = Filename
        Log = logging.getLogger()
        Log.setLevel( logging.NOTSET )
        if   self.CmdLogHandler:
            Log.removeHandler( self.CmdLogHandler )
        if   self.FileLogHandler:
            Log.removeHandler( self.FileLogHandler )
        Log.setLevel( logging.NOTSET )
        self.CmdLogHandler = logging.StreamHandler()
        name2 = Filename
        if type( name2 ) is  types.UnicodeType  :
            name2 = name2.encode( "gbk" )
        Partname2 = LogItemName
        if type( Partname2 ) is  types.UnicodeType  :
            Partname2 = Partname2.encode( "gbk" )
        File = self.reportsRootDir + os.sep + "TestSysLog" + os.sep + name2 + ".log"
        self.CmdLogHandler.setLevel( self.CmdLogLevel )
            # set a format which is simpler for console use
        formatter = logging.Formatter( '%(asctime)s  %(name)-25s %(levelname)-8s %(message)s', '%Y-%m-%d %H:%M:%S' )
            # tell the handler to use this format
        self.CmdLogHandler.setFormatter( formatter )
            # add the handler to the root logger
        logging.getLogger().addHandler( self.CmdLogHandler )
        dirname = os.path.split( File )[0]
        if not os.path.isdir( dirname ):
           os.makedirs( dirname )
        LogFileHandler = logging.handlers.RotatingFileHandler( File, 'a', maxBytes = 2*1024 * 1024, backupCount = 3 )
        formatter = logging.Formatter( '%(asctime)s %(name)-25s %(levelname)-8s %(message)s', '%Y-%m-%d %H:%M:%S' )
        LogFileHandler.setFormatter( formatter )
        LogFileHandler.setLevel( self.FileLogLevel )
        self.FileLogHandler = LogFileHandler
        Log.addHandler( LogFileHandler )
        return self.getLog( Partname2 )

    def getLog( self, name ):
        '''
        name ����������־����ʾ����־��Ŀ������һ���ֵģ� ������������������豸1����־�����豸2����־
        '''
        if self.CmdLogHandler and self.FileLogHandler:
            Log = logging.getLogger( name )
            return Log
        return None

    def setCmdLogLevel( self, level ):
        '''
        level ��������Ҫ����������п���̨�������־�ǵȼ�
        '''
        self.CmdLogLevel = level
        self.CmdLogHandler.setLevel( self.CmdLogLevel )

    def setFileLogLevel( self, level ):
        '''
        level ��������Ҫ�������־�ļ��������־�ǵȼ�
        '''
        self.FileLogLevel = level
        self.FileLogHandler.setLevel( level )

    def getCmdLogLevel( self ):
        '''
         ���ص�ǰ������������п���̨�������־�ǵȼ�
        '''
        return self.CmdLogLevel

    def getFileLogLevel( self ):
        '''
        ���ص�ǰ���������־�ļ��������־�ǵȼ�
        '''
        return self.FileLogLevel

    def _getReportDir( self, reportDir, projectName ):
        from os.path import dirname
        import sys
        moduleDir = dirname( sys.modules[LogManager.__module__].__file__ )
        return moduleDir + os.sep + ".." + os.sep + ".." + os.sep + "workspace" + os.sep + "reports" + os.sep + reportDir + "_" + projectName
