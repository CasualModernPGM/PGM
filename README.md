PGM (CMP fork)
===
This fork's changes for our private server:
 
## `execute` action
Use any command you want. For example:

```xml
<actions>
    <action scope="player">
        <!-- Set a player's armor to red with a gold trim -->
        <execute command="item replace entity #player# armor.chest with leather_chestplate[dyed_color=11141120,trim={material:'minecraft:gold',pattern:'minecraft:snout'},unbreakable={}]"/>
    </actions>
    
    <action scope="match">
        <!-- Summon a creeper with NBT -->
        <execute command="summon minecraft:creeper 5 0 10 {Invulnerable:1b}"/>
    </action>
</actions>
```

As of February 13th, 2026, `<replacements/>` are now supported. Also contains two built-in special replacements: `#player#` (for the player triggering the action) and `~ ~ ~` for their coordinates.

```xml
<actions>
    <action scope="player">
        <execute command="say #player#'s x is: {variable}">
            <replacements>
                <decimal id="variable" value="player.x"/>
            </replacements>
        </execute>
    </action>
</actions>
```

Note that this will only work on the modern platform (which is the one we're using), as the vanilla `execute` command did not exist in 1.8.

Map example: [Tartarus](https://github.com/CasualModernPGM/ModernMaps/blob/master/maps/tension/tartarus/map.xml)

## Mob ownership
Disabled mob ownership when using/dispensing spawn eggs.

## `riding` filter
Filter if a player is riding an entity.

## `lock-mounts` module
Disable the ability to crouch to dismount an entity.

Usage:

```xml
<lock-mounts>on</lock-mounts>
```

## `quake` module
Makes `Netherite Hoe` items shoot ray projectiles when right-clicked and gives players velocity when left-clicked. A quick hard-coded gamemode module in the style of the old `<ghostsquadron/>` module from ProjectAres. Could see better integration/expansion if it sees use, but right now it was good enough.

Usage:

```xml
<quake/>
<kits>
    <kit id="example-kit">
        <item material="netherite hoe" name="`rRailgun"/>
    </kit>
</kit>
```

## `silent` attribute for shopkeepers
```xml
<shopkeepers silent="true">
    <shopkeeper name="`5Purple `7Item Shop" shop="purple-item-shop">
        <point yaw="0" pitch="10">-88.5,13,-103.5</point>
    </shopkeeper>
</shopkeepers>
```

## Other
- Avoid sending "Playing \<map> by \<author>" when no players are on.
- Allow spawning command-summoned mobs by default (spawn cause is different in 1.13+).
- Adds modern event filtering. Currently used to disable underwater forced dismounts when riding entities.
- Allows setting arrays in `/variable set`. Usage: `/variable set variable[index] 1`
- Track when a player kills another via an End Crystal explosion
