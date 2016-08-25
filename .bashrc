export force_color_prompt=yes
. ${HOME}/.bashrc
export MOS_HOME=$(readlink -f $(dirname $0))
export INSTALL_PREFIX=$MOS_HOME
export PATH="$MOS_HOME/tools/bin:$PATH"
export LD_LIBRARY_PATH="$MOS_HOME/lib"
export CMAKE_TOOLS="$MOS_HOME/tools/build/cmake"
export NUMBER_OF_CPU=$(expr 2 + $(grep "processor" /proc/cpuinfo|sort -u|wc -l))
export no_proxy=10.0.0.0/8,192.0.0.0/8,*.zte.com.cn,.zte.intra,$no_proxy
export PS1='\[\033[38;05;208m\][MOS=${MOS_HOME}]\[\033[00m\]$ '
