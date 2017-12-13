#include "Cat.h"

Cat::Cat(string name, string breed, string colour, string earType,
         string height, string tailColour, const Cat* father, const Cat* mother)
        : Animal(name, breed, colour, earType, height, tailColour),
          pFather(father), pMother(mother) { }
