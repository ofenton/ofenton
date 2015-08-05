#!/bin/bash

hive -e 'SHOW CREATE TABLE $1;'
