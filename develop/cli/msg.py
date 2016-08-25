#!/usr/bin/env python
# -*- coding: utf-8 -*-
import json

from server import server
from serializer import serialize


class MsgCreator(object):
    @staticmethod
    def create(command):
        msgPool = {'add': _AddMsg, 'get': _GetMsg, 'getConfig': _GetMsg, 'set': _SetMsg, 'del': _DelMsg, 'ls': _LsMsg, \
                   'commit': _EndtransMsg, 'rollback': _EndtransMsg, 'loadlink': _LoadlinkMsg, \
                   'action': _ActionMsg, 'find' : _FindMsg, 'findConfig' : _FindMsg }
        return msgPool[command.name](command) if msgPool.has_key(command.name) else None

        
class CommandMsg(object):
    def __init__(self, command = None):
        self.command = command
        self.sessionId = server.sessionId
        
    def send(self):
        pass
               
    def request(self, methodName, dump = True, needAck = True, **kwargs):
        try:
            method = server.getMethod(methodName)
#            print 'send %s: %s' % (methodName, kwargs)
            response = method(sessionId = self.sessionId, **kwargs)
            if dump and response:
                rspCopy = {}
                rspCopy.update(response)
                
                #rspCopy.pop('transId', None)
                print serialize(rspCopy, 'json')
            if needAck:
                self.ack(response)
            return response
        except:
            import traceback
            traceback.print_exc()
            print jsonrpclib.history.request   
            print jsonrpclib.history.response 
                                    
    def ack(self, response):
        if self.command.inTrans():
            if response.has_key('transId'):
                self.command.setTransId(response['transId'])
        elif response["result"] == 0:
            self.notify('commit', transId = response['transId'])
        elif response.has_key('transId'):
            self.notify('rollback', transId = response['transId'])
                
    def notify(self, ntfName, **kwargs):
        try:
#            print 'send %s: %s' % (ntfName, kwargs)
            method = server.getNotify(ntfName)
            method(sessionId = self.sessionId, **kwargs)
        except:
            import traceback
            traceback.print_exc()
            print jsonrpclib.history.request
                
        
class _AddMsg(CommandMsg):
    def __init__(self, command):
        CommandMsg.__init__(self, command)
        
    def send(self):
        self.request(self.command.name, moList = [{'dn' : self.command.tomsg('dn'), 'mo' : self.command.tomsg('mo'), 'moClass' : self.command.tomsg('moClass')}], transId = self.command.transId.tomsg())

class _SetMsg(CommandMsg):
    def __init__(self, command):
        CommandMsg.__init__(self, command)
        
    def send(self):
#        self.request(self.command.name, moList = [{'dn' : self.command.dn, 'mo' : self.command.mo.tomsg()}], transId = self.command.transId.tomsg())
        self.request(self.command.name, moList = [{'dn' : self.command.tomsg('dn'), 'mo' : self.command.tomsg('mo')}], transId = self.command.transId.tomsg())

class _DelMsg(CommandMsg):
    def __init__(self, command):
        CommandMsg.__init__(self, command)
        
    def send(self):
        self.request(self.command.name, dnList = [self.command.tomsg('dn')], transId = self.command.transId.tomsg())

class _GetMsg(CommandMsg):
    def __init__(self, command):
        CommandMsg.__init__(self, command)
        
    def send(self):
        self.request(self.command.name, needAck = False, dn = self.command.tomsg('dn'), transId = self.command.transId.tomsg())
            
class _LsMsg(CommandMsg):
    def __init__(self, command):
        CommandMsg.__init__(self, command)
        
    def send(self):
        self.request(self.command.name, needAck = False, dn = self.command.tomsg('dn'), transId = self.command.transId.tomsg())           

class _EndtransMsg(CommandMsg):
    def __init__(self, command):
        CommandMsg.__init__(self, command)
        
    def send(self):
        if self.command.transId.isPresent():
            self.notify(self.command.name, transId = self.command.transId.getId())
            self.command.transId.reset()
                        
class _LoadlinkMsg(CommandMsg):
    def __init__(self, command):
        CommandMsg.__init__(self, command)
        
    def send(self):
        self.request(self.command.name, needAck = False, name = self.command.getInput('name'))

class _ActionMsg(CommandMsg):
    def __init__(self, command):
        CommandMsg.__init__(self, command)
        
    def send(self):
        self.request(self.command.name, needAck = False, dn = self.command.tomsg('dn'), action = self.command.tomsg('actionClass'), mo = self.command.tomsg('mo'))

class _FindMsg(CommandMsg):
    def __init__(self, command):
        CommandMsg.__init__(self, command)
        
    def send(self):
        self.request(self.command.name, needAck = False, dn = self.command.tomsg('dn'), criteria = self.command.tomsg('criteria'), transId = self.command.transId.tomsg())

class GetMetaMsg(CommandMsg):
    def __init__(self, dn, name, transId):
        CommandMsg.__init__(self)
        self.dn = dn
        self.name = name
        self.isDnValid = False if name else True
        self.transId = transId
        
    def send(self):
        result = self.request('get_meta', dump = False, needAck = False, isDnValid = self.isDnValid, dn = self.dn.tomsg(), name = self.name, transId = self.transId.tomsg())
        return json.loads(result['meta']) if result['result'] == 0 else None

class LsExMsg(CommandMsg):
    def __init__(self, dn, transId):
        CommandMsg.__init__(self)
        self.dn = dn
        self.transId = transId
        
    def send(self):
        return self.request('ls', dump = False, needAck = False, dn = self.dn, transId = self.transId.tomsg())           

        
    
