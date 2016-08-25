import os

class EMS( object ):
    def __init__(self, param):
        self.param = param
    def open(self):
        self.ems = _EMS(self.param)
        self.ems.open()
    def close(self):
        self.ems.close()

class _EMS( object ):
    def __init__(self, param):
        self.param = param
        self.txt = '/var/lib/mos/ems.txt'

    def open(self):
        os.system('rm -rf $MOS_HOME/build/mos-ne/%s/store' % self.param["version"])
        os.system('cp -fr $MOS_HOME/testnew/prepares/mos-ne/store $MOS_HOME/build/mos-ne/%(version)s/' % {'path' : self.param["version"], 'version' : self.param["version"]})
        os.system('ems_start %s' % self.param["version"] + ' > '+ self.txt)

    def close(self):
        os.system('ems_stop %s'% self.param["version"] + ' >> '+ self.txt)
