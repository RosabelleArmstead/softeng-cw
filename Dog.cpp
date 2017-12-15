#include "Dog.h"

// Constructor to allow a Dog to be instantiated. Passes six of the attributes
// to the base class Animal to be saved.
Dog::Dog(string name, string breed, string colour, string earType,
         string height, string tailColour, const Dog* father, const Dog* mother)
        : Animal(name, breed, colour, earType, height, tailColour),
          pFather(father), pMother(mother) { }
