#coding=gbk

import sys
import os.path
from main import FrameworkError
from kernel.framework.case import TestCase 
from data import TestData 
from kernel.loader.pyloader import PyLoader
from kernel.loader.ymlloader import YmlLoader

class TestLoader(object):
    def loadTestCase(self, desc, managers, suite):     

        moduleType = os.path.splitext(os.path.basename(desc.moduleFile))[1]        
        if moduleType == '.py':
            return PyLoader().loadTestCase(desc, managers, suite)
        elif moduleType == '.yml':
            return YmlLoader().loadTestCase(desc, managers, suite)
        else:
            raise FrameworkError("Test module  type is not support") 


            
