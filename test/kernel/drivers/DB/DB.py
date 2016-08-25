__author__ = 'olinchy'


class DB(object):
    def __init__(self, connection):
        self._res = {}
        self._connection = connection

    def proc(self, command):
        self._res = self._connection.select(command)
        return self

    def expect(self, expectation):
        if expectation == 'NONE':
            if self._res is not None:
                raise Exception("expecting <" + expectation + "> but result is <" + str(self._res) + ">")
        elif expectation == 'NOT NONE':
            if self._res is None:
                raise Exception("expecting <" + expectation + "> but res is <None>")
        else:
            if self._res is None:
                raise Exception("expecting <" + expectation + "> but res is <None>")
            if expectation not in self._res:
                raise Exception("<" + expectation + '> is not in <' + str(self._res) + ">")
        return self

    def close(self):
        pass
