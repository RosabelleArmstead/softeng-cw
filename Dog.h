//Dog.h
#ifndef _DOG_
#define _DOG_

#include <String>
using namespace std;

class Dog {
private:
  String *pFather = NULL;
  String *pMother = NULL;

public:
  Dog(String name, String breed, String colour, String earType, double height, String tailColour, String father, String mother);
  // purpose -- to create a new Dog object
  // input -- The different attributes that an Dog must have including name, breed, colour, Ear Type, height and Tail Colour, father and mother
  // output -- None
  virtual ~Dog();
  // purpose -- To free up all memory used by an unneeded Dog object
  // input -- None
  // output -- None

  void setFather(String father);
  // purpose -- To set the father of a dog
  // input -- the name of the father
  // output -- None
  *String getFather();
  // purpose -- To get the father of a dog
  // input -- None
  // output -- a pointer to the father

  void setMother(String mother);
  // purpose -- To set the mother of a dog
  // input -- the name of the father
  // output -- None
  *String getMother();
  // purpose -- To get the mother of a dog
  // input -- the name of the mother
  // output -- None

};
#endif
