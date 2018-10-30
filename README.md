# Weather app

## Installation

- `sudo ./install.sh`. Script for building and installing C dependencies. Installing binaries to `/usr/local/lib` and headers to `/usr/local/include`. Requires **cmake** and libssl for curl.
- `./scripts/build-{platform}.sh`. Script building binaries for specific platform. Binaries destination `./bin/weather-app.kexe`
- `./bin/weather-app.kexe` for running app.