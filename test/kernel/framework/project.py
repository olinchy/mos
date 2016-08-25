import sys
import time
import os
import os.path
from xml.dom import minidom
from xml.parsers.expat import ExpatError
from main import *


class TestModuleDesc(object):
    def __init__(self):
        self.moduleFile = ''
        self.dataFile = ''

    def __repr__(self):
        return '{moduleFile:"%s", dataFile:"%s"}' % (self.moduleFile, self.dataFile)


class TestProject(object):
    def __init__(self, filename):
        self.name = os.path.basename(filename).split(".")[0]
        xmldoc = self._openProjectFile(filename)
        self._readProjectFile(xmldoc)
        self._getProjectReportsRootDir()

    def _getModule(self, module):
        desc = TestModuleDesc()
        desc.moduleFile = self.moduleRoot + os.sep + module.attributes['filePath'].value
        try:
            desc.dataFile = self.moduleRoot + os.sep + module.attributes['dataFilePath'].value
        except KeyError:
            desc.dataFile = None
        return desc

    def __repr__(self):
        format = '{name:"%s", moduleRoot:"%s", moduleNumber:%d, modules:%s", failurePolicy:"%s"}'
        return format % (self.name,
                         self.moduleRoot, self.moduleNumber, self.modules.__repr__(), self.failurePolicy)

    def _openProjectFile(self, filename):
        try:
            return minidom.parse(filename)
        except IOError:
            raise FrameworkError("Can't find project file (" + filename + ")")
        except ExpatError as (ex):
            raise FrameworkError("Project file (" + filename + ") format error: %s" % ex)

    def _readProjectFile(self, xmldoc):
        projectNode = xmldoc.getElementsByTagName('project')[0]

        modulesNode = projectNode.getElementsByTagName('modules')[0]

        try:
            self.moduleRoot = modulesNode.attributes['root'].value
        except KeyError:
            self.moduleRoot = getTestCasesDir()

        modules = modulesNode.getElementsByTagName('module')
        self.moduleNumber = modules.length
        self.modules = []

        for module in modules:
            self.modules.append(self._getModule(module))

        self._readConfig(projectNode)
        self._readMailSetting(projectNode)

    def _readConfig(self, projectNode):
        configNode = projectNode.getElementsByTagName('config')[0]

        failureconfignode = configNode.getElementsByTagName('failureconfig')[0]
        self.failurePolicy = failureconfignode.attributes['failurePolicy'].value

        logConfigNode = configNode.getElementsByTagName('logconfig')[0]
        self.stdLogLevel = logConfigNode.attributes['stdLevel'].value
        self.fileLogLevel = logConfigNode.attributes['fileLevel'].value

        self.devices = self._readDevices(projectNode.getElementsByTagName("devices")[0])
        self.clis = self._readClis(projectNode.getElementsByTagName("clis")[0])
        if len(configNode.getElementsByTagName("db")) > 0:
            self.db = self._readDB(configNode.getElementsByTagName("db")[0])
        else:
            self.db = None
        pressureNode = configNode.getElementsByTagName('pressureconfig')[0]
        self.isPressure = pressureNode.attributes['ispressure'].value
        self.repetition = pressureNode.attributes['repetition'].value

    def _readMailSetting(self, projectNode):
        mailNodes = projectNode.getElementsByTagName("mail")
        if (len(mailNodes) > 1):
            raise FrameworkError("Project file is not allowed to contain multiply 'mail' nodes.")

        self.needSendMail = False

        if (len(mailNodes) == 1):
            self.needSendMail = True
            mailNode = mailNodes[0]
            self.mailServer = mailNode.attributes["server"].value
            self.mailSender = mailNode.attributes["sender"].value
            self._readMailReceiverList(mailNode)

    def _readMailReceiverList(self, mailNode):
        receiverNodes = mailNode.getElementsByTagName("receiver")
        self.mailReceivers = []
        for receiverNode in receiverNodes:
            self.mailReceivers.append(receiverNode.attributes["address"].value)

    def _getProjectReportsRootDir(self):
        startTime = time.strftime("%Y%m%d%H%M%S", time.localtime())
        projectDir = os.sep + startTime + "_" + self.name
        self.reportsRootDir = getReportsDir() + projectDir

    def _readDevices(self, devicesNode):
        return map(_readDevice, devicesNode.getElementsByTagName("device"))

    def _readClis(self, clisNode):
        return map(_readCli, clisNode.getElementsByTagName("cli"))

    def _readDB(self, dbNode):
        value = {}
        _readAttribute(value, dbNode, "connection")
        _readAttribute(value, dbNode, "type")
        return value


def _readDevice(node):
    values = {}
    _readAttribute(values, node, "id")
    _readAttribute(values, node, "type")
    _readAttribute(values, node, "address")
    _readAttribute(values, node, "port")
    _readAttribute(values, node, "version")
    _readAttribute(values, node, "port")
    return values


def _readCli(node):
    values = {}
    _readAttribute(values, node, "name")
    _readAttribute(values, node, "address")
    _readAttribute(values, node, "dnPrefix")
    return values


def _readAttribute(values, node, name):
    if node.hasAttribute(name):
        values[name] = node.getAttribute(name)


if __name__ == '__main__':
    project = TestProject(sys.argv[1])
