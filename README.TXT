---------------------------
SUPER ITACHI STARLIGHTS
---------------------------

Created by Anne Zhang
DATE: 01/31/2023

---------------------------------------------------------------------------------------

GAME PREMISE:

You have been recruited as the sole protector and provider for your kingdom, 210Land!

Your mission today is to:
-travel Eastward in order to collect *gems* and *money* (aka affluence) for 210Land
-extinguish Enemy Starlights who come at you by throwing shurikens at them
 (Leaked enemies will cost the kingdom affluence!)
-avoid coming in physical contact with Enemies, as that will decrease your health

This game is similar to Super Mario Bros. It's set in a 2-D world, and you control a
main character who you generally should have running rightwards during gameplay.
Jump on blocks to collect items, and throw shurikens at Enemy Starlights to protect
yourself and your kingdom!

---------------------------------------------------------------------------------------

HOW TO PLAY:

Controls:
Right arrow key – move right
Left arrow key – move left
Up arrow key - jump
Spacebar – throw shuriken

Enemy details:
-Coming in contact with an Enemy will you lose you health
-A leaked Enemy will cost your kingdom 5 affluence
-Health will refill by itself over time

Item details:
Croissant – reloads you with 10 shurikens
Bill – adds to your kingdom's affluence a random amount between 1 and 10
Yellow gem = jubilation gem
Purple gem = prosperity gem
Red gem = harmony gem

Game ends when either of the following happens:
1. You have no health left
2. The kingdom affluence balance goes below zero

...so if you're doing well, just keep doing it forever!
See how many gems you can collect!

---------------------------------------------------------------------------------------

TIER 2:

1. Shooting in one or more directions
	-Main character throws shurikens

2. Keep track of items hit with “bullet” type objects and display on the screen (maybe)
	-# of shurikens remaining is displayed (I'm not sure if this counts)

3. Gravity AND jumping!
	-Main character can jump or run off a platform and be affected by gravity

4. Scrolling backgrounds
	-Hills scroll

5. Power ups and/or bullet reloads and/or drop off points for collected items, etc.
	-shuriken reloads
	-bills/affluence
	-gems

---------------------------------------------------------------------------------------

TIER 3:

1. Constructors that take parameters
	-Gem constructor (gem type and whether it's acc part of the game)
	-Shuriken constructor (direction of travel)

2. Using and making accessors and mutators in classes
	-MyWorld: getPlayer()A, getHealthBar()A
	-Bill, Gem, Reload: isOnBlock()A
	-Enemy: returnHasContactedPlayer()A, wasContacted()M
	-Gem: hover()M, getGemType()A
	-Player: enemyLeaked()M, isAtCenter()A
	
3. Changing the transparency of an image
	-Main character's transparency is changed for a time after being contacted by an enemy

4. Animations using arrays
	-Enemy
	-Player
	-Shuriken

---------------------------------------------------------------------------------------

SELF-ASSESSMENT:

96% overall
I used variables in new and interesting ways, I used arrays of images effectively, my code is
decently efficient, there is much interaction between classes, there are minimal bugs (I think),
the game is beyond the programming complexities of class tutorials, my commenting is thorough,
classes/methods/variables are named appropriately, proper indentation everywhere, plays like a
real game (a basic one though). It's not a 100%-project I think mostly b/c it could use some
better visuals (e.g., for the text), more than one World, and maybe something like increasing
difficulty over time—things that would make it more aesthetic and/or make the fun last for longer.