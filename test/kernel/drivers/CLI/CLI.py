import json

import pexpect

from kernel.framework.case import *


class CLI(object):
    def __init__(self, name, dnPrefix, address):
        self._spawn(name, address)
        self.dnPrefix = dnPrefix
        self.name = name

    def _spawn(self, name, address):
        self.logfile = "./log/" + name + "_log.txt"
        self.child = pexpect.spawn("../tools/bin/cli " + str(address), logfile=open(self.logfile, "a+"))
        self.child.delaybeforesend = 0

    def proc(self, command):
        self.expect("\$")
        extra = self.processCommand(command)
        self.child.sendline(extra)

        return self

    def emsDn(self, command, nPos):
        return command[0:nPos] + self.dnPrefix + command[nPos:]

    def emsCommand(self, command):
        nPos = command.find('/')
        if nPos < 0:
            return command
        else:
            return self.emsDn(command, nPos)

    def processCommand(self, command):
        return self.emsCommand(command)

    def expect(self, expectation):
        self.child.expect(expectation, timeout=20)
        return self

    def assert_result(self, expectedDict):
        self.expect("{.*\$")
        self.child.sendline("")
        try:
            if self.child.after.endswith('<trans>$'):
                after = self.child.after.rstrip('<trans>$')
                result = json.loads(after)
            else:
                after = self.child.after.rstrip('$')
                result = json.loads(after)
        except:
            print "before: ", self.child.before
            print "expect: ", expectedDict
            print "after:  ", self.child.after
            raise

        self.compare(expectedDict, result)
        return self

    def compare(self, expectedDict, result):
        for key in expectedDict:
            value = self._find(result, key)
            assertEquals(expectedDict[key], value, key)

    def compare_result(self, expectedDict):
        self.expect("{.*\$")
        self.child.sendline("")
        try:
            if self.child.after.endswith('<trans>$'):
                after = self.child.after.rstrip('<trans>$')
                result = json.loads(after)
            else:
                after = self.child.after.rstrip('$')
                result = json.loads(after)
        except:
            print "before: ", self.child.before
            print "expect: ", expectedDict
            print "after:  ", after
            raise
        return self.compareWithoutAssert(expectedDict, result)

    def compareWithoutAssert(self, expectedDict, result):

        for key in expectedDict:
            value = self._find(result, key)
            if value is None:
                return False
            if expectedDict[key] != value:
                return False
        return True

    def close(self):
        self.child.sendline('exit')
        self.child.sendline('exit')
        self.child.sendline('exit')
        self.child.expect(pexpect.EOF)
        self.child.close()
        self.child = None

    def _find(self, result, key):
        import types

        if key in result:
            return result[key]
        for resKey in result:
            value = result.get(resKey, None)
            if value is not None:
                if types.DictType == type(value):
                    dict_value = self._find(value, key)
                    if dict_value is not None:
                        return dict_value
                if types.ListType == type(value):
                    for obj in value:
                        obj_value = self._find(obj, key)
                        if obj is not None:
                            return obj_value
        return None
