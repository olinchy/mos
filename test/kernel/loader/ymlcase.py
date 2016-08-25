import time

from kernel.drivers.DB import *
from kernel.framework.case import TestCase
from kernel.framework.data import DataSet
from kernel.drivers.CLI import *


class YmlCase(TestCase):
    def __init__(self, module, case, managers, suite):
        self.case = case
        self.managers = managers
        self.steps = []
        self.connections = {}
        self.module = module
        TestCase.__init__(self, "runTest", DataSet("NoData"), managers)
        self.name = ('%s.%s.%s') % (self.module, case["name"], DataSet("NoData").name)
        self._generate(case["steps"])

    def setUp(self):
        for step in self.case["steps"]:
            self._connect(step.get('connection'))

    def tearDown(self):
        for key in self.connections.keys():
            self._disconnect(key)

    def runTest(self):
        for step in self.steps:
            connection = self.connections[step.getConnectionName()]
            step.run(connection)

    def getName(self):
        return self.name

    def _generate(self, steps):

        for step in steps:
            self.steps.append(
                TestStep(step['command'],
                         _getTestResult(step.get('result', '')),
                         step.get('connection', 'cli1')))

    def _disconnect(self, name):
        self.connections.pop(name).close()

    def _connect(self, name):
        if name not in self.connections:
            if 'cli' in name:
                self.connections[name] = CLI(name, self.managers.cliManager.getDnPrefix(name),
                                             self.managers.cliManager.getAddress(name))
            elif 'db' in name:
                self.connections[name] = DB(self.managers.dbConnector)


class TestStep(object):
    def __init__(self, command, result, connection):
        self.command = command
        self.result = result
        self.connection = connection

    def run(self, connection):
        if self.__isGet():
            self.__runGetCommand(connection)
        else:
            self.__runCommand(connection)

    def getConnectionName(self):
        return self.connection

    def __isGet(self):
        if self.command.find("get") >= 0:
            return True
        else:
            return False

    def __runGetCommand(self, connection):
        for i in range(10):
            connection.proc(self.command)
            if self.result.verifyWithoutAssert(connection):
                return
            else:
                time.sleep(10)

        self.__runCommand(connection)

    def __runCommand(self, connection):
        connection.proc(self.command)
        self.result.verify(connection)


class DollarResult(object):
    def verify(self, cli):
        pass

    def verifyWithoutAssert(self, cli):
        pass


class StringResult(object):
    def __init__(self, expectation):
        self.expectation = expectation

    def verify(self, connection):
        connection.expect(self.expectation)

    def verifyWithoutAssert(self, connection):
        connection.expect(self.expectation)


class DictResult(object):
    def __init__(self, expectation):
        self.expectation = expectation

    def verify(self, connection):
        connection.assert_result(self.expectation)

    def verifyWithoutAssert(self, connection):
        return connection.compare_result(self.expectation)


def _getTestResult(result):
    import types

    if result == None:
        return DollarResult()
    elif type(result) == types.DictType:
        return DictResult(result)
    else:
        return StringResult(result)
