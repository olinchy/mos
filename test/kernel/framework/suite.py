from color.printcolor import printGreen
from color.printcolor import printRed


class TestSuite(object):
    def __init__(self, project):
        self.casesToRun = []
        self.casesBack = []
        self.casesRunned = []
        self.casesRunnedFail = []
        self.totalNumber = 0
        self.successNumber = 0
        self.failureNumber = 0
        self.project = project
        self.errorNumber = 0

    def addTestCase(self, testcase):
        self.casesToRun.append(testcase)

    def runOnce(self, managers):
        for testcase in self.casesToRun:
            if managers.pressureManager.isPressure != "yes":
                managers.deviceManager.open()
            self.totalNumber += 1
            try:
                testcase.run()
            finally:
                if testcase.result == "Pass":
                    self.successNumber += 1
                elif testcase.result == "Fail":
                    self.failureNumber += 1
                    self.casesRunnedFail.append(testcase.name)
                else:
                    self.errorNumber += 1

                managers.reportManager.addTestReport(self._createTestReport(testcase))
                self.casesRunned.append(testcase)

                if (testcase.result != "Pass") and (self.project.failurePolicy == "stopOnFailure"):
                    break
                if managers.pressureManager.isPressure != "yes":
                    managers.deviceManager.close()

    def runTests(self, managers):
        managers.reportManager.startTestRun(self.project.name)

        if managers.pressureManager.isPressure == "yes":
            managers.deviceManager.open()
        for i in range(int(managers.pressureManager.repetition)):
            self.runOnce(managers)
        if managers.pressureManager.isPressure == "yes":
            managers.deviceManager.close()

        printGreen('[  TOTAL   ]', '%u tests' % (self.totalNumber))
        printGreen('[  PASSED  ]', '%u tests' % (self.successNumber))
        if self.failureNumber != 0:
            printRed('[  FAILED  ]', '%u tests, listed below' % (self.failureNumber))
        for item in self.casesRunnedFail:
            printRed('[  FAILED  ] %s' % item, '')

        managers.logManager.createLog('framework')
        print "[  REPORT  ] %s" % managers.reportManager.reportFileName
        if self.project.needSendMail:
            managers.reportManager.sendReportMail("TEST REPORT (%s)" % self.project.name,
                                                  self.project.mailServer,
                                                  self.project.mailSender,
                                                  self.project.mailReceivers)

    def _createTestReport(self, testcase):
        from report import TestReport

        return TestReport(testcase.name, testcase.startTime, testcase.result, testcase.failureMessage, self.totalNumber,
                          self.successNumber, self.failureNumber, self.errorNumber)
