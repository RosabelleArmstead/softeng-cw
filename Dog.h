//Dog.h
#ifndef DOG
#define DOG
#include "Animal.h"
#include <string>

class Dog : public Animal {
  private:
    // A dog can have a mother and a father of type Dog
    const Dog *pFather;
    const Dog *pMother;

  public:
    // purpose -- to create a new Dog object
    // input -- The different attributes that an Dog must have including name, breed, colour, Ear Type, height and Tail Colour, father and mother
    // output -- None
    Dog(string name, string breed, string colour, string earType, string height,
        string tailColour, const Dog* father, const Dog* mother);

    // purpose -- to return a pointer to the father of the dog
    // input -- None
    // output -- A pointer to the Dog's father
    inline const Animal* getFather() const { return pFather; }

    // purpose -- to return a pointer to the mother of the Dog
    // input -- None
    // output --  A pointer to the Dog's mother
    inline const Animal* getMother() const { return pMother; }

    // purpose -- to return the animal type to easily display when needed
    // input -- None
    // output -- A string naming the animal type 'Dog'
    inline const string getAnimalType() const { return "Dog"; }

};

#endif
