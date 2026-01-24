PGM (CMP fork)
===
Currently, this fork just adds a fun action for our private server - an `execute` action to use any command we want.

For example:
```xml
<actions>
    <action scope="player">
        <!-- Set a player's armor to red with a gold trim -->
        <execute command="item replace entity {player} armor.chest with leather_chestplate[dyed_color=11141120,trim={material:'minecraft:gold',pattern:'minecraft:snout'},unbreakable={}]"/>
    </actions>
    
    <action scope="match">
        <!-- Summon a creeper -->
        <execute command="summon minecraft:creeper 5 0 10"/>
    </action>
</actions>
```

Note that this will only work on the modern platform (which is the one we're using), as the vanilla `execute` command did not exist in 1.8.

Map example: [Tartarus](https://github.com/CasualModernPGM/ModernMaps/blob/master/maps/tension/tartarus/map.xml)

Original PGM description
===

The original PvP Game Manager for Minecraft.

Overview
--------

Back in 2011, a Minecraft map creator named dewtroid released a PvP map called [Airship Battle.](https://www.youtube.com/watch?v=3dLo8ytygWs) At the time, there were no comprehensive Bukkit plugins to manage PvP matches, so everything was done manually. Then, two young developers named [Apple](https://github.com/tonybruess) and [Anxuiz](https://github.com/anxuiz) came along and created the first version of "PGM" (also known as **P**vP **G**ame **M**anager) to automate the process of playing PvP matches. Later, they would establish the popular Minecraft server network, `Overcast Network` (also know as `oc.tc`). After the network went through a cycle of hyper-growth, stabilization, and eventual closure in 2016, its [plugins](https://github.com/OvercastNetwork/ProjectAres) were open sourced for the community to enjoy.

This project is an earlier fork of those plugins with three major changes:

1. Using [Minecraft 1.8](https://github.com/Electroid/SportPaper), the biggest ask by the community.
2. No backend, website, or API to make server hosting more cost effective.
3. No dependency injection to make contributing more accessible.

Documents
---------

1. [`LICENSE`](LICENSE) - any forks or modifications to this project must be kept public.
2. [`CODE_OF_CONDUCT`](docs/CODE_OF_CONDUCT.md) - guidelines that contributors and server owners must agree to.
3. [`RUNNING`](docs/RUNNING.md) - how to host and run a PGM server.
4. [`CONTRIBUTING`](docs/CONTRIBUTING.md) - how to build, compile, and submit changes to the project.


Governance
----------

The lead maintainer of this project is [Electroid](https://github.com/Electroid), a former administrator and software developer at [`oc.tc`](https://oc.tc/). As the project grows, we'll scale the governance model to meet those needs.
