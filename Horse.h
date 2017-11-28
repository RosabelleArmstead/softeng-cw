//Horse.h
#ifndef _HORSE_
#define _HORSE_

#include <string>
using namespace std;

class Horse : public Animal{
private:
  Horse *pFather = NULL;
  Horse *pMother = NULL;

public:
  Horse(string name, string breed, string colour, string earType, int height, string tailColour, string father, string mother);
  // purpose -- to create a new Horse object
  // input -- The different attributes that an Horse must have including name, breed, colour, Ear Type, height and Tail Colour, father and mother
  // output -- None
  virtual ~Horse();
  // purpose -- To free up all memory used by an unneeded Horse object
  // input -- None
  // output -- None

  void setFather(const Horse father);
  // purpose -- To set the father of a horse
  // input -- the name of the father
  // output -- None
  *Horse getFather();
  // purpose -- To get the father of a horse
  // input -- None
  // output -- a pointer to the father

  void setMother(const Horse mother);
  // purpose -- To set the mother of a horse
  // input -- the name of the father
  // output -- None
  *Horse getMother();
  // purpose -- To get the mother of a horse
  // input -- the name of the mother
  // output -- None

};
#endif
