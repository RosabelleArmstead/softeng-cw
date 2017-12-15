//Horse.h
#ifndef HORSE
#define HORSE
#include "Animal.h"
#include <string>

class Horse : public Animal {
  private:
    // A horse can have a mother and a father of type Horse
    const Horse *pFather;
    const Horse *pMother;

  public:
    // purpose -- to create a new Horse object
    // input -- The different attributes that an Horse must have including name, breed, colour, Ear Type, height and Tail Colour, father and mother
    // output -- None
    Horse(string name, string breed, string colour, string earType, string height,
        string tailColour, const Horse* father, const Horse* mother);

    // purpose -- to return a pointer to the father of the horse
    // input -- None
    // output -- A pointer to the Horse's father
    inline const Animal* getFather() const { return pFather; }

    // purpose -- to return a pointer to the mother of the Horse
    // input -- None
    // output --  A pointer to the Horse's mother
    inline const Animal* getMother() const { return pMother; }

    // purpose -- to return the animal type to easily display when needed
    // input -- None
    // output -- A string naming the animal type 'Horse'
    inline const string getAnimalType() const { return "Horse"; }

};

#endif
