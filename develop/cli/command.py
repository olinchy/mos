#!/usr/bin/env python
# -*- coding: utf-8 -*-

import os.path
import sys
import traceback

import yaml
from cmd import Cmd
from cmd2 import EmbeddedConsoleExit, Statekeeper
import metaInfo
import para
from msg import MsgCreator
from utils import mysplit, joinDn, lookupItem
from serializer import serialize
from cmdContext import CommandContext


class _CommandFactory(object):
    def __init__(self):
        self.models = {}
    
    def loadModels(self, path):
        for item in os.listdir(path):
            filePath = os.path.join(path, item)
            if os.path.isfile(filePath) and item.split('.')[1] == 'yml':
                model = yaml.load(file(filePath, 'r'))
                self.models[model['name']] = model
    
    def create(self, name, context = CommandContext()):
        model = self.models.get(name, None)
        if not model:
            print "\nNot found model by command name %s" % name
            return None
        try:
            paras = self.__createParams(model.get('params', []), context)
            return Command(self.models[name], paras, context)
        except para.ParamException, e:
            e.dump()
            return None
    
    def __createParams(self, config, context):
        paras = {}
        paraList = []
        for model in config:
            parameter = para.create(model, paras, context)
            if not parameter:
                if context.runNow:
                    raise para.ParamException('create parameter %s fail!' % model['name'])
                else:
                    return paraList
            paraList.append(parameter)
            paras[model['name']] = parameter
#            print 'create para %s success: %s, complete %s' % (model['name'], parameter.tomsg(), parameter.isComplete())
        return paraList
        
    
factory = _CommandFactory()
                                    
class Command(Cmd):
    debug = False
    locals_in_py = True
    def __init__(self, model, paras, context):
        Cmd.__init__(self)
        self.model = model
        self.name = model['name']
        self.confPrompt = model.get('prompt', '$')
        self.commands = model.get('subCmd', [])        
        self.msgType = model.get('msgType', None)
        self.exitWhenEmptyLine = model.get('exitWhenEmptyLine', True)
        self.exitAfterSubCmd = model.get('exitAfterSubCmd', [])
        
        self.pystate = {}
        self.state = 'init'
        
        self.__updatePrompt(context.cookie)
        
        self.subCmd = None

        self.transId = context.transId
        if model.get('inTrans', False):
            self.transId.setPresent()
        
        self.realParams = paras
        if self.model.get('params', []):
            self.state = 'paras'
        if context.args:
            self.cmdqueue.append(' '.join(context.args))
            
        if self.commands:
            self.state = 'sub'
        self.hasSubMode = self.__shouldEnterSub()
        
        if not self.hasSubMode and context.runNow:
            self.__over()  
            self.state = 'over'
     
    def __shouldEnterSub(self):
        if self.commands:
            return True
        return lookupItem(self.realParams, lambda para: para.hasSubMode()) != None
            
    def tomsg(self, paraName):
        para = lookupItem(self.realParams, lambda item: item.getName() == paraName)
        return para.tomsg()
    
    def onecmd_plus_hooks(self, line):
        lines = self.precmd(line)
        if lines[1:]:
            self.cmdqueue.extend(lines[1:])
        stop = self.onecmd(lines[0])
        stop = self.postcmd(stop, lines[0])
        return stop
             
    def doCmd(self, line):
        name = 'do_' + self.state
        try:
            func = getattr(self, name)
        except AttributeError:
            print 'not fount do function by name %s' % name
            return self.default(line)
        return func(line.rstrip(';'), line.endswith(';'))
    
    def do_sub(self, line, terminal = False):
        args = mysplit(line)
        cmd = args[0]
        if cmd not in self.commands:
            print '%s is not in sub commands %s' % (cmd, self.commands)
            return None
        try:
            func = getattr(self, 'do_' + cmd)
            return func(' '.join(args[1:]))
        except AttributeError:
            args = args[1:]
            lastWord = args[-1] if args else ''
            context = CommandContext(self.cookie, self.transId, ' '.join(args), lastWord, runNow = True)
            self.subCmd = factory.create(cmd, context)
            if self.subCmd:
                return self.doSubLoop(line, terminal)
            else:
                print "create sub command %s fail" % cmd
                return None
    
    def doSubLoop(self, line, terminal):
        if self.subCmd.state != 'over':
            if terminal:
                self.subCmd.cmdqueue.append('')
            self.subCmd.cmdqueue.extend(self.cmdqueue)
            self.cmdqueue = []
            self.subCmd.cmdloop()
            self.cmdqueue.extend(self.subCmd.cmdqueue)
        return self.postSub(line)
            
    def postSub(self, line):
        self.__updatePrompt(self.subCmd.cookie)
        return True if line in self.exitAfterSubCmd else None
    
    def do_paras(self, line, terminal = False):
        self.realParams[-1].parse(line)
        if terminal:
            self.cmdqueue.append('')
        return None
    
    def do_py(self, line):  
        '''
        py <command>: Executes a Python command.
        py: Enters interactive Python mode.
        End with ``Ctrl-D`` (Unix) / ``Ctrl-Z`` (Windows), ``quit()``, '`exit()``.
        Non-python commands can be issued with ``cmd("your command")``.
        Run python code from external files with ``run("filename.py")``
        '''
        self.pystate['self'] = self
        line = self.rebuildline(line)
        arg = line.strip()
        localvars = (self.locals_in_py and self.pystate) or {}
        from code import InteractiveConsole, InteractiveInterpreter
        interp = InteractiveConsole(locals=localvars)
        interp.runcode('import sys, os;sys.path.insert(0, os.getcwd())')
        if arg.strip():
            interp.runcode(arg)
        else:
            def quit():
                raise EmbeddedConsoleExit
            def onecmd_plus_hooks(arg):
                return self.onecmd_plus_hooks(arg + '\n')
            def run(arg):
                try:
                    file = open(arg)
                    interp.runcode(file.read())
                    file.close()
                except IOError as e:
                    self.perror(e)
            self.pystate['quit'] = quit
            self.pystate['exit'] = quit
            self.pystate['cmd'] = onecmd_plus_hooks
            self.pystate['run'] = run
            try:
                cprt = 'Type "help", "copyright", "credits" or "license" for more information.'        
                keepstate = Statekeeper(sys, ('stdin','stdout'))
                sys.stdout = self.stdout
                sys.stdin = self.stdin
                interp.interact(banner= "Python %s on %s\n%s\n(%s)\n%s" %
                       (sys.version, sys.platform, cprt, self.__class__.__name__, self.do_py.__doc__))
            except EmbeddedConsoleExit:
                pass
            keepstate.restore()
                
    def do_cd(self, line):
        if not line:
            newCookie = '/'
        else:
            newCookie = joinDn(self.cookie, line.split()[0])
        if metaInfo.isDnExist(newCookie, self.transId):
            self.__updatePrompt(newCookie)
        else:
            print "cd %s: No such dn" % line
        return None
            
    def do_tree(self, line):
        if not line:
            dn = self.cookie
        else:
            dn = joinDn(self.cookie, line.split()[0])
        tree = metaInfo.makeTree(self.transId, dn)
        print serialize(tree[dn], 'yaml')
        return None
    
    def default(self, line):
        return True
    
    def emptyline(self):
        if self.state == 'paras' and not self.realParams[-1].isComplete():
            #            print 'emptyline: ', self.realParams[-1].getName()
            return self.realParams[-1].emptyline()
        return self.exitWhenEmptyLine
    
    def postloop(self):
        return self.__over()
    
    def precmd(self, line):
        return self.parseline(line)
    
    def parseline(self, line):
        line = line.strip()
        lines = filter(lambda x: x !='', line.split(';'))
        if not lines:
            return ['']
        newLines = map(lambda x: x + ';', lines)
        if not line.endswith(';'):
            newLines[-1] = newLines[-1].rstrip(';')
        return newLines
    
    def rebuildline(self, head):
        line = head
        if self.cmdqueue:
            tail = ''.join(self.cmdqueue)
            line = ';'.join([head, tail])
            self.cmdqueue = []
        return line
    
    def inTrans(self):
        return self.transId.inTrans()
    
    def setTransId(self, transId):
        self.transId.setId(transId)
    
    def tab(self, text, line, begidx, endidx):
        line = line.split(';')[-1].lstrip()
        name = 'tab_' + self.state
        try:
            func = getattr(self, name)
        except AttributeError:
            print 'not fount tab function by name %s' % name
            return []
        completions = func(text, line, begidx, endidx)
        return completions
    
    def tab_sub(self, text, line, begidx, endidx):
        completions = []
        if line and line != text:
            args = mysplit(line)
            self.subCmd, line = self.__parseSubcmd(args[0], ' '.join(args[1:]), text)
            if self.subCmd != None:
                return self.subCmd.tab(text, line, begidx, endidx)
        commands = [cmd for cmd in self.commands]
        if not text:
            completions = commands
        else:
            completions = [f for f in commands if f.startswith(text)]
        return completions
    
    def tab_paras(self, text, line, begidx, endidx):     
#        print 'tab para ', self.realParams[-1].getName()
        return self.realParams[-1].tab(text, line, begidx, endidx)
    
    def __over(self):
        if not self.msgType:
            return
        msg = MsgCreator.create(self)
        msg.send()
        
    def __updatePrompt(self, cookie):
        self.cookie = cookie
        self.prompt = self.confPrompt if cookie == '/' else cookie + self.confPrompt
    
    def __parseSubcmd(self, cmd, line, lastWord):
        if self.commands and cmd and cmd in self.commands:
            context = CommandContext(self.cookie, self.transId, line, lastWord, runNow = False)
            subCmd = factory.create(cmd, context)
            line = ' '.join(context.args)
            return subCmd, line
        else:
            return None, line

    def perror(self, errmsg, statement=None):
        if self.debug:
            traceback.print_exc()
        print (str(errmsg))
                       
