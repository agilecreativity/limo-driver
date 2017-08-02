# limo-driver

[![Clojars Project](https://img.shields.io/clojars/v/limo-driver/lein-template.svg)](https://clojars.org/limo-driver/lein-template)
[![Dependencies Status](https://jarkeeper.com/agilecreativity/limo-driver/status.svg)](https://jarkeeper.com/agilecreativity/limo-driver)

A Leiningen template for creating a simple [Limo][] project.

Many of the generation codes are from the excellent [Chestnut][] template.

## Usage

For more information about how the Limo api works, please see [Limo][] page.

```sh
# Clone this repository
git clone https://github.com/agilecreativity/limo-driver.git

# The simple project with basic example
cd limo-driver && lein install

# You are now ready to create a Limo project using the template
lein new limo <your-project-name>

# To include the sample codes that loads config from property files
lein new limo-driver <your-project-name> +example

cd <your-project-name>
```

### Sample Usage

- See the generated code `src/<your-project-name>/core.clj`

- More complex example will be in the `src/<your-project-name>/example/quick-login.clj` if `+example` is used.

```sh
# Create the project with some example
lein new limo-driver <your-project-name> +example

# e.g. if your project name is awesome-limo then
lein new limo-driver awesome-limo +example
```

## License

Copyright © 2017 Burin Choomnuan

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

[Limo]: https://github.com/Mayvenn/limo
[Chestnut]: https://github.com/plexus/chestnut
