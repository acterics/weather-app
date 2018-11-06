# Weather app

## Installation

- `make install-kotlinc-native`. Install kotlinc native and add compiler path(`$HOME/kotlin-native/kotlin-native-linux-$VERSION/bin`) to PATH variable
- `sudo make install`. Script for building and installing C dependencies. Installing binaries to `/usr/local/lib` and headers to `/usr/local/include`. Requires **cmake** and libssl for curl.
- `make` build app for current platform. Supported linux based platforms only. Binaries destination `./bin/weather-app.kexe`
- `./bin/weather-app.kexe` for running app.