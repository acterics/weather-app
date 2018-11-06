#!/usr/bin/env sh

rm -rf ./dist
mkdir -p ./dist

cp -r ./src ./dist/
cp -r ./scripts ./dist/
cp Makefile ./dist


echo Succsessfuly copied sources to ./dist