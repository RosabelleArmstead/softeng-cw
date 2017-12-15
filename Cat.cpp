#include "Cat.h"

// Constructor to allow a Cat to be instantiated. Passes six of the attributes
// to the base class Animal to be saved. 
Cat::Cat(string name, string breed, string colour, string earType,
         string height, string tailColour, const Cat* father, const Cat* mother)
        : Animal(name, breed, colour, earType, height, tailColour),
          pFather(father), pMother(mother) { }
