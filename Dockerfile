FROM ubuntu:16.04
RUN apt-get update \
    && apt-get install --reinstall binutils \
    && apt-get install -y wget \
    && apt-get install -y xz-utils \
    && apt-get install -y make \
    && apt-get install -y cmake \
    && apt-get install -y libcurl4-openssl-dev

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

WORKDIR /${curl_folder}
RUN export CC=/${linaro_toolchain}/bin/arm-linux-gnueabihf-gcc \
    && ./configure --build=arm-linux-eabihf --host=x86_64-linux-gnu --target=arm-linux-eabihf
RUN make && make install

WORKDIR /

# Kotlin native

RUN apt-get install -y default-jdk

ENV version 0.9.3
ENV kotlin_native kotlin-native-linux-${version}

RUN wget https://download-cf.jetbrains.com/kotlin/native/builds/releases/${version}/linux/${kotlin_native}.tar.gz
RUN tar xzf ${kotlin_native}.tar.gz

COPY ./test/main.kt main.kt
RUN /${kotlin_native}/bin/kotlinc-native main.kt

# Cinterop

COPY ./src/main/c_interop /cinterop/def
RUN /${kotlin_native}/bin/cinterop -def /cinterop/def/cjson.def -o /cinterop/cjson -target linux_arm32_hfp
RUN /${kotlin_native}/bin/cinterop -def /cinterop/def/libcurl.def -o /cinterop/libcurl -target linux_arm32_hfp

# Compile project
COPY . /app
WORKDIR /app

RUN /${kotlin_native}/bin/kotlinc-native -p program ./src -l cjson.klib -l libcurl.klib -r /cinterop -o weather-app -verbose -opt -target linux_arm32_hfp
RUN mkdir -p bin \
    && mv ./weather-app.kexe ./bin

ENTRYPOINT ["bash", "-c"]

