#!/usr/bin/env sh
./gradlew compileReleaseExecutableLinux_x64KotlinNative
mkdir -p bin
cp ./build/exe/main/release/executable/linux_x64/weather-app.kexe ./bin