import os

class EMS_APP( object ):
    def __init__(self, param):
        self.param = param
        self.txt = '/var/lib/mos/ems_app.txt'

    def open(self):
        os.system('ems_app start >> '+ self.txt)

    def close(self):
        os.system('ems_app stop >>'+ self.txt)
        
