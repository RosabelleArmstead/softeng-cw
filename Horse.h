//Horse.h
#ifndef HORSE
#define HORSE
#include "Animal.h"
#include <string>

class Horse : public Animal {
  private:
    Horse *pFather;
    Horse *pMother;

  public:
    Horse(string name, string breed, string colour, string earType, string height,
        string tailColour, Horse* father, Horse* mother);
    // purpose -- to create a new Horse object
    // input -- The different attributes that an Horse must have including name, breed, colour, Ear Type, height and Tail Colour, father and mother
    // output -- None

    inline Animal* getFather() const { return pFather; }
    // purpose -- to return a pointer to the father of the horse
    // input -- None
    // output -- A pointer to the Horse's father

    inline Animal* getMother() const { return pMother; }
    // purpose -- to return a pointer to the mother of the Horse
    // input -- None
    // output --  A pointer to the Horse's mother

    inline const string getAnimalType() const { return "Horse"; }
    // purpose -- to return the animal type to easily display when needed
    // input -- None
    // output -- A string naming the animal type 'Horse'

};

#endif
