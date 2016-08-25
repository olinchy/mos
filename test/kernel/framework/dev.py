#coding=gbk

import os.path
import types

class DeviceManager( object ):
    def __init__(self, devicesDescription):
        self.devices = self._load(devicesDescription)      
        
    def open(self):
        for device in self.devices:   
            device.open()

    def close(self):
        for device in self.devices:
            device.close()
        
    def _load(self, devicesDescription):
        return map(lambda desc: self._loadDeviceClass(desc)(desc), devicesDescription)

    def _getModulePath(self, type):
        return "kernel.drivers." + type + "." + type

    def _loadDeviceClass(self, desc):
        typeString = desc["type"]
        module = __import__(self._getModulePath(typeString), fromlist=[typeString])
        driverClass = getattr(module, typeString)
        if isinstance(driverClass, type) or isinstance(driverClass, types.ClassType):
            return driverClass






