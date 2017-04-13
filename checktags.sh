#!/bin/bash

MYTAGS=`git tag --points-at HEAD`

echo "Have run script"
echo $MYTAGS

if [[ ${MYTAGS} != *"built"* ]];then
  echo "Built is not in tags"
fi
