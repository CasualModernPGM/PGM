PGM (CMP fork)
===
This fork's changes for our private server:
 
## `execute` action
Use any command you want. For example:

```xml
<actions>
    <action scope="player">
        <!-- Set a player's armor to red with a gold trim -->
        <execute command="item replace entity {player} armor.chest with leather_chestplate[dyed_color=11141120,trim={material:'minecraft:gold',pattern:'minecraft:snout'},unbreakable={}]"/>
    </actions>
    
    <action scope="match">
        <!-- Summon a creeper with NBT -->
        <execute command="summon minecraft:creeper 5 0 10 {Invulnerable:1b}"/>
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

## Misc.
- Avoid sending "Playing \<map> by \<author>" when no players are on.
- Allow spawning command-summoned mobs by default (spawn cause is different in 1.13+).
- Adds modern event filtering. Currently used to disable underwater forced dismounts when riding entities.
