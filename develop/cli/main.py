#!/usr/bin/env python
# -*- coding: utf-8 -*-
import sys
import os.path
from command import factory
from server import server

if __name__ == '__main__':
    modelPath = os.path.join(os.path.dirname(os.path.realpath(sys.argv[0])), "model/")
    factory.loadModels(modelPath)
    try:
        started = server.start(sys.argv[1]) if len(sys.argv) > 1 else server.start()
        if not started:
            raise Exception("start failed, connection refused!")
        factory.create('prompt').cmdloop()
    except KeyboardInterrupt:
        pass
    except:
        import traceback
        print traceback.print_exc()
    try:
        server.stop()
        print 'bye!'
    except:
        pass

