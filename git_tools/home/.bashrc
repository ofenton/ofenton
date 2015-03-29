# .bashrc

# Source global definitions
if [ -f /etc/bashrc ]; then
        . /etc/bashrc
fi

#################
# ENV VARIABLES #
#################
export GIT_HOME=$HOME/git
export PATH="$PATH:$GIT_HOME/ofenton/ofenton/git-tools/home/bin"
export PATH="$PATH:$HOME/.rvm/bin" # Add RVM to PATH for scripting


###########
# ALIASES #
###########
lcd () 
{
    cd "$1" && ls -al
}


alias mvn_coverage='mvn clover2:setup test clover2:aggregate clover2:clover'

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
