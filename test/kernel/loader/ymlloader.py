#coding=gbk

import sys
import os.path
from main import FrameworkError
from ymlcase import YmlCase 

class YmlLoader(object):     
      
    def loadTestCase(self, desc, managers, suite):      
        import yaml  
   
        content = yaml.load(open(desc.moduleFile))
        
        moduleName = os.path.splitext(os.path.basename(desc.moduleFile))[0] 
        for case in content:
            suite.addTestCase(YmlCase(moduleName, case, managers, suite))

 
                
  

            
