#!/usr/bin/env python
# -*- coding: utf-8 -*-
from metaInfo import getMeta
from serializer import serialize
from utils import mysplit, lookupItem, isComplete

class AttrCreator(object):   
    @staticmethod
    def create(attr, dn):
        mapTable = {'enum': EnumAttr, 'anonymousEnum': AnonymousEnumAttr, 'String': StringAttr, \
               'BYTE': DigitalAttr, 'WORD16': DigitalAttr, 'WORD32': DigitalAttr, 'WORD64': DigitalAttr, \
               'CHAR': DigitalAttr, 'SWORD16': DigitalAttr, 'SWORD32': DigitalAttr, 'SWORD64': DigitalAttr, \
               'FLOAT': DigitalAttr, 'DOUBLE': DigitalAttr, 'Array': ArrayAttr, 'List': ArrayAttr, 'Set': ArrayAttr, \
               'IPv4Addr': Ip4Attr, 'IPv6Addr': Ip6Attr, 'bool': BoolAttr, 'MacAddr': MacAttr, 'Date': DateAttr, \
               'Reference': ReferenceAttr, 'Range': RangeAttr, 'object': ObjectAttr}
        attrType = attr['type']
        if 'Enum' in attr['type']:
            attrType = 'enum'
        elif attr.has_key('enum'):
            attrType = 'anonymousEnum'
        elif attrType not in mapTable:
            attrType = 'object'
        return mapTable[attrType](attr, dn)

class Object(object):
    def __init__(self, meta):
        self.objMeta = meta
        self.attributes = self.__buildAttrsMeta(self.objMeta)
        self.attrNames = map(lambda item: item['name'], self.attributes)
        self.curAttr = None
        self.data = {}
    
    def parseWords(self, string): #return(result(bool), complete(bool), value)
        if string == 'clear':
            return self.doClear()
        
        if self.curAttr:
            return self.doParseAttr(string)
        
        complete = False
        args = mysplit(string)
        for i in range(len(args)):
            if i % 2 == 0:
                if not self.doParseName(args[i]):
                    print args[i] + ' name is error!'
                    return (False, complete, None)
            else:
                (result, complete, data) = self.curAttr.parse(args[i])
                if not result:
                    print("%s not valid!" % args[i])
                    self.curAttr = None
                    return (False, complete, None)
                self.data[self.curAttr.getName()] = data
                self.curAttr = None
        if self.curAttr and not self.curAttr.hasSubMode():
            self.curAttr = None
        return (True, complete, self.data)
    
    def parseLine(self, string):
        if not string.startswith('{') or not string.endswith('}'):
            print 'SyntaxError: invalid syntax, must be object'
            return (False, True, None)
        return self.parseWords(string[1:-1].strip())
        
    def doParseName(self, name):
        self.curAttr = self.makeAttr(name)
        return self.curAttr
    
    def doParseAttr(self, string):
        (result, complete, data) = self.curAttr.parseWords(string)
        if result:
            self.data[self.curAttr.getName()] = data
        return (result, complete, data)   
    
    def doClear(self):
        if self.curAttr:
            return self.curAttr.doClear()
        else:
            self.data = {}
            return (True, False, self.data)
        
    def makeAttr(self, name):
        attrMeta = self.__getAttrMeta(name)
        if attrMeta:
            return AttrCreator.create(attrMeta, self.dn)
        else:
            print 'Get attribute meta error, by name %s' % name
            return None 
        
    def emptyline(self):
        if not self.curAttr:
            return True
        else:
            if self.curAttr.emptyline():
                self.data[self.curAttr.getName()] = self.curAttr.data
                print '%s.%s committed' % (self.getName(), self.curAttr.getName())
                self.curAttr = None
            return False
    
    def isTabName(self, args, lastWord):
        if not args:
            return (True, '')
        count = len(args)
        if not lastWord:
            if isComplete(args[-1]):
                if count % 2 == 0:
                    return (True, None)
                else:
                    return (False, -1)
            else:
                if count % 2 == 0:
                    return (False, -2)
                else:
                    return (True, None)
        else:
            if count % 2 == 0:
                return (False, -2)
            else:
                return (True, None)
                
    def tabSelf(self, text, line):
        if self.curAttr:
            return self.curAttr.tabSelf(text, line)
        
        args = mysplit(line)
        count = len(args) 
        completions = []
        (tabName, nameIdx) = self.isTabName(args, text)
        if tabName:
            completions = [ f for f in self.attrNames if f.startswith(text)]
        else:
            attr = self.makeAttr(args[nameIdx])
            if attr:
                newLine = '' if nameIdx == -1 else args[-1]
                completions = attr.tabInContext(text, newLine)
            
#        if  not text:
#            if  count % 2 == 0: 
#                completions = [ f for f in self.attrNames]
#            else:
#                attrName = args[-1]  
#                attr = self.makeAttr(attrName)
#                if attr:
#                    completions = attr.tabInContext(text, '')
#        else:
#            if  count % 2 == 0: 
#                attrName = args[-2]  
#                attr = self.makeAttr(attrName)
#                if attr:
#                    completions = attr.tabInContext(text, args[-1])
#            else:
#                completions = [ f for f in self.attrNames if f.startswith(text)] 
        return completions
    
    def tabInContext(self, text, line):
        if not line:
            completions = ['{' + f for f in self.attrNames]
            return completions
        
        if not line.startswith('{'):
            return []
        prefix = ''
        if text == line:
            text = text[1:]
            prefix = '{'
        completions = map(lambda x: prefix + x, self.tabSelf(text, line[1:]))
        return completions
           
    def __getAttrMeta(self, name):
        return lookupItem(self.attributes, lambda x: x['name'] == name)
    
    def __buildAttrsMeta(self, meta):
        attributes = meta.get('attributes', [])
        references = meta.get('reference', [])
        for item in references:
            reference = {}
            reference['name'] = item['name']
            if item.get('isMulti', False): 
                reference['type'] = 'Array'
                reference['items'] = {}
                reference['items']['type'] = 'Reference'
                if 'maxItems' in item:
                    reference['maxItems'] = item['maxItems']
            else:
                reference['type'] = 'Reference'
            attributes.append(reference)
        return attributes
        
class Attribute(object):
    def __init__(self, meta, dn):
        self.meta = meta
        self.dn = dn
        self.range = []
        
    def getName(self):
        return self.meta['name']
        
    def getRange(self):
        pass
    
    def hasSubMode(self):
        return False
    
    def parse(self, value):
        valid = self.verify(value)
        complete = True
        data = None
        if valid:
            data = self.evalue(value)
        return (valid, complete, data)
    
    def tabSelf(self, text, line):
        if text:
            return [x for x in self.getRange() if x.startswith(text)]
        else:
            return self.getRange()
        
    def tabInContext(self, text, line):
        return self.tabSelf(text, line)
                
    def verify(self, value):
        return True
    
    def evalue(self, value):
        return value
    
    def validate(self, result, error):
        if result:
            return True
        else:
            print error
            return False
    
class ObjectAttr(Attribute, Object):
    def __init__(self, meta, dn):
        Attribute.__init__(self, meta, dn)
        objMeta = getMeta(self.dn, self.meta['type'])
        Object.__init__(self, objMeta)
        
    def parse(self, value):
        return Object.parseLine(self, value)
    
    def evalue(self, value):
        return self.parse(value)[2]
    
    def tabSelf(self, text, line):
        return Object.tabSelf(self, text, line)
    
    def tabInContext(self, text, line):
        return Object.tabInContext(self, text, line)
#        return ['<Object: %s>' % self.meta['type'], 'like {aName aValue bName bValue}', 'Please Press Enter']
    
    def getRange(self):
        return ['<Object: %s>' % self.meta['type'], 'like {aName aValue bName bValue}', 'Please Press Enter']
    
    def hasSubMode(self):
        return True
            
    
class DigitalAttr(Attribute):
    default = {'BYTE': (0, 255), 'WORD16': (0, 0xffff), 'WORD32': (0, 0xffffffff)}

    def __init__(self, meta, dn):
        Attribute.__init__(self, meta, dn)
        self.minimum = self.meta.get('minimum', '')
        self.maximum = self.meta.get('maximum', '')
        if not self.minimum and self.default.has_key(self.meta["type"]):
            self.minimum = self.default[self.meta["type"]][0]
        if not self.maximum and self.default.has_key(self.meta["type"]):
            self.maximum = self.default[self.meta["type"]][1]
        self.rangeList = self.meta.get('range', [])
        self.range = [self.meta["type"], 'minimum: ' + str(self.minimum) , 'maximum: ' + str(self.maximum)]
        if self.rangeList:
            self.range.append('range: ' + str(self.rangeList))
        
    def getRange(self):
        return self.range
    
    def verify(self, valuestr):
        try:
            value = eval(str(valuestr))
            if self.meta["type"] not in ['FLOAT', 'DOUBLE'] and not isinstance(value, (int, long)):
                print 'input %s must be %s ' % (valuestr, self.meta["type"])
                return False
        except:
            print 'input %s is not numeric' % valuestr
            return False
        if self.minimum and value < self.minimum:
            print self.meta.get("name", '') + " should be bigger then " + str(self.minimum)
            return False
        elif self.maximum and value > self.maximum:
            print self.meta.get("name", '') + " should be smaller then " + str(self.maximum)
            return False
        elif self.rangeList:
            for item in self.rangeList:
                if value >= item[0] and value <= item[-1]:
                    return True
            print self.meta.get("name", '') + " should be in range " + str(self.rangeList)
            return False
        else:
            return True
        
    def evalue(self, value):
        return eval(value)

class StringAttr(Attribute):
    def __init__(self, meta, dn):
        Attribute.__init__(self, meta, dn)
        self.maxLength = self.meta["maxLength"]
        self.range = [self.meta["type"] + ':', ' '.join(['max length is', str(self.maxLength)]) ]
        
    def getRange(self):
        return self.range
        
    def verify(self, string):
        if string.startswith('"') and string.endswith('"'):
            length = len(string) - 2
        else:
            length = len(string)
        errorMsg = "The length of " + self.meta.get("name", '') + " should be less then " + str(self.maxLength)
        return self.validate(len(string) <= self.maxLength, errorMsg)

    def evalue(self, value):
        if value.startswith('"') and value.endswith('"'):
            return value.strip('"')
        else:
            return value

class BoolAttr(Attribute):
    def __init__(self, meta, dn):
        Attribute.__init__(self, meta, dn)
        self.range = ['true', 'false']
        
    def getRange(self):
        return self.range
        
    def verify(self, string):
        errorMsg = "The value should be true or false"
        return self.validate(string in self.range, errorMsg)

    def evalue(self, value):
        return value == 'true'

class OidAttr(Attribute):   
    def __init__(self, meta, dn):
        Attribute.__init__(self, meta, dn)
        
    def getRange(self):
        return ['Oid', 'like "/***/***"']
        
    def verify(self, string):
        value = self.evalue(string)
        return value.startswith('/') or len(value) == 0

    def evalue(self, value):
        if value.startswith('"') and value.endswith('"'):
            return value.strip('"')
        else:
            return value

class ReferenceAttr(OidAttr):   
    def __init__(self, meta, dn):
        super(ReferenceAttr, self).__init__(meta, dn)

class SpecStringAttr(Attribute):
    def __init__(self, meta, dn, pattern, example):
        Attribute.__init__(self, meta, dn)
        self.range = [self.meta["type"], example]
        self.pattern = pattern
        self.example = example
        
    def getRange(self):
        return self.range
        
    def verify(self, string):
        import re
        string = self.evalue(string)
        errorMsg = self.meta.get("name", '') + ' should like  "' + self.example + '"'
        return self.validate(re.match(self.pattern, string), errorMsg)

    def evalue(self, value):
        if value.startswith('"') and value.endswith('"'):
            return value.strip('"')
        else:
            return value

class Ip4Attr(SpecStringAttr):
    def __init__(self, meta, dn):
        SpecStringAttr.__init__(self, meta, dn, ur'^((25[0-5]|2[0-4]\d|[01]?\d\d?)\.){3}(25[0-5]|2[0-4]\d|[01]?\d\d?)$', '192.168.255.255')

class Ip6Attr(SpecStringAttr):
    def __init__(self, meta, dn):
        pattern = ur'^([\da-fA-F]{1,4}:){7}[\da-fA-F]{1,4}$|^:((:[\da-fA-F]{1,4}){1,6}|:)$|^[\da-fA-F]{1,4}:((:[\da-fA-F]{1,4}){1,5}|:)$|^([\da-fA-F]{1,4}:){2}((:[\da-fA-F]{1,4}){1,4}|:)$|^([\da-fA-F]{1,4}:){3}((:[\da-fA-F]{1,4}){1,3}|:)$|^([\da-fA-F]{1,4}:){4}((:[\da-fA-F]{1,4}){1,2}|:)$|^([\da-fA-F]{1,4}:){5}:([\da-fA-F]{1,4})?$|^([\da-fA-F]{1,4}:){6}:$'
        SpecStringAttr.__init__(self, meta, dn, pattern, '')

class MacAttr(SpecStringAttr):
    def __init__(self, meta, dn):
        pattern = ur'^[A-F0-9]{2}(-[A-F0-9]{2}){5}$'
        SpecStringAttr.__init__(self, meta, dn, pattern, 'AF-12-23-43-CA-09')

class DateAttr(SpecStringAttr):
    def __init__(self, meta, dn):
        pattern = ur'^(?:(?:1[6-9]|[2-9][0-9])[0-9]{2}([-]?)(?:(?:0?[1-9]|1[0-2])\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\1(?:29|30)|(?:0?[13578]|1[02])\1(?:31))|(?:(?:1[6-9]|[2-9][0-9])(?:0[48]|[2468][048]|[13579][26])|(?:16|[2468][048]|[3579][26])00)([-]?)0?2\2(?:29))\s+([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$'
        SpecStringAttr.__init__(self, meta, dn, pattern, 'yyyy-MM-dd HH:mm:ss')

class EnumAttr(Attribute):
    def __init__(self, meta, dn):        
        Attribute.__init__(self, meta, dn) 
        self.range = []     
   
    def getRange(self):
        if not self.range:
            self.range = getMeta(self.dn, self.meta['type'])['enum'].keys()
        return self.range

    def verify(self, value): 
        value = self.evalue(value)  
        errorMsg = value + " should be in: \n%s " % serialize(self.getRange(), 'json')            
        return self.validate(value in self.getRange(), errorMsg)    
    
    def evalue(self, value):
        if value.startswith('"') and value.endswith('"') and ' ' not in value:
            return value.strip('"')
        else:
            return value

class AnonymousEnumAttr(EnumAttr):
    def __init__(self, meta, dn):        
        Attribute.__init__(self, meta, dn) 
        self.range = self.meta['enum'].keys()   
   
    def getRange(self):
        return self.range
        
class ArrayAttr(Attribute):
    def __init__(self, meta, dn):        
        Attribute.__init__(self, meta, dn) 
        self.range = [] 
        self.item = AttrCreator.create(meta['items'], dn)
   
    def getRange(self):
        if not self.range:
            self.range.append(self.meta['type'])
            self.range.append('maxItems: ' + str(self.meta.get('maxItems', 'Not defined')))
            self.range.extend(self.item.getRange())
        return self.range

    def verify(self, value):
        value = str(value)
        if not value.startswith('[') or not value.endswith(']'):
            print 'SyntaxError: invalid syntax, must be list'  
            return False
        value = value[1:-1].strip()
        ivalue = mysplit(value, ',')
#        try:
#            ivalue = eval(value)
#        except:
#            print 'eval %s error' % value        
#            return False
#        if not isinstance(ivalue, list): 
#            print 'expect a list'
#            return False
        if 'maxItems' in self.meta and len(ivalue) > int(self.meta['maxItems']):
            print 'length of list should be less than %s' % (int(self.meta['maxItems']) + 1)
            return False
        for i in ivalue:
            if not self.checkItemType(i) or not self.item.verify(str(i)):
                return False
        return True
    
    def evalue(self, value):
        value = value[1:-1].strip()
        ivalue = mysplit(value, ',')
        import copy
        return map(lambda x: copy.deepcopy(self.item).evalue(str(x)), ivalue)

    def checkItemType(self, item):
        return self.item.verify(item)
      
class RangeAttr(ArrayAttr): 
    def __init__(self, meta, dn):
        items = {}
        items['type'] = 'Array'
        items['maxItems'] = 2
        items['items'] = {}
        items['items']['type'] = 'WORD32'
        meta['items'] = items
        ArrayAttr.__init__(self, meta, dn) 
        
    def getRange(self):
        if not self.range:
            self.range.append(self.meta['type'])
            self.range.append('maxItems: ' + str(self.meta.get('maxItems', 'Not defined')))
            self.range.append('like [[a, b], [c, d], [e]]')
        return self.range
    
    def checkItemType(self, item):
        if not ArrayAttr.checkItemType(self, item): 
            return False
        return item[0] <= item[1] if 2 == len(item) else True

    def evalue(self, value):
        arrayValue = filter(lambda item: len(item) > 0, ArrayAttr.evalue(self, value))
        items = map(lambda item: [item[0]] if len(item) > 1 and item[0] == item[1] else item, arrayValue)
        items.sort(key = lambda item: item[0])
        i = 0
        while i < len(items) - 1:
            if items[i][-1] >= items[i+1][0] - 1:
                end = items[i+1][-1]
                if items[i][-1] > items[i+1][-1]:
                    end = items[i][-1]
                items[i] = [items[i][0], end]
                items.pop(i+1)
                continue
            i += 1
        return items
            
        

        
