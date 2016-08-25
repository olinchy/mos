FROM moslite:jdk 
ADD etc/default/snmp etc/default/snmp
ADD etc/vsftpd.conf etc/vsftpd.conf
RUN mkdir -p /var/run/vsftpd/empty && useradd -p zteftp2013 -m -U -r -s /bin/bash zteftp && echo "zteftp:zteftp2013" | chpasswd



