# Stuff to do

**Nav:**

* A way to do the screensaver thing??
* A method to give a sensible, **unobstructed path to the Archon** (able to make gardeners, but not so stationary that it can be ambushed)
* A method for **running away from things** in general (toward allied soldiers??)

**ArchonBot:**

* Use Nav methods ^
* Think out when/how to dispense Gardeners

**GardenerBot:**

* Get organized strategy and coded method(s) for **planting trees** (in a grid, or funny shape, or in pods, etc.). Make it workable on lots of maps and unobstructive
* Get method that obviously **waters the trees** and keeps them alive correctly
* Set up structures/uhhh...control flow that manages **when/how to make which bots** (THIS IS KINDA BIG) -- think about **tanks** too? They are heavily encouraged with the new specs

**RootBot:**

* Revise what info every bot needs; keep an eye on byte codes (every update() method takes some bytecodes for sensing all that stuff)

**ScoutBot:**

* Deliberate over **strategy** -- their HP is super low now. Figure out where to make them go, when, what to broadcast, etc.
* Get some good **dodging** code down; they need to be able to survive and be useful

**SoldierBot:**

* Work on Gardener first!! (soldiers don't really get dispensed right now)
* Work on **combat**. Probably shoot triad/pentad shots in very close range to end fights quicker
* Make decisions about **pathing/when to do what job** (which ones will guard our archons, which will go destroy other archons/gardeners, which ones just kill other soldiers, real time decisions w/ global vars etc.)
* Figure out how to assure we're not hurting allies when we attack? 

**Lumberjack:**

* Make a lumberjack file...
* Figure out when/how to communicate we need to make Lumberjacks
* Decide where they should blaze paths and/or what they should attack/prioritize attacking

**TankBot:**

* Make a TankBot file...
* Have Lumberjacks follow it through thick woods to assist in letting other bots file through?
* Make whatever priorities are needed -- just go for archons and hit really hard or dent soldiers or what?
* When do we start making tanks?

**Team:**

* When to buy victory points and how many when we do (prices scale up as time progresses)
* System of protecting archons
* Figure out what all **Comms** will be used for and make methods for it -- it should be relatively easy, so it's a good idle-thinking choice.
* **OVERARCHING TEAM STRATEGY!!! How it will all work together, using most bytecodes allowed and getting the most efficient work done**
