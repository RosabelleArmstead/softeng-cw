//Cat.h
#ifndef CAT
#define CAT
#include "Animal.h"
#include <string>

using namespace std;

class Cat : public Animal {
  private:
    // A cat can have a mother and a father of type Cat
    const Cat *pFather;
    const Cat *pMother;

  public:

    // purpose -- to create a new Cat object
    // input -- The different attributes that an Cat must have including name, breed, colour, Ear Type, height and Tail Colour, father and mother
    // output -- None
    Cat(string name, string breed, string colour, string earType, string height,
        string tailColour, const Cat* father, const Cat* mother);

    // purpose -- to return a pointer to the father of the cat
    // input -- None
    // output -- A pointer to the Cat's father
    inline const Animal* getFather() const { return pFather; }

    // purpose -- to return a pointer to the mother of the Cat
    // input -- None
    // output --  A pointer to the Cat's mother
    inline const Animal* getMother() const { return pMother; }

    // purpose -- to return the animal type to easily display when needed
    // input -- None
    // output -- A string naming the animal type 'Cat'
    inline const string getAnimalType() const { return "Cat"; }

};

#endif
