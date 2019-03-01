# ShadowVillageCore

## Description

This is a plugin working on Shadow Village Minecraft Server written by Shengguang Bai.

The plugin is based on Minecraft 1.7.10.

Version: 1.0.1

## Library

* Spigot 1.7.10

* Citizens 2.0.13

## Instruction

### Shadow Man and Shadow Beast

At a specific minute every hour, shadow man or shadow beast will appear and teleport random times. 

* Shadow Man
Shadow man will teleport to the back of a random player. It never move or attack, and just gaze at target player.
During a random period, it will detect whether target player move or not. If target player move, it will give him a bad status effect or steal his items. When defeated, it will drop diamond block and shadow soul book.

* Shadow Beast
Shadow beast will teleport to nearby place of a random player. It will appear as random type of animal entities, such as sheep and cow. It will move and attack target player. If attacking on target player, it will give him a bad status effect or steal his items. When defeated, it will drop diamond block and shadow spirit book.

### Shadow Stone

A special currency which can only get by donating.

Players can spend shadow stone on:

* Get Minecraft money (need Vault plugin)
* Summon Boss
* Get equipment
* Get the permission of flying
* Get the permission of creative mod
* Get the permission of OP

## Commands

### Shadow Village Core

Shadow Village Core provides basic functions for the plugin. 

* /svc empower

  Nothing happens.

* /svc address [player]

  Get IP address of target player.
  
* /svc help

  Get a helper book.
  
* /svc debug

  Turn on or off debug mod.

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

* /svb lookup shadowstone (/svb l shadowstone)

  Look up amount of shadow stones in inventory.

* /svb gamble 1 [number] (/svb g 1 [number])

  Gamble on weapon. (use diamond as cost)

* /svb gamble 2 [number] (/svb g 2 [number])

  Gamble on helmet.

* /svb gamble 3 [number] (/svb g 3 [number])

  Gamble on chestplate.

* /svb gamble 4 [number] (/svb g 4 [number])

  Gamble on leggings.

* /svb gamble 5 [number] (/svb g 5 [number])

  Gamble on boots.

* /svb set damage [number]

  Set the damage of item in hand.
  
* /svb set name [name]

  Set the name of item in hand.

## Permissions

* svc.empower -- OP

* svc.address -- OP

* svc.help -- Everyone

* svc.debug -- OP

* sve.summon.shadowman -- OP

* svc.summon.shadowbeast -- OP

* svc.summon.shadowsoul -- OP

* svb.give.shadowstone -- OP

* svb.give.shadowsoulbook -- OP

* svb.lookup.shadowstone -- Everyone

* svb.gamble.weapon -- Everyone

* svb.gamble.helmet -- Everyone

* svb.gamble.chestplate -- Everyone

* svb.gamble.leggings -- Everyone

* svb.gamble.boots -- Everyone

* svb.set.damage -- OP

* svb.set.name -- OP
