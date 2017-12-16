// Horse.h
#ifndef HORSE
#define HORSE
#include "Animal.h"

// Horse is a derived class of Animal.
class Horse : public Animal {
  private:
    // A Horse may only have a father pointer and a mother pointer of type Horse.
    const Horse *pFather;
    const Horse *pMother;

  public:
    // Input   : All strings required for Animal and pointers for father and mother Horses, if applicable.
    // Purpose : Used to create a new Horse object.
    // Output  : None.
    // Return  : N/A.
    // Throw   : None.
    Horse(string name, string breed, string colour, string earType, string height, string tailColour,
          const Horse* father, const Horse* mother);

    // Input   : None.
    // Purpose : Gets father pointer as const. Variable pointer, const value. Used by Animal's
    //           printPaternalTree method.
    // Output  : None.
    // Return  : Father pointer of type Horse. May be null.
    // Throw   : None.
    inline const Animal* getFather() const { return pFather; }

    // Input   : None.
    // Purpose : Gets mother pointer as const. Variable pointer, const value.
    // Output  : None.
    // Return  : Mother pointer of type Horse. May be null.
    // Throw   : None.
    inline const Animal* getMother() const { return pMother; }

    // Input   : None.
    // Purpose : Gets Animal's type as string for printing purposes.
    // Output  : None.
    // Return  : Const string describing the Animal's type--Horse.
    // Throw   : None.
    inline const string getAnimalType() const { return "Horse"; }
};

#endif
