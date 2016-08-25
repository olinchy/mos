#coding=gbk
import os
from xml.dom import minidom
from xml.parsers.expat import ExpatError
from main import FrameworkError

class DataSet(dict):
    def __init__(self, name):
        dict.__init__(self)
        self.name = name
        
    def __repr__(self):
        return '%s: %s' % (self.name, dict.__repr__(self))

class TestData(object):
    def __init__(self, filename):
        if filename is not None:
            #ztq 首先判断文件是否存在，如果数据文件不存在那么，创建空数据集
            if os.path.exists(filename):
                xmldoc = self._openDataFile(filename)
                self._readDataFile(xmldoc)
            else:
                self._createNullDataSet()
                print "dataFile %s not exist" %filename
        else:
            self._createNullDataSet()
        
    def __repr__(self):
        return self.dataSets.__repr__()
    
    def _openDataFile(self, filename):
        try:
            return minidom.parse(filename)
        except IOError:
            raise FrameworkError("Can't find data file (" + filename + ")")
        except ExpatError as (ex):
            raise FrameworkError("Test data file (" + filename + ") format error: %s" % ex)

    def _readDataFile(self, xmldoc):
        self.dataSets = []
        for dataSetNode in xmldoc.getElementsByTagName('dataset'):
            dataSet = DataSet(dataSetNode.attributes['name'].value)
            for dataNode in dataSetNode.getElementsByTagName('data'):
                dataSet[dataNode.attributes['name'].value] = dataNode.attributes['value'].value
            self.dataSets.append(dataSet) 
    
    def _createNullDataSet(self):
        self.dataSets = [DataSet("NoData")]   
