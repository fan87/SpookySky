# SpookySky
An injection based Minecraft client.

Nothing special, you may want to find other clients (Well until I spend enough time on it and decided
to make it a real project). It's a client for me to practice kotlin, bytecode, and OpenGL, so you
shouldn't be using it, but learning the code as needed.

This is also a perfect example of a library I made a while back ago:
[Regular Bytecode Expression](https://github.com/fan87/Regular-Bytecode-Expression)


## Using
### Running
If you have lunar installed, you could run `./gradlew lunarRun`, and it will start lunar client with required
jvm arguments.

### Using it on other clients
Run `./gradlew agent`, and it will print out the jvm arguments you need to add. It should work in
clients mentioned below.

If you couldn't understand that, don't DM me for it as you are not supposed to use it.

### Known Supported Clients
#### 1.8
1. Vanilla
2. Optifine
3. Forge (will 100% work without mixin, may work with some mixin)
4. Lunar (The client that's being used for the development)
