all: cinterop compile

JAVA_OPTS=-Xmx256M

compile:
	export JAVA_OPTS=$(JAVA_OPTS)
	kotlinc-native -p program ./src -l cjson.klib -l libcurl.klib -r build/cinterop -o weather-app -verbose -opt -java_opts "-Xmx256M"
	mkdir -p bin
	mv ./weather-app.kexe ./bin

test:
	export JAVA_OPTS=$(JAVA_OPTS)
	kotlinc-native ./main-test.kt -verbose

cinterop: cjson-interop libcurl-interop

# sudo required
install: cjson-install libcurl-install

CINTEROP_BUILD_PATH=./build/cinterop
TMP_DIR=$(shell pwd)/tmp

cjson-interop:
	cinterop -def ./src/main/c_interop/cjson.def -o $(CINTEROP_BUILD_PATH)/cjson

	
CJSON_DOWNLOAD_LINK=https://github.com/DaveGamble/cJSON/archive/v1.7.8.tar.gz
CJSON_DEPENDENCY=cjson
CJSON_CMAKE_FLAGS="-DENABLE_CJSON_UTILS=On -DENABLE_CJSON_TEST=Off"

cjson-install:
	mkdir -p $(TMP_DIR)
	./scripts/build-dependency.sh $(CJSON_DEPENDENCY) $(CJSON_DOWNLOAD_LINK) $(TMP_DIR) $(CJSON_CMAKE_FLAGS)
	rm -rf $(TMP_DIR)

libcurl-interop:
	cinterop -def ./src/main/c_interop/libcurl.def -o $(CINTEROP_BUILD_PATH)/libcurl

CURL_DOWNLOAD_LINK=https://github.com/curl/curl/releases/download/curl-7_61_1/curl-7.61.1.tar.gz
CURL_DEPENDENCY=curl

libcurl-install:
	mkdir -p $(TMP_DIR)
	./scripts/build-dependency.sh $(CURL_DEPENDENCY) $(CURL_DOWNLOAD_LINK) $(TMP_DIR)
	rm -rf $(TMP_DIR)
	
KOTLIN_NATIVE_VERSION=0.9.3
KOTLIN_NATIVE_FOLDER=kotlin-native-linux-$(KOTLIN_NATIVE_VERSION)

install-kotlinc-native:
	mkdir -p $(HOME)/kotlin-native
	wget -O $(HOME)/kotlin-native/$(KOTLIN_NATIVE_FOLDER).tar.gz https://github.com/JetBrains/kotlin-native/releases/download/v$(KOTLIN_NATIVE_VERSION)/$(KOTLIN_NATIVE_FOLDER).tar.gz
	tar -xzvf $(HOME)/kotlin-native/$(KOTLIN_NATIVE_FOLDER).tar.gz -C $(HOME)/kotlin-native
	export PATH=$(PATH):$(HOME)/kotlin-native/$(KOTLIN_NATIVE_FOLDER)/bin
	echo $(kotlinc-native -version)