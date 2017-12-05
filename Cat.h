//Cat.h
#ifndef CAT
#define CAT

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

  //void setFather(const Cat father);
  // purpose -- To set the father of a cat
  // input -- the name of the father
  // output -- None
  //*Cat getFather();
  // purpose -- To get the father of a cat
  // input -- None
  // output -- a pointer to the father

  //void setMother(const Cat mother);
  // purpose -- To set the mother of a cat
  // input -- the name of the father
  // output -- None
  //*Cat getMother();
  // purpose -- To get the mother of a cat
  // input -- the name of the mother
  // output -- None

};
#endif
