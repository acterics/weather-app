#!/usr/bin/env sh
./gradlew compileReleaseExecutableLinux_arm32_hfpKotlinNative
mkdir bin
cp ./build/exe/main/release/executable/linux_arm32_hfp/weather-app.kexe ./bin