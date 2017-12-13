#!/bin/bash
g++ -Wall -c Main.cpp
g++ -Wall -c Animal.cpp
g++ -Wall -c Cat.cpp
g++ -Wall -c Horse.cpp
g++ -Wall -c Dog.cpp
g++ -Wall Main.o Animal.o Cat.o Horse.o Dog.o -o Program
./Program
