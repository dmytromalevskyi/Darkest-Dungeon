# Darkest-Dungeon
Oversimplified version of the game Darkest Dungeon that was written for my university course to learn about OOP and polymorphism. It is a turn base game where the player needs to explore the map and fight off enemies to get to the end of the dungeon. The game can be enjoyed on a terminal but a really simple GUI is on the way.

Features
-------------------------
* 3 different classes each with a unique ability
* Most classes have a unique passive ability
* Inventory with items that give permanent or time limited effect (4 items currently in the game)


In development
-------------------------
* At least 2 more classes
* (Really simple) GUI
* Map with scattered items all over it (progressively explored)
* More unique item

Example of a part of the game
-----------------------------
```text
<<<<<<<<<<<<<<<<<<<<<<<< Your Turn >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
========== Current Character (Paladin) [1] ==========

------------------------------------------------------------
	[1]	[2]	[3]		[1]	[2]	[3]
Health	90	60	60		100	55	70
Damage	6	20	6		6	18	7
Agility	1	9	2		1	5	3
Defence	11	0	3		12	0	3
------------------------------------------------------------

Enter a number for what you want to do: [1] Basic attack, [2] Special ability, [3] Open Inventory, [4] Skip: 3
<+=----------------------------------------=+>
The item inside your bag is/are: 
[1] Red Potion (4)
Adds 8 point to health of a chosen character.
[2] Nasty Poison (2)
Takes 5 point from health of a chosen enemy. Note: it ignores any defence points.
[3] Fishing Net (1)
Takes away 5 point/s of agility from a given enemy for 3 round/s.
[4] Hydro Acid (3)
Takes away 10 point/s of defence from a given enemy for 3 round/s.
<+=----------------------------------------=+>
Enter a number of the item you want to use (0 to cancel): 1
Enter a number for the character you want to use Red Potion item on (0 to cancel): 3

------------------------------------------------------------
	[1]	[2]	[3]		[1]	[2]	[3]
Health	90	60	68		100	55	70
Damage	6	20	6		6	18	7
Agility	1	9	2		1	5	3
Defence	11	0	3		12	0	3
------------------------------------------------------------

Enter a number for what you want to do: [1] Basic attack, [2] Special ability, [3] Open Inventory, [4] Skip: 2
Name of the ability: The Blessing of The King
Description: Adds 10.0% of this character's health to defence of the chosen character for 4 round/s. Defence buff works out to be 9.
Enter a number for the character you want to use The Blessing of The King ability on (0 to cancel): 2
========== Current Character (Thief) [2] ==========

The Blessing of The King will last for 4 round/s more.

------------------------------------------------------------
	[1]	[2]	[3]		[1]	[2]	[3]
Health	90	60	68		100	55	70
Damage	6	20	6		6	18	7
Agility	1	9	2		1	5	3
Defence	11	9	3		12	0	3
------------------------------------------------------------

Enter a number for what you want to do: [1] Basic attack, [2] Special ability, [3] Open Inventory, [4] Skip: 1
Enter a number for the enemy you want to attack (0 to cancel): 2

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
20 damage delt!
0 damage blocked by the victim.
Attacker had no chance to miss.
New victim's health 55 ~~> 35
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

========== Current Character (Preacher) [3] ==========

------------------------------------------------------------
	[1]	[2]	[3]		[1]	[2]	[3]
Health	90	60	68		100	35	70
Damage	6	20	6		6	18	7
Agility	1	9	2		1	5	3
Defence	11	9	3		12	0	3
------------------------------------------------------------

Enter a number for what you want to do: [1] Basic attack, [2] Special ability (cooldown: 3 round/s), [3] Open Inventory, [4] Skip: 4
Turn skipped.
<<<<<<<<<<<<<<<<<<<<<<<< Enemies' Turn >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
========== Current Enemy (Paladin) [1] ==========
...
  ```
