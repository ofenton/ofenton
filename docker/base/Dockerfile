# Base Centos
FROM centos:centos7

MAINTAINER ofenton

# Pre-install bits & pieces
RUN yum -y install wget; yum -y clean all

# Add relevant repos
RUN wget http://repos.fedorapeople.org/repos/dchen/apache-maven/epel-apache-maven.repo -O /etc/yum.repos.d/epel-apache-maven.repo

ENV DOCKER_CONTAINER='base'

# Finished
CMD ["Docker base container deploy complete"]
