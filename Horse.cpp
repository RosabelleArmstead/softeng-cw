#include "Horse.h"

// Constructor to allow a Horse to be instantiated. Passes six of the attributes
// to the base class Animal to be saved.
Horse::Horse(string name, string breed, string colour, string earType,
             string height, string tailColour, const Horse* father, const Horse* mother)
            : Animal(name, breed, colour, earType, height, tailColour),
              pFather(father), pMother(mother) { }
