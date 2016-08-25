#!/usr/bin/env python
# -*- coding: utf-8 -*-

def isComplete(word):
    model = {'[': ']', '{': '}', '"': '"'}
    modelR = {']': '[', '}': '{', '"': '"'}
    stack = []
    for char in word:
        if char in model.values() and stack and stack[-1] == modelR[char]:
            stack.pop()
        elif char in model.keys():
            stack.append(char)
    return len(stack) == 0


def mysplit(line, deli=''):
    if not line:
        return []
    line = line.strip()
    args = line.split(deli) if deli else line.split()
    deli = deli if deli else ' '
    i = 0
    if not args:
        return []
    while i < len(args) - 1:
        arg = args[i]
        if not isComplete(arg):
            args[i] = deli.join([arg, args[i + 1]])
            args.pop(i + 1) 
            continue
        i += 1
    return map(lambda x: x.strip(), args)
     
def joinDn(curDn, newDn):
    if not newDn:
        return curDn
    if newDn.startswith('/'):
        return newDn
    oldList = filter(lambda x: x != '', curDn.split('/'))
    newList = filter(lambda x: x != '', newDn.split('/'))
    for item in newList:
        if item == '..' :
            if oldList:
                oldList.pop(-1)
            else:
                print 'path is error'
                continue
        elif item == '.':
            pass
        else:
            oldList.append(item)
    return '/' + '/'.join(oldList)

def lookupItem(aList, function, default = None):
    for item in aList:
        if function(item):
            return item
    return default
