# coding=gbk

import os.path
import types


class CliManager(object):
    def __init__(self, cliDescription):
        self.clis = []
        self._load(cliDescription)

    def getAddress(self, name):
        cli = self.getCli(name)
        if (cli):
            return cli.address;

    def getDnPrefix(self, name):
        cli = self.getCli(name)
        if (cli):
            return cli.dnPrefix;

    def _load(self, cliDescription):
        for cli in cliDescription:
            self.clis.append(CliDesc(cli["name"], cli["address"], cli["dnPrefix"]))

    def getCli(self, name):
        for cli in self.clis:
            if 0 == cmp(cli.name, name):
                return cli
        return 0;


class CliDesc(object):
    def __init__(self, name, address, dnPrefix):
        self.name = name
        self.address = address
        self.dnPrefix = dnPrefix
