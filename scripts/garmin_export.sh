#!/bin/bash

# Simple script to download all Garmin data using kjkjava's simple downloader

python ${GIT_HOME}/kjkjava/garmin-connect-export/gcexport.py all gpx /Users/ofenton/data/garmin/
#python $GIT_HOME/kjkjava/garmin-connect-export/gcexport.py all gpx /Users/ofenton/data/garmin/`date "+%Y%m%d”`
