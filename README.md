# ShadowVillageCore

## Description

This is a plugin working on Shadow Village Minecraft Server written by Shengguang Bai.

The plugin is based on Minecraft 1.7.10.

Version: 1.0.2

## Library

* Spigot 1.7.10

* Citizens 2.0.13

* ProtocolLib 3.6.4

## Instruction

### Shadow Entity

At a certain time, shadow man or shadow beast will appear and teleport random times. 

* Shadow Man
Shadow man will teleport to the back of a random player. It never move or attack, and just gaze at target player.
During a random period, it will detect whether target player move or not. If target player move, it will give him a bad status effect or steal his items. When defeated, it will drop diamond block and shadow soul book.

* Shadow Beast
Shadow beast will teleport to nearby place of a random player. It will appear as random type of animal entities, such as sheep and cow. It will move and attack target player. If attacking on target player, it will give him a bad status effect or steal his items. When defeated, it will drop diamond block and shadow spirit book.

### Shadow Stone

A special currency which can only get by special activity.

Players can use shadow stone on:

* Get Minecraft money (need Vault plugin)
* Summon Boss
* Get powerful equipment
* Get the permission of flying
* Get the permission of creative mod
* Get the permission of OP

## Commands

### Shadow Village Core

Shadow Village Core provides basic functions for the plugin. 

* /svc version

  Get current version of server.
  
* /svc help

  Get a helper book.

* /svc empower

  Nothing happens.
  
* /svc debug

  Turn on or off debug mod.
  
* /svc test
  
  For testing if needed.

### Shadow Village Ecology
Shadow Village Ecology provides functions about entities.

* /sve summon shadowman

  Summon shadow man.

* /sve summon shadowbeast

  Summon shadow beast.
  
* /sve summon shadowsoul

  Summon shadow soul.

### Shadow Village Black Market
Shadow Village Black Market provides functions about items.

* /svb give shadowstone [number]

  Give self shadow stones.
  
* /svb give shadowsoulbook

  Give self a shadow soul book.
  
* /svb give shadowspiritbook

  Give self a shadow spirit book.
  
* /svb give shadowlordbooktop

  Give self the top version of shadow lord book.
  
* /svb give shadowlordbookbottom

  Give self the bottom version of shadow lord book.

* /svb give serverequipment

  Give self all server equipments.
  
* /svb set attack [number]

  Set the attack damage of item in hand.
  
* /svb set health [number]

  Set the health buff of item in hand.
  
* /svb set name [name]

  Set the name of item in hand.

* /svb lookup shadowstone (/svb l shadowstone)

  Look up amount of shadow stones in inventory.
  
* /svb lookup possibilities [1-6] [number] (/svb l possibilities [1-6] [number])

  Look up possibilities of gamble items.

* /svb gamble [1-6] [number] (/svb g [1-6] [number])

  Gamble on sword(1), bow(2), helmet(3), chestplate(4), leggings(5), boots(6). (use diamond as cost)

* /svb bound

  Bound an item to self.
  
### Shadow Village Support
Shadow Village Support provides functions for supporting.

* /svs address [player]

  Get IP address of target player.
  
* /svs addplayer [name]

  Add a fake player to tab list.
  
* /svs removeplayer [name]

  Remove a fake player from tab list.
  
* /svs synchat true

  Begin to receive message from group chat.

* /svs synchat false

  Stop to receive message from group chat

## Permissions

* svc.version -- Everyone

* svc.help -- Everyone

* svc.empower -- OP

* svc.debug -- OP

* sve.summon.shadowman -- OP

* sve.summon.shadowbeast -- OP

* sve.summon.shadowsoul -- OP

* svb.give.shadowstone -- OP

* svb.give.shadowsoulbook -- OP

* svb.give.shadowspiritbook -- OP

* svb.give.shadowloardbooktop -- OP

* svb.give.shadowloardbookbottom -- OP

* svb.give.serverequipment -- OP

* svb.set.damage -- OP

* svb.set.health -- OP

* svb.set.name -- OP

* svb.lookup.shadowstone -- Everyone

* svb.lookup.possibilities -- Everyone

* svb.gamble.sword -- Everyone

* svb.gamble.bow -- Everyone

* svb.gamble.helmet -- Everyone

* svb.gamble.chestplate -- Everyone

* svb.gamble.leggings -- Everyone

* svb.gamble.boots -- Everyone

* svb.bound -- Everyone

* svs.address -- OP

* svs.addplayer -- OP

* svs.removeplayer -- OP

* svs.synchat -- Everyone

## Changelog

### 1.0.2

* Fix bugs.

* Improve user experience.

* Add support system.

### 1.0.1

* Fix tons of bugs.

* Improve user experience.

* Add shadow soul.

### 1.0.0

* Add core system, ecology system, and black market system.

* Add shadow man and shadow beast in ecology system.

* Add gamble system in black market system.
