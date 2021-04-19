#!/bin/bash


#缺少tcl
# install tcl
wget http://downloads.sourceforge.net/tcl/tcl8.6.1-src.tar.gz
tar xzvf tcl8.6.1-src.tar.gz  -C /usr/local/
cd  /usr/local/tcl8.6.1/unix/
./configure
make & make install

# install redis
wget http://download.redis.io/releases/redis-6.0.6.tar.gz
tar xzf redis-6.0.6.tar.gz
cd redis-6.0.6
make

