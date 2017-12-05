//Animal.h
#ifndef _ANIMAL_
#define _ANIMAL_

#include <string>
using namespace std;

class Animal {
private:
  string name = new string;
  string breed = new string;
  string colour = new string;
  string earType = new string;
  int height = new int;
  string tailColour = new string;

public:
  Animal(string param_name, string param_breed, string param_colour, string param_earType, double param_height, string param_tailColour);
  // purpose -- to create a new Animal object
  // input -- The different attributes that an Animal must have including name, breed, colour, Ear Type, height and Tail Colour
  // output -- None

  virtual ~Animal();
  // purpose -- To free up all memory used by an unneeded Animal object
  // input -- None
  // output -- None

  string getName();
  // purpose -- to get the name of an Animal
  // input -- None
  // output -- the name of the Animal

  string getBreed();
  // purpose -- to get the breed of an Animal
  // input -- None
  // output -- the breed of the Animal

  string getColour();
  // purpose -- to get the colour of an Animal
  // input -- None
  // output -- the colour of the Animal

  string getEarType();
  // purpose -- to get the Ear Type of an Animal
  // input -- None
  // output -- the Ear Type of the Animal

  int getHeight();
  // purpose -- to get the height of an Animal
  // input -- None
  // output -- the height of the Animal

  string getTailColour();
  // purpose -- to get the tail colour of an Animal
  // input -- None
  // output -- the tail colour of the Animal

  virtual void setFather(const Animal param_father);
  virtual const Animal getFather();
  virtual Animal setMother(const Animal param_mother);
  virtual const Animal getMother();
};

#endif
