FROM ubuntu:15.10
ENV http_proxy http://proxyxa.zte.com.cn:80/
RUN sed -i 's/archive.ubuntu.com/cn.archive.ubuntu.com/g' /etc/apt/sources.list && apt-get update  && apt-get install -yy --no-install-recommends wget snmp snmpd vsftpd && rm -rf /var/lib/apt/lists/*

