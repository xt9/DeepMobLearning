# 1.12.2

### 2.2.0
- Removed GuideAPI guide
- Added Patchouli guide (Soft dependency)
- Reworked Deep Learner Recipe to require less dyes
- Reworked Soot-covered Machine Casing recipe to require less obsidian.
- Reworked Loot Fabricator recipe to require less dye types.
- Added a fallback book that tells the player to install Patchouli if it's not loaded
- Added available attunements to the tooltip of the Trial Key

### 2.1.1
- Fixed a connection crash on the client.
- The area checked for participants when a Trial starts is now larger (required arena size is unchanged)
- Straying too far from the Trial will now remove you from the participant list
- Merged PR #14 from tommyTT, streamlining associating mobs with Data Models. Mobs can now be included into any given data model through the config.

### 2.1.0
- Redesigned the Thunderdome affix to spawn in creatures of thunder (Charged creepers & Witches)
- Creatures in the Trial now spawn a smidge closer to the middle of the room
- Added sounds to Trials
- The trial keystone now checks for nearby players on each new wave, so other players can join in late. (There's no extra reward for being more players but this will display the HUD for players that wasn't present at the start)
- Fixed an issue where Players would get stuck in place after dying in a Trial
- Fixed an issue where the trial kept going even if all participants has died.
- Fixed an issue where the trial started on wave 2 if the player died during a wave countdown in a previous trial.
- Slightly nerfed the damage ceiling of the Glitch Sword

### 2.0.6
- Fixed guidebook recipe conflict with bm/woot (#11)

### 2.0.5
- Added config options for horizontal and vertical 'spacing' for the DeepLearner overlay. These values will be relative to the selected overlay corner.

### 2.0.4
- Fixed thunderdome affix crash when no entity is in the area
- Fixed a bug where some foreground elements was not drawn in the Loot Fabricator
- Added a click area for the Loot Fabricator to remove the selected item

### 2.0.3
- DeepLearner should no longer play re-requip animation when nbt data change
- Simulation Chamber now consumes Polymer clay on simulation start rather then on end (Prevent success fishing)
- All text animation messages in the Simulation Chamber should now be animated at a similiar pace on all framerates (Lower framerates animated painfully slow before)
- Cleaned up Container class for Simulation Chamber

### 2.0.2
- Added Guidebook chapter for the BloodMagic Addon
- Fixed Jei Categories clipping the new border style
- Changed nether star transmutation recipe to require wither skeleton skulls

### 2.0.1
- Updated to forge 14.23.4.2703
- Stopped using a deprecated register function for TE's (Got deprecated in 2703)
- Added additional tooltip to deep learner to make them aware of the HUD
- Prevent opening the tile entities when sneaking
- Added a Guidebook (With Guidebook API)
- Changed the Ender pearl transmutation recipe to be more expensive.
- Added OreDict in recipes where applicable.
- Added Ender IO enderman heads to the pristine enderman matter table. (Remove previous config to regenerate)
- Actually added a recipe for the Tinker Slime Data model... Oopsie
- Actually added a recipe for the Shulker Data model... Oopsie
- Actually added a recipe for the Guardian Data model... Oopsie

### 2.0 - Trials
Fight your way through combat trials, gaining additional data to your data models and
receiving rewards upon completion.
- Added The trial key, used in the Trial keystone.
- Added the Trial Keystone. The heart of trials.
- Added a Armor set with a set bonus that's relevant for the mods progression path.
- Added a Sword that helps you level models faster.
- Added data model for Shulkers.
- Added data model for Guardians.
- Added config option to add max tier specific rewards for trials.
- Disabled flight during trials.
- Renamed Chared redstone to Soot-covered redstone.
- Soot covered redstone is now crafted by left clicking blocks of coal with a piece of redstone.
- Data models is now crafted using Soot-covered redstone, instead of vanilla redstone.
- Added a config option to disable the Redstone/Block of coal interaction.
- Added JEI integration for the trials, so added rewards are easily found.
- Added JEI integration for the simulation chamber.
- Removed some information tabs in JEI since the new categories are self explanatory.
- Retextured a few pristine matters that needed some love (zombie/witherskeleton/thermal).



### 1.2
- Added more configurable positions for the Experience gui (The bars that show up when holding the deep learner)
- Added support for the Tinker's construct blue slime.

### 1.1
- Flattened item structure before the 1.13 removal of metadata (Breaking change)
- New machine, The Loot Fabricator. All the existing pristine -> item recipes was moved to this machine
- Configurable loot tables for the Loot Fabricator.
- Added JEI support for the Loot Fabricator.
- Added support for Thermal Expansion mobmetas
- Added support for Twilight forest mobmetas (Split into 4 different zones)
- Fixed server start crashes.
- Added proper creative tab
- Made the Creeper, Skeleton & Zombie data models easier to acquire (No longer crafted with skulls)
- Added a recipe for chorus fruit (Extraterrestrial matter)
- Altered the Simulation chamber recipe to take a regular glass pane(Less microcrafting)
- Blank data models are now stackable
- Added some transmutational recipes
- Changed the polymer clay recipe (again)
- The different realm matters (Ow/Hellish/Terrestrial/Twilight) now grant experience when consumed.

### 1.0 (RC)
- Added Dragon & Slime Data models
- Added Pristine matter and resulting items for the Dragon & Slime model
- Added configuration options for RF/t costs on the data models.
- Added configuration options for pristine matter chance.
- Added configuration option for which side the Deep Learner overlay GUI should appear on.
- Added confiugration options for the required kills to tier up the models (and the kill multipliers).
- Added a Gui configuration screen.living_matter_overworldian.json
- Added a Factory class for mobmetadata and decoupled a lot of hardcoding from ItemMobChip
- Refactored MobMetaData classes to do their init in the factory

### 0.10
- Buffed the pristine spider matter (spider eyes)
- Added creative item for data model leveling
- The deep learner now only opens while not holding shift or ctrl
    (to avoid screwing up sneaking/block placing scenarios)
- Bumped the max input rf/t for the Simulation chamber to 25k rf/t
- Bumped the internal rf buffer of the Simulation chamber to 2 million
- Slightly changed the Polymer clay recipe

### 0.9.0
- Added the remaining textures needed
- Mob chips now have different rf/t simulation costs in the simulation chamber
- Added charred redstone/plates to avoid mod recipe collisions
- Added more transmution recipes
- Data model leveling tweaked to be easier