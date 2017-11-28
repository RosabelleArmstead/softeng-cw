//Cat.h
#ifndef _CAT_
#define _CAT_

#include <string>
using namespace std;

class Cat : public Animal{
private:
  Cat *pFather = NULL;
  Cat *pMother = NULL;

public:
  Cat(string name, string breed, string colour, string earType, int height, string tailColour, string father, string mother);
  // purpose -- to create a new Cat object
  // input -- The different attributes that an Cat must have including name, breed, colour, Ear Type, height and Tail Colour, father and mother
  // output -- None
  virtual ~Cat();
  // purpose -- To free up all memory used by an unneeded Cat object
  // input -- None
  // output -- None

  void setFather(string father);
  // purpose -- To set the father of a cat
  // input -- the name of the father
  // output -- None
  *string getFather();
  // purpose -- To get the father of a cat
  // input -- None
  // output -- a pointer to the father

  void setMother(string mother);
  // purpose -- To set the mother of a cat
  // input -- the name of the father
  // output -- None
  *string getMother();
  // purpose -- To get the mother of a cat
  // input -- the name of the mother
  // output -- None

};
#endif
