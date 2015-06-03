# .bashrc

# Source global definitions
if [ -f /etc/bashrc ]; then
        . /etc/bashrc
fi

#################
# ENV VARIABLES #
#################
export GIT_HOME=$HOME/git
export HADOOP_HOME=$HOME//opt/hadoop-2.7.0
export M2_HOME=$HOME/opt/apache-maven-3.3.3

# NOTE: Mac Specific
export JAVA_HOME=`/usr/libexec/java_home`

export PATH="$PATH:$GIT_HOME/ofenton/ofenton/git_tools/bin"
export PATH="$PATH:$HADOOP_HOME/bin/"
export PATH="$PATH:$M2_HOME/bin/"


###########
# ALIASES #
###########
lcd () 
{
    cd "$1" && ls -al
}

######################################
# Enable Python virtual environments #
######################################
source /usr/local/bin/virtualenvwrapper.sh

##################
# COMMAND PROMPT #
##################
h=`hostname | perl -e '$_=<STDIN>; my @h = split /\./; print "$h[1].$h[0]"'`
PS1="\[\e[36m\]\u\[\e[0m\]@"
PS1="$PS1\[\e[34;1m\]$h\[\e[0m\]"
if [ "x$YROOT_NAME" != "x" ]; then
  # Yroot Indicator
  PS1="$PS1{\[\e[32;40m\]$YROOT_NAME\[\e[0m\]}"
fi
PS1="$PS1:\w >"
