#include "Dog.h"

Dog::Dog(string name, string breed, string colour, string earType,
         string height, string tailColour, Dog* father, Dog* mother)
        : Animal(name, breed, colour, earType, height, tailColour),
          pFather(father), pMother(mother) { }
