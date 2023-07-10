# PlayerMobCap Bukkit Plugin

PlayerMobCap is a Bukkit plugin for Minecraft servers that allows you to control the maximum number of hostile mobs that can spawn near a player.

## Features

- Set a maximum limit to the number of hostile mobs that can spawn near players.
- Customizable mob cap and search distance values.
- Provides commands to modify the mob cap and search distance during runtime.

## Commands

- `/mcpset <number>`: Set the maximum number of mobs that can spawn near a player.
- `/mcpdist <number>`: Set the search distance within which the plugin counts the number of mobs.

Both of these commands require corresponding permissions: `mobcap.set` for `/mcpset` and `mobcap.dist` for `/mcpdist`.

## Configuration

The plugin loads the mob cap and search distance values from a configuration file. If the configuration file does not exist, it will be created automatically with default values (64 for mob cap and 128 for search distance).

## Installation

1. Download the plugin JAR file from the releases tab and place it in your server's plugins directory.
2. Restart your server. The plugin should load automatically and create a configuration file in the plugin's directory.
3. Customize the mob cap and search distance by editing the configuration file or using the provided commands.

## Building

To build the plugin from source, you need to have Maven and JDK installed. Clone the repository and run `mvn package` in the repository root. The built JAR file will be placed in the `target` directory.

## License

PlayerMobCap is open-source software released under the MIT license. See the LICENSE file for details.
