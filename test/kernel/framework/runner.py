# coding=gbk
import sys
import os

from main import FrameworkError
from main import getTestCasesDir


class Managers(object):
    def __init__(self, project, options):
        from log import LogManager
        from report import ReportManager
        from dev import DeviceManager
        from cli import CliManager
        from pressure import PressureManager
        from db import DBConnector

        self.logManager = LogManager(options.outputdir)
        self.logManager.setCmdLogLevel(project.stdLogLevel)
        self.logManager.setFileLogLevel(project.fileLogLevel)

        self.reportManager = ReportManager(options.outputdir)
        self.deviceManager = DeviceManager(project.devices)
        self.cliManager = CliManager(project.clis)
        if project.db is not None:
            self.dbConnector = DBConnector(project.db).createConnect()
        self.pressureManager = PressureManager(project.isPressure, project.repetition)


class TestRunner(object):
    def  __init__(self):
        self.managers = None

    def run(self, cmdLine):
        try:
            options = self._parseCmd(cmdLine)
            self._loadProject(options)
            self._createManagers(options)
            self._loadModules()
            self.suite.runTests(self.managers)
        except FrameworkError as error:
            self._processError(error)

    def _parseCmd(self, cmdLine):
        from optparse import OptionParser

        parser = OptionParser()
        parser.add_option("-p", "--project", dest="filename",
                          help="specify test project of FILE", metavar="FILE")
        parser.add_option("-o", "--output", dest="outputdir",
                          help="specify out put dir", metavar="FILE")
        parser.add_option("-q", "--quiet", action="store_false",
                          dest="verbose", default=True,
                          help="don't print status messages to stdout")
        ( options, args ) = parser.parse_args(cmdLine)

        if options.filename is None:
            import sys

            parser.parse_args([sys.argv[0], '-h'])  # print help and exit
        return options

    def _loadProject(self, options):
        from project import TestProject

        self.project = TestProject(options.filename)

    def _loadModules(self):
        from loader import TestLoader
        from suite import TestSuite
        #ztq,add 2012-1-9
        self.AddTestCasePath()
        loader = TestLoader()
        self.suite = TestSuite(self.project)
        for module in self.project.modules:
            loader.loadTestCase(module, self.managers, self.suite)

    def _processError(self, error):
        if self.managers and self.managers.logManager:
            self.managers.logManager.getLog("framework").error(error)
        else:
            print error

    def _createManagers(self, options):
        self.managers = Managers(self.project, options)

    def AddTestCasePath(self):
        '''
        ztq,将testcase文件夹的路径加入
        '''
        TestCasePath = getTestCasesDir()
        if TestCasePath not in sys.path:
            sys.path.append(TestCasePath)
