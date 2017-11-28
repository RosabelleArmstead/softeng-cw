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

  void setName(string name);
  // purpose -- to set the name of an Animal
  // input -- the name of the Animal
  // output -- None
  string getName();
  // purpose -- to get the name of an Animal
  // input -- None
  // output -- the name of the Animal

  void setBreed(string breed);
  // purpose -- to set the breed of an Animal
  // input -- the breed of the Animal
  // output -- None
  string getBreed();
  // purpose -- to get the breed of an Animal
  // input -- None
  // output -- the breed of the Animal

  void setColour(string colour);
  // purpose -- to set the colour of an Animal
  // input -- the colour of the Animal
  // output -- None
  string getColour();
  // purpose -- to get the colour of an Animal
  // input -- None
  // output -- the colour of the Animal

  void setEarType(string earType);
  // purpose -- to set the Ear Type of an Animal
  // input -- the Ear Type of the Animal
  // output -- None
  string getEarType();
  // purpose -- to get the Ear Type of an Animal
  // input -- None
  // output -- the Ear Type of the Animal

  void setHeight(double height);
  // purpose -- to set the height of an Animal
  // input -- the height of the Animal
  // output -- None
  int getHeight();
  // purpose -- to get the height of an Animal
  // input -- None
  // output -- the height of the Animal

  void setTailColour(string tailColour);
  // purpose -- to set the tail colour of an Animal
  // input -- the tail colour of the Animal
  // output -- None
  string getTailColour();
  // purpose -- to get the tail colour of an Animal
  // input -- None
  // output -- the tail colour of the Animal
};

#endif
