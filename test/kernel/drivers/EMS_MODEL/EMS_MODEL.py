import os

class EMS_MODEL(  object  ):
    def __init__(self, param):
        self.param = param
        self.txt = '/var/lib/mos/ems_model.txt'

    def open(self):
        os.system('rm $(ls $MOS_HOME/build/mos-ne/%s/store/* | grep -v NE.xml | grep -v Ne.xml)' % self.param["version"])
        os.system('ems_model mos-ne %s start >> ' % self.param["version"] + self.txt)
        
    def close(self):
        os.system('ems_model mos-ne %s stop >>' % self.param["version"] + self.txt)
        
        
