import sys
import re

__author__ = 'olinchy'

"""
   connector for db
     incl : oracle, sqlserver
"""

import cx_Oracle
import pymssql
from abc import abstractmethod


def _analyze_exp(sqlexp):
    pattern = re.compile('(.*)\.{1,2}(.*)\[(.*)\](\{([a-zA-Z0-9]*)=(.*)\})')
    obj = pattern.match(sqlexp)
    if obj is None:
        pattern = re.compile('(.*)\.{1,2}(.*)\[(.*)\]')
        return pattern.match(sqlexp)
    return obj


class Connection(object):
    def __init__(self, connection):
        self._connection_desc = connection
        self._connection = None
        self._connect()

    def select(self, sqlexp):
        obj = _analyze_exp(sqlexp)
        if len(obj.groups()) == 3:
            sql = '''select %(field)s from %(user)s%(dbSpec)s%(table)s''' % {'field': obj.group(3),
                                                                             'dbSpec': self._getSpec(),
                                                                             'table': obj.group(2),
                                                                             'user': obj.group(1)}
        else:
            sql = '''select %(field)s from %(user)s%(dbSpec)s%(table)s where %(cond)s=\'%(value)s\'''' % {
                'field': obj.group(3),
                'dbSpec': self._getSpec(),
                'table': obj.group(2),
                'user': obj.group(1),
                'cond': obj.group(5),
                'value': obj.group(6)}
        self._cursor.execute(sql)
        row = self._cursor.fetchone()
        if row:
            return row

    @abstractmethod
    def _connect(self):
        pass

    @abstractmethod
    def _getSpec(self):
        pass


class OracleConnection(Connection):
    def _getSpec(self):
        return '.'

    def _connect(self):
        self._connection = cx_Oracle.connect(self._connection_desc)
        self._cursor = self._connection.cursor()


class SqlServerConnection(Connection):
    def _getSpec(self):
        return '..'

    def _connect(self):
        # server=sa/omc!2012@10.86.56.206:1433/dbname,
        first = self._connection_desc.split('@')
        userAndPass = first[0].split('/')
        serverAndDatabase = first[1].split('/')

        host = serverAndDatabase[0]
        usr = userAndPass[0]
        psw = userAndPass[1]
        if len(serverAndDatabase) > 1:
            database = serverAndDatabase[1]
        else:
            database = ''
        self._cursor = pymssql.connect(host, usr, psw, database).cursor()


class DBConnector(object):
    def __init__(self, db_desc):
        self._connection = db_desc["connection"]
        self._type = db_desc["type"]

    def createConnect(self):
        if self._type == 'oracle':
            return OracleConnection(self._connection)
        else:
            return SqlServerConnection(self._connection)


if __name__ == "__main__":
    pattern = re.compile('\{([a-zA-Z0-9]*)=(.*)\}')
    obj = pattern.match('{OID=e1 link=1}')
    obj1 = pattern.match('{OID=/Ems/1}')
    # obj = _analyze_exp('MW_CM.TOPO_NELINK_TABLE[OID]{OID=e1 link=1}')
    print obj.group(1)
    print obj.group(2)
    print obj1.group(1)
    print obj1.group(2)
    # desc = {"connection": sys.argv[1], "type": sys.argv[2]}
    # connect = DBConnector(desc).createConnect()
    #
    # print connect.select(sys.argv[3])
    # server = '10.86.56.206:1433'
    # user = 'sa'
    # password = 'omc!2012'
    #
    # conn = pymssql.connect(server, user, password)
    # cursor = conn.cursor()
    # # cursor.execute("""
    # # IF OBJECT_ID('persons', 'U') IS NOT NULL
    # #     DROP TABLE persons
    # # CREATE TABLE persons (
    # #     id INT NOT NULL,
    # #     name VARCHAR(100),
    # #     salesrep VARCHAR(100),
    # #     PRIMARY KEY(id)
    # # )
    # # """)
    # # cursor.executemany(
    # #     "INSERT INTO persons VALUES (%d, %s, %s)",
    # #     [(1, 'John Smith', 'John Doe'),
    # #      (2, 'Jane Doe', 'Joe Dog'),
    # #      (3, 'Mike T.', 'Sarah H.')])
    # # you must call commit() to persist your data if you don't set autocommit to True
    # conn.commit()
    #
    # cursor.execute('SELECT name FROM uep4x..res_definition_table')
    # row = cursor.fetchone()
    # print row
    # while row:
    #     print("Name=%s" % (row[0]))
    #     row = cursor.fetchone()
    #
    # conn.close()
