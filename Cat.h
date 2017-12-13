//Cat.h
#ifndef CAT
#define CAT
#include "Animal.h"
#include <string>

using namespace std;

class Cat : public Animal {
  private:
    Cat *pFather;
    Cat *pMother;

  public:
    Cat(string name, string breed, string colour, string earType, string height,
        string tailColour, Cat* father, Cat* mother);
    // purpose -- to create a new Cat object
    // input -- The different attributes that an Cat must have including name, breed, colour, Ear Type, height and Tail Colour, father and mother
    // output -- None

    inline Animal* getFather() const { return pFather; }
    // purpose -- to return a pointer to the father of the cat
    // input -- None
    // output -- A pointer to the Cat's father

    inline Animal* getMother() const { return pMother; }
    // purpose -- to return a pointer to the mother of the Cat
    // input -- None
    // output --  A pointer to the Cat's mother

    inline const string getAnimalType() const { return "Cat"; }
    // purpose -- to return the animal type to easily display when needed
    // input -- None
    // output -- A string naming the animal type 'Cat'

};

#endif
