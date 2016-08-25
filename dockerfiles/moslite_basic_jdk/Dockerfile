FROM moslite:basic
RUN mkdir -p /usr/lib/jvm && wget --no-proxy -O - ftp://soft:soft@10.86.56.6/tools/jdk/jdk-7u75-linux-x64.tar.gz | tar zxf - -C /usr/lib/jvm && update-alternatives --install "/usr/bin/java" "java" "/usr/lib/jvm/jdk1.7.0_75/bin/java" 1 && apt-get purge -y wget && apt-get autoremove -y && apt-get clean -y && rm -rf /tmp/* && rm -rf /var/cache/*
