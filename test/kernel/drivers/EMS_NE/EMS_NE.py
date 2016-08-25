import os
from kernel.drivers.NE import NE

class EMS_NE( object ):
    def __init__(self, param):
        self.param = param
    def open(self):
        self.ne = NE(self.param)
        self.ems = _EMS(self.param)
        self.ems_model = _EMS_MODEL(self.param)
        self.ems_app = _EMS_APP(self.param)
        self.ne.open()
        self.ems.open()
        self.ems_model.open()
        self.ems_app.open()
    def close(self):
        self.ems_app.close()
        self.ems_model.close()
        self.ems.close()
        self.ne.close()

class _EMS( object ):
    def __init__(self, param):
        self.param = param
        self.txt = '/var/lib/mos/ems.txt'

    def open(self):
        os.system('ems start >> '+ self.txt)

    def close(self):
        os.system('ems stop >>'+ self.txt)
        
        
class _EMS_APP( object ):
    def __init__(self, param):
        self.param = param
        self.txt = '/var/lib/mos/ems_app.txt'

    def open(self):
        os.system('ems_app start >> '+ self.txt)

    def close(self):
        os.system('ems_app stop >>'+ self.txt)

class _EMS_MODEL(  object  ):
    def __init__(self, param):
        self.param = param
        self.txt = '/var/lib/mos/ems_model.txt'

    def open(self):
        os.system('rm -rf $(ls $MOS_HOME/build/mos-ne/%s/store/* | grep -v NE.xml | grep -v Ne.xml)' % self.param["version"])
        os.system('ems_model mos-ne %s start >> ' % self.param["version"] + self.txt)
        
    def close(self):
        os.system('ems_model mos-ne %s stop >>' % self.param["version"] + self.txt)
        
