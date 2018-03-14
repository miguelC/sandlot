#!/bin/bash

DIR=$(hostname)
DIR="${DIR,,}"

if [ -d "$DIR" ]; then
  echo "Copying files from $DIR"

  cp "$DIR/sandlotRoutes.xml" ../routes/sandlotRoutes.xml
  cp "$DIR/context.xml" ../context.xml
  if [ -e "$DIR/logback.xml" ]; then
    cp "$DIR/logback.xml" ../logback.xml
  fi


else
  echo "Directory $DIR not found, nothing to configure"
fi

echo "Deleting all directories under deployment"
find . -maxdepth 1 ! -name "$DIR" ! -name '.*' | xargs rm -rf
  
echo "Done!"