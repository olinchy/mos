# coding=gbk
from main import FrameworkError
from color.printcolor import printGreen
from color.printcolor import printRed
import time
import traceback


class TestCase(object):
    def __init__(self, methodName, dataSet, managers):
        self.result = True
        self.failureMessage = 'None'
        self.dataSet = dataSet
        self.methodName = methodName
        self.name = ('%s.%s.%s') % (self.__class__.__module__, self.methodName, self.dataSet.name)

        self.startTime = ''
        self.managers = managers
        self.logManager = managers.logManager
        self.logger = None

    def depend(self, methodName):
        try:
            method = getattr(self, methodName)
            method()
        except AttributeError:
            raise FrameworkError("Can't find depended testcase '%s'" % methodName)

    def getName(self):
        return self.name

    def splitData(self, **kwargs):
        keysList = kwargs['keysStr'].split(',')
        GroupNameList = kwargs['GroupsName'].split(',')
        MultiDict = {}
        for index in range(len(GroupNameList)):
            Dict = {}
            for i in range(len(keysList)):
                Dict[keysList[i]] = kwargs[keysList[i]].split(',')[index]
            MultiDict[GroupNameList[index]] = Dict
        return MultiDict

    def setUp(self):
        pass

    def tearDown(self):
        pass

    def run(self):
        self.logger = self.logManager.createLog(self.getName())
        self.startTime = time.strftime("%Y-%m-%d %H:%M:%S", time.localtime())
        self._run()
        self._printResult()

    def _run(self):
        method = getattr(self, self.methodName)
        try:
            printGreen("[ RUN      ] ", self.getName())
            self.setUp()
            method()
        except AssertError, ex:
            self.result = "Fail"
            self._saveFailureMessage(ex)
            traceback.print_exc()
        except FrameworkError, ex:
            self.result = "Exception"
            self._saveFailureMessage(ex)
            traceback.print_exc()
            raise
        except KeyError, Var:
            if self.dataSet.name.find("NoData") != -1:
                Var = "data file not exist or key word error"
            self.result = "Exception"
            message = "KeyError: %s" % (str(Var))
            self._saveFailureMessage(message)
            traceback.print_exc()
        except NaError, Var:
            self.result = Var.Result
            self.failureMessage = Var.Reason
        except Exception, Var:
            self.result = "Exception"
            self._saveFailureMessage("Exception:%s" % Var)
            traceback.print_exc()
        else:
            self.result = "Pass"
        finally:
            self._invokeTearDown()

    def _printResult(self):
        if self.result == "Pass":
            printGreen("[     OK   ] ", self.getName())
        else:
            printRed("[  FAILED  ] " + self.getName(), '')
            print self.failureMessage

    def _invokeTearDown(self):
        try:
            self.tearDown()
        except AssertError, ex:
            self.result = "Fail"
            self._saveFailureMessage(ex)
        except FrameworkError, ex:
            self.result = "Exception"
            self._saveFailureMessage(ex)
        except Exception, ex:
            self.result = "Exception"
            self._saveFailureMessage(ex)

    def _saveFailureMessage(self, message):
        self.failureMessage = message


class AssertError(FrameworkError):
    def __str__(self):
        return "AssertError: " + str(self.value)


def assertEquals(first, second, msg=None):
    if not first == second:
        raise AssertError('%s' % msg + ' %r != %r' % (first, second))


def assertNotEquals(first, second, msg=None):
    if first == second:
        raise AssertError('%s' % msg + ' %r == %r' % (first, second))


def assertTrue(expr, msg=None):
    if not expr:
        raise AssertError(msg)


def assertFalse(expr, msg=None):
    if expr:
        raise AssertError(msg)


class NaError(Exception):
    '''
    有的测试用例不符合测试条件，则在测试报告里面结果显示NA
    '''

    def __init__(self, Reason):
        self.Reason = Reason
        self.Result = "NA"

    def __str__(self):
        return self.Reason


def NotTest(Msg="no test for test emvirment"):
    '''
    如果因为环境所限，测试用例无法进行测试，在测试用例使用该函数，则在报告里提示NA
    '''
    raise NaError(Msg)
