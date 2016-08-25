#!/usr/bin/env python
# -*- coding: utf-8 -*-

from attribute import AttrCreator, Object
import utils
import metaInfo

def create(model, existingParas, context):
    paraRepos = {'Dn' : _Dn, 'MoClass' : _MoClass, 'Mo' : _Mo, 'ActionClass' : _ActionClass, 'Action' : _Action, 
                 'String' : _String, 'Sentence' : _Sentence}
    return paraRepos[model['type']].create(model, existingParas, context)  
 

class ParamException(Exception):
    def __init__(self, error = ''):
        self.__excepInfo = error
        
    def dump(self):
        print '\n'
        print self.__excepInfo
        
class _Param(object): # Parameter Base class
    def __init__(self, model, complete = True):
        self.model = model
        self.complete = complete
        
    def tab(self, text, line, begidx, endidx):#should implement in subclass
        return []
    
    def tomsg(self):#should implement in subclass
        return ''
    
    def getName(self):
        return self.model['name']
    
    def isComplete(self):
        return self.complete
    
    def hasSubMode(self):#should implement in subclass
        return False
    
    def isOptional(self):
        self.model.get('isOptional', True)
    

class _Dn(_Param):
    def __init__(self, model, cookie, arg, transId, complete):
        super(_Dn, self).__init__(model, complete)
        self.cookie = cookie
        self.dn = utils.joinDn(cookie, arg)
        self.transId = transId
    
    @staticmethod
    def create(model, existingParas, context):
        args = context.args
        partDn = args[0] if args else ''
        if context.runNow:
            complete = True
        elif args[1:] or (args and args[0] != context.lastWord):
            complete = True
        else:
            complete = False
        context.args = args[1:]
        return _Dn(model, context.cookie, partDn, context.transId, complete)
    
    def tomsg(self):
        return self.dn
    
    def tab(self, text, line, begidx, endidx):
        if self.isComplete():
            return []
        dn = utils.joinDn(self.cookie, text)
        if dn.endswith('/'):
            children = metaInfo.getChildren(dn, self.transId)
            completions = [text + f for f in children]
        else:
            children = metaInfo.getChildren(dn, self.transId)
            if children:
                if not text or text.endswith('/') :
                    completions = [text + f for f in children]
                else:
                    completions = [text + '/' + f for f in children]
            else:
                (head, mid, tail) = text.rpartition('/') 
                dn = utils.joinDn(self.cookie, head)
                children = metaInfo.getChildren(dn, self.transId)
                completions = [head + mid + f for f in children if f.startswith(tail)]
                
        return completions    
    
class _MoClass(_Param):
    def __init__(self, model, dn, clsName, complete):
        super(_MoClass, self).__init__(model, complete)
        self.dn = dn
        self.clsName = clsName
        
    @staticmethod
    def create(model, existingParas, context):
        dn = existingParas['dn']
        if not dn.isComplete():
            return None
        transId = context.transId
        meta = metaInfo.getMeta(dn, '', transId)
        if not meta:
            raise ParamException('Get mo meta fail by dn %s' % dn.tomsg())
        cls = meta['className']
        complete = True
        if meta.get('isAbstract', False):
            args = context.args
            meta = metaInfo.getMeta(dn, args[0], transId) if args else None
            (cls, complete) = _getClsName(meta, dn, context)
        return _MoClass(model, dn, cls, complete)
   
    def tomsg(self):
        return self.clsName
    
def _getClsName(meta, dn, context):#return (cls, complete)
    args = context.args
    if meta:
        context.args = args[1:]
        return (args[0], True)
    else:
        if context.runNow:
            raise ParamException('Get meta fail by dn %s, please input specific class name! ' % dn.tomsg())
        else:
            if args:
                if len(args) > 1 or not context.lastWord:
                    raise ParamException('Get mo specific meta fail by dn %s, please input correct class name!' % dn.tomsg())
                else:
                    context.args = args[1:]
                    return (context.lastWord, False)
            else:
                return ('', False)


class _Mo(_Param, Object):
    def __init__(self, model, dn, meta):
        _Param.__init__(self, model, False)
        Object.__init__(self, meta)
        self.dn = dn
        self.meta = meta
   
    @staticmethod
    def create(model, existingParas, context):
        dn = existingParas['dn']
        if not dn.isComplete():
            return None
        transId = context.transId
        if existingParas.has_key('moClass'):
            cls = existingParas['moClass'].tomsg()
        else:
            cls = ''
        meta = metaInfo.getMeta(dn, cls, transId)
        if not meta:
            raise ParamException('Get mo meta fail by dn %s and class %s' % (dn.tomsg(), cls))
        return _Mo(model, dn, meta)
              
    def parse(self, line):
        return Object.parseWords(self, line) 

    def tab(self, text, line, begidx, endidx):
        return Object.tabSelf(self, text, line) 
    
    def tomsg(self):
        return self.data
    
    def hasSubMode(self):
        return True

    def isComplete(self):
        return self.curAttr == None

class _ActionClass(_MoClass):
    def __init__(self, model, dn, clsName, complete):
        super(_ActionClass, self).__init__(model, dn, clsName, complete)
        
    @staticmethod
    def create(model, existingParas, context):
        dn = existingParas['dn']
        if not dn.isComplete():
            return None
        args = context.args
        meta = metaInfo.getActionMeta(dn, args[0]) if args else None
        (cls, complete) = _getClsName(meta, dn, context)
        return _ActionClass(model, dn, cls, complete)
    
    def tomsg(self):
        return self.clsName
    
    def tab(self, text, line, begidx, endidx):
        actions = metaInfo.getActionNames(self.dn) 
        return [x for x in actions if x.startswith(self.clsName)]   
    
class _Action(_Mo):
    def __init__(self, model, dn, meta):
        super(_Action, self).__init__(model, dn, meta)
    
    @staticmethod
    def create(model, existingParas, context):
        cls = existingParas['actionClass']
        if not cls.isComplete() or not cls.isComplete():
            return None
        dn = existingParas['dn']
        meta = metaInfo.getActionMeta(dn, cls.tomsg())
        if meta:
            return _Action(model, existingParas['dn'], meta)
        raise ParamException('Get action meta fail by dn %s and action class %' % (dn.tomsg(), cls.tomsg()))
        
class _String(_Param):
    def __init__(self, model, string):
        super(_String, self).__init__(model)
        self.string = string
    
    @staticmethod
    def create(model, existingParas, context):
        string = context.args[0] if context.args else ''
        context.args = context.args[1:]
        return _String(model, string)
    
    def tomsg(self):
        return self.string
    
    
class _Sentence(_Param):
    def __init__(self, model, sentence):
        super(_Sentence, self).__init__(model)
        self.sentence = sentence
    
    @staticmethod
    def create(model, existingParas, context):
        args = context.args
        context.args = []
        return _Sentence(model, ' '.join(args))
    
    def tomsg(self):
        return self.sentence
    
