# ShadowVillageCore

## Description

This is a plugin working on Shadow Village Minecraft Server written by Shengguang Bai.

The plugin is based on Minecraft 1.7.10.

## Library

* Spigot 1.7.10

* Citizens 2.0.13

## Instruction

### Shadow Man and Shadow Spirit

At a specific minute in every hour, shadow man or shadow spirit will appear and teleport random times. 

* Shadow Man
Shadow man will teleport to the back of a random player. It never move or attack, and just gaze at target player.
During a random period, it will detect whether target player move or not. If target player move, it will give him a bad status effect or steal his items.

* Shadow Spirit
Shadow Spirit will teleport to nearby place of a random player. It will appear as random type of animal entities, such as sheep and cow. It will move and attack target player. If attacking on target player, it will give him a bad status effect or steal his items.

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

* /sve spawn shadowman

  Spawn shadow man.

* /sve spawn shadowspirit

  Spawn shadow spirit.

### Shadow Village Black Market
Shadow Village Black Market provides functions about items.

* /svb give shadowstone [number]

  Give self shadow stones.

* /svb lookup shadowstone

  Look up amount of shadow stones in inventory.

* /svb gamble weapon [number]

  Gamble on weapon. (use diamond as cost)

* /svb gamble helmet [number]

  Gamble on helmet.

* /svb gamble chestplate [number]

  Gamble on chestplate.

* /svb gamble leggings [number]

  Gamble on leggings.

* /svb gamble boots [number]

  Gamble on boots.

* /svb set damage [number]

  Set the damage of item in hand.

## Permissions

* svc.empower -- OP

* svc.address -- OP

* svc.help -- Everyone

* svc.debug -- OP

* sve.spawn.shadowman -- OP

* svc.spawn.shadowspirit -- OP

* svb.give.shadowstone -- OP

* svb.lookup.shadowstone -- Everyone

* svb.gamble.weapon -- Everyone

* svb.gamble.helmet -- Everyone

* svb.gamble.chestplate -- Everyone

* svb.gamble.leggings -- Everyone

* svb.gamble.boots -- Everyone

* svb.set.damage -- OP
