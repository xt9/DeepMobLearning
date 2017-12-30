# 1.12.2

### 1.0 (RC)
- Added Dragon & Slime Data models
- Added Pristine matter and resulting items for the Dragon & Slime model
- Added configuration options for RF/t costs on the data models.
- Added configuration options for pristine matter chance.
- Added configuration option for which side the Deep Learner overlay GUI should appear on.
- Added confiugration options for the required kills to tier up the models (and the kill multipliers).
- Added a Gui configuration screen.
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