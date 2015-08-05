#!/bin/bash

hive -e 'DESCRIBE FUNCTION EXTENDED $1;'
