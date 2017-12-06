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
    Cat(string name, string breed, string colour, string earType, int height,
        string tailColour, Cat* father, Cat* mother);
    // purpose -- to create a new Cat object
    // input -- The different attributes that an Cat must have including name, breed, colour, Ear Type, height and Tail Colour, father and mother
    // output -- None
    virtual ~Cat();
    // purpose -- To free up all memory used by an unneeded Cat object
    // input -- None
    // output -- None

    inline Animal* getFather() { return pFather; }
    inline Animal* getMother() { return pMother; }
    inline const string getAnimalType() { return "Cat"; }

};
#endif
