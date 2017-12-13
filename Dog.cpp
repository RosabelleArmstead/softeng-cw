#include "Dog.h"

Dog::Dog(string name, string breed, string colour, string earType,
         string height, string tailColour, const Dog* father, const Dog* mother)
        : Animal(name, breed, colour, earType, height, tailColour),
          pFather(father), pMother(mother) { }
