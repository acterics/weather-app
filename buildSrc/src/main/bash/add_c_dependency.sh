#!/usr/bin/env sh

DEPENDENCY_NAME=$1
DOWNLOAD_LINK=$2
WORKING_DIR=$3
UNZIP_PATH=$4

archive_name=${DEPENDENCY_NAME}.tar.gz

downloads_dir=${WORKING_DIR}/downloads
sources_dir=${WORKING_DIR}/sources
tmp_dir=${WORKING_DIR}/tmp

mkdir


mkdir ${downloads_dir}
mkdir ${sources_dir}
mkdir ${tmp_dir}

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