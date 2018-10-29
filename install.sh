#!/usr/bin/env sh


TMP_DIR=$(pwd)/tmp
CJSON_DOWNLOAD_LINK=https://github.com/DaveGamble/cJSON/archive/v1.7.8.tar.gz
CJSON_DEPENDENCY=cjson
CJSON_CMAKE_FLAGS=-DENABLE_CJSON_UTILS=On -DENABLE_CJSON_TEST=Off

CURL_DOWNLOAD_LINK=https://github.com/curl/curl/releases/download/curl-7_61_1/curl-7.61.1.tar.gz
CURL_DEPENDENCY=curl


mkdir ${TMP_DIR}

sudo ./scripts/build-dependency.sh ${CJSON_DEPENDENCY} ${CJSON_DOWNLOAD_LINK} ${TMP_DIR} ${CJSON_CMAKE_FLAGS}
sudo ./scripts/build-dependency.sh ${CURL_DEPENDENCY} ${CURL_DOWNLOAD_LINK} ${TMP_DIR}


sudo rm -rf ${TMP_DIR}