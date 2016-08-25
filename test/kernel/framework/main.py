# coding=gbk
"""
Created on 2011-8-16

@author: hanbt
"""
import sys
import os
import os.path


class FrameworkError(Exception):
    def __init__(self, value):
        self.value = value

    def __str__(self):
        return "FrameworkError: " + str(self.value)


def run():
    sys.path.append(".")
    from runner import TestRunner

    testRunner = TestRunner()
    testRunner.run(sys.argv)


def getKernelDir():
    return os.path.abspath(".")


def getDriversDir():
    return getKernelDir() + os.sep + "drivers"


def getWorkspaceDir():
    return os.path.abspath(getKernelDir() + os.sep + ".." + os.sep + "workspace")


def getReportsDir():
    return os.path.abspath(getWorkspaceDir() + os.sep + "reports")


def getTestCasesDir():
    return os.path.abspath(getWorkspaceDir() + os.sep + "testcases")


def getConfigurationDir():
    return os.path.abspath(getWorkspaceDir() + os.sep + "configuration")


if __name__ == '__main__':
    run()
