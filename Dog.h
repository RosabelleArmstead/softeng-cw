//Dog.h
#ifndef _DOG_
#define _DOG_

#include <string>
using namespace std;

class Dog : public Animal{
private:
  string *pFather = NULL;
  string *pMother = NULL;

public:
  Dog(string name, string breed, string colour, string earType, double height, string tailColour, string father, string mother);
  // purpose -- to create a new Dog object
  // input -- The different attributes that an Dog must have including name, breed, colour, Ear Type, height and Tail Colour, father and mother
  // output -- None
  virtual ~Dog();
  // purpose -- To free up all memory used by an unneeded Dog object
  // input -- None
  // output -- None

  void setFather(string father);
  // purpose -- To set the father of a dog
  // input -- the name of the father
  // output -- None
  *string getFather();
  // purpose -- To get the father of a dog
  // input -- None
  // output -- a pointer to the father

  void setMother(string mother);
  // purpose -- To set the mother of a dog
  // input -- the name of the father
  // output -- None
  *string getMother();
  // purpose -- To get the mother of a dog
  // input -- the name of the mother
  // output -- None

};
#endif
