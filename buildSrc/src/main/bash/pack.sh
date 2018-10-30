#!/usr/bin/env sh

mkdir -p ./dist

cp -r ./src ./dist/
cp -r ./scripts ./dist/

mkdir -p ./dist/buildSrc
cp -r ./buildSrc/src ./dist/buildSrc
cp ./buildSrc/build.gradle.kts ./dist/buildSrc

cp -r ./gradle ./dist/gradle
cp build.gradle.kts ./dist
cp gradle.properties ./dist
cp gradlew ./dist
cp install.sh ./dist
cp settings.gradle.kts ./dist

echo Succsessfuly copied sources to ./dist