# Slideshow and ticker JAR family

These Java 8 builds use readable source for the applet entry points, display
scheduling, and mailbox window. They retain the archived Java classes for the
serialized wire objects, reliable streams, authentication, keyboard activation,
and message codecs. The build reads the corresponding archived JAR to package
those dependencies.

Run from the `cia-2010-covert-communication-websites` directory.

```bash
python3 comms-reverse/jar/slideshow/build.py mynepalnews.com
./reconstruction.py mynepalnews.com --source-jar comms-reverse/jar/slideshow/build/mynepalnews.com/nepali.jar

python3 comms-reverse/jar/slideshow/build.py inews-today.com
./reconstruction.py inews-today.com --source-jar comms-reverse/jar/slideshow/build/inews-today.com/headlines.jar

python3 comms-reverse/jar/slideshow/build.py half-court.net
./reconstruction.py half-court.net --source-jar comms-reverse/jar/slideshow/build/half-court.net/basket.jar
```

Run the servers separately or give them different ports. Use the activation
keys printed on startup, then log in with `smiley` or `karla` and the demo
password. The server supplies slide timing or ticker speed. Reload the page
and log in again after sending text to retrieve the scripted reply.

The build generates each applet entry point from its site record. The encrypted
`Applet.properties` from that capture selects slideshow or ticker mode.

`DisplayMailbox.java` maps operations to the archived classes and methods. The
Nepal build reads `mynepalnews.com/20110204143500/nepali.jar` and retains the
`irf.gnh.qec.*` wire descriptors. The iNews build reads
`inews-today.com/20110201140115/headlines.jar` and retains the
`ftkj.xzb.bvw.*` wire descriptors. The original is still served at
`/__reconstruction__/original.jar`.

Half Court uses the 376-class `half-court.net/20101229100220/basket.jar`.
Its `timeout.whistle.side.basketApplet` entry point extends `b.b.e.a` in the
archive. The rebuild uses the shared source presentation and retains the
`timeout.whistle.ball.*` wire descriptors. The class map in
`half-court-types.json` pairs its archived names with the names used by the
later builds. The applet uses a literal sequence while the trigger key is
held. Its dispatcher supplies the reliable transport directly, and the
action stream has no acknowledgement channel.

Add `--self-test` to a replay command to test activation, display rotation,
login, text send, reconnect, and deletion against the existing local protocol
server. Real-time cover scheduling and the Swing window require a graphical
runtime. During login and transfer, extra display requests can advance the
server-side rotation.
