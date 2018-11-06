# Weather app

## Installation

- `make` or `make docker-build-arm` to compiling sources for arm. Binaries destination `./bin/weather-app.kexe`
- `sudo make install-dependencies` to install dependency libs on your machine. Instalation path `/usr/local/include` for headers and `/usr/local/lib` for .so files. `libcurl` requires `openssl` lib (`apt-get install -y libcurl4-openssl-dev`)