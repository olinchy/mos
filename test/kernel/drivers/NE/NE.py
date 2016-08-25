import pexpect
import os
import time
from threading import Thread,Event

class NE(object):
    def __init__(self, param):
        self.param = param
    def open(self):
        self.ne = _NE(self.param)
        self.ne.open()
    def close(self):
        self.ne.close()
        self.ne = None

class _NE(Thread):
    def __init__(self, param):
        Thread.__init__(self)
        self.param = param
        self.is_up = Event()
        self.is_close = Event()

    def run(self):
        os.system('rm -rf /var/lib/mos/*.json')
        self.child = pexpect.spawn("demo_ne.bin -p %s" % self.param["port"], logfile=open("/var/lib/mos/demo_ne.log", "a+"))
        self.child.expect("up and running")
        time.sleep(0.1)
        self.is_up.set()
        self.child.expect(pexpect.EOF, timeout = None)
        while self.child.isalive():
            time.sleep(0.1)
        self.child.close()
        self.is_close.set()

    def open(self):
        self.start()
        self.is_up.wait()

    def close(self):
        self.child.sendcontrol('c')
        self.is_close.wait()
        self.join()
        self.child = None
