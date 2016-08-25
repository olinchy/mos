#!/usr/bin/env python
# -*- coding: utf-8 -*-

class TransId(object):
    def __init__(self, present = False, value = None):
        self.__present = present
        self.__value = value
        
    def setId(self, value):
        self.__present = True
        self.__value = value
    
    def setPresent(self):
        self.__present = True
        
    def isPresent(self):
        return self.__present and self.__value != None
    
    def inTrans(self):
        return self.__present

    def copy(self, other):
        if self.__present and other.__value != None:
            self.__value = other.__value
            
    def getId(self):
        return self.__value
    
    def reset(self):
        self.__present = False
        self.__value = None
        
    def tomsg(self):
        if self.isPresent():            
            return {'present' : self.__present, 'value' : self.__value}
        else:
            return {'present' : False}
    