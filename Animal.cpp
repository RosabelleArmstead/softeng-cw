#include <string>
#include "Animal.h"
using namespace std;

Animal::Animal(string param_name, string param_breed, string param_colour, string param_earType, int param_height, string param_tailColour){
  name = param_name;
  breed = param_breed;
  colour = param_colour;
  earType = param_earType;
  height = param_height;
  tailColour = param_tailColour;
}

inline string Animal::getName() { return name; }

inline string Animal::getBreed() { return breed; }

inline string Animal::getColour() { return colour; }

inline string Animal::getEarType() { return earType; }

inline string Animal::getHeight() { return height; }

inline string Animal::getTailColour() { return tailColour; }