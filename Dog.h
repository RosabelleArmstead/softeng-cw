//Dog.h
#ifndef DOG
#define DOG
#include "Animal.h"
#include <string>

class Dog : public Animal {
  private:
    Dog *pFather;
    Dog *pMother;

  public:
    Dog(string name, string breed, string colour, string earType, string height,
        string tailColour, Dog* father, Dog* mother);
    // purpose -- to create a new Dog object
    // input -- The different attributes that an Dog must have including name, breed, colour, Ear Type, height and Tail Colour, father and mother
    // output -- None

    inline Animal* getFather() const { return pFather; }
    // purpose -- to return a pointer to the father of the dog
    // input -- None
    // output -- A pointer to the Dog's father

    inline Animal* getMother() const { return pMother; }
    // purpose -- to return a pointer to the mother of the Dog
    // input -- None
    // output --  A pointer to the Dog's mother

    inline const string getAnimalType() const { return "Dog"; }
    // purpose -- to return the animal type to easily display when needed
    // input -- None
    // output -- A string naming the animal type 'Dog'

};

#endif
