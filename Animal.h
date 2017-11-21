//Animal.h
#ifndef _ANIMAL_
#define _ANIMAL_

#include <String>
using namespace std;

class Animal {
private:
  String name;
  String breed;
  String colour;
  String earType;
  double height;
  String tailColour;

public:
  Animal(String name, String breed, String colour, String earType, double height, String tailColour);
  // purpose -- to create a new Animal object
  // input -- The different attributes that an Animal must have including name, breed, colour, Ear Type, height and Tail Colour
  // output -- None

  virtual ~Animal();
  // purpose -- To free up all memory used by an unneeded Animal object
  // input -- None
  // output -- None

  void setName(String name);
  // purpose -- to set the name of an Animal
  // input -- the name of the Animal
  // output -- None
  String getName();
  // purpose -- to get the name of an Animal
  // input -- None
  // output -- the name of the Animal

  void setBreed(String breed);
  // purpose -- to set the breed of an Animal
  // input -- the breed of the Animal
  // output -- None
  String getBreed();
  // purpose -- to get the breed of an Animal
  // input -- None
  // output -- the breed of the Animal

  void setColour(String colour);
  // purpose -- to set the colour of an Animal
  // input -- the colour of the Animal
  // output -- None
  String getColour();
  // purpose -- to get the colour of an Animal
  // input -- None
  // output -- the colour of the Animal

  void setEarType(String earType);
  // purpose -- to set the Ear Type of an Animal
  // input -- the Ear Type of the Animal
  // output -- None
  String getEarType();
  // purpose -- to get the Ear Type of an Animal
  // input -- None
  // output -- the Ear Type of the Animal

  void setHeight(double height);
  // purpose -- to set the height of an Animal
  // input -- the height of the Animal
  // output -- None
  double getHeight();
  // purpose -- to get the height of an Animal
  // input -- None
  // output -- the height of the Animal

  void setTailColour(String tailColour);
  // purpose -- to set the tail colour of an Animal
  // input -- the tail colour of the Animal
  // output -- None
  String getTailColour();
  // purpose -- to get the tail colour of an Animal
  // input -- None
  // output -- the tail colour of the Animal
};

#endif
