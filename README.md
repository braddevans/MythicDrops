# MythicDrops
MythicDrops is a Bukkit plugin that brings an RPG-like system of drops to Minecraft. Items can
have unique names, lore, and enchantments added randomly to them through a tier system.

![Bintray](https://img.shields.io/bintray/v/pixeloutlaw/mythicdrops/MythicDrops?style=flat-square)

forked from:
http://github.com/PixelOutlaw/MythicDrops

## How To Get It (Server Runners)
https://www.spigotmc.org/resources/mythicdrops.6114/

## How To Get It (Developers)

## How to Get
### Maven
Add the Bintray repository to your POM:
```
<repositories>
    <!-- other repositories... -->
    <repository>
        <id>mythicdrops-bintray</id>
        <url>https://dl.bintray.com/pixeloutlaw/mythicdrops</url>
    </repository>
</repositories>

<dependencies>
    <!-- other dependencies... -->
    <dependency>
        <groupId>io.pixeloutlaw</groupId>
        <artifactId>mythicdrops</artifactId>
        <version>x.y.z</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```
