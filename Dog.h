// Dog.h
#ifndef DOG
#define DOG
#include "Animal.h"

// Dog is a derived class of Animal.
class Dog : public Animal {
  private:
    // A Dog may only have a father pointer and a mother pointer of type Dog.
    const Dog *pFather;
    const Dog *pMother;

  public:
    // Input   : All strings required for Animal and pointers for father and mother Dogs, if applicable.
    // Purpose : Used to create a new Dog object.
    // Output  : None.
    // Return  : N/A.
    // Throw   : None.
    Dog(string name, string breed, string colour, string earType, string height, string tailColour,
        const Dog* father, const Dog* mother);

    // Input   : None.
    // Purpose : Gets father pointer as const. Variable pointer, const value. Used by Animal's
    //           printPaternalTree method.
    // Output  : None.
    // Return  : Father pointer of type Dog. May be null.
    // Throw   : None.
    inline const Dog* getFather() const { return pFather; }

    // Input   : None.
    // Purpose : Gets mother pointer as const. Variable pointer, const value.
    // Output  : None.
    // Return  : Mother pointer of type Dog. May be null.
    // Throw   : None.
    inline const Dog* getMother() const { return pMother; }

    // Input   : None.
    // Purpose : Gets Animal's type as string for printing purposes.
    // Output  : None.
    // Return  : Const string describing the Animal's type--Dog.
    // Throw   : None.
    inline const string getAnimalType() const { return "Dog"; }
};

#endif
