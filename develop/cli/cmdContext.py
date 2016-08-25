#!/usr/bin/env python
# -*- coding: utf-8 -*-

import utils
from transId import TransId

class CommandContext(object):
    def __init__(self, cookie = '/', transId = TransId(), line = '', lastWord = '', runNow = True):
        self.cookie = cookie
        self.transId = transId
        self.line = line
        self.lastWord = lastWord
        self.args = utils.mysplit(line)
        self.runNow = runNow
        