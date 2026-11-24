# Minecart Orbital 1.0.0

A Paper/Spigot plugin that rains TNT minecarts from the sky.

## Command

`/minecartorbital [count] [radius]`

- `count`: number of TNT minecarts (default 20, max 200)
- `radius`: spread around you in blocks (default 10, max 50)

Permission: `minecartorbital.use` (default: OP)

## Building the jar with GitHub

1. Create a new GitHub repository.
2. Upload everything in this folder, including the hidden `.github` folder, `pom.xml` and `src`.
3. Open the **Actions** tab. The "Build Minecart Orbital" workflow runs on every push (or click **Run workflow**).
4. When it finishes, open the run and download `MinecartOrbital-1.0.0` under **Artifacts**.
5. Unzip it and drop `MinecartOrbital-1.0.0.jar` into your server's `plugins` folder, then restart.

## Local build

```
mvn clean package
```

The jar appears in `target/MinecartOrbital-1.0.0.jar`.

Targets Paper 1.20.4 and Java 17.
