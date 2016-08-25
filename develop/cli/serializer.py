#!/usr/bin/env python
# -*- coding: utf-8 -*-

def serialize(obj, style):
    formatters = {'json': _JsonSerializer, 'yaml': _YamlSerializer}
    try:
        stream = formatters[style](obj).serialize()
        return stream
    except:
        print 'not found formatter by %s' % style
        return None
    
class _Serializer(object):
    def __init__(self, obj):
        self.obj = obj
    
    def serialize(self):
        pass
    
    
class _JsonSerializer(_Serializer):
    def __init__(self, obj):
        _Serializer.__init__(self, obj)
        
    def serialize(self):
        import json
        return json.dumps(self.obj, indent = 4, ensure_ascii = False)
        
class _YamlSerializer(_Serializer):
    def __init__(self, obj):
        _Serializer.__init__(self, obj)
        
    def serialize(self):
        import yaml
        return yaml.safe_dump(self.obj, default_flow_style = False, allow_unicode = True, encoding = None)
