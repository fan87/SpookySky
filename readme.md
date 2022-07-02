# SpookySky
An JavaAgent based Minecraft client.

Nothing special, you may want to find other clients (Well until I spend enough time on it and decided
to make it a real project). It's a client for me to practice kotlin, bytecode, and OpenGL, so you
shouldn't be using it, but learning the code as needed.

This is also a perfect example of a library I made a while back ago:
[Regular Bytecode Expression](https://github.com/fan87/Regular-Bytecode-Expression)


## Disclaimer
You are not supposed to cheat on servers using this software! This was 1000% made for learning purposes 
(im serious, this project was actually made for [Regular Bytecode Expression](https://github.com/fan87/Regular-Bytecode-Expression),
I need something to be able to test it, and I feel like coding another hacked client will be a great idea).

You may fork this repository and add bypasses to it, but I'm not doing them, at least for now.

(So I'm not cheating on servers with this, don't ban me just because I made this repo without any proof,
it's stupid)

## Using
### Running
If you have lunar installed, you could run `./gradlew lunarRun`, and it will start lunar client with required
jvm arguments.

### Using it on other clients
Run `./gradlew agent`, and it will print out the jvm arguments you need to add. It should work in
clients mentioned below.

If you couldn't understand that, don't DM me for it as you are not supposed to use it.

### Notes
So if I release it, I can know what to write:

#### How to check the status of injection
You can look at left bottom corner of the screen, there should be a colored 5 pixels square.
If it's red, try going to MultiPlayer menu and wait for a while until it becomes green. Try following steps:
- Go multiplayer menu
- Add a server to servers list
- Join a single player game

Wait for at least 10 seconds, if it becomes green then you are free to join servers

If it's green, it means that everything has been mapped, and the square will only appear for 20 more seconds

If you saw nothing **from the beginning**, it means that the client is not supported at all. Keep in mind that
if everything is mapped, the square will also disappear 20 seconds after the green square 


![](https://imgur.com/cFfpDNh.png)

(The green square)

### Known Supported Clients
#### 1.8
1. Vanilla
2. Optifine
3. Forge (will 100% work without mixin, may work with some mixin)
4. Lunar (The client that's being used for the development)
