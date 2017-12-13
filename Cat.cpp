#include "Cat.h"

Cat::Cat(string name, string breed, string colour, string earType,
         string height, string tailColour, Cat* father, Cat* mother)
        : Animal(name, breed, colour, earType, height, tailColour),
          pFather(father), pMother(mother) { }
