Student name: Dmytro
Student number: Malevskyi

Now complete the statements below for each level you wish to be marked. Replace all text in square brackets.

LEVEL ONE

My code demonstrates inheritance in the following way...

I have a superclass called [Character]

This superclass is extended into at least two subclasses called [Paladin, Thief and Preacher (Preacher has no instance variable by design, that is why it is not described below us a subclass)]

For each of the named subclasses complete the following...

Subclass 1.

Subclass [Paladin] extends the superclass by adding at least one property and its getters and setters. The name(s) of the added properties are [chanceToBlock]

These/this new properties/property are used by the subclass in the following way: [it is used everytime a paladin is attacked so the attack could be blocked fully. Only paladins are able to block an attack as it is their passive perk. It is used in Paladin.java in lines (9-20).]

Subclass [Paladin] extends the superclass by overriding the following methods (there must be at least one): [getAttacked in lines (9-20)]

These overridden methods are used in the working code in the following places: [in Character.java in lines (26-28) in the attack funcion. The attack funcion is called in Game.java in lines: 89, 376 and 384]

Subclass 2.

Subclass [Thief] extends the superclass by adding at least one property and its getters and setters. The name(s) of the added properties are [chanceToAttackBack]

These/this new properties/property are used by the subclass in the following way: [it is used everytime a thief is attacked so that the thief could possibly attack back. Only thiefs are able to attack back as it is their passive perk. It is used in Thief.java in lines (9-20)]

Subclass [Thief] extends the superclass by overriding the following methods (there must be at least one): [getAttacked in lines (9-20).]

These overridden methods are used in the working code in the following places: [in Character.java in lines (26-28) in the attack funcion. The attack funcion is called in Game.java in lines: 89, 376 and 384]


LEVEL TWO

Polymorphism consists of the use of the Substitution principle and Late Dynamic binding.

In my code, polymorphism is implemented in at least two places…

Example 1.

The substitution principle can be seen in use in [Game.java in lines (31-41)]. The name of the superclass used in this example is [Character] and the subclasses used are [Paladin, Thief and Preacher].

Late dynamic binding can be seen in [Game.java in lines: 89, 376 and 384].

[This allows me to create different characters that will have different behavior when taking damage based on its class. Polymorphism allows me to easily loop through the ArrayList of this different characters and call attack() method.]

Example 2.

The substitution principle can be seen in use in [Game.java in lines (49-52)]. The name of the superclass used in this example is [Item] and the subclasses used are [PermanentItem and TemporaryItem].

Late dynamic binding can be seen in [Game.java in line 133].

[This allows me to create items that have immediate effect and those whose effect lasts for some rounds. With polymorphism I can put them into the same ArrayList called inventory and call use() funcion.]
