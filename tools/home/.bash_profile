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
export M2_HOME=$HOME/opt/apache-maven-3.3.3

# NOTE: Mac Specific
export JAVA_HOME=`/usr/libexec/java_home`

export PATH="$PATH:$HOME/git/hotels/hdw/tools/bin"
export PATH="$PATH:$GIT_HOME/github/ofenton/ofenton/tools/bin"
export PATH="$PATH:$HADOOP_HOME/bin/"
export PATH="$PATH:$M2_HOME/bin/"


###########
# ALIASES #
###########
lcd () 
{
    cd "$1" && ls -al
}

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
export PROJECT_HOME=$HOME/dev
source /usr/local/bin/virtualenvwrapper.sh

##################
# COMMAND PROMPT #
##################
h=`hostname | perl -e '$_=<STDIN>; my @h = split /\./; print "$h[1].$h[0]"'`
PS1="\[\e[36m\]\u\[\e[0m\]@"
PS1="$PS1\[\e[34;1m\]$h\[\e[0m\]"
PS1="$PS1:\w >"
