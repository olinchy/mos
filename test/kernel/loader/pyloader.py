#coding=gbk

import sys
import os.path
from main import FrameworkError
from kernel.framework.case import TestCase 
from data import TestData 

class PyLoader(object):
    def loadTestCase(self, desc, managers, suite):
        testCaseClass = self._loadTestCaseClass(desc.moduleFile)       
        testData = self._loadTestData(desc.dataFile)  
        self._createTestCase(testCaseClass, testData, managers, suite)

    def _loadTestCaseClass(self, filename):
        module = self._loadModuleByFileName(filename)
        testCaseClass = self._loadTestCaseFromModule(module)
        return testCaseClass
        
    def _loadModuleByFileName(self, filename):
        self._addModulePathByFileName(filename)
        return self._importModuleByFileName(filename)
    
    def _addModulePathByFileName(self, filename):
        dir = os.path.dirname(filename)
        if dir not in sys.path:
            sys.path.append(dir)
            
    def _importModuleByFileName(self, filename):
        moduleName = os.path.splitext(os.path.basename(filename))[0]
        try:
            return __import__(moduleName)
        except ImportError as ex:
            raise FrameworkError("Can't load test module (%s)-> %s" % (filename, ex))
        
    def _loadTestCaseFromModule(self, module):
        import types
        testClasses = []    
        
        for testClassName in dir(module):        
            testClass = getattr(module, testClassName)          
            if isinstance(testClass, (type, types.ClassType)) and issubclass(testClass, TestCase) and (testClass is not TestCase):
                testClasses.append(testClass)
 
        if len(testClasses) != 1:
            raise FrameworkError("Test module (%s) must contain only one testcase class" % module.__name__)
        else:
            return testClasses[0]
                
    def _loadTestData(self, filename):
        return TestData(filename)
    
    def _createTestCase(self, testCaseClass, testData, managers, suite):
        methodNames = self._getTestMethod(testCaseClass)       
        for name in methodNames:
            for dataSet in testData.dataSets:                   
                suite.addTestCase(testCaseClass(name, dataSet, managers))

    def _getTestMethod(self, testCaseClass):
        def isTestMethod(methodName, testCaseClass = testCaseClass):
            return (methodName.startswith('test') or (methodName == 'runTest')) \
                     and hasattr(getattr(testCaseClass, methodName), '__call__')
        methodNames = filter(isTestMethod, dir(testCaseClass))
        return methodNames

            
