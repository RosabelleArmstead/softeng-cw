// Dog.cpp
#include "Dog.h"

// Constructor for Dog. Data members assigned using member initialisation list and base class constructor.
Dog::Dog(string name, string breed, string colour, string earType, string height, string tailColour,
         const Dog* father, const Dog* mother)
        : Animal(name, breed, colour, earType, height, tailColour), pFather(father), pMother(mother) { }
