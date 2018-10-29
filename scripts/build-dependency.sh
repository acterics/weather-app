#!/usr/bin/env sh

DEPENDENCY_NAME=$1
DOWNLOAD_LINK=$2
WORKING_DIR=$3
CMAKE_FLAGS=$4

archive_name=${DEPENDENCY_NAME}.tar.gz

downloads_dir=${WORKING_DIR}/downloads/${DEPENDENCY_NAME}
sources_dir=${WORKING_DIR}/sources/${DEPENDENCY_NAME}
tmp_dir=${WORKING_DIR}/tmp/${DEPENDENCY_NAME}

mkdir -p ${downloads_dir}
mkdir -p ${sources_dir}
mkdir -p ${tmp_dir}

echo Downloading dependency ${DEPENDENCY_NAME} from ${DOWNLOAD_LINK}

cd ${downloads_dir}

wget -O ${archive_name} ${DOWNLOAD_LINK}

echo Successfully downloaded
echo Unzip sources


tar_output=$(tar -xzvf ${downloads_dir}/${archive_name} -C ${tmp_dir})
extracted_dir=$(echo ${tar_output} | tr " " "\n" | head -1 | cut -f1 -d"/")
echo Extracted ${extracted_dir}



mv ${tmp_dir}/${extracted_dir}/* ${sources_dir}
rm -rf ${tmp_dir}

cd ${sources_dir}
mkdir build
cd build
cmake .. ${CMAKE_FLAGS}
make
make install