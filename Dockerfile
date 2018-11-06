FROM ubuntu:16.04
RUN apt-get update
RUN apt-get install --reinstall binutils
RUN apt-get install -y wget
RUN apt-get install -y xz-utils
RUN apt-get install -y make
RUN apt-get install -y cmake

# Arm toolchain
ENV arch x86_64
ENV target_arch arm-linux-gnueabihf
ENV linaro_toolchain gcc-linaro-7.3.1-2018.05-${arch}_${target_arch}

RUN wget https://releases.linaro.org/components/toolchain/binaries/latest-7/${target_arch}/${linaro_toolchain}.tar.xz
RUN tar -xJf /${linaro_toolchain}.tar.xz



COPY arm.cmake arm.cmake

# Install cjson
ENV cjson_version 1.7.8
ENV cjson_folder cJSON-${cjson_version}
ENV cjson_archive v${cjson_version}
ENV cjson_cmake_flags -DENABLE_CJSON_UTILS=On -DENABLE_CJSON_TEST=Off

RUN wget https://github.com/DaveGamble/cJSON/archive/${cjson_archive}.tar.gz
RUN tar -xzf /${cjson_archive}.tar.gz

WORKDIR /${cjson_folder}
RUN mkdir build

# Add arm config
RUN cat /arm.cmake > tmp \
    && cat CMakeLists.txt >> tmp \
    && mv tmp CMakeLists.txt

WORKDIR /${cjson_folder}/build

RUN export TOOLCHAIN_PATH=/${linaro_toolchain} \
    && cmake .. ${cjson_cmake_flags}
RUN make install

# Install curl

WORKDIR /
ENV curl_version 7.61.1
ENV curl_folder curl-${curl_version}
RUN wget https://github.com/curl/curl/releases/download/curl-7_61_1/${curl_folder}.tar.gz
RUN tar -xzf /${curl_folder}.tar.gz
RUN apt-get install -y libcurl4-openssl-dev

WORKDIR /${curl_folder}
RUN export CC=/${linaro_toolchain}/bin/arm-linux-gnueabihf-gcc \
    && ./configure --build=arm-linux-eabihf --host=x86_64-linux-gnu --target=arm-linux-eabihf
RUN make && make install

WORKDIR /
# Kotlin native
COPY . /app
WORKDIR /app

RUN apt-get install -y default-jdk
RUN ./gradlew compileReleaseExecutableLinux_arm32_hfpKotlinNative
# RUN ls ./build
# ENV version 0.9.3
# ENV kotlin_native kotlin-native-linux-${version}

# RUN wget https://download-cf.jetbrains.com/kotlin/native/builds/releases/${version}/linux/${kotlin_native}.tar.gz
# RUN tar xzf ${kotlin_native}.tar.gz



# RUN export PATH=$PATH:/${kotlin_native}/bin \
#     && make cinterop \
#     && make compile-arm

ENTRYPOINT ["bash", "-c"]

