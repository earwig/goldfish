goldfish
========

**goldfish** (GoLdfish) is a simple Game of Life implementation in Java.

Setup
-----

Clone the project with:

    git clone https://github.com/earwig/goldfish.git goldfish

The simplest way to build and run it is with
[Apache Ant](http://ant.apache.org/):

    cd goldfish
    ant run

Alternatively, you can compile it using `javac` and run it with `java`:

    cd goldfish/src
    javac edu/stuy/goldfish/*.java edu/stuy/goldfish/rules/*.java
    java edu.stuy.goldfish.Goldfish

Usage
-----

When you start goldfish, you will be presented with a
[Gosper glider gun](http://www.conwaylife.com/wiki/Gosper_glider_gun) running
under the standard
[Conway's Game of Life](http://en.wikipedia.org/wiki/Conway's_Game_of_Life)
rules. The buttons allow you to pause/unpause the simulation, reset it to the
default glider gun (or whatever the default pattern is for the chosen
algorithm), randomize the grid, and clear it completely. The slider allows you
to set the maximum FPS that the simulation will run at.

By clicking on the screen and dragging your mouse, you can set the state of
patches directly. This works best when paused. "Painting" over living patches
will set their state as dead. In automata with muliple states, right-clicking
will paint in a different state than left-clicking.

The *algorithms* menu allows you to switch to different cellular automaton
rulesets.

### Algorithms

* **Conway**: a standard
  [Conway's Game of Life](http://en.wikipedia.org/wiki/Conway's_Game_of_Life)
  simulator.
* **Conway4**: equivalent to Conway, but patches use their
  [von Neumann neighborhood](http://en.wikipedia.org/wiki/Von_Neumann_neighborhood)
  instead of their
  [Moore neighborhood](http://en.wikipedia.org/wiki/Moore_neighborhood).
* **Life Without Death**: equivalent to Conway, but patches do not die.
* [**Brian's Brain**](http://en.wikipedia.org/wiki/Brian's_Brain): a
  three-state automaton with an "alive", "dying", and "off" state. Generally
  more chaotic than Conway with interesting patterns.
