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

## `purchaseable` and `click-action` attributes for shop items

`purchaseable="false"` allows you to make unpurchaseable items in shops.
`click-action="<action>"` allows you to execute an action when you click an item in a shop.

```xml
<shops>
    <shop id="main-menu">
        <category id="main" name="Main" material="knowledge book">
            <!-- Unpurchaseable item -->
            <item material="stone" name="`rUnpurchaseable Item" amount="1" purchasable="false"/>
            <!-- Buy item and trigger action (show message) -->
            <item material="cobblestone" name="test" amount="1" price="0" click-action="sample-text"/>
            <!-- Trigger action (show some text) with no item -->
            <item material="dirt" name="`rShow some text" amount="1" purchasable="false" click-action="sample-text"/>
            <!-- Open another shop -->
            <item material="chest" name="`rAnother menu" amount="1" purchasable="false" click-action="open-another-menu"/>
        </category>
    </shop>
    <shop id="another-menu">
        <category id="another-menu" name="Another menu" material="knowledge book">
            <item material="diamond" amount="1" price="0"/>
        </category>
    </shop>
</shops>
<actions>
    <action id="sample-text" scope="player">
        <message text="Sample text!"/>
    </action>
    <action id="open-another-menu" scope="player">
        <open-shop shop="another-menu"/>
    </action>
</actions>
```

This allows you to essentially have menus.
You can combine multiple menus or shops by selling an item with an `open-shop` click-action!

## Other
- Avoid sending "Playing \<map> by \<author>" when no players are on.
- Allow spawning command-summoned mobs by default (spawn cause is different in 1.13+).
- Adds modern event filtering. Currently used to disable underwater forced dismounts when riding entities.
- Allows setting arrays in `/variable set`. Usage: `/variable set variable[index] 1`
- Track when a player kills another via an End Crystal explosion
