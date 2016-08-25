#coding=gbk
'''
Created on 2011-8-19

@author: 李哲
'''
import os
import logging
import logging.handlers
import time
import types

class LogManager( object ):
    '''
    本类用于日志管理
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
        projName 是指标识工程名称的字符串
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
         
        Filename 是指要保存的日志的名称，是去掉后缀的文件名，比如要保存为test1.log，则为test1
        通常现在是每个测试用例一个日志文件，则FileName可以为这个测试用例类的类名
        LogItemName 是用于在日志中显示该日志属于那一部分的，假设某个测试用例类的日志，里面会包含多个测试函数，每个测试函数又包含多个测试数据，
        就可以采用LogItemName来标识区分。 如果不设置这块，则默认和FileName一致
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
        name 是用于在日志中显示该日志条目属于那一部分的， 比如可以用来区分是设备1的日志还是设备2的日志
        '''
        if self.CmdLogHandler and self.FileLogHandler:
            Log = logging.getLogger( name )
            return Log
        return None

    def setCmdLogLevel( self, level ):
        '''
        level 是用设置要输出到命令行控制台的最低日志登等级
        '''
        self.CmdLogLevel = level
        self.CmdLogHandler.setLevel( self.CmdLogLevel )

    def setFileLogLevel( self, level ):
        '''
        level 是用设置要输出到日志文件的最低日志登等级
        '''
        self.FileLogLevel = level
        self.FileLogHandler.setLevel( level )

    def getCmdLogLevel( self ):
        '''
         返回当前的输出到命令行控制台的最低日志登等级
        '''
        return self.CmdLogLevel

    def getFileLogLevel( self ):
        '''
        返回当前的输出到日志文件的最低日志登等级
        '''
        return self.FileLogLevel

    def _getReportDir( self, reportDir, projectName ):
        from os.path import dirname
        import sys
        moduleDir = dirname( sys.modules[LogManager.__module__].__file__ )
        return moduleDir + os.sep + ".." + os.sep + ".." + os.sep + "workspace" + os.sep + "reports" + os.sep + reportDir + "_" + projectName
