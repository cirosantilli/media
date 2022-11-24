# Recovered clients

The representative JAR captures and their SHA-256 hashes are listed in [jar/captures.json](jar/captures.json). From the media project directory, run `python3 comms-reverse/jar/build-family.py weather`, substituting any family in the table. The output is in that family's `build/` directory.

| Family | Representative site | Where to start reading |
| --- | --- | --- |
| Bandwidth meter | worldofonlinenews.com | [MeterApplet](jar/bandwidth-meter/src/recovered/b/a/MeterApplet.java) starts the [traffic worker](jar/bandwidth-meter/src/recovered/b/a/a/TrafficWorker.java). |
| Bandwidth meter without bw.properties | factorforcenews.com | [FactorData](jar/bandwidth-meter-no-bw-properties/src/FactorData.java) builds the throughput requests. |
| Content transport | worldfinancetoday.net | [ContentApplet](jar/content-transport/src/recovered/a/b/g/ContentApplet.java) initializes the client from the bundled configuration. |
| JS content transport | aeronet-news.com | [ContentApplet](jar/js-content-transport/src/recovered/a/a/b/ContentApplet.java) calls the [page's JavaScript](jar/js-content-transport/page.js). |
| Weather | information-junky.com | [WeatherCodec](jar/weather/src/recovered/WeatherCodec.java) reads and writes weather protocol messages. |

The build uses Java 8 `javac`, Java 17 or newer, ASM 9 and the original capture from the website dump. Each family's `names.tsv` maps the archived identifiers to the source identifiers. Some archived classes have methods that differ only in return type, so their Java source names include a return-type suffix. The build restores the archived names after compilation and copies the non-class resources from the capture.

The [password-only](jar/password-only/README.md) and [slideshow and scrolling](jar/slideshow/README.md) JARs have their own build instructions. The JavaScript families are [password-only](password-only.js) and [polling](polling.js).

[The Flash assembler](swf/build.py) rebuilds World Dispatch with `python3 comms-reverse/swf/build.py`. Its [49 AVM1 action blocks](swf/src/) include the [root timeline](swf/src/root.avm1). The original capture supplies the artwork and other movie assets. The rebuilt movie matches the capture byte for byte after decompression.

The [CGI](cgi/login.html) and [CGI variant](cgi-variant/login.html) examples preserve the browser forms, including the field names and submission targets. The captures contain only the client side.
