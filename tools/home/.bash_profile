# .bashrc

# Source global definitions
if [ -f /etc/bashrc ]; then
        . /etc/bashrc
fi

#################
# ENV VARIABLES #
#################
export GIT_HOME=$HOME/git
export HADOOP_HOME=$HOME/opt/hadoop-2.7.0
export HIVE_HOME=$HOME/opt/apache-hive-1.2.1-bin
export M2_HOME=$HOME/opt/apache-maven-3.3.3

OS=`uname -s`
if [ ${OS} == "Darwin" ]; then
  export JAVA_HOME=`/usr/libexec/java_home`
elif [ ${OS} == "Linux" ]; then
  JAVA_HOME=$(readlink -f /usr/bin/java | sed "s:bin/java::")
  export JAVA_HOME
else
  echo "UNKNOWN OPERATING SYSTEM"
fi

export PATH="$PATH:$HOME/git/stash/hdw/tools/bin"
export PATH="$PATH:$GIT_HOME/github/ofenton/ofenton/tools/bin"
export PATH="$PATH:$HADOOP_HOME/bin"
export PATH="$PATH:$HIVE_HOME/bin"
export PATH="$PATH:$M2_HOME/bin"


###########
# ALIASES #
###########
lcd () 
{
    cd "$1" && ls -al
}

alias grep='grep --color=auto'
alias fgrep='fgrep --color=auto'
alias egrep='egrep --color=auto'

alias ll='ls -alF'

##############################################################################################
# Python                                                                                     #
# Some Instructions: http://hackercodex.com/guide/python-development-environment-on-mac-osx/ #
#                  : http://virtualenvwrapper.readthedocs.org/en/latest/install.html         #
##############################################################################################
# pip should only run if there is a virtualenv currently activated
export PIP_REQUIRE_VIRTUALENV=true
# cache pip-installed packages to avoid re-downloading
export PIP_DOWNLOAD_CACHE=$HOME/.pip/cache

export WORKON_HOME=$HOME/.virtualenvs
source /usr/local/bin/virtualenvwrapper.sh

###############
# Docker      #
###############
#export DOCKER_HOST=tcp://localhost:4243

##################
# COMMAND PROMPT #
##################
h=`hostname | perl -e '$_=<STDIN>; my @h = split /\./; print "$h[1].$h[0]"'`
PS1="\[\e[36m\]\u\[\e[0m\]@"
PS1="$PS1\[\e[34;1m\]$h\[\e[0m\]"
PS1="$PS1:\w >"
