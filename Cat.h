// Cat.h
#ifndef CAT
#define CAT
#include "Animal.h"

// Cat is a derived class of Animal.
class Cat : public Animal {
  private:
    // A Cat may only have a father pointer and a mother pointer of type Cat.
    const Cat *pFather;
    const Cat *pMother;

  public:
    // Input   : All strings required for Animal and pointers for father and mother Cats, if applicable.
    // Purpose : Used to create a new Cat object.
    // Output  : None.
    // Return  : N/A.
    // Throw   : None.
    Cat(string name, string breed, string colour, string earType, string height, string tailColour,
        const Cat* father, const Cat* mother);

    // Input   : None.
    // Purpose : Gets father pointer as const. Variable pointer, const value. Used by Animal's
    //           printPaternalTree method.
    // Output  : None.
    // Return  : Father pointer of type Cat. May be null.
    // Throw   : None.
    inline const Animal* getFather() const { return pFather; }

    // Input   : None.
    // Purpose : Gets mother pointer as const. Variable pointer, const value.
    // Output  : None.
    // Return  : Mother pointer of type Cat. May be null.
    // Throw   : None.
    inline const Animal* getMother() const { return pMother; }

    // Input   : None.
    // Purpose : Gets Animal's type as string for printing purposes.
    // Output  : None.
    // Return  : Const string describing the Animal's type--Cat.
    // Throw   : None.
    inline const string getAnimalType() const { return "Cat"; }
};

#endif
