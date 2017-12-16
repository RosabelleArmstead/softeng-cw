// Horse.cpp
#include "Horse.h"

// Constructor for Horse. Data members assigned using member initialisation list and base class constructor.
Horse::Horse(string name, string breed, string colour, string earType, string height, string tailColour,
             const Horse* father, const Horse* mother)
            : Animal(name, breed, colour, earType, height, tailColour), pFather(father), pMother(mother) { }
