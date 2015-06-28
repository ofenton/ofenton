FROM ofenton/java_base:latest

MAINTAINER ofenton

# Install everything
RUN yum -y install \
    apache-maven   \
    git;
    yum -y clean all

# Set up environment
ENV GIT_HOME=${HOME}/git

# Finished
CMD ["Docker build container deploy complete"]
