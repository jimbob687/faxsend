#!/bin/bash

MYTAGS=`git tag --points-at HEAD`

DATENOW=`date +%s`
echo "DATENOW-$DATENOW"
echo "DATENOW-${DATENOW}"
NEWTAG='BUILT-${DATENOW}'

echo "Have run script"
echo $MYTAGS

if [[ ${MYTAGS} != *"BUILT"* ]];then
  echo "Built is not in tags"
  git tag -a $NEWTAG -m "have built version"
  git push origin --tag
else
  echo "Tag is already in commit"
fi
