all: docker-build-arm

docker-build-arm:
	docker build -t acterics/arm-compiler .
	docker run --name temp-container acterics/arm-compiler /bin/true
	mkdir -p $(shell pwd)/bin
	docker cp temp-container:/app/bin/weather-app.kexe $(shell pwd)/bin/weather-app.kexe
	docker rm temp-container


# use with sudo
install-dependencies: install-curl install-cjson


curl_version=7.61.1
curl_folder=curl-$(curl_version)

install-curl:
	wget https://github.com/curl/curl/releases/download/curl-7_61_1/$(curl_folder).tar.gz
	tar -xzf ./$(curl_folder).tar.gz
	cd ./$(curl_folder) && ./configure
	cd ./$(curl_folder) && make
	cd ./$(curl_folder) && make install
	rm $(curl_folder).tar.gz
	rm -rf $(curl_folder)


cjson_version=1.7.8
cjson_folder=cJSON-$(cjson_version)
cjson_archive=v$(cjson_version)
cjson_cmake_flags="-DENABLE_CJSON_UTILS=On -DENABLE_CJSON_TEST=Off"

install-cjson:
	wget https://github.com/DaveGamble/cJSON/archive/$(cjson_archive).tar.gz
	tar -xzf ./$(cjson_archive).tar.gz
	cd ./$(cjson_folder) && mkdir build
	cd ./$(cjson_folder)/build && cmake .. $(cjson_cmake_flags)
	cd ./$(cjson_folder)/build && make install
	rm ./$(cjson_archive).tar.gz
	rm -rf ./$(cjson_folder)
	