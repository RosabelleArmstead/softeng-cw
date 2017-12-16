// Cat.cpp
#include "Cat.h"

// Constructor for Cat. Data members assigned using member initialisation list and base class constructor.
Cat::Cat(string name, string breed, string colour, string earType, string height, string tailColour,
         const Cat* father, const Cat* mother)
        : Animal(name, breed, colour, earType, height, tailColour), pFather(father), pMother(mother) { }
