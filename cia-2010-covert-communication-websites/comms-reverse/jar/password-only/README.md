# Password-only JAR family

This project builds a Java 8 applet with the `users.help.Members` entry point used by the 2011 capture. It uses the recovered password-only mailbox protocol and an independently designed interface. The compiler inputs are the Java sources in `src/`.

Run these from the `cia-2010-covert-communication-websites` directory.

```bash
project=comms-reverse/jar/password-only
python3 "$project/build.py"
./reconstruction.py europeannewsflash.com --source-jar "$project/build/europeannewsflash.com/users.jar"
```

Open the printed URL with CheerpJ Applet Runner, or add `--native` to open Java 8 AppletViewer. Enter the server's demo password. Sign out and back in to retrieve the scripted reply after the first send.

`reconstruction.py` embeds the local endpoint, RSA public key, password-wrapped secret, and archived transport and file settings in the JAR it serves. It delivers it at `/users.jar` and keeps the archived file available at `/__reconstruction__/original.jar`. The build output can be reused across server starts.

## Recovered behavior

| Archived classes | Source implementation |
| --- | --- |
| `At` and its resource codecs | `JarResources` reads the pair-swapped IDEA/CBC container, gzip payload, and little-endian resource records. |
| `Av`, `Ah`, `A`, and `Cj` | `LegacyClient` implements the RSA/AES password envelope, token and rotating challenge, and binary, Base64, and hexadecimal transports. This capture uses binary transport. |
| `O`, `K`, `Aj`, and `Bv` | The client implements first/next inbox reads, deletion, and text and binary sends. It preserves the AES message layout and the little-endian timestamp and marker. |
| `Members`, `Bk`, `Bb`, and `Cm` | The applet exposes password login, inbox, quoting, compose, attachment upload, save, delete confirmation, and sign-out. The archive's upload cap and filename marker rules carry over. |

The source models the recovered messaging behavior of this capture with standard Java cryptographic providers and a separate AWT interface. The original JAR is the reference for its archived widget classes, artwork, and browser integration.

Run the headless protocol test with the same server implementation.

```bash
./reconstruction.py europeannewsflash.com --source-jar "$project/build/europeannewsflash.com/users.jar" --self-test
```

The test covers bad-password rejection, the two-stage login, inbox reads, UTF-8 send, reply on the next login, deletion, and binary upload. Viewer and file-dialog behavior require a graphical runtime.
