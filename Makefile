all: docker-build-arm

docker-build-arm:
	docker build -t acterics/arm-compiler .
	docker run --name temp-container acterics/arm-compiler /bin/true
	mkdir -p $(shell pwd)/bin
	docker cp temp-container:/app/bin/weather-app.kexe $(shell pwd)/bin/weather-app.kexe
	docker rm temp-container
