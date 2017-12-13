#!/bin/bash
g++ -std=c++11 -Wall -c Main.cpp
g++ -std=c++11 -Wall -c Animal.cpp
g++ -std=c++11 -Wall -c Cat.cpp
g++ -std=c++11 -Wall -c Horse.cpp
g++ -std=c++11 -Wall -c Dog.cpp
g++ -std=c++11 -Wall Main.o Animal.o Cat.o Horse.o Dog.o -o Program
./Program
