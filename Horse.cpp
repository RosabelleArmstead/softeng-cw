#include "Horse.h"

Horse::Horse(string name, string breed, string colour, string earType,
             string height, string tailColour, Horse* father, Horse* mother)
            : Animal(name, breed, colour, earType, height, tailColour),
              pFather(father), pMother(mother) { }
