#!/usr/bin/env python
# -*- coding: utf-8 -*-
from msg import GetMetaMsg, LsExMsg
from transId import TransId
import utils

def _initAttr(dictionary, key, default):
    if key not in dictionary:
        dictionary[key] = default

def _initMeta(meta):
    _initAttr(meta, 'attributes', [])
    _initAttr(meta, 'actions', {})
    _initAttr(meta['actions'], 'customs', [])

def _mergeList(list1, list2):
    list1.extend(list2)

def _mergeMeta(target, source):
    _initMeta(target)
    if source:
        _initMeta(source)
        target['attributes'].extend(source['attributes'])
        target['actions']['customs'].extend(source['actions']['customs'])
    return target

def _getMeta(dn, name, transId):
    meta = GetMetaMsg(dn, name, transId).send() if dn or name else None
    if meta and meta.get('baseClass', ''):
        parent = _getMeta(dn, meta['baseClass'], transId)
        meta = _mergeMeta(meta, parent)
    return meta

def getMeta(dn, name = '', transId = TransId()):
    return _getMeta(dn, name, transId)

def getActionMeta(dn, name):
    trans = TransId()
    meta = _getMeta(dn, '', trans)
    if meta and meta.get('actions', None):
        actions = meta['actions'].get('customs', [])
        return utils.lookupItem(actions, lambda action: action['name'] == name)
    return None

def getActionNames(dn):  
    trans = TransId()
    meta = _getMeta(dn, '', trans)
    return map(lambda action: action['name'], meta.get('actions', {}).get('customs', [])) if meta else []
    
def getChildren(dn, transId):
    response = LsExMsg(dn, transId).send()
    return response['children'] if response['result'] == 0 else []

def isDnExist(dn, transId):
    response = LsExMsg(dn, transId).send()
    return response['result'] == 0

def makeTree(transId, dn, key = None):
    tree = {}
    children = getChildren(dn, transId)
    childTree = map(lambda x: makeTree(transId, utils.joinDn(dn, x), x), children)
    if not key:
        key = dn
    tree[key] = childTree
    return tree
    
