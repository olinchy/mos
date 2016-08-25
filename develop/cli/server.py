#!/usr/bin/env python
# -*- coding: utf-8 -*-
import jsonrpclib
    
class MyServer(object): 
    def __init__(self):
        self.server = None
        
    def start(self, url = 'http://localhost:8080'):     
        self.server = jsonrpclib.Server(url)
        retry = 0
        while retry < 10 :
            try:
                ret = self.server.login(username="", passwd="", role="CLI", server={"present": False})
                if ret["result"] == 0:
                    self.sessionId = ret["sessionId"]
                    return True
                else:
                    raise Exception("login failed")
            except :
                retry = retry + 1
                continue
        return False
        
    def stop(self):
        self.server._notify.logout(sessionId = self.sessionId) 
        
    def ping(self):
        return  self.server.ping(sessionId = self.sessionId) 
        
    def getMethod(self, name):
        return self.server.__getattr__(name)
    
    def getNotify(self, name):
        return self.server._notify.__getattr__(name)
         
server = MyServer()
